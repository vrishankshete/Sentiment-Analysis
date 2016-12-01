package com.lokesh.SentAnalysis.Preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoadPMIMap {

	public static Set<String> eng_dictionary = new HashSet<String>();

	public static void main(String[] args) throws Exception {

		//String fileName = args[0];
		//System.out.println(fileName);
		loadPMIMap(args[0], args[1], args[2]);

	}

	public static Map<String , PMCount> loadPMIMap(String fileName,String PMIFileOutput, String wordlistFile){

		Map<String, PMCount> pmiCount = new HashMap<String, PMCount>();
		try {
			loadEnglishDictionary(wordlistFile);

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = null;
			while((line = br.readLine())!=null){

				String linewithLebel[] = line.split("	");

				String words[] = linewithLebel[0].trim().split(" ");
				String lebel = linewithLebel[1].trim();
				for(int index =0 ; index < words.length ; index++){


					if(words[index].trim().length()!=1 || words[index].trim().length()!=0){

						if(words[index].trim().toLowerCase().contains("neg_") ||
								eng_dictionary.contains(words[index].trim().toLowerCase())){
							if(pmiCount.containsKey(words[index].trim())){
								PMCount pmCount = pmiCount.get(words[index].trim());
								pmCount = updatePMCount(pmCount,lebel);
							}
							else{

								PMCount pmCount = updatePMCount(null,lebel);
								pmiCount.put(words[index].trim(), pmCount);
							}
						}
						else if(!eng_dictionary.contains(words[index].trim().toLowerCase())){

							String normalisedWord=null;

							if(words[index].length()!=0){
								normalisedWord = WordNormalise.getNormalisedWord(words[index].trim().toLowerCase());

							}

							if(pmiCount.containsKey(words[index].trim())){
								PMCount pmCount = pmiCount.get(words[index].trim());
								pmCount = updatePMCount(pmCount,lebel);
							}
							else{

								PMCount pmCount = updatePMCount(null,lebel);
								pmiCount.put(words[index].trim(), pmCount);
							}

							if(normalisedWord!=null){
								if(pmiCount.containsKey(normalisedWord)){
									PMCount pmCount = pmiCount.get(normalisedWord);
									pmCount = updatePMCount(pmCount,lebel);
								}
								else{

									PMCount pmCount = updatePMCount(null,lebel);
									pmiCount.put(normalisedWord, pmCount);
								}

							}
						}
					}
				}
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(PMIFileOutput));
			for(Map.Entry<String, PMCount> m : pmiCount.entrySet()){

				String str = m.getKey()+"\t"+m.getValue().getPosCount()+" "+m.getValue().getNegCount()+" "
						+m.getValue().getNeutralCount();
				bw.write(str);
				bw.write("\n");
			}
			br.close();
			bw.close();


		} catch (Exception e1) {

			e1.printStackTrace();
		}

		return pmiCount;

	}

	private static PMCount updatePMCount(PMCount pmCount, String lebel) {

		if(lebel.equals("-1")){
			if(pmCount==null){
				pmCount = new PMCount(0, 1, 0);
			}
			else{
				pmCount.setNegCount(pmCount.getNegCount()+1);
			}
		}
		else if(lebel.equals("0")){
			if(pmCount==null){
				pmCount = new PMCount(0, 0, 1);
			}
			else{
				pmCount.setNeutralCount(pmCount.getNeutralCount()+1);
			}
		}
		else{
			if(pmCount==null){
				pmCount = new PMCount(1, 0, 0);
			}
			else{
				pmCount.setPosCount(pmCount.getPosCount()+1);
			}
		}
		return pmCount;
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
