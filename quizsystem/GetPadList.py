from pathlib import Path

quizSystemPath = Path("../saves/")
cardPadList = list(quizSystemPath.glob("*.csv"))
for cardPad in cardPadList:
    print(str(cardPad).split('/')[2])
