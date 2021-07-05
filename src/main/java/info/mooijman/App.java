package info.mooijman;

import java.io.File;
import java.io.IOException;

public class App {
	public static void main(String[] args) {
		System.out.println("This will be printed");
		listFiles();
		HtmlToPdf htmlToPdf = new HtmlToPdf();
		try {
			htmlToPdf.test();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

public static void listFiles(){

	File folder = new File(".");
	File[] listOfFiles = folder.listFiles();

	for (int i = 0; i < listOfFiles.length; i++) {
		if (listOfFiles[i].isFile()) {
			System.out.println("File " + listOfFiles[i].getName());
		} else if (listOfFiles[i].isDirectory()) {
			System.out.println("Directory " + listOfFiles[i].getName());
		}
	}

}


}
