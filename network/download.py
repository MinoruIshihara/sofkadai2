from __future__ import print_function
import pickle
import os.path
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
import io
from googleapiclient.http import MediaIoBaseDownload
import upload

SCOPES = ['https://www.googleapis.com/auth/drive']

def download():
	creds = None
	saveFolder = None
	saveFiles = []

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
#			print(folder.get('name'))
#			print(folder.get('id'))
			if folder.get('name') == 'quiz_save':
				saveFolder = folder.get('id')
		page_token = results.get('nextPageToken',None)
		if page_token is None:
			break

	print("'"+saveFolder+"' in parents")

	page_token = None
	while True:
		results = drive_service.files().list(
			q="'"+saveFolder+"' in parents and trashed = false",
			spaces='drive',
			fields="nextPageToken, files(id, name)",
			pageToken=page_token).execute()

		for rfile in results.get('files',[]):
#			print(rfile.get('name'))
#			print(rfile.get('id'))
			saveFiles.append(rfile)
		page_token = results.get('nextPageToken',None)
		if page_token is None:
			break
	for dfile in saveFiles:
		request = drive_service.files().get_media(fileId=dfile.get('id'))
		fh = open('../cache/downloaded/'+dfile.get('name'),'wb')
		downloader = MediaIoBaseDownload(fh,request)
		done = False
		while done is False:
			status, done = downloader.next_chunk()
			print(status)
		print ("Download %d%%." % int(status.progress() * 100))
