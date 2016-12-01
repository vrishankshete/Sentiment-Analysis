#! /bin/bash

cur_dir=`pwd`

pageId="garbagebin"

FB_Raw_data_folder_path="$cur_dir""/FB_Crawled_data/Raw/"
CM_comments_folder_path="$cur_dir""/FB_Crawled_data/CodeMixed/"

#English Excluded hindi dictionary will give less results but more accurate...  Hindi_Words_English_Excluded.txt & Hindi_Words.txt
Hindi_WordList="$cur_dir""/Dictionaries/Words_List/Hindi_Words.txt"
English_WordList="$cur_dir""/Dictionaries/Words_List/En_Words.txt"

fb_access_token="CAACEdEose0cBALbNKB41er0CSf3USkASRSfpv8YELUAldUAUZBGuNQOzKjvlXggQv7QVcoskH6zkxGFNwdSktIJQih0MFpdLZAbSYdjYHFq9aeGaH1JPk7P2M21otwU3yPW1fauY4qslInrJTHhZBndZAW6xZAVQFjTIAmGX4FUE5Qvq1chKkd9b1MLTMtrHTauRGHzJQL5TZCtuvdbhQS";
#Expires in 2-3 hours. Get it from https://developers.facebook.com/tools/explorer/

cd "Code/FBCrawler/"
javac -cp javax.json-1.0.2.jar -d . FBCrawler.java
java -cp :javax.json-1.0.2.jar com.vrishank.SentAnalysis.FBCrawler.FBCrawler "$pageId" "$FB_Raw_data_folder_path" "$fb_access_token"
echo "FB Data crawled"

javac -d . CodeMixed.java
java com.vrishank.SentAnalysis.FBCrawler.CodeMixed  "$pageId" "$FB_Raw_data_folder_path" "$CM_comments_folder_path" "$Hindi_WordList" "$English_WordList"
echo "Code mixed phrases extracted"

cd "$cur_dir"
