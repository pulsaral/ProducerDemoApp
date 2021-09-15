package com.demo.producer.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileUtility {
	
	public static String readLastLineFromFile(File file) {
		String lastLine = null;
		FileInputStream fs;
		try {
			fs = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			
			for (String line = br.readLine(); line != null; line = br.readLine()) {
			       lastLine = line;
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lastLine;
	}
	
	public static String getFileTopic(String fileName) {
		String topic = null;
		
		switch (fileName) {
			case "file1.log":
				topic = "testtopic";
				break;
			case "file2.log":
				topic = "testtopic1";
				break;
		}
		
		return topic;
	}

}
