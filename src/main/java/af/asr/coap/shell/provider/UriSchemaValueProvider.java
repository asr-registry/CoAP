package af.asr.coap.shell.provider;

import java.util.Arrays;

import org.springframework.stereotype.Component;


@Component
public class UriSchemaValueProvider extends StringListValueProvider {
    public UriSchemaValueProvider() {
        super(Arrays.asList("coap://", "coaps://"));
    }
}