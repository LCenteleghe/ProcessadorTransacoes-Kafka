package br.edu.unisinos.ptcc.validatransacao;

import br.edu.unisinos.ptcc.customserdes.TransacaoCartaoSerdes;
import br.edu.unisinos.ptcc.model.TransacaoCartao;
import br.edu.unisinos.ptcc.utils.TransacaoUtils;
import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

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
                .filter((String k, TransacaoCartao v) -> TransacaoUtils.validaTransacao(v))
                .to("transacoes-aprovadas");

        transacoesCartoes
                .filterNot((String k, TransacaoCartao v) -> TransacaoUtils.validaTransacao(v))
                .to("transacoes-reprovadas");

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
    }

}
