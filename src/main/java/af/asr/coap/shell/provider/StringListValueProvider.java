package af.asr.coap.shell.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.util.Assert;

public abstract class StringListValueProvider extends ValueProviderSupport {

    private CopyOnWriteArrayList<String> prefixHints = new CopyOnWriteArrayList();

    public StringListValueProvider() {
        this(new ArrayList<>());
    }

    public StringListValueProvider(List<String> prefixHints) {
        Assert.notNull(prefixHints, "Prefix Hints can't be null");
        this.prefixHints = new CopyOnWriteArrayList();
        this.prefixHints.addAll(prefixHints);
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter,
                                             CompletionContext completionContext, String[] hints) {

        return prefixHints.stream()
                .filter(o -> o.startsWith(prefix(completionContext)))
                .map(contentType -> new CompletionProposal(contentType))
                .collect(Collectors.toList());
    }

    private String prefix(CompletionContext completionContext) {
        final String prefix = completionContext.currentWordUpToCursor();
        return (prefix != null) ? prefix : "";
    }

    public void updatePrefixHints(List<String> hintsUpdate) {
        this.prefixHints.clear();
        this.prefixHints.addAll(hintsUpdate);
    }

    public void addPrefixHint(String hint) {
        this.prefixHints.add(hint);
    }

    public void clearHints() {
        this.prefixHints.clear();
    }
}