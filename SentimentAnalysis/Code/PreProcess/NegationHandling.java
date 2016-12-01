package com.lokesh.SentAnalysis.Preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NegationHandling {

	public static Set<String> eng_dictionary = new HashSet<String>();
	public static HashSet<String> neg_hindi_words = new HashSet<String>(Arrays.asList("nahi","nhi","ni","nai","nh","na",
			"nahin","mat","mt","nahn","nhin"));
	public static HashSet<String> neg_english_words = new HashSet<String>(Arrays.asList("ain't","aint","aren't","arent","can't",
			"cannot","cant","couldn't","couldnt","didn't","didnt","doesn't","doesnt","don't","dont",
			"hadn't","hadnt","hasn't","hast","haven't","havent","isn't","isnt","never","no","none",
			"noone","not","nothing","nowhere","shouldn't","shouldnt","won't","wont","wouldn't","wouldnt"));
	public static HashSet<String> punc_words = new HashSet<String>(Arrays.asList(".",",",":",";","?"));

	public static void main(String[] args) throws Exception {

		getNegationHandledFile(args[0],args[1],args[2]);
	}

	public static void getNegationHandledFile(String fileName, String NegationOutputFile, String en_dictionary){

		try {

			loadEnglishDictionary(en_dictionary);

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			BufferedWriter bw = new BufferedWriter(new FileWriter(NegationOutputFile));
			String line = null;

			while((line=br.readLine())!=null){
				String lineWithLebel[] = line.split("\t");
				String words[] = lineWithLebel[0].trim().split("\\s+");
				String label = "0";
				if(lineWithLebel.length==2){
					label = lineWithLebel[1].trim();
				}
				
				int previousPuncMarker = 0, nextPuncMarker=-1;
				StringBuilder sbTotal = new StringBuilder();
				int i=0,j=0;

				while(i < words.length){
					j=i;
					while(j< words.length && !punc_words.contains(words[j].trim())){
						j++;
					}
					if(j!=words.length){
						nextPuncMarker = j;
					}
					else{
						nextPuncMarker = words.length;
					}
					int status = 0;
					for(int k=previousPuncMarker ; k<nextPuncMarker ;k++){
						if(neg_hindi_words.contains(words[k].trim())){
							status =-1;
							break;
						}
						else if(neg_english_words.contains(words[k].trim())){
							status = 1;
							j=k+1;
							break;
						}
					}

					if(status==-1){
						StringBuilder sb = new StringBuilder();
						int k =0;

						for(k=previousPuncMarker ; k<nextPuncMarker ;k++){

							String normalisedWord = getNormalisedWord(words[k]);
							sb.append("neg_"+normalisedWord+" ");
						}
						previousPuncMarker = k;
						sbTotal.append(sb.toString());
					}
					else if(status==1){
						StringBuilder sb = new StringBuilder();
						int k =0;
						for(k=j ; k<nextPuncMarker ;k++){
							String normalisedWord = getNormalisedWord(words[k]);
							sb.append("neg_"+normalisedWord+" ");
						}
						previousPuncMarker = k;
						sbTotal.append(sb.toString());
					}
					else{
						StringBuilder sb = new StringBuilder();
						int k =0;
						for(k= previousPuncMarker; k<nextPuncMarker ;k++){
							//String normalisedWord = getNormalisedWord(words[k]);
							//sb.append(normalisedWord+" ");

							sb.append(words[k].toLowerCase().trim()+" ");
						}
						previousPuncMarker = k;
						sbTotal.append(sb.toString());
					}

					i = previousPuncMarker+1;
				}

				//System.out.println(sbTotal.toString());
				bw.write(sbTotal.toString().trim()+"\t"+label);
				bw.write("\n");

			}

			br.close();
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static String getNormalisedWord(String word) {

		if(!eng_dictionary.contains(word.trim().toLowerCase())){
			String normalisedWord=null;

			if(word.length()!=0){
				normalisedWord = WordNormalise.getNormalisedWord(word.toLowerCase());

			}
			return normalisedWord;
		}
		else{
			return word.toLowerCase().trim();
		}
	}

	public static void loadEnglishDictionary(String en_dictionary) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(en_dictionary));
		String fileLine = null;

		while((fileLine = br.readLine())!=null){

			eng_dictionary.add(fileLine.trim().toLowerCase());
		}

		br.close();

	}
}
