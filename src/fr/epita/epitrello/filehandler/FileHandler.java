package fr.epita.epitrello.filehandler;

import java.io.*;

public class FileHandler {

	public static void writeToFile(String content) {

		if (!(content != null))
			content = "";

		try {
			File file = new File("Output.txt");
			FileWriter fileWriter = new FileWriter(file, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(content);
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
