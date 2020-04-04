package com.unic.fr;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = {"com.guf.batch.data.entity", "com.unic.fr.model"})
public class EspaceUnicienBackEndApplication {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(EspaceUnicienBackEndApplication.class, args);
		
		
	}

}
