package af.asr.coap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"af.asr.coap.web.*"})
public class CoapApplication {
	public static void main(String[] args) {
		SpringApplication.run(CoapApplication.class, args);
	}

}
