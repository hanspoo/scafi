package utils;

import java.lang.reflect.Field;

public class TemplateTipoCampoString extends TemplateTipoCampo {

	public TemplateTipoCampoString(Field field, String nombreInstancia,
			String modo) {
		super(field, nombreInstancia, modo);

	}

	@Override
	public String htmlStringWrite() {

		String template = "\t\t#{field '%s'}\r\n\t\t<div class=\"form-group  ${field.errorClass ? 'has-error' : ''}\">\r\n\t\t\t<label for=\"${field.id}\" class=\" control-label\">%s <span class=\"required\">*</span></label>  \r\n\t\t\t<input type=\"text\" class=\"form-control \" name=\"${field.name}\" id=\"${field.id}\"  "
				+ "value=\"${field.value}\">  \r\n\t\t\t<span class=\"error\">${field.error}</span>\t\r\n\t\t</div>\r\n\t\t#{/field}\r\n";
		return String.format(template, this.campo, this.label);
	}

	@Override
	protected String htmlStringTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
