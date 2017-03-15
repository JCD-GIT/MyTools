// Tool to convert txt files to CSV table 
// parse_txt < input.txt    -  output 3 columns csv
// parse_txt 2 < input.txt  -  output 4 columns csv removing repeated and counting in 4th column. Input.txt should be sort to work
// parse_txt 0 < input.xml  -  Extract localizable strings from a XML for Management Package

#include "stdafx.h"
#include <stdio.h>
#include <string.h>


// word separators
char separators[] = { " ;,-.\t\n:()0123456789" };
char separator2 = 13;

// Convert TXT file in table CSV with 3 columns
void parse()
{
	short int start_char_id;
	short int end_char_id;
	short int last_char;
	int size;
	char temp_str[2];


	bool flag_in_word;
	char c;
	int wc;

	c = 0;
	temp_str[1] = 0;

	flag_in_word = false;

	while (c != EOF)
	{
		c = getchar(); 
		temp_str[0] = c;
		if ((c >= 'a') & (c <= 'z')) c = c - 'a' + 'A';  //* Uppercase should be used to consider "Word" or "word" same way.
		if (((c < 'A') | (c > 'Z')) & (c!=EOF)) c = '@'; //* Unicode or extended chars like á, ô should be included in same group 
		
		if ((strstr(separators, temp_str ) != NULL) || (c == EOF) || c== separator2)
		{
			if (flag_in_word)
			{
				// Word ending 
				flag_in_word = false;
				end_char_id = last_char;
				size = wc;
				printf("%d,%d,%d\n", start_char_id, end_char_id, size);
			}
		}
		else
		{
			if (!flag_in_word)
			{
				// Word starting 
				wc = 1; // Start word count
				flag_in_word = true;
				last_char = c;
				start_char_id = last_char;
			}
			else
			{
				// One more char
				wc++;
				last_char = c;

			}
		}
	}
}
    
// Remove repeated entries and add 4 column with number of occurrences
void add_repeated()
{
	int prev_start_char_id=0;
	int prev_end_char_id=0;
	char prev_start_char = 0;
	char prev_end_char = 0;
	int prev_size = 0;
	int new_start_char_id = 0;
	int new_end_char_id = 0;
	char new_start_char = 0;
	char new_end_char = 0;
	int new_size = 0;
	int occurrences;
	bool flag_end;
	int scanning;



	printf("FirstCharID, LastCharID, FirstChar, LastChar, Size, Occurrences\n");
	scanning = scanf_s("%d,%d,%d\n", &prev_start_char_id, &prev_end_char_id, &prev_size);
	occurrences = 1;
	
	flag_end = false;

	while (scanning==3)
	 {
		// if lines match increase occurrence
		scanning = scanf_s("%d,%d,%d\n", &new_start_char_id, &new_end_char_id, &new_size);
			if ((prev_start_char_id == new_start_char_id) &
				(prev_end_char_id == new_end_char_id) &
				(prev_size == new_size))
			{
				// more one occurrence
				occurrences++;
			}
			else  // if not print previous string and go ahead
			{
				printf("%d,%d,%c,%c,%d,%d\n", prev_start_char_id, prev_end_char_id, prev_start_char_id, prev_end_char_id, prev_size, occurrences);
				occurrences = 1;
				prev_start_char_id = new_start_char_id;
				prev_end_char_id = new_end_char_id;
				prev_start_char = new_start_char;
				prev_end_char = new_end_char;
				prev_size = new_size;
			}
	 }
}

// Extract localized strings from a XML for Management Package
void parse_xml()
{
	char c, v[15],flag;
	int i,p;
	char buffer[10000];

	c = 0;
	p = 0;
	flag = 0;
	
	while (c != EOF)
	 {
		c = getchar();
		for (i = 1; i < 15; i++) v[i-1] = v[i];
		v[14] = c;
		if (flag == 0)
		 {
		  // if tag showing start of string to extract is found, set flag
	      if (strncmp(v+14-13, "<Description>", 13) == 0) flag = 1;
		  if (strncmp(v+14-6, "<Name>", 6) == 0) flag = 1;
		 }
	    if (flag == 1)  // if flag is set keep the string
		 {
  		  buffer[p] = c;
		  p++;
		  // if tag showing the end of string to extract is found print string and reset flag
		  if (strncmp(v+14-14, "</Description>", 14) == 0) 
		   {	
			flag = 0;
			buffer[p - 15] = 0;
			printf("%s\n", buffer);
			p = 0;
		   }
		  if (strncmp(v+14-7, "</Name>", 7) == 0) 
		   {
			flag = 0;
			buffer[p - 8] = 0;
			printf("%s\n", buffer);
			p = 0;
		   }
	  }
   }
}

int _tmain(int argc, _TCHAR* argv[])
{
	if (argc == 2)
	{
		
		if (argv[1][0] == '2')
			add_repeated();
		if (argv[1][0] == '0')
			parse_xml();
	}
	else
	   parse();
	return 0;
}



