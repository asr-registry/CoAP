package af.asr.coap.shell.infrastructure.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Row {
    private List<String> column = new ArrayList<>();
}