import sys
#pos_tags_dict=dict.fromkeys(['N','O','^','S','Z','V','A','R','!','D','P','&','T','X','#','@','~','U','E','$','G','L','M','Y'])


def pos_tagger():
	f=open(sys.argv[1],'r')
	line=f.readline();
	#line='sdfdf isn\'t god of cricket : the best , in the world . Really ?	^ V ^ P N , D A , P D N , R ,'
	while(line):
		pos_tags_dict={'!': 0, '#': 0, '$': 0, '&': 0, 'A': 0, '@': 0, 'E': 0, 'D': 0, 'G': 0, 'M': 0, 'L': 0, 'O': 0, 'N': 0, 'P': 0, 'S': 0, 'R': 0, 'U': 0, 'T': 0, 'V': 0, 'Y': 0, 'X': 0, 'Z': 0, '^': 0, '~': 0 ,',':0}
		arr=line[:-1]
		postags_tweet=arr.split()
		for it in postags_tweet:
			pos_tags_dict[it] += 1
		sorted(pos_tags_dict)
		temp_str=''
		for x in pos_tags_dict:
			temp_str= temp_str +'EN_'+x+':'+str(pos_tags_dict[x]) + '\t'
		print temp_str.strip('\t')
		line=f.readline();
	f.close()
pos_tagger()
