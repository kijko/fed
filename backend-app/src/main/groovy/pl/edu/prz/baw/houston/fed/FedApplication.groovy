package pl.edu.prz.baw.houston.fed

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class FedApplication {

	static void main(String[] args) {
		SpringApplication.run(FedApplication, args)
	}

}
