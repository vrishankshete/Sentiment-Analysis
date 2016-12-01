package com.gagan.SentAnalysis.posTagging;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class ExtractHindiPOSTags
{
	static HashMap<String, Integer> posCounts= new HashMap<String, Integer>(){{
		  put("HIN_NN",0);  put("HIN_NST",0);
		  put("HIN_NNP",0);
		  put("HIN_PRP",0);
		  put("HIN_DEM",0);
		  put("HIN_VM",0);
		  put("HIN_VAUX",0);
		  put("HIN_JJ",0);
		  put("HIN_RB",0);
		  put("HIN_PSP",0);
		  put("HIN_RP",0);
		  put("HIN_CC",0);
		  put("HIN_QW",0);
		  put("HIN_QF",0);
		  put("HIN_QC",0);
		  put("HIN_QO",0);
		  put("HIN_CL",0);
		  put("HIN_INTF",0);
		  put("HIN_INJ",0);
		  put("HIN_NEG",0);
		  put("HIN_UT",0);
		  put("HIN_SYM",0);
		  put("HIN_NNC",0);
		  put("HIN_NNPC",0);
		  put("HIN_RDP",0);
		  put("HIN_ECH",0);
		  put("HIN_UNK",0);
		  put("HIN_DEFAULT",0);
	}};

	public static void main(String[] args) {
		
		if(args.length!=2)
		{
			System.out.println("Usage of ExtractHindiPOSTags is :   java ExtractHIndiPOSTags <input_file> <output_file_to_which_POSTAGS_to_be_appended_on_new_line>");
			System.exit(1);
		}
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(args[0])));
			FileWriter fw = new FileWriter(args[1],true);
			//fw.write(args[1]+"\t");
			String str=null, arr[];
			int count=0;
			while((str=br.readLine())!=null)
			{
				arr=str.split("\t");
				if(arr.length < 2)
					continue;
				if(!posCounts.containsKey("HIN_"+arr[2])){
					count = posCounts.get("HIN_DEFAULT");
					count++;
					posCounts.put("HIN_DEFAULT", count);
					continue;
				}
				count = posCounts.get("HIN_"+arr[2]);
				count++;
				posCounts.put("HIN_"+arr[2], count);
			}
			String temp=new String();
			for (String key : posCounts.keySet())
				temp = temp + key + ":" + posCounts.get(key) + "\t";
			temp=temp.trim();
			fw.write(temp+"\n");
			if(br!=null)
				br.close();
			if(fw!=null)
				fw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
