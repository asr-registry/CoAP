package af.asr.coap.shell.infrastructure;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("coap")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CoapShellProperties {

    @NotEmpty
    private String trustStoreLocation;
    @NotEmpty
    private String trustStorePassword;
    @NotEmpty
    private String keyStoreLocation;
    @NotEmpty
    private String keyStorePassword;
    @NotEmpty
    private String keyStoreAlias;
    @NotEmpty
    @PositiveOrZero
    private int staleConnectionThreshold = 24 * 60 * 60; // 24 hours (sec)


}