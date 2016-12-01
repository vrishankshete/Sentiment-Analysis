#! /bin/bash
script_home=`pwd`

TaggedCommentsPath="$script_home""/TaggedComments/"
PosTagsCountsDirPath="$script_home""/POS_Tags_Counts/"
Train_file="train.txt"
Test_file="test.txt"
Train_neg_norm_file="train_neg_norm.txt"
Test_neg_norm_file="test_neg_norm.txt"

trainEnglishPOSTagFileName="$PosTagsCountsDirPath""trainEnglishPOScounts.txt"
testEnglishPOSTagFileName="$PosTagsCountsDirPath""testHindiPOScounts.txt"
trainHindiPOSTagFileName="$PosTagsCountsDirPath""trainHindiPOScounts.txt"
testHindiPOSTagFileName="$PosTagsCountsDirPath""testHindiPOScounts.txt"

SVMInputPath="$script_home""/Input_SVM_Data/"
ModelFileNameWithPath="$script_home""/Model/vector_train_model"
HindiSentiWordNet="$script_home""/Dictionaries/SentiWordNet/Hindi_Word_SentiScore_Final.txt"
EnglishSentiWordNet="$script_home""/Dictionaries/SentiWordNet/English_Word_SentiScore.txt"

PMIDictFile="$script_home""/Dictionaries/SentiWordNet/PMIDict.txt"


trainOutputFile="Vector_""$Train_file"
testOutputFile="Vector_""$Test_file"

cd "Code/Classifier/"
javac -d . *.java
java com.vrishank.SentAnalysis.Classifier.TrainClassifier "$TaggedCommentsPath""$Train_file" "$SVMInputPath""$trainOutputFile"  "$ModelFileNameWithPath" "$EnglishSentiWordNet" "$HindiSentiWordNet" "$trainEnglishPOSTagFileName" "$trainHindiPOSTagFileName" "$PMIDictFile" "$TaggedCommentsPath""$Train_neg_norm_file"

java com.vrishank.SentAnalysis.Classifier.TestClassifier "$TaggedCommentsPath""$Test_file" "$SVMInputPath""$testOutputFile"  "$ModelFileNameWithPath" "$EnglishSentiWordNet" "$HindiSentiWordNet" "$testEnglishPOSTagFileName" "$testHindiPOSTagFileName" "$PMIDictFile" "$TaggedCommentsPath""$Test_neg_norm_file"
echo "Vector creation complete..."

cd "$script_home"
#liblinear-1.96
#libsvm-3.20
cd "Classifier/libsvm-3.20/"
./svm-train -t 2 -c 300000 "$SVMInputPath""/$trainOutputFile" "$script_home""/Model/train.model"
./svm-predict "$SVMInputPath""/$testOutputFile" "$script_home""/Model/train.model" "$script_home""/Output/out.txt"
#cd "Classifier/liblinear-1.96/"
#./train "$SVMInputPath""/$trainOutputFile" "$script_home""/Model/train.model"
#./predict "$SVMInputPath""/$testOutputFile" "$script_home""/Model/train.model" "$script_home""/Output/out.txt"

cd "$script_home"
