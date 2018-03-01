package com.s3a.test.util;

import static org.asciidoctor.OptionsBuilder.options;

import java.io.*;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.asciidoctor.*;
import org.asciidoctor.internal.IOUtils;

public class AsciidocConvert {
	

	public static boolean createAsciidoc(StringBuffer sb, String filePath) {
		try {
			File file = new File(filePath);
			if(!file.exists()){
				file.delete();
			}
			file.createNewFile();
			OutputStreamWriter  writer = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(sb.toString());
			bw.close();
			writer.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static boolean convertToHtml(String inputPath, String outputPath){
		try {
			File inputFile = new File(inputPath);
			File outputFile = new File(outputPath);
			if (outputFile.exists()) {
				System.out.println("  file is exists delete it");
				outputFile.delete();
			}

			Asciidoctor asciidoctor = Asciidoctor.Factory.create();
			FileReader reader = new FileReader(inputFile);
			StringWriter writer = new StringWriter();


			boolean result = convertToDefaultHtml(asciidoctor, inputFile, outputFile);

			//asciidoctor.convert(reader, writer, options().asMap());
			//boolean result = changeToHtml(writer, outputFile);
			//boolean result = true;
			return result;
		} catch (Exception e) {
			return false;
		}

		
	}

	public static boolean convertToDefaultHtml(Asciidoctor asciidoctor, File inputFile, File outputFile) {
		try {
			AttributesBuilder attributes = AttributesBuilder.attributes();
			attributes.tableOfContents(true);
			attributes.tableOfContents(Placement.LEFT);

			OptionsBuilder options = OptionsBuilder.options();
			options.safe(SafeMode.UNSAFE);
			options.attributes(attributes);
		   /* When generating to String headerFooter must be set to true because it is assumed the String will be embedded into another content and Headers are omitted. */
			options.headerFooter(true);

			options.backend("html5");
			//setSourceHighlighter("html5", attributes);

			String output = asciidoctor.convertFile(inputFile, options);

			FileUtils.writeStringToFile(outputFile, output);
		} catch (Exception e) {
			return false;
		}

		return true;
	}


	public static boolean changeToHtml(StringWriter writer,  File outputFile) {
		try {
			System.out.println("  using vm template to  create html");
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
			ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
			ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
			ve.init();
			String path ="modulhtml.vm";
			
			System.out.println(" vm path" + path);
			Template t = ve.getTemplate(path);
			VelocityContext ctx = new VelocityContext();
			StringBuffer htmlBuffer = writer.getBuffer();
			ctx.put("assmsg", htmlBuffer.toString());
			StringWriter sw = new StringWriter();
			t.merge(ctx, sw);

			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(outputFile), "utf-8");
			BufferedWriter bwriter = new BufferedWriter(write);
			bwriter.write(sw.getBuffer().toString());
			bwriter.flush();
			bwriter.close();

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	

}
