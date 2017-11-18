package br.edu.unisinos.ptcc.customserdes;

import br.edu.unisinos.ptcc.model.TransacaoCartao;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class TransacaoCartaoSerdes implements Serde<TransacaoCartao> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //Sem implementação.
    }

    @Override
    public void close() {
        //Sem implementação.
    }

    @Override
    public Serializer<TransacaoCartao> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<TransacaoCartao> deserializer() {
        return new JsonDeserializer<>(TransacaoCartao.class);
    }

}
