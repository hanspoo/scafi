package scaffers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class ScaffolderBootstrap extends ScaffolderImpl {

	public ScaffolderBootstrap(Class<?> clazz) {
		super(clazz);
	}

	@Override
	protected String getFieldsTemplate() {
		return "field.template.bootstrap.html";
	}




}
