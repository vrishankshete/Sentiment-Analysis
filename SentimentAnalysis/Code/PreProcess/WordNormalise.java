package com.lokesh.SentAnalysis.Preprocess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordNormalise {


	public static String getNormalisedWord(String word){


		StringBuilder result = new StringBuilder();

		Set<Character> vowels = new HashSet<Character>();
		vowels.add('a');
		vowels.add('e');
		vowels.add('i');
		vowels.add('o');
		vowels.add('u');

		Pattern regex = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!]");
		String b = word.replaceAll("(.)\\1+", "$1");
		int length = b.length();

		for(int index=0;index<length;index++){

			if(((vowels.contains(b.charAt(index))) && (index != 0 && index !=length-1)) || 
					regex.equals(b.charAt(index))){
				continue;
			}
			else{	
				result.append(b.charAt(index));
			}
		}

		return result.toString();

	}

	public static void main(String[] args) {


		ArrayList<String> input = new ArrayList<String>();

		input.add("a");
		input.add("aagya");
		for(int index =0 ;index < input.size(); index++){

			System.out.println(getNormalisedWord(input.get(index).toLowerCase()));
		}


	}

}
