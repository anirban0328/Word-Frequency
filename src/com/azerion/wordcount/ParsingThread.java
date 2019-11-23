package com.azerion.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;

public class ParsingThread implements Runnable{
	private String fileName;
	private File file;
	private ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> wordCount;

	public ParsingThread(String fileName,File file,ConcurrentHashMap<String,
			ConcurrentHashMap<String,Integer>> wordCount){
	    this.fileName = fileName;
	    this.file = file;
	    this.wordCount = wordCount;
	}

	@Override
	public void run(){
	    parseFileAndSaveToCache(fileName,file,wordCount);
	}
	
	// Parsing the input file line by line and storing it locally in the ConcurrentHashMap
	public void parseFileAndSaveToCache(String fileName,File file,
			ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> wordCount){	
		String str;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new 
				FileInputStream(file), "UTF-8"));) {  // Ensures the file is UTF-8 format
			
			// Reading each line of the file one by one instead of loading entire file in memory
			while ((str = br.readLine()) != null) {
			  String[] words = str.split("\\s+"); // Splitting words based on one or more spaces
			  
			  for(int i=0; i<words.length; i++) {
				  String word = words[i].replaceAll("[^a-zA-Z0-9]", ""); // Removing special characters from the word
				  word = word.toLowerCase(); // Converting to lower-case to ensure case insensitive
				  ConcurrentHashMap<String,Integer> hm = new ConcurrentHashMap<String,Integer>();
				  
				  if(wordCount.containsKey(word)) {
					  if(wordCount.get(word).containsKey(fileName)) { // If the map contains the word for the given file
						  hm = wordCount.get(word);
						  hm.put(fileName, hm.get(fileName)+1);
					  } else { // If the word for the given file appears first time but exist for other file in the map
						  hm = wordCount.get(word);
						  hm.put(fileName,1);
					  }
				  } else {  // If word doesn't exist in the map at all
					  hm.put(fileName,1);
				  }
				  wordCount.put(word, hm);
			  }
			}
			br.close();
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
	    } catch (IOException e) {
            e.printStackTrace();
        }
	}    
}