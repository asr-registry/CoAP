package af.asr.coap;

import af.asr.coap.shell.infrastructure.CoapShellProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CoapShellProperties.class)
public class CoapApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoapApplication.class, args);
	}

}
