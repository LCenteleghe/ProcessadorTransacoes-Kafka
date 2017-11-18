package br.edu.unisinos.ptcc.geradorestatisticas;

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
        Properties config = new Properties();
        config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TransacaoCartaoSerdes.class);
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 5000);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, TransacaoCartao> transacoesCartoes = builder.stream("transacoes-cartoes");

        transacoesCartoes
                .groupBy((k, v) -> v.getEstabelecimento().getTipoEstabelecimento().name())
                .aggregate(
                        () -> 0.0,
                        (k, transacao, totalParcial) -> transacao.getValor() + totalParcial,
                        Materialized.with(Serdes.String(), Serdes.Double()))
                .toStream()
                .to("valor-transacoes-por-tipo-estabelecimento",
                        Produced.with(Serdes.String(), Serdes.Double()));

        KStream<String, TransacaoCartao> transacoesAprovadas = builder.stream("transacoes-aprovadas");
        transacoesAprovadas
                .groupBy((k, v) -> "transacoes-aprovadas")
                .count()
                .toStream()
                .to("total-aprovadas-reprovadas",
                        Produced.with(Serdes.String(), Serdes.Long()));

        KStream<String, TransacaoCartao> transacoesReprovadas = builder.stream("transacoes-reprovadas");
        transacoesReprovadas
                .groupBy((k, v) -> "transacoes-reprovadas")
                .count()
                .toStream()
                .to("total-aprovadas-reprovadas",
                        Produced.with(Serdes.String(), Serdes.Long()));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
    }

}
