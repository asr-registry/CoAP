package af.asr.coap.shell.infrastructure.util;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.elements.util.StringUtil;
import org.w3c.dom.Document;

import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;


public class PrintUtils {
    /**
     * Formats a {@link Response} into a readable String representation.
     *
     * @param coapResponse
     * @return the pretty print
     */
    public static String prettyPrint(CoapResponse coapResponse, String header) {

        if (coapResponse == null) {
            return red("NULL response!");
        }

        Response r = coapResponse.advanced();

        int httpStatusCode = Integer.valueOf(r.getCode().codeClass) * 100 + Integer.valueOf(r.getCode().codeDetail);
        HttpStatus httpStatus = HttpStatus.resolve(httpStatusCode);
        String status = colorText(String.format("%s-%s", httpStatusCode, httpStatus.getReasonPhrase()), httpStatus.isError() ? AnsiColor.RED : AnsiColor.CYAN);


        String rtt = (r.getRTT() != null) ? "" + r.getRTT() : "";

        StringBuilder sb = new StringBuilder();
        sb.append(green("----------------------------------- Response -----------------------------------")).append(StringUtil.lineSeparator());
        if (StringUtils.hasText(header)) {
            sb.append(header).append(StringUtil.lineSeparator());
        }
        sb.append(String.format("MID: %d, Type: %s, Token: %s, RTT: %sms", r.getMID(), cyan(r.getType().toString()), r.getTokenString(), rtt)).append(StringUtil.lineSeparator());
        sb.append(String.format("Options: %s", r.getOptions().toString())).append(StringUtil.lineSeparator());
        sb.append(String.format("Status : %s, Payload: %dB", status, r.getPayloadSize())).append(StringUtil.lineSeparator());
        sb.append(green("................................... Payload ....................................")).append(StringUtil.lineSeparator());
        if (r.getPayloadSize() > 0 && MediaTypeRegistry.isPrintable(r.getOptions().getContentFormat())) {
            sb.append(prettyPayload(r)).append(StringUtil.lineSeparator());
        }
        sb.append(green("--------------------------------------------------------------------------------"));

        return sb.toString();
    }

    public static String prettyPayload(Response r) {
        if (r.getOptions().toString().contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            return cyan(prettyJson(r.getPayloadString()));
        }
        else if (r.getOptions().toString().contains(MimeTypeUtils.APPLICATION_XML_VALUE)) {
            return cyan(prettyXml(r.getPayloadString()));
        }
        else if (r.getOptions().toString().contains("application/link-format")) {
            return cyan(prettyLink(r.getPayloadString()));
        }
        return r.getPayloadString();
    }

    private static String prettyJson(String text) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(text, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        }
        catch (IOException io) {
            return text;
        }
    }

    private static String prettyLink(String text) {
        try {
            StringBuffer sb = new StringBuffer();
            for (String link : text.split(",")) {
                sb.append(link).append(StringUtil.lineSeparator());
            }
            return sb.toString();
        }
        catch (Exception io) {
            return text;
        }
    }

    private static String prettyXml(String text) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(text);

            Transformer tform = TransformerFactory.newInstance().newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "yes");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StringWriter sw = new StringWriter();
            tform.transform(new DOMSource(document), new StreamResult(sw));
            return sw.toString();
        }
        catch (Exception e) {
            return text;
        }
    }

    public static String cyan(String text) {
        return colorText(text, AnsiColor.CYAN);
    }

    public static String red(String text) {
        return colorText(text, AnsiColor.RED);
    }

    public static String green(String text) {
        return colorText(text, AnsiColor.GREEN);
    }

    public static String normal(String text) {
        return colorText(text, AnsiColor.DEFAULT);
    }

    public static String colorText(String text, AnsiColor color) {
        return AnsiOutput.toString(color, text, AnsiColor.DEFAULT);
    }
}