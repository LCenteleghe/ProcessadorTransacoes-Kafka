package br.edu.unisinos.ptcc.webdashboard.kafka.consumer.config;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import br.edu.unisinos.ptcc.model.TransacaoCartao;


@EnableKafka
@Configuration
public class TransacoesCartoesConsumerConfig {

    @Value(value = "${kafka.bootstrap-servers}")
    private String bootstrapServers;

    public ConsumerFactory<String, TransacaoCartao> consumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(TransacaoCartao.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransacaoCartao> transacoesCartoesListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TransacaoCartao> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory("dashboard-transacoes"));

        return factory;
    }
}
