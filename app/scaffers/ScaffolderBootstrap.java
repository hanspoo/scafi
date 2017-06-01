package scaffers;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

public class ScaffolderBootstrap implements Scaffolder {

	private Class<?> clazz;

	public ScaffolderBootstrap(Class<?> clazz) {
		this.clazz = clazz;

	}

	@Override
	public List<String> fieldsHtml(String instanceName) {

		List<Field> fields = Scaffolder.fields(clazz);
		List<String> fieldsHtml = new ArrayList<>();

		for (Field field : fields) {

			String template;
			try {
				template = Files.lines(Paths.get("test/field.template.bootstrap.html")).collect(Collectors.joining(""));
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
			template = template.replaceAll("FIELD_NAME", instanceName + "." + field.getName());
			template = template.replaceAll("FIELD_LABEL", StringUtils.capitalize(field.getName()));

			fieldsHtml.add(template);

		}
		return fieldsHtml;

	}

}
