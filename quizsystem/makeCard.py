import sys

args = sys.argv

with open('../saves/'+args[1]+'.csv','a') as padFile:
    padFile.write(args[2]+','+args[3]+'\n')
