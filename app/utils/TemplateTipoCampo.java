package utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;

import play.db.jpa.Model;
import play.templates.JavaExtensions;

public abstract class TemplateTipoCampo {

	Class<?> type;

	Field field;
	protected String nombreInstancia;
	protected String modo;

	protected String label, campo;

	public TemplateTipoCampo(Field field, String nombreInstancia, String modo) {
		super();
		this.field = field;
		this.nombreInstancia = nombreInstancia;
		this.modo = modo;
		label = JavaExtensions.camelCase(field.getName());
		campo = nombreInstancia + "." + field.getName();
	}

	public static TemplateTipoCampo newInstance(Field field,
			String nombreInstancia, String modo) {

		Class<?> fieldType = field.getType();

		if (Model.class.isAssignableFrom(fieldType)
				|| Collection.class.isAssignableFrom(fieldType)) {
			return new TemplateTipoCampoModelo(field, nombreInstancia, modo);
		}
		if (Date.class.isAssignableFrom(fieldType)) {
			return new TemplateTipoCampoDate(field, nombreInstancia, modo);
		}
		if (Boolean.class.isAssignableFrom(fieldType)
				|| field.getName().matches("boolean")) {
			return new TemplateTipoCampoBoolean(field, nombreInstancia, modo);
		}

		return new TemplateTipoCampoString(field, nombreInstancia, modo);

	}

	public final String htmlString() {

		if (modo.equals("w"))
			return htmlStringWrite();

		if (modo.equals("r"))
			return htmlStringRead();

		if (modo.equals("t"))
			return htmlStringTable();

		throw new IllegalArgumentException("Modo " + modo + " inválido");
	}

	protected abstract String htmlStringWrite();

	protected String htmlStringRead() {

		return String.format("<dt>%s</dt><dd>${%s}</dd>\n", label, this.campo);

	}

	protected abstract String htmlStringTable();

}
