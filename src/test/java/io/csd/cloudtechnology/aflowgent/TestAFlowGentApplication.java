package io.csd.cloudtechnology.aflowgent;

import org.springframework.boot.SpringApplication;

public class TestAFlowGentApplication {

	public static void main(String[] args) {
		SpringApplication.from(AFlowGentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
