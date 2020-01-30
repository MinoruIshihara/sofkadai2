import download
import upload
from pathlib import Path
from datetime import datetime

def main():
	download.download()

	saveFilePath = Path("./downloaded/")
	timeDateFileList = list(saveFilePath.glob("timeDate.txt"))

	if len(timeDateFileList) == 0:
		print("not found log files");
		upload.upload()
	else:
		with open('./downloaded/timeDate.txt','r') as timeDateFile:
			timeDateLogs = timeDateFile.readlines()
			remoteTime = timeDateLogs[len(timeDateLogs)-1].split(';')[0].split('\n')[0]
			tdate = datetime.date(tdatetime.year, tdatetime.month, tdatetime.day)
			print(remoteTime)

		with open('../saves/timeDate.txt','r') as timeDateFile:
			timeDateLogs = timeDateFile.readlines()
			localTime = timeDateLogs[len(timeDateLogs)-1].split(';')[0].split('\n')[0]
			print(localTime)
		if datetime.strptime(remoteTime, '%Y-%m-%dT%H:%M:%S.') < datetime.strptime(localTime, '%Y-%m-%dT%H:%M:%S.'):
			print("Upload")
			upload.upload()
		else:
			print('copy')

if __name__ == '__main__':
	main()
