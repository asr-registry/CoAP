package af.asr.coap.shell.infrastructure;

public class CoapConnectionStatus {

    public enum RequestMode {con, non}

    private String baseUri;
    private RequestMode mode;
    private String observedUri;
    private String identity;
    private String secret;

    public String getBaseUri() {
        return baseUri;
    }

    public CoapConnectionStatus setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public RequestMode getMode() {
        return mode;
    }

    public CoapConnectionStatus setMode(RequestMode mode) {
        this.mode = mode;
        return this;
    }

    public boolean isObserveActivated() {
        return this.observedUri != null;
    }

    public CoapConnectionStatus reset() {
        this.baseUri = null;
        this.mode = RequestMode.con;
        this.observedUri = null;
        this.identity = null;
        this.secret = null;
        return this;
    }

    public String getObservedUri() {
        return observedUri;
    }

    public CoapConnectionStatus setObservedUri(String observedUri) {
        this.observedUri = observedUri;
        return this;
    }

    public String getIdentity() {
        return identity;
    }

    public CoapConnectionStatus setIdentity(String identity) {
        this.identity = identity;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public CoapConnectionStatus setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Override
    public String toString() {
        return String.format("[%s], [%s], [%s]", baseUri, mode,
                (isObserveActivated()) ? "observable" : "non-observable");
    }
}