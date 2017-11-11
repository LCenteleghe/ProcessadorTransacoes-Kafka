package br.edu.unisinos.ptcc.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.ptcc.model.TransacaoCartao;
import br.edu.unisinos.ptcc.service.kafka.producer.TransacoesCartoesKafkaProducer;

@RestController
public class TransacoesCartoesController {
	
	@Autowired
	private TransacoesCartoesKafkaProducer transacoesCartoesProducer;

	@RequestMapping(value = "/transacoes-cartoes", method = RequestMethod.POST)
	public ResponseEntity<TransacaoCartao> registrar(@RequestBody TransacaoCartao transacaoCartao) {
		transacoesCartoesProducer.send(transacaoCartao);		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
