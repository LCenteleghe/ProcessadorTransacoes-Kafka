package br.edu.unisinos.processadortransacoes.brokerconnection;

import java.io.IOException;
import java.util.Properties;

public class KafkaConfigs {

	public static Properties getProperties(){
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("kafka.properties"));
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getNomeTopicoTransacoesCartoes(){
		return "transacoes-cartoes";
	}
	
}
