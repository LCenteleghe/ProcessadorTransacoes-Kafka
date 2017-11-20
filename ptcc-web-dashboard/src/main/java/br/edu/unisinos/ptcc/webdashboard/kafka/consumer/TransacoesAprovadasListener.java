package br.edu.unisinos.ptcc.webdashboard.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import br.edu.unisinos.ptcc.model.TransacaoCartao;


@Component
public class TransacoesAprovadasListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransacoesAprovadasListener.class);

	@Value(value = "${websocket.mensagens.transacoes-aprovadas}")
	private String destinationMessagesOut;

	@Autowired
	private SimpMessagingTemplate simpleMessageTemplate;

	@KafkaListener(topics = "${kafka.topics.transacoes-aprovadas}", containerFactory = "transacoesCartoesListenerContainerFactory")
	public void receive(TransacaoCartao transacao) {
		LOGGER.info("Transacao aprovada recebida='{}'", transacao);
		simpleMessageTemplate.convertAndSend(destinationMessagesOut, transacao);
	}
}
