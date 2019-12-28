package af.asr.coap.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.californium.core.coap.MediaTypeRegistry;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

@Component
public class ContentTypeValueProvider extends ValueProviderSupport {

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter,
                                             CompletionContext completionContext, String[] hints) {

        return MediaTypeRegistry.getAllMediaTypes().stream()
                .map(id -> MediaTypeRegistry.toString(id))
                .filter(contentType -> contentType.startsWith(prefix(completionContext)))
                .map(contentType -> new CompletionProposal(contentType))
                .collect(Collectors.toList());
    }

    private String prefix(CompletionContext completionContext) {
        final String prefix = completionContext.currentWordUpToCursor();
        return (prefix != null) ? prefix : "";
    }
}