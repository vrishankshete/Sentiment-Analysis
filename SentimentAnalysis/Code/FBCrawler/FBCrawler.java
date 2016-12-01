package com.vrishank.SentAnalysis.FBCrawler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
public class FBCrawler {

	/**
	 * Start FB Crawler
	 * Coded By: Vrishank Shete
	 * This class is used to get comments by users on posts by popular hindi newspapers and 
	 * Indian famous personalities.
	 * Access Token from facebook graph api is used in the code
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/*
		 * Args:
		 * 1  FB Page ID
		 * 2  Folder path where output files wanted to be stored
		 * 3  FB Access token (from https://developers.facebook.com/tools/explorer/)
		 */
		
		String pageId = args[0];
		String comments_out_folderName = args[1];
		String access_token = args[2];
		/* Access Token Expires in 2-3 hours
		 */
		
		String comments_outFileName = pageId+"_comments.txt";
		
		URL url = new URL("https://graph.facebook.com/"+pageId+"/posts?access_token="+access_token);
		System.setProperty("https.proxyHost", "proxy.iiit.ac.in");
		System.setProperty("https.proxyPort", "8080");
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(comments_out_folderName + comments_outFileName));
		
		System.out.println("Crawling FB Data...");
		
		/* JSON data is returned by facebook.
		 */
		try (InputStream is = url.openStream();
				JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();
			JsonArray results = obj.getJsonArray("data");
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				if(result.getJsonObject("comments") == null)
					continue;
				JsonObject js_nested = result.getJsonObject("comments");
				JsonArray results1 = js_nested.getJsonArray("data");
				for (JsonObject result1 : results1.getValuesAs(JsonObject.class)) {
					bw.write(result1.getString("message")+"\n");
				}
				try{
				if(js_nested.containsKey("paging")){
					while(js_nested.getJsonObject("paging").containsKey("next")){
						URL url_after = new URL(js_nested.getJsonObject("paging").getString("next"));
						try (InputStream is_after = url_after.openStream();
								JsonReader rdr_after = Json.createReader(is_after)) {

							js_nested = rdr_after.readObject();
							JsonArray results_after = js_nested.getJsonArray("data");
							for (JsonObject result_after1 : results_after.getValuesAs(JsonObject.class)) {
								bw.write(result_after1.getString("message")+"\n");
							}
						}
					}
				}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
			bw.close();
		}
	}
	/**
	 * End FB Crawler
	 */
}
