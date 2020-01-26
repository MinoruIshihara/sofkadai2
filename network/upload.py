from __future__ import print_function
import pickle
import os.path
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
from apiclient.http import MediaFileUpload

SCOPES = ['https://www.googleapis.com/auth/drive']

def main():
	creds = None
	saveFolder = None

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

	page_token = None
	while True:
		results = drive_service.files().list(
			q="mimeType='application/vnd.google-apps.folder'",
			spaces='drive',
			fields="nextPageToken, files(id, name)",
			pageToken=page_token).execute()

		for folder in results.get('files',[]):
			print(folder.get('name'))
			print(folder.get('id'))
			if folder.get('name') == 'quiz_save':
				saveFolder = folder.get('id')
		page_token = results.get('nextPageToken',None)
		if page_token is None:
			break

	file_metadata = {
		'name' : 'new.json',
		'parents' : [saveFolder]
	}
	media = MediaFileUpload('files/new.json',
				mimetype='application/json')
	file = drive_service.files().create(body=file_metadata,media_body=media,fields='id').execute()

	print('File ID:')
	print(file.get('id'))

if __name__ == '__main__':
	main()
