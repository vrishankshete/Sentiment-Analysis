#! /bin/bash
echo "Script started..."
script_home=`pwd`

ModelFileNameWithPath="$script_home""/Model/vector_train_model"
HindiSentiWordNet="$script_home""/Dictionaries/SentiWordNet/Hindi_Word_SentiScore_Final.txt"
EnglishSentiWordNet="$script_home""/Dictionaries/SentiWordNet/English_Word_SentiScore.txt"
testFile="test_user"
testOutputFile="Vector_""$testFile"

PosTagsCountsDirPath="$script_home""/POS_Tags_Counts/"
testEnglishPOSTagFileName="$script_home""/Temp/testEnglishPOScounts.txt"
testHindiPOSTagFileName="$script_home""/Temp/testHindiPOScounts.txt"
PMIPath="$script_home""/Dictionaries/SentiWordNet/PMIDict.txt"

temp_dir="$script_home""/Temp/"
cur_time=`date +%Y%m%d%H%M%S`

echo "$1" > "$temp_dir""$cur_time"

paste "$temp_dir""$cur_time" "$temp_dir""label" -d "\t" > "$temp_dir""tempFi"

python "$script_home""/POSTagger/"normalize_url_uname_lower.py "$temp_dir""tempFi" > "$temp_dir""$testFile"

#START English POS

"$script_home""/POSTagger/ark-tweet-nlp-0.3.2/./runTagger.sh" "$temp_dir""$cur_time" > "$script_home""/Temp/test_Temp_tagger_out.txt"
python "$script_home""/POSTagger/get_tokens_or_tags.py" "$script_home""/Temp/test_Temp_tagger_out.txt" 1 > "$script_home""/Temp/testEnglishTags.txt"
python "$script_home""/POSTagger/get_pos_count.py" "$script_home""/Temp/testEnglishTags.txt" > "$testEnglishPOSTagFileName"

#END ENGLISH POS

#START HINDI POS
codePath="$script_home""/Code/PoSTagger/"
rm "$testHindiPOSTagFileName" 2>/dev/null
export SHALLOW_PARSER_HIN=/home/ubuntu/sampark/shallow_parser_hin
export PATH=$PATH:$SHALLOW_PARSER_HIN/bin/sys/hin/

javac -d "$codePath" "$codePath/"*.java
while read line
do
	echo "$line" | shallow_parser_hin --in_encoding=utf --out_encoding=wx
	java -cp "$codePath" com.gagan.SentAnalysis.posTagging.ExtractHindiPOSTags "$script_home""/OUTPUT.tmp/postagger.tmp" "$testHindiPOSTagFileName"
done < "$temp_dir""$cur_time"
rm -rf "OUTPUT.tmp"

#END HINDI POS

#PREPROCESS START
codePath="$script_home""/Code/PreProcess/"
en_dict_File="$script_home""/Dictionaries/Words_List/En_Words.txt"
negTestFile="$temp_dir""neg_$testFile"
normNegTestFile="$temp_dir""norm_neg_$testFile"

javac -d "$codePath" "$codePath/"*.java
java -cp "$codePath" com.lokesh.SentAnalysis.Preprocess.NegationHandling "$temp_dir""$testFile" "$negTestFile" "$en_dict_File"
java -cp "$codePath" com.lokesh.SentAnalysis.Preprocess.GetFinalNormalised "$negTestFile" "$normNegTestFile" "$en_dict_File"

#PREPROCESS END

#GENERATE VECTOR
cd "Code/Classifier/"
javac -d . *.java
java com.vrishank.SentAnalysis.Classifier.TestClassifier "$temp_dir""$testFile" "$temp_dir""$testOutputFile"  "$ModelFileNameWithPath" "$EnglishSentiWordNet" "$HindiSentiWordNet" "$testEnglishPOSTagFileName" "$testHindiPOSTagFileName" "$PMIPath" "$normNegTestFile"

cd "$script_home"
cd "Classifier/libsvm-3.20/"
./svm-predict "$temp_dir""$testOutputFile" "$script_home""/Model/train.model" "$temp_dir""out.txt"
cd "$script_home"

label=`cat "$temp_dir""out.txt"`

#rm "$temp_dir""$cur_time" "$temp_dir""tempFi" "$temp_dir""$testFile" "$script_home""/Temp/test_Temp_tagger_out.txt" "$script_home""/Temp/testEnglishTags.txt" "$testEnglishPOSTagFileName" "$testHindiPOSTagFileName" "$negTestFile" "$normNegTestFile" "$temp_dir""$testOutputFile"

if [[ label -eq "0" ]]
then
	echo "Predicted Label: Neutral" > "$temp_dir""out.txt"
elif [[ label -eq "1" ]]
then
	echo "Predicted Label: Positive" > "$temp_dir""out.txt"
else
	echo "Predicted Label: Negative" > "$temp_dir""out.txt"
fi

echo "$1	$label" >> "$temp_dir/history.txt"

cat "$temp_dir""out.txt"
