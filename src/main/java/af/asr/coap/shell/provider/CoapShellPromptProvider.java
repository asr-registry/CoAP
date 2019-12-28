package af.asr.coap.shell.provider;

import af.asr.coap.shell.infrastructure.CoapConnectionStatus;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class CoapShellPromptProvider implements PromptProvider {

    private CoapConnectionStatus connectionStatus;

    @Override
    public AttributedString getPrompt() {
        if (this.connectionStatus != null && StringUtils.hasText(this.connectionStatus.getBaseUri())) {
            return new AttributedString(this.promptPrefix() + ":>",
                    AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
        }
        else {
            return new AttributedString("server-unknown:>",
                    AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
        }
    }

    @EventListener
    public void handle(CoapConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    private String promptPrefix() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.connectionStatus.getBaseUri());
        if (this.connectionStatus.getMode() != CoapConnectionStatus.RequestMode.con) {
            sb.append("[").append(this.connectionStatus.getMode().name().toUpperCase()).append("]");
        }
        if (this.connectionStatus.isObserveActivated()) {
            sb.append("[OBS]");
        }
        return sb.toString();
    }
}