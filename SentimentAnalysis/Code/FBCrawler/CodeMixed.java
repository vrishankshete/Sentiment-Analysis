package com.vrishank.SentAnalysis.FBCrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.TreeSet;

public class CodeMixed {

	public static TreeSet<String> hindi_words = new TreeSet<String>();
	public static TreeSet<String> english_words = new TreeSet<String>();
	/**
	 * Start Detect code mixed data
	 * Coded By: Vrishank Shete
	 * This class is used to detect comments that are Hindi-English code mixed.
	 * We used dictionary based methods. Hindi dictionary in Roman letters and 
	 * English dictionary from Ubuntu /usr/share/dict/words  
	 */
	
	/*
	 * Load hindi and english dictionaries in TreeSet (Sorted words).
	 */
	public static void loadDictionaries(String hindiPath, String englishPath) throws Exception{
		BufferedReader brHindi = new BufferedReader(new FileReader(hindiPath));
		BufferedReader brEnglish = new BufferedReader(new FileReader(englishPath));
		String hindiLine;
		String englishLine;
		while((hindiLine = brHindi.readLine())!=null){
			hindi_words.add(hindiLine);
		}
		brHindi.close();
		while((englishLine = brEnglish.readLine())!=null){
			english_words.add(englishLine);
		}
		brEnglish.close();
		
		
		/* Below code snippet is used for getting only those hindi words that are not present
		 * in English dictionary. (e.g. man from hindi dictionary is removed as it is an Englisg word).  
		 
		BufferedWriter bw = new BufferedWriter(new FileWriter("/home/ubuntu/Senti_Data/Hindi_Words_Pure.txt"));
		Iterator<String> it = hindi_words.iterator();
		while(it.hasNext()){
			String thi1 = it.next();
			if(!english_words.contains(thi1)){
				bw.write(thi1 + "\n");
			}
			else{
				System.out.println(thi1 + "   ");
			}
		}
		bw.close();
		*/
	}
	
	public static void main(String[] args) throws Exception {
		
		/*
		 * Args:
		 * 0  pageID
		 * 1  Raw comments folder path (input file to this code)			
		 * 2  CodeMixed comments folder path (output file from this code)
		 * 3  Hindi wordList name (with full path)
		 * 4  English wordList name (with full path)
		 */
		
		String pageId = args[0];
		String inFileName = pageId + "_comments.txt";
		String inFilePath = args[1];
		String outFilePath = args[2];
		
		String hindiDictPath = args[3];//dataPath + "Hindi_Words_Pure.txt";
		String englishDictPath = args[4];//dataPath + "En_Words.txt";
		String phrasesFilePath = outFilePath + pageId + "_comments_codeMixed.txt";
		String rawFiles = inFilePath + inFileName;
		String rawString;
		
		loadDictionaries(hindiDictPath, englishDictPath);
		
		/*
		 *	 If a sentence contains both hindi and english words atleast once, that sentence is
		 *	 useful to us.
		 */
		BufferedReader brRawPhrases = new BufferedReader(new FileReader(rawFiles));
		BufferedWriter bwPhrases = new BufferedWriter(new FileWriter(phrasesFilePath,true));
		
		int flagHindi=0, flagEnglish=0;
		while((rawString = brRawPhrases.readLine()) != null){
			flagHindi=0;
			flagEnglish=0;
			rawString = rawString.toLowerCase().trim();
			if(rawString.contains("free unlimited") || rawString.contains("earntalktime") || 
					rawString.contains("mcent") || rawString.contains("free recharge") || 
					rawString.contains("sex") || rawString.contains("mms") || rawString.contains("http")){
				continue;
			}
			String []rawArray = rawString.split(" ");
			for(int i=0; i<rawArray.length; i++){
				if(hindi_words.contains(rawArray[i])){
					flagHindi=1;
				}
				if(english_words.contains(rawArray[i])){
					flagEnglish=1;
				}
			}
			if(flagHindi==1 && flagEnglish==1){
				bwPhrases.write(rawString + "\n");
			}
		}
		
		brRawPhrases.close();
		bwPhrases.close();
	}
	
	/**
	 * End Detect code mixed data
	 */
}
