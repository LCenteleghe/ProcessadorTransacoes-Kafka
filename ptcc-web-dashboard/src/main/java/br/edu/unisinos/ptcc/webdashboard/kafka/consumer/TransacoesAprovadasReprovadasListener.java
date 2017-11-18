package br.edu.unisinos.ptcc.webdashboard.kafka.consumer;

import br.edu.unisinos.ptcc.model.TipoEstabelecimento;
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

@Component
public class TransacoesAprovadasReprovadasListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TransacoesAprovadasReprovadasListener.class);

	@Value(value = "${websocket.mensagens.total-aprovadas-reprovadas}")
	private String destinationMessagesOut;

	@Autowired
	private SimpMessagingTemplate simpleMessageTemplate;

	@KafkaListener(topics = "${kafka.topics.total-aprovadas-reprovadas}", containerFactory = "transacoesAprovadasReprovadasListenerContainerFactory")
	public void receive(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String topico, @Payload Long count) {
		LOGGER.info("Transacao recebida='{}'", topico);
		simpleMessageTemplate.convertAndSend(destinationMessagesOut, new AbstractMap.SimpleEntry<>(topico, count));
	}
    
}
