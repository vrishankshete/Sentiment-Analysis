#! /bin/bash

script_home=`pwd`

cut -f1 "$script_home""/TaggedComments/train.txt" > "$script_home""/Temp/train_only_comments.txt"
cut -f1 "$script_home""/TaggedComments/test.txt" > "$script_home""/Temp/test_only_comments.txt"

"$script_home""/POSTagger/ark-tweet-nlp-0.3.2/./runTagger.sh" "$script_home""/Temp/train_only_comments.txt" > "$script_home""/Temp/train_Temp_tagger_out.txt"

"$script_home""/POSTagger/ark-tweet-nlp-0.3.2/./runTagger.sh" "$script_home""/Temp/test_only_comments.txt" > "$script_home""/Temp/test_Temp_tagger_out.txt"

python "$script_home""/POSTagger/get_tokens_or_tags.py" "$script_home""/Temp/train_Temp_tagger_out.txt" 1 > "$script_home""/Temp/trainEnglishTags.txt"

python "$script_home""/POSTagger/get_tokens_or_tags.py" "$script_home""/Temp/test_Temp_tagger_out.txt" 1 > "$script_home""/Temp/testEnglishTags.txt"

python "$script_home""/POSTagger/get_pos_count.py" "$script_home""/Temp/trainEnglishTags.txt" > "$script_home""/POS_Tags_Counts/trainEnglishPOScounts.txt"

python "$script_home""/POSTagger/get_pos_count.py" "$script_home""/Temp/testEnglishTags.txt" > "$script_home""/POS_Tags_Counts/testEnglishPOScounts.txt"
