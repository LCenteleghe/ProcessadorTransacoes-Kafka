package br.edu.unisinos.ptcc.geradorestatisticas.geradores;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import br.edu.unisinos.ptcc.customserdes.TransacaoCartaoSerdes;
import br.edu.unisinos.ptcc.model.TransacaoCartao;

public class Main {

	public static void main(final String[] args) throws Exception {
		Properties config = new Properties();
		config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TransacaoCartaoSerdes.class);

		StreamsBuilder builder = new StreamsBuilder();
		KStream<Long, TransacaoCartao> textLines = builder.stream("transacoes-cartoes");

		textLines.foreach((k, v) -> print(v));

		// KTable<String, Long> wordCounts = textLines
		// .flatMapValues(textLine ->
		// Arrays.asList(textLine.toLowerCase().split("\\W+")))
		// .groupBy((key, word) -> word)
		// .count(Materialized.<String, Long, KeyValueStore<Bytes,
		// byte[]>>as("counts-store"));

		// wordCounts.toStream().to("WordsWithCountsTopic",
		// Produced.with(Serdes.String(), Serdes.Long()));

		KafkaStreams streams = new KafkaStreams(builder.build(), config);
		streams.start();
	}

	private static void print(TransacaoCartao v) {
		System.out.println(v);
	}

}
