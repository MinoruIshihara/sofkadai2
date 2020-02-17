import download
import upload
from pathlib import Path
from datetime import datetime
import os

def main():
	download.download()

	saveFilePath = Path("../cache/downloaded/")
	timeDateFileList = list(saveFilePath.glob("timeDate.txt"))

	if len(timeDateFileList) == 0:
		print("not found log files");
		upload.upload()
	else:
		localTimeObj2=None
		remoteTimeObj2=None
		with open('../cache/downloaded/timeDate.txt','r') as timeDateFile:
			timeDateLogs = timeDateFile.readlines()
			remoteTime = timeDateLogs[len(timeDateLogs)-1].split(';')[0].split('\n')[0]
			remoteTimeSprObj = datetime.strptime(remoteTime,'%Y-%m-%d %H:%M:%S')
			remoteTimeObj2 = datetime(remoteTimeSprObj.year, remoteTimeSprObj.month, remoteTimeSprObj.day, remoteTimeSprObj.hour,remoteTimeSprObj.minute, 0,0,None)
			print(remoteTimeObj2)

		with open('../saves/timeDate.txt','r') as timeDateFile:
			timeDateLogs = timeDateFile.readlines()
			localTime = timeDateLogs[len(timeDateLogs)-1].split(';')[0].split('\n')[0]
			localTimeSprObj = datetime.strptime(localTime,'%Y-%m-%d %H:%M:%S')
			localTimeObj2 = datetime(localTimeSprObj.year, localTimeSprObj.month, localTimeSprObj.day, localTimeSprObj.hour,localTimeSprObj.minute, 0,0,None)
			print(localTimeObj2)
		if remoteTimeObj2 < localTimeObj2:
			print("Upload")
			upload.upload()
		else:
			print('copy')
			os.system('mv ../cache/downloaded/* ../saves')

if __name__ == '__main__':
	main()
