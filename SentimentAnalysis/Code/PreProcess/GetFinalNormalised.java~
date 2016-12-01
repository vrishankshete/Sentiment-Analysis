package com.lokesh.SentAnalysis.Preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class GetFinalNormalised {

	public static Set<String> eng_dictionary = new HashSet<String>();
	
	public static void main(String[] args) throws Exception {
	
		getNormalisedFile(args[0],args[1],args[2]);
	}

	public static void getNormalisedFile(String inputFile, String outputFile, String wordlistFile) {
		
		
		try {
			
			loadEnglishDictionary(wordlistFile);
			
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
			
			String fileLine = null;
			
			while((fileLine = br.readLine())!=null){
				
				String line[] = fileLine.split("	");
				String words[] = line[0].trim().split(" ");
				
				for(int index=0; index < words.length; index++){
					
					if(words[index].trim().toLowerCase().contains("neg_") ||
							eng_dictionary.contains(words[index].trim().toLowerCase())){
						
						bw.write(words[index].trim().toLowerCase()+" ");
					}
					if(!eng_dictionary.contains(words[index].trim().toLowerCase())){
						
						try{
							
							Integer.parseInt(words[index].trim());
						}
						catch(NumberFormatException ex){
							
							String normalisedWord=null;
							
							if(words[index].length()!=0){
								normalisedWord = WordNormalise.getNormalisedWord(words[index].toLowerCase());
								
							}
							
							bw.write(normalisedWord+" ");
							
							
						}
					}
					
					
					
				}
				if(line.length==2){
					bw.write("	"+line[1]);
				}
				else{
					bw.write("	0");
				}
				
				bw.write("\n");
				
				
			}
			
			br.close();
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void loadEnglishDictionary(String wordlistFile) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader(wordlistFile));
		String fileLine = null;
		
		while((fileLine = br.readLine())!=null){
			
			eng_dictionary.add(fileLine.trim().toLowerCase());
		}
		
		br.close();
		
	}

}
