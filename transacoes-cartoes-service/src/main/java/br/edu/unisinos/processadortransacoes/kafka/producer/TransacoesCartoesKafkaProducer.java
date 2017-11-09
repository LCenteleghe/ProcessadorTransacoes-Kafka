package br.edu.unisinos.processadortransacoes.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import br.edu.unisinos.processadortransacoes.model.TransacaoCartao;

@Component
public class TransacoesCartoesKafkaProducer {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TransacoesCartoesKafkaProducer.class);

	@Value("${kafka.topics.transacoes-cartoes}")
	private String topicoTransacoesCartoes;

	@Autowired
	private KafkaTemplate<Long, TransacaoCartao> kafkaTemplate;

	public void send(TransacaoCartao transacaoCartao) {
		LOGGER.info(
				"Enviando mensagem='{}' para o tópico='{}'",
				transacaoCartao,
				topicoTransacoesCartoes);
		kafkaTemplate.send(topicoTransacoesCartoes, transacaoCartao.getCodigo(), transacaoCartao);
	}
}
