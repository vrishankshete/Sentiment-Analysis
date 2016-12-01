import sys

f = open(sys.argv[1], "r")
col = int(sys.argv[2])
l = []
for line in f:
	tem = line[:-1]
	tem = tem.split('\t')

	l.append(tem[col])

f.close()

for i in l:
	print i

