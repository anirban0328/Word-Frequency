package com.azerion.wordcount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PrintOutputThread implements Runnable{
	private String fileName1;
	private String fileName2;
	private ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> wordCount;

	public PrintOutputThread(String fileName1,String fileName2,
			ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> wordCount){
	    this.fileName1 = fileName1;
	    this.fileName2 = fileName2;
	    this.wordCount = wordCount;
	}
	
	@Override
	public void run(){
		List<WordModel> sortedlist = getOrder(fileName1,fileName2,wordCount);
		printOutput(fileName1,fileName2,wordCount,sortedlist);   
	}
	
	// Sorting the word cache in decreasing order of total count of word in both the files
	public List<WordModel> getOrder(String fileName1,String fileName2, 
			ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> wordCount) {
		List<WordModel> list = new ArrayList<WordModel>(); // List of objects containing word name and count
		for (Map.Entry<String,ConcurrentHashMap<String,Integer>> entry : wordCount.entrySet()) {
			String word = entry.getKey();
			int total = 0;
			if(entry.getValue().containsKey(fileName1))
				total = entry.getValue().get(fileName1);
			if(entry.getValue().containsKey(fileName2))	
				total += entry.getValue().get(fileName2);
			WordModel obj = new WordModel(word,total);
			list.add(obj);
		} 
		Comparator<WordModel> compareByCount = (WordModel o1, WordModel o2) -> o2.getCount().compareTo(o1.getCount());
		Collections.sort(list,compareByCount);
		return list;
	}
	
	// Printing the output based on word frequency
	public void printOutput(String fileName1, String fileName2,
			ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> wordCount,
			List<WordModel> list) {
		for(WordModel wm : list) {
			int count1=0,count2=0;
			if(wordCount.get(wm.getWord()).containsKey(fileName1))
				count1 = wordCount.get(wm.getWord()).get(fileName1); // Count of given word in file1
			if(wordCount.get(wm.getWord()).containsKey(fileName2))
				count2 = wordCount.get(wm.getWord()).get(fileName2); // Count of given word in file2
			
			// Printing final Output
			System.out.println(wm.getWord() + " " + wm.getCount() + " = " + count1 + " + " + count2);
		}
	}
}