package af.asr.coap.provider;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class DiscoveryQueryValueProvider extends StringListValueProvider {

    public DiscoveryQueryValueProvider() {
        super(Arrays.asList("href", "rt", "ct", "es", "obs"));
    }
}