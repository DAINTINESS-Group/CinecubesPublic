/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File    : dbinfo.cpp
 * Purpose : example file 2
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * Get informations of a database
 *
 * Copyright (c) 1998 Faculte Polytechnique de Mons (TCTS lab)
 */
#include <stdio.h>
#include <windows.h>
#include "mbrola.h"

int main(int argc, char** argv)
{
	// First, Load DLL
	if (!load_MBR())
	{
		fputs("\nCan't load mbrola.dll\n",stderr);
		return -1;
	}
	// Verify number of arguments
	if (argc<2)
	{
		fputs("\nFormat : dbinfo database\n",stderr);
		unload_MBR();
		return -1;
	}
	// Try to load french database
	if (init_MBR(argv[1])<0)
	{
		fprintf(stderr,"\nCan't find %s database\n",argv[1]);
		unload_MBR();
		return -1;
	}
	char *buffer;
	int l=1,i=0;

	while(l>0)
	{
		l=getDatabaseInfo_MBR(NULL,0,i);
		if (l>0)
		{
			buffer=(char*)malloc((l+1)*sizeof(char));
			getDatabaseInfo_MBR(buffer,l+1,i);
			printf("%s\n\n",buffer);
			free(buffer);
			i++;
		}
	}
	close_MBR();
	unload_MBR();
	return 0;
}