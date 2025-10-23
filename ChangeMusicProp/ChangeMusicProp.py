
import sys
import os

from mutagen.mp3 import MP3
from mutagen.id3 import ID3NoHeaderError
from mutagen.id3 import ID3, TIT2, TPE1, TALB, TDRC, TRCK


def recode(s):
 correct = "áéíóúêôãàÁÉÍÓÚÊÔÃÀçÇ"
 wrong   = "ßΘφ≤·Ω⌠πα┴╔═╙┌╩╘├└τ╟"
 wrong2  = "├í├⌐├¡├│├║├¬├┤├ú├á├ü├ë├ì├ô├Ü├è├ö├â├Ç├º├ç"

 #Fix chars if UTF-8 
 for i in range(len(correct)):
   c = correct[i]
   w = wrong2[2*i:2*i+2]
   pos = s.find(w)
   while (not pos==-1):
     s = s[0:pos] + c + s[pos+2:]
     pos = s.find(w) 

 #Fix chars if ANSI
 for i in range(len(correct)):
   c = correct[i]
   w = wrong[i]
   pos = s.find(w)
   while (not pos==-1):
     s = s[0:pos] + c + s[pos+1:]
     pos = s.find(w) 
 return(s)


if len(sys.argv) != 5:
   print("\nUse python ChangeMusicProp.py Filename Artist Album Music\n\n")
   exit()

file   = sys.argv[1]
artist = sys.argv[2]
album  = sys.argv[3]
music  = sys.argv[4]

artist = recode(artist)
album  = recode(album)
music  = recode(music)

if (not os.path.isfile(file)):
   print (file + " is not a file.")
   exit()

print ("Processing: ",file, " - ", artist," - ", album," - ",music)

try:
    audio = MP3(file, ID3=ID3)
except ID3NoHeaderError:
    # If no ID3 tags exist, create them
    audio = MP3(file)
    audio.add_tags()

audio["TIT2"] = TIT2(encoding=3, text=[music])
audio["TPE1"] = TPE1(encoding=3, text=[artist])
audio["TALB"] = TALB(encoding=3, text=[album])
audio.save()

newname = file + "\..\\" + music + " - " + artist + ".mp3"

os.rename(file,newname)





