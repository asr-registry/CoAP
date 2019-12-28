package af.asr.coap.command;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import af.asr.coap.infrastructure.util.Row;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

@ShellComponent
@ShellCommandGroup(CoapShellCommands.SHELL_COAP_REST_COMMANDS_GROUP)
public class CoapInfoCommands {

    @ShellMethod(key = "mime types", value = "List supported MIME types")
    public Table mimeTypes() {

        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("column[0]", "Type Id");
        headers.put("column[1]", "Type Name");

        List<Row> list = MediaTypeRegistry.getAllMediaTypes().stream()
                .map(i -> {
                    Row row = new Row();
                    row.getColumn().add("" + i);
                    row.getColumn().add(MediaTypeRegistry.toString(i));
                    return row;
                }).collect(Collectors.toList());

        TableModel model = new BeanListTableModel(list, headers);
        TableBuilder tableBuilder = new TableBuilder(model);
        return tableBuilder.addHeaderAndVerticalsBorders(BorderStyle.fancy_light).build();
    }
}