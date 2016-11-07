package utils;

import java.lang.reflect.Field;

public class TemplateTipoCampoBoolean extends TemplateTipoCampo {

	public TemplateTipoCampoBoolean(Field field, String nombreInstancia,
			String modo) {
		super(field, nombreInstancia, modo);
	}

	@Override
	public String htmlStringWrite() {

		String template = "<div class=\"checkbox\">\r\n  <label><input name='%s' type=\"checkbox\" value=\"\">%s</label>\r\n</div>";
		return String.format(template, this.campo, this.label);
	}

	@Override
	protected String htmlStringTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
