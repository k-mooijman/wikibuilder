package info.mooijman;

import info.mooijman.action.Action;
import info.mooijman.action.GitActionImpl;
import info.mooijman.action.HtmlActionImpl;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.io.IOException;


public class App {
	public static void main(String[] args) {
		System.out.println("This will be printed");
//		listFiles();
//		HtmlToPdf htmlToPdf = new HtmlToPdf();
//		try {
//			htmlToPdf.test();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}


//		Action a1 = new GitActionImpl();
//		GitActionImpl g1 = new GitActionImpl();

		System.out.println("Start Process");

//		DirectoryWalker walker = new DirectoryWalker(new GitActionImpl());
		DirectoryWalker walker = new DirectoryWalker(new HtmlActionImpl());

		File dir = new File("../wikibase");

		JsonObject tree = walker.start(dir);
		System.out.println(tree.encodePrettily());

		System.out.println("end Process");

//		int getal1 = 1;
//		int getal2 = 2;
//
//		int getal = getal1+getal2;








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
