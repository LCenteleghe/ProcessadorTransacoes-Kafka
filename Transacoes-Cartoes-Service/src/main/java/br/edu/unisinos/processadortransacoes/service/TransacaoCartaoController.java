package br.edu.unisinos.processadortransacoes.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.processadortransacoes.brokerconnection.KafkaConfigs;
import br.edu.unisinos.processadortransacoes.model.TransacaoCartao;

@RestController
public class TransacaoCartaoController {

	@RequestMapping(value = "/transacoes-cartoes", method = RequestMethod.POST)
	public ResponseEntity<TransacaoCartao> registrar(@RequestBody TransacaoCartao transacaoCartao) {
		enviarTransacaoParaTopicoKafka(transacaoCartao);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	protected void enviarTransacaoParaTopicoKafka(TransacaoCartao transacaoCartao) {
		createKafkaProducer().send(createProducerRecordFor(transacaoCartao));
	}

	protected ProducerRecord<Long, TransacaoCartao> createProducerRecordFor(
			TransacaoCartao transacaoCartao) {
		return new ProducerRecord<>(
				KafkaConfigs.getNomeTopicoTransacoesCartoes(),
				transacaoCartao.getCodigo(),
				transacaoCartao);
	}

	protected KafkaProducer<Long, TransacaoCartao> createKafkaProducer() {
		return new KafkaProducer<>(
				KafkaConfigs.getProperties());
	}
}
