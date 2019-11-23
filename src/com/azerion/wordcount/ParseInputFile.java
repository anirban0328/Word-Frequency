package com.azerion.wordcount;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class ParseInputFile {

	public static void main(String[] args) throws InterruptedException {
		if(args.length != 2) {
			System.out.println("Invalid input");
			return;
		}
	
		String filePath1 = args[0]; // file1 path
		String filePath2 = args[1]; // file2 path
		File file1 = new File(filePath1);
		File file2 = new File(filePath2);
		
		// Check if both file exist
		if(file1.exists()==false||file2.exists()==false) {
			System.out.println("One or more File(s) doesn't exist");
			return;
		}
		
		String fileName1 = file1.getName();
		String fileName2 = file2.getName();
		
		// Checks if file is of text format
		String fileExt1 = getFileExtension(fileName1);
		String fileExt2 = getFileExtension(fileName2);		
		if(! (fileExt1.equals("txt") && fileExt2.equals("txt")) ) {
			System.out.println("Invalid file format");
			return;
		}
		
		// Its a concurrent HashMap with key as word and value as another concurrent HashMap
		// with its key as fileName and value as count of words in that file
		ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> wordCount = 
				new ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>>();
				
		Thread thread1 = new Thread(new ParsingThread(fileName1,file1,wordCount));
		Thread thread2 = new Thread(new ParsingThread(fileName2,file2,wordCount));
        thread1.run();
        thread2.run();
        
        thread1.join();
        thread2.join();
        
        Thread thread3 = new Thread(new PrintOutputThread(fileName1,fileName2,wordCount));
        thread3.run();       
	}
	
	public static String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        	return fileName.substring(fileName.lastIndexOf(".")+1);
        else 
        	return "";
    }
}