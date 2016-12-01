package com.vrishank.SentAnalysis.Classifier;

/*This is the main class where feature vectors are created
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TrainClassifier
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
	
	public static TreeMap<String, Integer> support_Vector = new TreeMap<String, Integer>();
	public static int indexCounter = 0;
	public static int n_gram = 2;

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

	public static void addInMap(String str)
	{
		String []arr = str.trim().split("\\s+");
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<arr.length;i++){
			if(!stopwordHashSet.contains(arr[i])){
				sb.append(arr[i]+" ");
			}
		}
		
		str = sb.toString().trim();
		for (int n = 1; n <= n_gram; n++)
		{
			for (String ngram : ngrams(n, str))
			{
				if(!stopwordHashSet.contains(ngram)){
					if(!support_Vector.containsKey(ngram))
					{
						support_Vector.put(ngram, indexCounter++);
					}
				}
			}
		}
	}

	public static void generateVector_add_n_grams(String input_train){
		BufferedReader br = null;
		String sCurrentLine = null;
		try
		{
			br = new BufferedReader(new FileReader(input_train));
			while ((sCurrentLine = br.readLine()) != null)
			{
				sCurrentLine = sCurrentLine.toLowerCase();
				addInMap(sCurrentLine.split("\t")[0]); 					//addInMap will create a vector of unique unigrams, bigrams, etc
			}
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

 	public static void generateVector_addSentiLabels(){
		support_Vector.put("HIN_POSITIVE_COUNT", indexCounter++);
		support_Vector.put("HIN_NEGATIVE_COUNT", indexCounter++);
		support_Vector.put("HIN_MAX_POSITIVE", indexCounter++);
		support_Vector.put("HIN_MAX_NEGATIVE", indexCounter++);
		support_Vector.put("HIN_TOT_POSITIVE", indexCounter++);
		support_Vector.put("HIN_TOT_NEGATIVE", indexCounter++);
		support_Vector.put("EN_POSITIVE_COUNT", indexCounter++);
		support_Vector.put("EN_NEGATIVE_COUNT", indexCounter++);
		support_Vector.put("EN_MAX_POSITIVE", indexCounter++);
		support_Vector.put("EN_MAX_NEGATIVE", indexCounter++);
		support_Vector.put("EN_TOT_POSITIVE", indexCounter++);
		support_Vector.put("EN_TOT_NEGATIVE", indexCounter++);
	}
 	
	public static void generateVector_addEnglishPOSTagCounts(){
		support_Vector.put("EN_N", indexCounter++);
		support_Vector.put("EN_O", indexCounter++);
		support_Vector.put("EN_S", indexCounter++);
		support_Vector.put("EN_^", indexCounter++);
		support_Vector.put("EN_Z", indexCounter++);
		support_Vector.put("EN_L", indexCounter++);
		support_Vector.put("EN_M", indexCounter++);
		support_Vector.put("EN_V", indexCounter++);
		support_Vector.put("EN_A", indexCounter++);
		support_Vector.put("EN_R", indexCounter++);
		support_Vector.put("EN_!", indexCounter++);
		support_Vector.put("EN_D", indexCounter++);
		support_Vector.put("EN_P", indexCounter++);
		support_Vector.put("EN_&", indexCounter++);
		support_Vector.put("EN_T", indexCounter++);
		support_Vector.put("EN_X", indexCounter++);
		support_Vector.put("EN_Y", indexCounter++);
		support_Vector.put("EN_#", indexCounter++);
		support_Vector.put("EN_@", indexCounter++);
		support_Vector.put("EN_~", indexCounter++);
		support_Vector.put("EN_U", indexCounter++);
		support_Vector.put("EN_E", indexCounter++);
		support_Vector.put("EN_$", indexCounter++);
		support_Vector.put("EN_,", indexCounter++);
		support_Vector.put("EN_G", indexCounter++);
	}
	
	public static void generateVector_addHindiPOSTagCounts(){
		support_Vector.put("HIN_NN", indexCounter++);
		support_Vector.put("HIN_NST", indexCounter++);
		support_Vector.put("HIN_NNP", indexCounter++);
		support_Vector.put("HIN_PRP", indexCounter++);
		support_Vector.put("HIN_DEM", indexCounter++);
		support_Vector.put("HIN_VM", indexCounter++);
		support_Vector.put("HIN_VAUX", indexCounter++);
		support_Vector.put("HIN_JJ", indexCounter++);
		support_Vector.put("HIN_RB", indexCounter++);
		support_Vector.put("HIN_PSP", indexCounter++);
		support_Vector.put("HIN_RP", indexCounter++);
		support_Vector.put("HIN_CC", indexCounter++);
		support_Vector.put("HIN_QW", indexCounter++);
		support_Vector.put("HIN_QF", indexCounter++);
		support_Vector.put("HIN_QC", indexCounter++);
		support_Vector.put("HIN_QO", indexCounter++);
		support_Vector.put("HIN_CL", indexCounter++);
		support_Vector.put("HIN_INTF", indexCounter++);
		support_Vector.put("HIN_INJ", indexCounter++);
		support_Vector.put("HIN_NEG", indexCounter++);
		support_Vector.put("HIN_UT", indexCounter++);
		support_Vector.put("HIN_SYM", indexCounter++);
		support_Vector.put("HIN_NNC", indexCounter++);
		support_Vector.put("HIN_NNPC", indexCounter++);
		support_Vector.put("HIN_RDP", indexCounter++);
		support_Vector.put("HIN_ECH", indexCounter++);
		support_Vector.put("HIN_UNK", indexCounter++);
		support_Vector.put("HIN_DEFAULT", indexCounter++);
	}
 	
 	public static void generateVector_addSmileys(){
 		support_Vector.put("0_POS", indexCounter++);
 		support_Vector.put("1_NEG", indexCounter++);
 		support_Vector.put("2_PLAYFUL", indexCounter++);
 		support_Vector.put("3_LOVE", indexCounter++);
 	}
 	
 	public static void generateVector_addPMI(){
 		support_Vector.put("PMI_POS", indexCounter++);
 		support_Vector.put("PMI_NEG", indexCounter++);
 		support_Vector.put("PMI_NEUTRAL", indexCounter++);
 	}
	
	public static void dumpSupportVectorToFile(String model_FileNamewithPath) throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter(model_FileNamewithPath));
		int flag = 0;
		for(Map.Entry<String, Integer> m: support_Vector.entrySet()){
			if(flag==0){
				bw.write(m.getKey()+"\t"+m.getValue());
				flag=1;
			}
			else
			{
				bw.write("\n" + m.getKey() + "\t"+m.getValue());
			}
		}
		bw.close();
	}

	public static void main(String[] args) throws Exception
	{
		/*
		 * Args:
		 * 0  Input test File Name with Path
		 * 1  Output test vector File Name with Path
		 * 2  Support vector model file Name with full path
		 * 3  English Sentiword Net file name (full path)
		 * 4  Hindi Sentiword Net file name (full path)
		 * 5  English POS tags counts file name with full path
		 * 6  Hindi POS tags counts file name with full path
		 * 7  PMI Dictionary with full path
		 * 8  Normalized Negation handled Input Test
		 */
		if(args.length != 9){
			System.out.println("ERROR... Usage: java ipFileNameWithPath opFileNameWithPath " +
					"model_FileNamewithPath englishSentiWordNet hindiSentiWordNet " +
					"englishPOSTagFileName, hindiPOSTagFileName, PMIDictFile");
			System.exit(0);
		}
		String ipFileNameWithPath = args[0];
		String opFileNameWithPath = args[1];
		String model_FileNamewithPath = args[2];
		String englishSentiWordNet = args[3];
		String hindiSentiWordNet = args[4];
		String englishPOSTagFileName = args[5];
		String hindiPOSTagFileName = args[6];
		String PMIDictFile = args[7];
		String norm_neg_input_File = args[8];
		
		try
		{
			generateVector_add_n_grams(norm_neg_input_File);
			generateVector_addSentiLabels();
			generateVector_addSmileys();
			//generateVector_addEnglishPOSTagCounts();
			//generateVector_addHindiPOSTagCounts();
			//generateVector_addPMI();
			
			dumpSupportVectorToFile(model_FileNamewithPath);
			System.out.println("Vector SIZE: "+support_Vector.size());
			System.out.println("Creating Train Feature Vector..");
			FeatureVector.createFeatureVector(ipFileNameWithPath, opFileNameWithPath, 
					norm_neg_input_File, support_Vector, englishSentiWordNet, hindiSentiWordNet, 
					englishPOSTagFileName, hindiPOSTagFileName, PMIDictFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
