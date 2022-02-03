package info.mooijman;

import info.mooijman.action.Action;
import info.mooijman.action.GitActionImpl;
import info.mooijman.action.HtmlAction2Impl;
import info.mooijman.action.HtmlActionImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {


		logger.info("This will be printed");

		logger.info("Start Process");

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



		logger.info(tree.encodePrettily());

		logger.info("end Process");

	}

public static void listFiles(){

	File folder = new File(".");
	File[] listOfFiles = folder.listFiles();

	assert listOfFiles != null;
	for (File listOfFile : listOfFiles) {
		if (listOfFile.isFile()) {
			logger.info("File {}", listOfFile.getName());
		} else if (listOfFile.isDirectory()) {
			logger.info("Directory {}", listOfFile.getName());
		}
	}

}



}
