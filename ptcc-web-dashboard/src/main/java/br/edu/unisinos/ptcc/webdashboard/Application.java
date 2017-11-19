package br.edu.unisinos.ptcc.webdashboard;

import br.edu.unisinos.ptcc.webdashboard.kafka.consumer.TransacoesAprovadasReprovadasListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.edu.unisinos.ptcc.webdashboard.kafka.consumer.TransacoesCartoesListener;
import br.edu.unisinos.ptcc.webdashboard.kafka.consumer.TransacoesPorTipoEstabelecimentoListener;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public TransacoesCartoesListener transacoesCartoesListener() {
		return new TransacoesCartoesListener();
	}

	@Bean
	public TransacoesPorTipoEstabelecimentoListener transacoesPorTipoEstabelecimentoListener() {
		return new TransacoesPorTipoEstabelecimentoListener();
	}
        
	@Bean
	public TransacoesAprovadasReprovadasListener transacoesAprovadasReprovadasListener() {
		return new TransacoesAprovadasReprovadasListener();
	}
}