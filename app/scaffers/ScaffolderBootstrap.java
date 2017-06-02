package scaffers;

public class ScaffolderBootstrap extends ScaffolderImpl {

	public ScaffolderBootstrap(Class<?> clazz) {
		super(clazz);
	}

	@Override
	protected String getFieldsTemplate() {
		return "field.template.bootstrap.html";
	}

	@Override
	public String alertClases() {
		return "alert alert-danger";
	}

}
