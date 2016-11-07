package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import play.data.validation.Required;
import play.db.jpa.JPA;
import play.mvc.Controller;
import play.templates.JavaExtensions;
import utils.TemplateTipoCampo;

public class TemplatesController extends Controller {

	public static void index() {
		render();
	}

	public static void templates() {

		Metamodel metamodel = JPA.em().getMetamodel();
		Set<EntityType<?>> entities = metamodel.getEntities();
		List<?> clases = entities.stream().map(e -> e.getJavaType())
				.collect(Collectors.toList());
		render(clases);

	}

	public static void crearTemplate(@Required String clase, String modo,
			String instanceName) throws ClassNotFoundException, IOException {

		if (validation.hasErrors())
			render("@templates");

		Class<?> clazz = Class.forName(clase);
		Field[] fields = clazz.getFields();
		List<Field> campos = Arrays.asList(fields);
		File f = File.createTempFile("template", ".html");

		campos = campos.stream()
				.filter(field -> !(field.getName().matches("id|willBeSaved")
						|| java.lang.reflect.Modifier
								.isStatic(field.getModifiers())))
				.collect(Collectors.toList());

		try (Writer w = new FileWriter(f)) {

			String header = "#{extends 'main.html' /}\r\n" + "\r\n"
					+ "#{ifErrors}\r\n"
					+ "<div class=\"alert alert-danger\">Por favor corrija los campos destacados</div>\r\n"
					+ "#{/ifErrors}\r\n" + "\r\n"
					+ "<form method=\"POST\" action=\"@{}\">\r\n";
			w.write(header);

			if (modo.equals("t")) {
				w.write("<table class='bstable'>\n<thead>"
						+ "<tr>\n");
				w.write("<th data-field=\"state\" data-checkbox=\"true\"></th>\n");
				CAMPO: for (Field field : campos) {
					w.write("<th>"
							+ JavaExtensions.capitalizeWords(field.getName())
							+ "</th>\n");
				}
				w.write("</tr>\n</thead>\n");
				w.write("<tbody>\n<tr>\n");
				w.write("<td></td>\n");
				CAMPO: for (Field field : campos) {
					w.write("<td>${" + instanceName + "." + field.getName()
							+ "}</td>\n");
				}
				w.write("</tr>\n</tbody>\n</table>\n");
			} else {
				CAMPO: for (Field field : campos) {

					TemplateTipoCampo tt = TemplateTipoCampo.newInstance(field,
							instanceName, modo);
					w.write(tt.htmlString());

				}

			}

			String footer = "	<p><button class=\"btn btn-primary\">Enviar</button></p>\n"
					+ "</form>\n";
			w.write(footer);

		}

		response.setContentTypeIfNotSet("text/plain");
		renderBinary(f);

	}

}
