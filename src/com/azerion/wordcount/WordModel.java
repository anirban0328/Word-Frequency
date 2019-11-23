package com.azerion.wordcount;

public class WordModel {
	private String word; // Word name
	private Integer count; // Total frequency of word
	
	public WordModel(String word,Integer count) {
		this.word = word;
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}	
}