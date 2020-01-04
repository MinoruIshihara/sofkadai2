import sys

args = sys.argv

with open('../saves/'+args[1]+'.csv','r') as padFile:
    cardsString = padFile.read()
    cardlist = cardsString.split('\n')
    print(cardlist[int(args[2])].split(',')[1])
