package info.mooijman;


import java.io.*;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlToPdf {
	private static final Logger log = LoggerFactory.getLogger(HtmlToPdf.class);


	public void test() throws IOException {

		String html = "<html lang=\"en\">\n" +
				"  <head>\n" +
				"    <title>MyPage</title>  \n" +
				"    <style type=\"text/css\">\n" +
				"      body{background-color: powderblue;}\n" +
				"    </style>\n" +
				"    <link href=\"css/mystyles.css\" rel=\"stylesheet\" >\n" +
				"     </link>" +
				"  </head>\n" +
				"  <body>\n" +
				"    <h1>Convert HTML to PDF</h1>\n" +
				"    <p>Here is an embedded image</p>\n" +
				"    <p style=\"color:red\">Styled text using Inline CSS</p>\n" +
				"    <i>This is italicised text</i>\n" +
				"    <p class=\"fontclass\">This text uses the styling from font face font</p>\n" +
				"    <p class=\"myclass\">This text uses the styling from external CSS class</p>\n" +
				"  </body>\n" +
				"</html>";

		File file = File.createTempFile("tmp_", "test.pdf");

		InputStream is = producePDF(html);
		byte[] arr = new byte[is.available()];
		is.read(arr);
		try (OutputStream os = new FileOutputStream(file)) {
			os.write(arr);
			log.info("written at : {}" , file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException: {}", e.getMessage());
		}
	}



	private InputStream producePDF(String html){

		StringWriter sw = new StringWriter();
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0)) {
				PdfRendererBuilder builder = new PdfRendererBuilder();
				String basePath = new File("/data/pdfImages/").toURI().toString();
				builder.toStream(byteArrayOutputStream);

				builder.withHtmlContent(html, basePath);
				builder.run();
				byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			}
		} catch (IOException e) {
			log.error("[PDFServiceImpl] IOException occured  PDF", e);
		} catch (Exception e) {
			log.error("[PDFServiceImpl] Error generating PDF", e);
		}
		return byteArrayInputStream;
	}
}
