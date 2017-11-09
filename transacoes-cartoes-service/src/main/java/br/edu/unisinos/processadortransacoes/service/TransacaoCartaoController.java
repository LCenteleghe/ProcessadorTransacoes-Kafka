package br.edu.unisinos.processadortransacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.processadortransacoes.kafka.producer.TransacoesCartoesKafkaProducer;
import br.edu.unisinos.processadortransacoes.model.TransacaoCartao;

@RestController
public class TransacaoCartaoController {
	
	@Autowired
	private TransacoesCartoesKafkaProducer transacoesCartoesProducer;

	@RequestMapping(value = "/transacoes-cartoes", method = RequestMethod.POST)
	public ResponseEntity<TransacaoCartao> registrar(@RequestBody TransacaoCartao transacaoCartao) {
		transacoesCartoesProducer.send(transacaoCartao);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
