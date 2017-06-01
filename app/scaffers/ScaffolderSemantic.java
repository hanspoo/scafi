package scaffers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

public class ScaffolderSemantic extends ScaffolderImpl {

	public ScaffolderSemantic(Class<?> clazz) {
		super(clazz);
	}

	@Override
	protected String getFieldsTemplate() {
		return "field.template.semantic.html";
	}

}
