package af.asr.coap.shell;

import af.asr.coap.shell.infrastructure.CoapShellProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan(basePackages = {"af.asr.coap.shell.*"})
@EnableConfigurationProperties(CoapShellProperties.class)
public class ShellApplication {
    public static  void  main(String[] args)
    {
        SpringApplication.run(ShellApplication.class,args);
    }
}
