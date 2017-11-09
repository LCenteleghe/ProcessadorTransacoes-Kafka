package br.edu.unisinos.processadortransacoes.webdashboard.messagelistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.edu.unisinos.processadortransacoes.model.TransacaoCartao;

@Component
public class TransacoesCartoesListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransacoesCartoesListener.class);

	@KafkaListener(topics = "${kafka.topics.transacoes-cartoes}")
	public void receive(TransacaoCartao transacao) {
		LOGGER.info("Transacao recebida='{}'", transacao);
	}
}
