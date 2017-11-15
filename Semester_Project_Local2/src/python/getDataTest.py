from apiclient import errors
from apiclient import http
import httplib2
from apiclient import discovery
from oauth2client import client
from oauth2client import tools
from oauth2client.file import Storage
import os
# # ...

# def print_file_metadata(service, file_id):
#   """Print a file's metadata.

#   Args:
#     service: Drive API service instance.
#     file_id: ID of the file to print metadata for.
#   """
#   try:
#     file = service.files().get(fileId=file_id).execute()

#     print ('Title: %s' % file['title'])
#     print ('MIME type: %s' % file['mimeType'])
#   except :
#       print ('An error occurred:')
  
  
# def print_file_content(service, file_id):
#   """Print a file's content.

#   Args:
#     service: Drive API service instance.
#     file_id: ID of the file.

#   Returns:
#     File's content if successful, None otherwise.
#   """
#   try:
#     print (service.files().get_media(fileId=file_id).execute())
#   except:
#     print ('An error occurred:')


# def download_file(service, file_id, local_fd):
#   """Download a Drive file's content to the local filesystem.

#   Args:
#     service: Drive API Service instance.
#     file_id: ID of the Drive file that will downloaded.
#     local_fd: io.Base or file object, the stream that the Drive file's
#         contents will be written to.
#   """
#   request = service.files().get_media(fileId=file_id)
#   media_request = http.MediaIoBaseDownload(local_fd, request)

#   while True:
#     try:
#       download_progress, done = media_request.next_chunk()
#     except:
#       print ('An error occurred:')
#       return
#     if download_progress:
#       print ('Download Progress: %d%%' % int(download_progress.progress() * 100))
#     if done:
#       print ('Download Complete')
#       return
from apiclient import discovery

drive_service = discovery.build('drive','v3',http=http)

file_id = "16veLFPwHKZQUU59-TnRtfB0cFoxjOGq3mlP1C9xOIoA"
# request = drive_service.files().get_media(fileId=file_id)
# fh = io.BytesIO()
# downloader = MediaIoBaseDownload(fh, request)
# done = False
# while done is False:
#     status, done = downloader.next_chunk()
#     print ("Download %d%%." % int(status.progress() * 100))

request = drive_service.files().export_media(fileId=file_id,
                                             mimeType='application/doc')
fh = io.BytesIO()
downloader = MediaIoBaseDownload(fh, request)
done = False
while done is False:
    status, done = downloader.next_chunk()