import sys
import re

####################
#	For normalizing - url , username , emoticons
#
###################
#emoticons_dict={':-)':'pos_POL', ':)':'pos_POL', ':o)':'pos_POL', ':]':'pos_POL' ,':3':'pos_POL',':c)':'pos_POL' , ':D':'Extpos_POL', 'C:':'Extpos_POL', 'D8':'ExtNEG_POL', 'D;':'ExtNEG_POL', 'D=':'ExtNEG_POL', 'DX':'ExtNEG_POL', 'v.v':'ExtNEG_POL', ':|':'Neut_POL'}

f = open(sys.argv[1], "r")

entire_emo = []

for line in f:
	tem = line[:-1]
	tm=tem.split('\t') # split to separate the sentence and label
	t = tm[0].split() # split to get the words 
	tw = [] # empty to print atlast
	for i in t:
		n = re.sub('((www\.[^\s]+)|(https?://[^\s]+))', 'http/URL', i)
		n = re.sub('@[^\s]+', '@someUSER', n)
		tw.append(n.lower())

	print " ".join(tw)+ '\t' + tm[1]

f.close()
