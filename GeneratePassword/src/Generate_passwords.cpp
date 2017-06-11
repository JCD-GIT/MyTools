// Generate_passwords.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <string.h>
#include <time.h>
#include <stdlib.h>


bool is_char_string(char* str)    /* Confirm if is a valid input for chars */
{
	int i;

	for (i = 0; i < strlen(str); i++)
	{
		if ((str[i] < 'a') || (str[i] > 'z'))
		 {
			if ((str[i] < 'A') || (str[i] > 'Z'))  return(false);
		 }
	}
	return(true);

}

bool is_number(char* str)     /* Confirm if is a valid input for number */
{
	int i;

	for (i = 0; i < strlen(str); i++)
	{
		if ((str[i] < '0') || (str[i] > '9')) return(false);
	}
	return(true);

}

bool is_symbol(char* str)     /* Confirm if is a valid input for symbols */
{
	int i;

	for (i = 0; i < strlen(str); i++)
	{
		if ((str[i] >= '0') && (str[i] <= '9')) return(false);
		if ((str[i] >= 'a') && (str[i] <= 'z')) return(false);
		if ((str[i] >= 'A') && (str[i] <= 'Z')) return(false);

	}
	return(true);

}


void _tmain(int argc, char* argv[])

{
	srand(time(NULL));   /* randomize */

	int r = rand();
	int i,j;

	if (argc != 4)
	{
		printf("Use password_generator char_string number symbol(s)");
		return;
	}
	else
	{
		if (!is_char_string(argv[1]))
		{
			printf("char_string should contain only char between a and z or A and Z");
			return;
		}
		if (!is_number(argv[2]))
		{
			printf("%s is not a number", argv[1]);
			return;
		}
		if (!is_symbol(argv[3]))
		{
			printf("%s should not contain chars from a to z, A to Z or digits from 0 to 9", argv[2]);
			return;
		}
	}

	
	char result[100];   /* Generate the password  using aleatory with input from user */

	if ((strlen(argv[1]) + strlen(argv[2]) + strlen(argv[3])) > 100)
	{
		printf("Parameters too long. Password max size=100 chars.");
		return;
	}

	strcpy(result, argv[1]);
	result[strlen(argv[1])] = 0;

	for (i = 0; i < strlen(result); i++)
	 {
		if ((result[i] >= 'A') && (result[i] <= 'Z')) result[i] = result[i] - 'A' + 'a';
	 }
	r = rand() % (strlen(result) >> 2)+1;
	for (i = 0; i < r; i++)
	{
		int p = rand() % strlen(result);
		if ((result[p] >= 'a') && (result[p] <= 'z')) result[p] = result[p] + 'A' - 'a';
	}
	

	for (i = 0; i < strlen(argv[3]); i++)
	{
		int p = rand() % strlen(result);
		for (j = strlen(result); j>=p; j--) result[j+1] = result[j];
		result[p] = argv[3][i];
	}


	int p = rand() % strlen(result);
	for (i = strlen(result); i >=p; i--)
	{
		result[i + strlen(argv[2])] = result[i];
	}
	for (i = 0; i < strlen(argv[2]); i++) result[p + i] = argv[2][i];

	printf("%s", result);

	return;

}


