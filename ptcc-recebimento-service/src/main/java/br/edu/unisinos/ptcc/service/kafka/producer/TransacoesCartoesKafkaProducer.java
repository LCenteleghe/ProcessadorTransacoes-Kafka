package br.edu.unisinos.ptcc.service.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import br.edu.unisinos.ptcc.model.TransacaoCartao;

@Component
public class TransacoesCartoesKafkaProducer {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TransacoesCartoesKafkaProducer.class);

    @Value("${kafka.topics.transacoes-cartoes}")
    private String topicoTransacoesCartoes;

    @Autowired
    private KafkaTemplate<String, TransacaoCartao> kafkaTemplate;

    public void send(TransacaoCartao transacaoCartao) {
        LOGGER.info("Enviando mensagem='{}' para o tópico='{}'", transacaoCartao, topicoTransacoesCartoes);
        kafkaTemplate.send(topicoTransacoesCartoes, transacaoCartao.getCodigo(), transacaoCartao).addCallback(createCallBackListener());
    }

    private ListenableFutureCallback<? super SendResult<String, TransacaoCartao>> createCallBackListener() {
        return new ListenableFutureCallback<SendResult<String, TransacaoCartao>>() {

            @Override
            public void onSuccess(SendResult<String, TransacaoCartao> result) {
                TransacaoCartao transacaoCartaoEnviada = result.getProducerRecord().value();
                LOGGER.info("Transação enviada ao tópico com sucesso. Dados transação: {}", transacaoCartaoEnviada);
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("Um erro ocorreu ao enviar uma transação para o tópico.", ex);
            }
        };
    }
}
