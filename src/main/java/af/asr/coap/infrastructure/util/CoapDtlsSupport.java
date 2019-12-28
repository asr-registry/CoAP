package af.asr.coap.infrastructure.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import af.asr.coap.infrastructure.CoapShellProperties;
import org.eclipse.californium.scandium.DTLSConnector;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.pskstore.StaticPskStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CoapDtlsSupport {

    private CoapShellProperties properties;

    @Autowired
    public CoapDtlsSupport(CoapShellProperties properties) {
        this.properties = properties;
    }

    public DTLSConnector createConnector(String identity, String preSharedKey) {

        DTLSConnector dtlsConnector = null;
        try {
            // load Java trust store
            Certificate[] trustedCertificates = loadTrustStore();

            // load client key store
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(getInputStream(this.properties.getKeyStoreLocation()), this.properties.getKeyStorePassword().toCharArray());

            // Build DTLS config
            DtlsConnectorConfig.Builder builder = new DtlsConnectorConfig.Builder()
                    .setAddress(new InetSocketAddress(0))
                    .setIdentity((PrivateKey) keyStore.getKey(this.properties.getKeyStoreAlias(), this.properties.getKeyStorePassword().toCharArray()),
                            keyStore.getCertificate(this.properties.getKeyStoreAlias()).getPublicKey())
                    .setTrustStore(trustedCertificates)
                    .setMaxConnections(100)
                    .setStaleConnectionThreshold(properties.getStaleConnectionThreshold());

            if (StringUtils.hasText(identity) && StringUtils.hasText(preSharedKey)) {
                builder.setPskStore(new StaticPskStore(identity, preSharedKey.getBytes()));
            }

            // Create DTLS endpoint
            dtlsConnector = new DTLSConnector(builder.build());
            dtlsConnector.setRawDataReceiver(raw -> System.out.println("Received response: " + new String(raw.getBytes())));
        }
        catch (Exception e) {
            System.err.println("Error creating DTLS endpoint");
            e.printStackTrace();
        }

        return dtlsConnector;
    }

    private Certificate[] loadTrustStore() throws Exception {
        // load client key store
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(getInputStream(this.properties.getTrustStoreLocation()), this.properties.getTrustStorePassword().toCharArray());

        // load trustStore
        TrustManagerFactory trustMgrFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustMgrFactory.init(trustStore);
        TrustManager trustManagers[] = trustMgrFactory.getTrustManagers();
        X509TrustManager defaultTrustManager = null;

        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                defaultTrustManager = (X509TrustManager) trustManager;
            }
        }

        return (defaultTrustManager == null) ? null : defaultTrustManager.getAcceptedIssuers();
    }

    private InputStream getInputStream(String resourceUri) throws IOException {
        return new DefaultResourceLoader().getResource(resourceUri).getInputStream();
    }

}