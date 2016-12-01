#!/bin/bash
script_home=`pwd`
inputTrainFile="$script_home""/TaggedComments/train_1.txt"
inputTestFile="$script_home""/TaggedComments/test.txt"

trainOnlyComments="$script_home""/Temp/train_only_comments.txt"
testOnlyComments="$script_home""/Temp/test_only_comments.txt"

cut -f1 "$inputTrainFile" > "$trainOnlyComments"
cut -f1 "$inputTestFile" > "$testOnlyComments"

trainHindiPOSTagFile="$script_home""/POS_Tags_Counts/trainHindiPOScounts.txt"
testHindiPOSTagFile="$script_home""/POS_Tags_Counts/testHindiPOScounts.txt"

#rm "$trainHindiPOSTagFile" 2>/dev/null
#rm "$testHindiPOSTagFile" 2>/dev/null
codePath="$script_home""/Code/PoSTagger/"

javac -d "$codePath" "$codePath""/ExtractHindiPOSTags.java"

echo "Starting Tagging..."
while read line
do
	echo "$line" | shallow_parser_hin --in_encoding=utf --out_encoding=wx
	java -cp "$codePath" com.gagan.SentAnalysis.posTagging.ExtractHindiPOSTags "$script_home""/OUTPUT.tmp/postagger.tmp" "$trainHindiPOSTagFile"
done < "$trainOnlyComments"

echo "Train File done... FileName: $trainHindiPOSTagFile"
rm -rf "OUTPUT.tmp"

while read line
do
	echo "$line" | shallow_parser_hin --in_encoding=utf --out_encoding=wx
	java -cp "$codePath" com.gagan.SentAnalysis.posTagging.ExtractHindiPOSTags "$script_home""/OUTPUT.tmp/postagger.tmp" "$testHindiPOSTagFile"
done < "$testOnlyComments"

echo "Test File done... FileName: $testHindiPOSTagFile"
rm -rf "OUTPUT.tmp"
