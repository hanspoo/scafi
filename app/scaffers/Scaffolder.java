package scaffers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface Scaffolder {

	public static Scaffolder get(TipoScaff tipo, Class<?> clazz) {

		Scaffolder scaf = null;

		switch (tipo) {
		case BOOTSTRAP:
			scaf = new ScaffolderBootstrap(clazz);
			break;

		default:
			scaf = new ScaffolderSemantic(clazz);
			break;

		}

		return scaf;

	}

	public List<String> fieldsHtml(String instanceName);

	static List<Field> fields(Class<?> clazz) {

		Field[] fields = clazz.getFields();
		List<Field> campos = Arrays.asList(fields);

		campos = campos.stream().filter(field -> !(field.getName().matches("id|willBeSaved")
				|| java.lang.reflect.Modifier.isStatic(field.getModifiers()))).collect(Collectors.toList());

		return campos;
	}

}
