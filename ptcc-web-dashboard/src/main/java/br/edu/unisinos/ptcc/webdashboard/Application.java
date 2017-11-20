package br.edu.unisinos.ptcc.webdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import br.edu.unisinos.ptcc.webdashboard.kafka.consumer.TotalTransacoesAprovadasReprovadasListener;
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
	public TotalTransacoesAprovadasReprovadasListener transacoesAprovadasReprovadasListener() {
		return new TotalTransacoesAprovadasReprovadasListener();
	}
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setForceEncoding(true);
	    characterEncodingFilter.setEncoding("UTF-8");
	    registrationBean.setFilter(characterEncodingFilter);
	    return registrationBean;
	}
}