package scaffers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public abstract class ScaffolderImpl implements Scaffolder {

	private Class<?> clazz;

	public ScaffolderImpl(Class<?> clazz) {
		this.clazz = clazz;

	}

	@Override
	public List<String> fieldsHtml(String instanceName) {

		List<Field> fields = fields();
		List<String> fieldsHtml = new ArrayList<>();

		for (Field field : fields) {

			String template;
			try {

				String file = getFieldsTemplate();
				InputStream inputStream = getClass().getResourceAsStream(file);
				template = IOUtils.toString(inputStream, "UTF-8");
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
			template = template.replaceAll("FIELD_NAME", instanceName + "." + field.getName());
			template = template.replaceAll("FIELD_LABEL", StringUtils.capitalize(field.getName()));

			fieldsHtml.add(template);

		}
		return fieldsHtml;

	}

	protected abstract String getFieldsTemplate();

	protected List<Field> fields() {

		Field[] fields = clazz.getFields();
		List<Field> campos = Arrays.asList(fields);

		campos = campos.stream().filter(field -> !(field.getName().matches("id|willBeSaved")
				|| java.lang.reflect.Modifier.isStatic(field.getModifiers()))).collect(Collectors.toList());

		return campos;
	}

}
