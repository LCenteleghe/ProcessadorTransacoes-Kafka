package br.edu.unisinos.ptcc.geradorestatisticas;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import br.edu.unisinos.ptcc.customserdes.TransacaoCartaoSerdes;
import br.edu.unisinos.ptcc.model.TransacaoCartao;

public class Application {

	public static void main(final String[] args) throws Exception {
		Properties baseConfig = getBaseConfig();

		criarStreamTotalizacaoValorPorTipoEstabelecimento((Properties) baseConfig.clone()).start();
		criarStreamTotalizacaoTransacoesAprovadas((Properties) baseConfig.clone()).start();
		criarStreamTotalizacaoTransacoesReprovadas((Properties) baseConfig.clone()).start();

	}

	private static KafkaStreams criarStreamTotalizacaoValorPorTipoEstabelecimento(Properties config) {
		String baseAppId = config.getProperty(StreamsConfig.APPLICATION_ID_CONFIG);
		config.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, baseAppId + "-totalizador-por-tipo-estabelecimento");

		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, TransacaoCartao> transacoesCartoes = builder.stream("transacoes-cartoes");
		transacoesCartoes
				.groupBy((k, v) -> 
					v.getEstabelecimento().getTipoEstabelecimento() == null ? "OUTROS" 
				    :v.getEstabelecimento().getTipoEstabelecimento().name())
				.aggregate(() -> 0.0, (k, transacao, totalParcial) -> transacao.getValor() + totalParcial, Materialized.with(Serdes.String(), Serdes.Double()))
				.toStream()
				.to("valor-transacoes-por-tipo-estabelecimento", Produced.with(Serdes.String(), Serdes.Double()));

		return new KafkaStreams(builder.build(), config);
	}

	private static KafkaStreams criarStreamTotalizacaoTransacoesAprovadas(Properties config) {
		String baseAppId = config.getProperty(StreamsConfig.APPLICATION_ID_CONFIG);
		config.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, baseAppId + "-totalizador-transacoes-aprovadas");

		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, TransacaoCartao> transacoesAprovadas = builder.stream("transacoes-aprovadas");
		transacoesAprovadas.groupBy((k, v) -> "transacoes-aprovadas")
			.count()
			.toStream()
			.to("total-aprovadas-reprovadas", Produced.with(Serdes.String(), Serdes.Long()));

		return new KafkaStreams(builder.build(), config);
	}

	private static KafkaStreams criarStreamTotalizacaoTransacoesReprovadas(Properties config) {
		String baseAppId = config.getProperty(StreamsConfig.APPLICATION_ID_CONFIG);
		config.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, baseAppId + "-totalizador-transacoes-reprovadas");

		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, TransacaoCartao> transacoesReprovadas = builder.stream("transacoes-reprovadas");
		transacoesReprovadas.groupBy((k, v) -> "transacoes-reprovadas")
			.count()
			.toStream()
			.to("total-aprovadas-reprovadas", Produced.with(Serdes.String(), Serdes.Long()));

		return new KafkaStreams(builder.build(), config);
	}

	private static Properties getBaseConfig() throws IOException {
		Properties config = new Properties();
		config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TransacaoCartaoSerdes.class);
		config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 5000);
		return config;
	}
}
