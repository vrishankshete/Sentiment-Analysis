#! /bin/bash
script_home=`pwd`

en_dict_File="$script_home""/Dictionaries/Words_List/En_Words.txt"

inputTrainFile="$script_home""/TaggedComments/train.txt"
inputTestFile="$script_home""/TaggedComments/test.txt"

negTrainFile="$script_home""/TaggedComments/train_neg.txt"
negTestFile="$script_home""/TaggedComments/test_neg.txt"

normNegTrainFile="$script_home""/TaggedComments/train_neg_norm.txt"
normNegTestFile="$script_home""/TaggedComments/test_neg_norm.txt"

PMIDictFile="$script_home""/Dictionaries/SentiWordNet/PMIDict.txt"

trainOnlyComments="$script_home""/Temp/train_only_comments.txt"
testOnlyComments="$script_home""/Temp/test_only_comments.txt"


codePath="$script_home""/Code/PreProcess/"

#cd "Code/PoSTagger/"
javac -d "$codePath" "$codePath/"*.java

java -cp "$codePath" com.lokesh.SentAnalysis.Preprocess.NegationHandling "$inputTrainFile" "$negTrainFile" "$en_dict_File"

java -cp "$codePath" com.lokesh.SentAnalysis.Preprocess.NegationHandling "$inputTestFile" "$negTestFile" "$en_dict_File"

java -cp "$codePath" com.lokesh.SentAnalysis.Preprocess.LoadPMIMap "$negTrainFile" "$PMIDictFile" "$en_dict_File"

java -cp "$codePath" com.lokesh.SentAnalysis.Preprocess.GetFinalNormalised "$negTrainFile" "$normNegTrainFile" "$en_dict_File"

java -cp "$codePath" com.lokesh.SentAnalysis.Preprocess.GetFinalNormalised "$negTestFile" "$normNegTestFile" "$en_dict_File"
