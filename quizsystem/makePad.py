import sys

args = sys.argv

if len(args) == 2:
    with open('../saves/'+args[1]+'.csv','w') as padFile:
        pass
else:
    print('err argument')

