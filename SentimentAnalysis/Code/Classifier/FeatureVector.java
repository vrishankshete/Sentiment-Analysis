package com.vrishank.SentAnalysis.Classifier;

/*This is the main class where feature vectors are created
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class FeatureVector
{
	public static HashSet<String> stopwordHashSet = new HashSet<String>(Arrays.asList("a","about","above",
			"after","again","against","all","am","an","and","any","are","as","at","be","because",
			"been","before","being","below","between","both","by","could","did","do","does",
			"doing","down","during","each","for","from","further","had","has","have","having",
			"he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself",
			"his","how","how's","i","i'd","i'll","i'm","i've","in","into","is","it","it's","its",
			"itself","let's","me","my","myself","of","off","on","once","only","or","other","ought",
			"our","ours","ourselves","out","over","own","same","she","she'd","she'll","she's",
			"should","so","some","such","than","that","that's","the","their","theirs","them",
			"themselves","then","there","there's","these","they","they'd","they'll","they're",
			"they've","this","those","through","to","under","until","up","very","was","we","we'd",
			"we'll","we're","we've","were","what","what's","when","when's","where","where's",
			"which","while","who","who's","whom","why","why's","with","won't","would","you",
			"you'd","you'll","you're","you've","your","yours","yourself","yourselves","hu","jn",
			"kn","kn","ks","ki","ase","ese","trh","sth","sth","sng","le","lye","kch","kchch","kch",
			"khn","kha","kha","khn","ydi","hi","ise","ise","ese","ese","he","abhi","sbhi","kl","rha",
			"rha","rhe","rhe","isi","use","js","us","us","asi","pe","un","bhi","bhe","bhe","ar",
			"tb","jb","tha","tha","tk","ap","ap","ye","yh","the","do","thi","ja","ja","yhi","whi",
			"yhi","whi","usi","fr","phr","mgr","mgr","ka","ek","ka","se","ko","is","is","k","jo",
			"kr","me","mn","mi","mn","mi","ne","to","th","hi","ya","ya","ho","unka","inka","unka",
			"inka","k","ke","ky","ki","hn","gya","gya","bni","ar","or","ha","hwa","hu","hn","sth",
			"sth","bd","pra","pra","sra","sra","hna","unko","unko","whn","wha","whi","whn","jhn",
			"jha","jdhr","jdhr","kse","ksi","kfi","kfi","phle","phle","phli","phli","nche","nche",
			"yhn","yha","jsa","jse","mno","mno","andr","bhtr","bhtr","uske","uske","jse","iski",
			"iski","skta","skta","rkhn","rkhe","rkhn","apna","rkh","rkha","hne","krte","krte",
			"krn","kre","hti","apni","apni","krn","unke","unke","khte","khte","hte","krta","krte",
			"unki","unki","krne","krne","kya","kye","lya","lye","apne","apna","dya","iska","iska",
			"krna","krna","wle","wle","skte","skte","iske","iske","sbse","sbse","hi","he","h","ha",
			"hha","hhha","lkn","isme","ismn","esme","ismn","jtna","jtna","pr","pr","in","in","wh",
			"wo","wh","yh","ye","ya","jnhe","znhe","jnhn","jnho","jne","jno","jnn","knhe","knho",
			"knhn","kne","kno","itydi","dwra","unhe","unhi","une","uni","ktna","dbra","dbra","dbr",
			"dbr","blkl","dsre","knsa","knsa","hta","jin","kaun","kon","kis","koi","aise","ese",
			"tarah","sath","saath","sang","lie","liye","kuch","kuchch","kuchh","kahan","kaha","kahaa",
			"kahaan","yadi","hui","isse","ise","ese","esse","hue","abhi","sabhi","kul","raha","rha",
			"rahe","rhe","isi","use","jis","us","uss","aisi","pe","un","bhi","bhee","bhe","aur","tab",
			"jab","tha","thaa","tak","aap","ap","ye","yeh","the","do","thi","ja","jaa","yahi","wahi",
			"yehi","wohi","usi","fir","phir","magar","magr","ka","ek","kaa","se","ko","is","iss","k",
			"jo","kar","me","main","mai","mein","mei","ne","to","toh","hi","ya","yaa","ho","unaka",
			"inaka","unka","inka","k","ke","key","ki","hain","gaya","gya","bani","aur","or","hua",
			"hwa","hu","hun","saath","sath","baad","poora","pura","saara","sara","hona","unako",
			"unko","wahan","waha","wahi","wahin","jahan","jaha","jeedhar","jidhar","kise","kisi",
			"kaafi","kafi","pahle","pehle","pahli","pehli","neeche","niche","yahan","yaha","jaisa",
			"jaise","maano","mano","andar","bhitar","bheetar","usake","uske","jise","isaki","iski",
			"sakata","sakta","rakhein","rakhe","rakhen","apna","rakh","rakha","hone","karate","karte",
			"karen","kare","hoti","apani","apni","karein","unake","unke","kehte","kahte","hote","karata",
			"karte","unki","unaki","karne","krne","kiya","kiye","liya","liye","apne","apna","diya",
			"iska","isaka","karana","karna","wale","waale","sakte","sakate","iske","isake","sabse",
			"sabase","hai","he","h","ha","haha","hahaha","lekin","isme","ismen","esme","ismein","jitna",
			"jtna","par","pr","in","inn","wah","wo","woh","yeh","ye","ya","jinhe","zinhe","jinhon",
			"jinho","jine","jino","jinon","kinhe","kinho","kinhon","kine","kino","ityadi","dwara",
			"unhe","unhi","une","uni","kitna","dobara","dubara","dubar","dobar","bilkul","dusre",
			"kaunsa","konsa","hota"));
			
	public static HashMap<String, Integer> smileys_category_map = new HashMap<String, Integer>(){{
	 put(":-)", 0); put(":)", 0); put("=)", 0); put("", 0); put("(:", 0);
	 put("(-:", 0); put(":-D", 0); put(":D", 0); put(":-d", 0);
	 put(":d", 0); put(":>", 0); put(":->", 0); put(":))", 0);
	 put("x-D", 0); put("X-D", 0); put("LOL", 0); put("lol", 0);
	 put("(lol)", 0); put("(LOL)", 0); put(":'D", 0); put(":'-D", 0);
	 put("LMAO", 0); put("(lmao)", 0); put("x'D", 0); put("X'D", 0);
	 put("x'-D", 0); put("X'-D", 0); put("ROFL", 0); put("(rofl)", 0);
	 put("ROFLMAO", 0); put("(giggle)", 0); put(":')", 0); put(":'-)", 0);		
	//negative ... 1
	 put(":(", 1); put(":-(", 1); put("=(", 1); put(":'(", 1);
	 put(":'-(", 1); put("(cry)", 1); put("(crying)", 1); put(":((", 1);
	 put(":-((", 1); put(":'s", 1); put(":'S", 1); put(":'-s", 1);
	 put(":'-S", 1); put(":-<", 1); put(":<", 1); put(":-[", 1);
	 put(":[", 1); put(":C", 1); put(":-C", 1); put(":c", 1);
	 put(":-c", 1); put(":'C", 1); put(":'c", 1); put(":'-c", 1);
	 put(":'-C", 1); put(":-O", 1); put(":O", 1); put("=-O", 1);
	 put(":o", 1); put(":-o", 1); put(":-S", 1); put(":-s", 1);
	 put(":s", 1); put(":-s", 1); put(":Z", 1); put(":z", 1);
	 put(":-Z", 1); put(":-z", 1); put(">-[", 1); put(">-(", 1);
	 put(":-@", 1); put(":@", 1); put(">:o", 1); put(">-@", 1);
	 put(">:0", 1); put(">-0", 1); put(">:-0", 1); put(">-o", 1);
	 put(">-O", 1); put("[-(", 1); put(":-L", 1); put(":L", 1);
	 put(":^o", 1); put("(U)", 1); put("(u)", 1);
	 //love .. 3
	 put(":*", 3); put(":-*", 3); put(":-{}", 3); put(":{}", 3);
	 put("(l)", 3); put("(L)", 3); put("(K)", 3); put("(k)", 3);
	 put("*KISSING*", 3); put("*KISSED*", 3); put(":-^", 3); put(";-^", 3);
	// playful ... 2 
	 put(";)", 2); put(";-)", 2); put(":P", 2); put(":-P", 2);
	 put(":p", 2); put(":-p", 2); put(";P", 2); put(";p", 2);
	 put(";-P", 2); put(";P", 2); put("%-)", 2); put("(drunk)", 2);
	 put("8-}", 2); put(":]", 2); put(":-]", 2); put(":-3", 2);
	 put("8-)", 2); put("B-)", 2); put("b-)", 2); put("(H)", 2);
	 put("(h)", 2); put("(sunglass)", 2); put("(sunglasses)", 2); put("B:)", 2);
	 put("B:-)", 2); put("B-p", 2); put("B-P", 2);
	 }};
//	public static HashSet<String> negativeWordsHashSet = new HashSet<String>(Arrays.asList("not","nor","never","n't","no","neither","nowhere"));
//	public static HashSet<String> conjunctionsHashSet = new HashSet<String>(Arrays.asList(".",",",":",";","!","?","\\", "^", "/", "@", "~", "!", "$", "%", "&", "*", "(", ")", "+", "-", "_", "[" , "]", "{", "}" , "|"));
	//public static TreeMap<String, Integer> support_Vector = new TreeMap<String, Integer>();
//	public static HashMap<String, String> wordToPOSMap = new HashMap<String, String>();
//	public static HashMap<String, String> POSInMemory = new HashMap<String, String>();
//	public static int indexCounter = 0;
	public static int n_gram = 2;
//	public static float total_count=0;
	public static HashMap<String, Positive_negative_score> word_score_map = new HashMap<String, Positive_negative_score>();
	public static Set<String> eng_dictionary = new HashSet<String>();
	public static HashMap<String, PMCount> PMI_word_score = new HashMap<String, PMCount>(); 
	
	public static void loadPMIDictionary(String dictPath) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(dictPath));
		String line=null;
		while((line=br.readLine())!=null){
			try{
			String[] arr = line.split("\t");
			String []scores = arr[1].trim().split(" ");
			PMCount p = new PMCount(Integer.parseInt(scores[0]), 
					Integer.parseInt(scores[1]), Integer.parseInt(scores[2]));
			PMI_word_score.put(arr[0], p);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		br.close();
	}
	
/*	public static void loadEnglishDictionary(String wordlistFile) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(wordlistFile));
		String fileLine = null;

		while((fileLine = br.readLine())!=null){

			eng_dictionary.add(fileLine.trim().toLowerCase());
		}

		br.close();

	}*/
	
	public static List<String> ngrams(int n, String str)
	{
		List<String> ngrams = new ArrayList<String>();
		String[] words = str.split("\\s+");
		for (int i = 0; i < words.length - n + 1; i++)
			ngrams.add(concat(words, i, i+n));
		return ngrams;
	}
	public static String concat(String[] words, int start, int end)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++)
			sb.append((i > start ? " " : "") + words[i]);
		return sb.toString();
	}
	
	public static void loadMaps(String hindiSentiWordNet){

		try{
			BufferedReader br = new BufferedReader(new FileReader(hindiSentiWordNet));
			String str = null;
			if((str = br.readLine())!=null){
				try{
					String []arr1 = str.split(",");
					String []arr2 = arr1[1].split(" ");
					word_score_map.put(arr1[0], new Positive_negative_score(Float.parseFloat(arr2[0]), Float.parseFloat(arr2[1])));
				}
				catch(Exception e){
					System.out.println("Error in parsing Hindi Sentiwordnet");
					e.printStackTrace();
				}
			}
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void writeToFile(BufferedWriter bw, double[] features_arr, String classLabel, TreeMap<String, Integer> support_Vector) throws Exception{
		int flag = 0;
		for(int i = 0 ; i < features_arr.length ; i++){
			if(features_arr[i] != 0)
			{
				flag = 1;
				break;
			}
		}
		if(flag == 0)
		{
			bw.write("0 " + support_Vector.size() + ":1.0");
		}
		else
		{
			bw.write(classLabel);
			for(int i=0; i<features_arr.length; i++)
				if(features_arr[i]!=0)
					bw.write(" "+(i+1)+":"+features_arr[i]);
		}
		bw.write("\n");
	}
	
	public static double[] createVector_addSmileys(String line, double []features_arr, TreeMap<String, Integer> support_Vector){
		int pos_0_count=0, neg_1_count=0, playful_2_count=0, love_3_count=0;
		for (String ngram : ngrams(1, line))
		{
			if(smileys_category_map.containsKey(ngram)){
				if(smileys_category_map.get(ngram) == 0){
					pos_0_count++;
				}
				if(smileys_category_map.get(ngram) == 1){
					neg_1_count++;
				}
				if(smileys_category_map.get(ngram) == 2){
					playful_2_count++;
				}
				if(smileys_category_map.get(ngram) == 3){
					love_3_count++;
				}
			}
		}
		features_arr[support_Vector.get("0_POS")] = pos_0_count;
		features_arr[support_Vector.get("1_NEG")] = neg_1_count;
		features_arr[support_Vector.get("2_PLAYFUL")] = playful_2_count;
		features_arr[support_Vector.get("3_LOVE")] = love_3_count;
		
		return features_arr;
	}

	public static double[] createVector_addSentiLbels(SentiWordNet s, String line, double []features_arr, TreeMap<String, Integer> support_Vector){
		int en_pos_count=0, en_neg_count=0, hin_pos_count=0, hin_neg_count=0;
		double en_pos_score=0, en_cur_pos, en_cur_neg, en_neg_score=0, en_max_pos=0, en_max_neg=0,
				hin_pos_score=0, hin_cur_pos, hin_cur_neg, hin_neg_score=0, hin_max_pos=0, hin_max_neg=0;
		for (String ngram : ngrams(1, line))
		{
			en_pos_score=0;
			en_neg_score=0;
			en_max_neg=0;
			en_max_pos=0;
			hin_pos_score=0;
			hin_neg_score=0;
			hin_max_neg=0;
			hin_max_pos=0;

			if(s.extract(ngram)>0){
				en_cur_pos = s.extract(ngram);
				if(en_cur_pos>0){
					en_pos_count++;
					en_pos_score += en_cur_pos;
					if(en_cur_pos>en_max_pos){
						en_max_pos = en_cur_pos;
					}
				}
			}
			else if(s.extract(ngram)<0){
				en_cur_neg = s.extract(ngram);
				if(en_cur_neg>0){
					en_neg_count++;
					en_neg_score += en_cur_neg;
					if(en_cur_neg>en_max_neg){
						en_max_neg = en_cur_neg;
					}
				}
			}

			if(word_score_map.containsKey(ngram)){
				Positive_negative_score p = word_score_map.get(ngram);
				hin_cur_pos = p.getPositive_score();
				hin_cur_neg = p.getNegative_score();
				if(hin_cur_pos>0){
					hin_pos_count++;
					hin_pos_score += hin_cur_pos;
					if(hin_cur_pos>hin_max_pos){
						hin_max_pos = hin_cur_pos;
					}
				}
				if(hin_cur_neg>0){
					hin_neg_count++;
					hin_neg_score += hin_cur_neg;
					if(hin_cur_neg>hin_max_neg){
						hin_max_neg = hin_cur_neg;
					}
				}
			}
		}

		features_arr[support_Vector.get("HIN_POSITIVE_COUNT")] = hin_pos_count;
		features_arr[support_Vector.get("HIN_NEGATIVE_COUNT")] = hin_neg_count;
		features_arr[support_Vector.get("HIN_MAX_POSITIVE")] = hin_max_pos;
		features_arr[support_Vector.get("HIN_MAX_NEGATIVE")] = hin_max_neg;
		features_arr[support_Vector.get("HIN_TOT_POSITIVE")] = hin_pos_score;
		features_arr[support_Vector.get("HIN_TOT_NEGATIVE")] = hin_neg_score;
		features_arr[support_Vector.get("EN_POSITIVE_COUNT")] = en_pos_count;
		features_arr[support_Vector.get("EN_NEGATIVE_COUNT")] = en_neg_count;
		features_arr[support_Vector.get("EN_MAX_POSITIVE")] = en_max_pos;
		features_arr[support_Vector.get("EN_MAX_NEGATIVE")] = en_max_neg;
		features_arr[support_Vector.get("EN_TOT_POSITIVE")] = en_pos_score;
		features_arr[support_Vector.get("EN_TOT_NEGATIVE")] = en_neg_score;

		return features_arr;
	}

	public static double[] createVector_add_n_grams(String line, double []features_arr, TreeMap<String, Integer> support_Vector){
		String strArr[] = new String[10000];
		String []arr = line.trim().split("\\s+");
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<arr.length;i++){
			if(!stopwordHashSet.contains(arr[i])){
				sb.append(arr[i]+" ");
			}
		}
		
		line = sb.toString().trim();
		
		int nGramCount = 0;
		for (int n = 1; n <= n_gram; n++)
		{
			for (String ngram : ngrams(n, line))
			{
				strArr[nGramCount++] = ngram;
			}
		}
		
		for(int i=0; i<nGramCount; i++)
		{
			if(!stopwordHashSet.contains(strArr[i]))
			{
				if(support_Vector.containsKey(strArr[i]))
				{
					features_arr[support_Vector.get(strArr[i])] = 1;
				}

			}
		}
		return features_arr;
	}
	
	public static double[] createVector_addEnglishPOSTagCounts(String line, double []features_arr, TreeMap<String, Integer> support_Vector){
		int EN_N_count=0, EN_O_count=0, EN_S_count=0, EN_raise_count=0, 
				EN_Z_count=0, EN_L_count=0, EN_M_count=0, EN_V_count=0, 
				EN_A_count=0, EN_R_count=0, EN_excl_count=0, EN_D_count=0,
				EN_P_count=0, EN_ampersand_count=0, EN_T_count=0, EN_X_count=0, 
				EN_Y_count=0, EN_hash_count=0, EN_at_count=0, EN_tilde_count=0, 
				EN_U_count=0, EN_E_count=0, EN_dollar_count=0, EN_comma_count=0, 
				EN_G_count=0;
		int global_count=1;
		
		if(!(line==null || line.equals(""))){
			String []tag_count_arr = line.split("\t");
			
			for(int i=0; i<tag_count_arr.length; i++){
				
				String []arr = tag_count_arr[i].split(":");
				String tag = arr[0];
				int count = Integer.parseInt(arr[1]);
				if(support_Vector.containsKey(tag)){
					
					switch (tag) {
					case "EN_N":
						EN_N_count = count;
						global_count += count;
						break;
					case "EN_O":
						EN_O_count = count;
						global_count += count;
						break;
					case "EN_S":
						EN_S_count = count;
						global_count += count;
						break;
					case "EN_^":
						EN_raise_count = count;
						global_count += count;
						break;
					case "EN_Z":
						EN_Z_count = count;
						global_count += count;
						break;
					case "EN_L":
						EN_L_count = count;
						global_count += count;
						break;
					case "EN_M":
						EN_M_count = count;
						global_count += count;
						break;
					case "EN_V":
						EN_V_count = count;
						global_count += count;
						break;
					case "EN_A":
						EN_A_count = count;
						global_count += count;
						break;
					case "EN_R":
						EN_R_count = count;
						global_count += count;
						break;
					case "EN_!":
						EN_excl_count = count;
						global_count += count;
						break;
					case "EN_D":
						EN_D_count = count;
						global_count += count;
						break;
					case "EN_P":
						EN_P_count = count;
						global_count += count;
						break;
					case "EN_&":
						EN_ampersand_count = count;
						global_count += count;
						break;
					case "EN_T":
						EN_T_count = count;
						global_count += count;
						break;
					case "EN_X":
						EN_X_count = count;
						global_count += count;
						break;
					case "EN_Y":
						EN_Y_count = count;
						global_count += count;
						break;
					case "EN_#":
						EN_hash_count = count;
						global_count += count;
						break;
					case "EN_@":
						EN_at_count = count;
						global_count += count;
						break;
					case "EN_~":
						EN_tilde_count = count;
						global_count += count;
						break;
					case "EN_U":
						EN_U_count = count;
						global_count += count;
						break;
					case "EN_E":
						EN_E_count = count;
						global_count += count;
						break;
					case "EN_$":
						EN_dollar_count = count;
						global_count += count;
						break;
					case "EN_,":
						EN_comma_count = count;
						global_count += count;
						break;
					case "EN_G":
						EN_G_count = count;
						global_count += count;
						break;
	
					default:
						break;
					}
				}
			}
		}
		features_arr[support_Vector.get("EN_N")] = EN_N_count/global_count;
		features_arr[support_Vector.get("EN_O")] = EN_O_count/global_count;
		features_arr[support_Vector.get("EN_S")] = EN_S_count/global_count;
		features_arr[support_Vector.get("EN_^")] = EN_raise_count/global_count;
		features_arr[support_Vector.get("EN_Z")] = EN_Z_count/global_count;
		features_arr[support_Vector.get("EN_L")] = EN_L_count/global_count;
		features_arr[support_Vector.get("EN_M")] = EN_M_count/global_count;
		features_arr[support_Vector.get("EN_V")] = EN_V_count/global_count;
		features_arr[support_Vector.get("EN_A")] = EN_A_count/global_count;
		features_arr[support_Vector.get("EN_R")] = EN_R_count/global_count;
		features_arr[support_Vector.get("EN_!")] = EN_excl_count/global_count;
		features_arr[support_Vector.get("EN_D")] = EN_D_count/global_count;
		features_arr[support_Vector.get("EN_P")] = EN_P_count/global_count;
		features_arr[support_Vector.get("EN_&")] = EN_ampersand_count/global_count;
		features_arr[support_Vector.get("EN_T")] = EN_T_count/global_count;
		features_arr[support_Vector.get("EN_X")] = EN_X_count/global_count;
		features_arr[support_Vector.get("EN_Y")] = EN_Y_count/global_count;
		features_arr[support_Vector.get("EN_#")] = EN_hash_count/global_count;
		features_arr[support_Vector.get("EN_@")] = EN_at_count/global_count;
		features_arr[support_Vector.get("EN_~")] = EN_tilde_count/global_count;
		features_arr[support_Vector.get("EN_U")] = EN_U_count/global_count;
		features_arr[support_Vector.get("EN_E")] = EN_E_count/global_count;
		features_arr[support_Vector.get("EN_$")] = EN_dollar_count/global_count;
		features_arr[support_Vector.get("EN_,")] = EN_comma_count/global_count;
		features_arr[support_Vector.get("EN_G")] = EN_G_count/global_count;
		
		return features_arr;
	}
	
	public static double[] createVector_addHindiPOSTagCounts(String line, double []features_arr, TreeMap<String, Integer> support_Vector){
		int HIN_NN_count=0, HIN_NST_count=0, HIN_NNP_count=0, HIN_PRP_count=0, 
				HIN_DEM_count=0, HIN_VM_count=0, HIN_VAUX_count=0, HIN_JJ_count=0, 
				HIN_RB_count=0, HIN_PSP_count=0, HIN_RP_count=0, HIN_CC_count=0, 
				HIN_QW_count=0, HIN_QF_count=0, HIN_QC_count=0, HIN_QO_count=0, 
				HIN_CL_count=0, HIN_INTF_count=0, HIN_INJ_count=0, HIN_NEG_count=0, 
				HIN_UT_count=0, HIN_SYM_count=0, HIN_NNC_count=0, HIN_NNPC_count=0, 
				HIN_RDP_count=0, HIN_ECH_count=0, HIN_UNK_count=0, HIN_DEFAULT_count=0;

		int global_count=1;
		
		if(!(line.equals("")||line==null)){
			String []tag_count_arr = line.split("\t");
			
			for(int i=0; i<tag_count_arr.length; i++){
				
				String []arr = tag_count_arr[i].split(":");
				String tag = arr[0];
				int count = Integer.parseInt(arr[1]);
				if(support_Vector.containsKey(tag)){
					
					switch (tag) {
					case "HIN_NN":
						HIN_NN_count = count;
						global_count += count;
						break;

					case "HIN_NST":
						HIN_NST_count = count;
						global_count += count;
						break;
						
					case "HIN_NNP":
						HIN_NNP_count = count;
						global_count += count;
						break;
					case "HIN_PRP":
						HIN_PRP_count = count;
						global_count += count;
						break;
					case "HIN_DEM":
						HIN_DEM_count = count;
						global_count += count;
						break;
					case "HIN_VM":
						HIN_VM_count = count;
						global_count += count;
						break;
					case "HIN_VAUX":
						HIN_VAUX_count = count;
						global_count += count;
						break;
					case "HIN_JJ":
						HIN_JJ_count = count;
						global_count += count;
						break;
					case "HIN_RB":
						HIN_RB_count = count;
						global_count += count;
						break;
					case "HIN_PSP":
						HIN_PSP_count = count;
						global_count += count;
						break;
					case "HIN_RP":
						HIN_RP_count = count;
						global_count += count;
						break;
					case "HIN_CC":
						HIN_CC_count = count;
						global_count += count;
						break;
					case "HIN_QW":
						HIN_QW_count = count;
						global_count += count;
						break;
					case "HIN_QF":
						HIN_QF_count = count;
						global_count += count;
						break;
					case "HIN_QC":
						HIN_QC_count = count;
						global_count += count;
						break;
					case "HIN_QO":
						HIN_QO_count = count;
						global_count += count;
						break;
					case "HIN_CL":
						HIN_CL_count = count;
						global_count += count;
						break;
					case "HIN_INTF":
						HIN_INTF_count = count;
						global_count += count;
						break;
					case "HIN_INJ":
						HIN_INJ_count = count;
						global_count += count;
						break;
					case "HIN_NEG":
						HIN_NEG_count = count;
						global_count += count;
						break;
					case "HIN_UT":
						HIN_UT_count = count;
						global_count += count;
						break;
					case "HIN_SYM":
						HIN_SYM_count = count;
						global_count += count;
						break;
					case "HIN_NNC":
						HIN_NNC_count = count;
						global_count += count;
						break;
					case "HIN_NNPC":
						HIN_NNPC_count = count;
						global_count += count;
						break;
					case "HIN_RDP":
						HIN_RDP_count = count;
						global_count += count;
						break;
					case "HIN_ECH":
						HIN_ECH_count = count;
						global_count += count;
						break;
					case "HIN_UNK":
						HIN_UNK_count = count;
						global_count += count;
						break;
					case "HIN_DEFAULT":
						HIN_DEFAULT_count = count;
						global_count += count;
						break;	
	
					default:
						break;
					}
				}
			}
		}
		
		features_arr[support_Vector.get("HIN_NN")] = HIN_NN_count/global_count;
		features_arr[support_Vector.get("HIN_NST")] = HIN_NST_count/global_count;
		features_arr[support_Vector.get("HIN_NNP")] = HIN_NNP_count/global_count;
		features_arr[support_Vector.get("HIN_PRP")] = HIN_PRP_count/global_count;
		features_arr[support_Vector.get("HIN_DEM")] = HIN_DEM_count/global_count;
		features_arr[support_Vector.get("HIN_VM")] = HIN_VM_count/global_count;
		features_arr[support_Vector.get("HIN_VAUX")] = HIN_VAUX_count/global_count;
		features_arr[support_Vector.get("HIN_JJ")] = HIN_JJ_count/global_count;
		features_arr[support_Vector.get("HIN_RB")] = HIN_RB_count/global_count;
		features_arr[support_Vector.get("HIN_PSP")] = HIN_PSP_count/global_count;
		features_arr[support_Vector.get("HIN_RP")] = HIN_RP_count/global_count;
		features_arr[support_Vector.get("HIN_CC")] = HIN_CC_count/global_count;
		features_arr[support_Vector.get("HIN_QW")] = HIN_QW_count/global_count;
		features_arr[support_Vector.get("HIN_QF")] = HIN_QF_count/global_count;
		features_arr[support_Vector.get("HIN_QC")] = HIN_QC_count/global_count;
		features_arr[support_Vector.get("HIN_QO")] = HIN_QO_count/global_count;
		features_arr[support_Vector.get("HIN_CL")] = HIN_CL_count/global_count;
		features_arr[support_Vector.get("HIN_INTF")] = HIN_INTF_count/global_count;
		features_arr[support_Vector.get("HIN_INJ")] = HIN_INJ_count/global_count;
		features_arr[support_Vector.get("HIN_NEG")] = HIN_NEG_count/global_count;
		features_arr[support_Vector.get("HIN_UT")] = HIN_UT_count/global_count;
		features_arr[support_Vector.get("HIN_SYM")] = HIN_SYM_count/global_count;
		features_arr[support_Vector.get("HIN_NNC")] = HIN_NNC_count/global_count;
		features_arr[support_Vector.get("HIN_NNPC")] = HIN_NNPC_count/global_count;
		features_arr[support_Vector.get("HIN_RDP")] = HIN_RDP_count/global_count;
		features_arr[support_Vector.get("HIN_ECH")] = HIN_ECH_count/global_count;
		features_arr[support_Vector.get("HIN_UNK")] = HIN_UNK_count/global_count;
		features_arr[support_Vector.get("HIN_DEFAULT")] = HIN_DEFAULT_count/global_count;
		
		return features_arr;
	}
	
	public static double[] createVector_addPMI(String line, double []features_arr, TreeMap<String, Integer> support_Vector, String PMIDictFile) throws Exception{
		double pos_count=0, neg_count=0, neut_count=0;
		for (String ngram : ngrams(1, line))
		{
			if(PMI_word_score.containsKey(ngram)){
				PMCount p = PMI_word_score.get(ngram);
				double sum = p.getPosCount() + p.getNegCount() + p.getNeutralCount();
				pos_count = pos_count + p.getPosCount()/6700;
				neg_count = neg_count + p.getNegCount()/6700;
				neut_count = neut_count + p.getNeutralCount()/6700;
			}
		}
		features_arr[support_Vector.get("PMI_POS")] = pos_count;
		features_arr[support_Vector.get("PMI_NEG")] = neg_count;
		features_arr[support_Vector.get("PMI_NEUTRAL")] = neut_count;
		
		return features_arr;
	}
	
	public static void createFeatureVector(String input_File, String output_File,
			String norm_neg_input_File, TreeMap<String, Integer> support_Vector, 
			String EnglishSentiWordNetPath, String HindiSentiWordNetPath, 
			String EnglishPOSCountsFilePath, String HindiPOSCountsFilePath, 
			String PMIDictFile) throws Exception{
		
		SentiWordNet s = new SentiWordNet(EnglishSentiWordNetPath);
		loadMaps(HindiSentiWordNetPath);
		loadPMIDictionary(PMIDictFile);
		long t1 = System.currentTimeMillis();
		
		double []features_arr = new double[support_Vector.size()+1];

		BufferedReader br = new BufferedReader(new FileReader(input_File));
		BufferedReader br_norm_neg = new BufferedReader(new FileReader(norm_neg_input_File));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output_File));
		
		BufferedReader br_EN_POS_Count = new BufferedReader(new FileReader(EnglishPOSCountsFilePath));
		BufferedReader br_HIN_POS_Count = new BufferedReader(new FileReader(HindiPOSCountsFilePath));
		String line_EN_POS_Count = null;
		String line_HIN_POS_Count = null;
		String line_norm_neg = null;
		
		String sCurrentLine = null;
		int lineNo = 0;
		while ((sCurrentLine = br.readLine()) != null)						//For each line in train dataset, create vector
		{
			line_EN_POS_Count = br_EN_POS_Count.readLine();
			line_HIN_POS_Count = br_HIN_POS_Count.readLine();
			String s_Cur_line_norm_neg = br_norm_neg.readLine();
			
			line_norm_neg = s_Cur_line_norm_neg.split("\t")[0];
			
			java.util.Arrays.fill(features_arr, 0);
			lineNo++;
			sCurrentLine = sCurrentLine.toLowerCase();
			String [] splitLine = sCurrentLine.split("\t");
			String line = splitLine[0];
			
			features_arr = createVector_add_n_grams(line_norm_neg, features_arr, support_Vector);
			features_arr = createVector_addSentiLbels(s, line, features_arr, support_Vector);
			features_arr = createVector_addSmileys(line, features_arr, support_Vector);
			//features_arr = createVector_addEnglishPOSTagCounts(line_EN_POS_Count, features_arr, support_Vector);
			//features_arr = createVector_addHindiPOSTagCounts(line_HIN_POS_Count, features_arr, support_Vector);
			//features_arr = createVector_addPMI(line_norm_neg, features_arr, support_Vector, PMIDictFile);

			String classLabel = "0";
			try{
				classLabel = splitLine[1];
			}
			catch(Exception e){
				System.out.println(sCurrentLine + "   ERRORRO  TRAIN   " + lineNo);
			}
			writeToFile(bw, features_arr, classLabel, support_Vector);
		}
		bw.close();
		br.close();
		br_EN_POS_Count.close();
		br_HIN_POS_Count.close();
		br_norm_neg.close();
		long t2 = System.currentTimeMillis();
		System.out.println("Feature vector created in "+(t2-t1)+" sec. Output File Name: "+output_File);
	}
}
