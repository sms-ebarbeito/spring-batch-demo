package com.labot.demo;

import com.labot.demo.repository.jpa.ImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BatchApplication {

	@Autowired
	private ImportRepository importRepository;

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

}
