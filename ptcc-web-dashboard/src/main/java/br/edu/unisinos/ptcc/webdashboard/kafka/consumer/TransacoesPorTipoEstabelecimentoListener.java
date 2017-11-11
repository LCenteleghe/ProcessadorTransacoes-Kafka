package br.edu.unisinos.ptcc.webdashboard.kafka.consumer;

import java.util.AbstractMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import br.edu.unisinos.ptcc.model.TipoEstabelecimento;

@Component
public class TransacoesPorTipoEstabelecimentoListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransacoesPorTipoEstabelecimentoListener.class);

	@Value(value = "${websocket.mensagens.valor-transacoes-por-tipo-estabelecimento}")
	private String destinationMessagesOut;

	@Autowired
	private SimpMessagingTemplate simpleMessageTemplate;

	@KafkaListener(topics = "${kafka.topics.valor-transacoes-por-tipo-estabelecimento}", containerFactory = "transacoesPorTipoEstabelecimentoListenerContainerFactory")
	public void receive(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) TipoEstabelecimento tipoEstabelecimento, @Payload Double count) {
		LOGGER.info("Transacao recebida='{}'", tipoEstabelecimento);
		simpleMessageTemplate.convertAndSend(destinationMessagesOut, new AbstractMap.SimpleEntry<>(tipoEstabelecimento, count));
	}
}
