from __future__ import print_function
import pickle
import os.path
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
import io
from apiclient.http import MediaIoBaseDownload


SCOPES = ['https://www.googleapis.com/auth/drive.appdata']

def main():
	creds = None
	fileID = []
	fileName = [] 

	if os.path.exists('token.pickle'):
		with open('token.pickle', 'rb') as token:
			creds = pickle.load(token)
	if not creds or not creds.valid:
		if creds and creds.expired and creds.refresh_token:
			creds.refresh(Request())
		else:
			flow = InstalledAppFlow.from_client_secrets_file(
				'credentials.json',SCOPES)
			creds = flow.run_local_server(port=0)
		with open('token.pickle','wb') as token:
			pickle.dump(creds,token)

	drive_service = build('drive', 'v3', credentials=creds)
	
	results = drive_service.files().list(
		spaces='appDataFolder',
		pageSize=10,
		fields="nextPageToken, files(id, name)").execute()
	items = results.get('files',[])

	if not items:
		print('No files found')
	else:
		for item in items:
			fileName.append(item['name'])
			fileID.append(item['id'])
			print(item['name'])
			print(item['id'])

	request = drive_service.files().get_media(fileId=fileID)
	fh = io.BytesIO()
	downloader = MediaIoBaseDownload(fh,request)
	done = False
	while done is False:
		status, done = downloader.next_chunk()
	print ("Download %d%%." % int(status.progress() * 100))



if __name__ == '__main__':
	main()
