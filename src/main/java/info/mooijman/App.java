package info.mooijman;

import info.mooijman.action.Action;
import info.mooijman.action.GitActionImpl;
import info.mooijman.action.HtmlAction2Impl;
import info.mooijman.action.HtmlActionImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.io.IOException;


public class App {
	public static void main(String[] args) {
		System.out.println("This will be printed");

		System.out.println("Start Process");

//		DirectoryWalker walker = new DirectoryWalker(new GitActionImpl());
//		DirectoryWalker walker = new DirectoryWalker(new HtmlActionImpl());

		HtmlAction2Impl htmlAction2 = new HtmlAction2Impl();

		DirectoryWalker walker = new DirectoryWalker(htmlAction2);

		File dir = new File("../wikibase");

		JsonObject tree = null;


		try {
			tree = walker.start(dir,new JsonArray());
		} catch (IOException e) {
			e.printStackTrace();
		}



		System.out.println(tree.encodePrettily());

		System.out.println("end Process");

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
