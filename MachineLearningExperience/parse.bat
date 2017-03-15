c:\util\parse_txt.exe < %1 | sort | c:\util\parse_txt.exe 2 > "%~dpn1.csv"

REM  c:\util\parse_txt.exe < %1 | sort > ~TEMPFILE.TXT
REM c:\util\parse_txt.exe 2 < ~TEMPFILE.TXT
REM DEL ~TEMPFILE.TXT