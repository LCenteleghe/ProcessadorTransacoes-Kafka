package br.edu.unisinos.ptcc.geradorestatisticas;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import br.edu.unisinos.ptcc.customserdes.TransacaoCartaoSerdes;
import br.edu.unisinos.ptcc.model.TransacaoCartao;

public class Main {

	public static void main(final String[] args) throws Exception {
		Properties config = new Properties();
		config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TransacaoCartaoSerdes.class);

		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, TransacaoCartao> transacoesCartoes = builder.stream("transacoes-cartoes");

		
		transacoesCartoes
				.groupBy((k, v) -> v.getEstabelecimento().getTipoEstabelecimento().name())
				.count()
				.toStream()
				.to("estatisticas-transacoes-por-tipo-estabelecimento",
						Produced.with(Serdes.String(), Serdes.Long()));


		KafkaStreams streams = new KafkaStreams(builder.build(), config);
		streams.start();
	}

}
