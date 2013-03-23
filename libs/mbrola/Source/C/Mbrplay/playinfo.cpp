/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File    : playinfo
 * Purpose : example file 5
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * Get informations about a database.
 *
 * Copyright (c) 1998 Faculte Polytechnique de Mons (TCTS lab)
 */
#include <stdio.h>
#include <windows.h>
#include "mbrplay.h"

int main(int argc,char** argv)
{
	if (MBR_LastError(NULL,0)<0)
	{
		char msg[200];
		MBR_LastError(msg,200);
		fprintf(stderr,"Mbrplay Error :\n%s\n",msg);
		return -1;
	}
	if (argc<2)
	{
		fputs("Format : playinfo database\n",stderr);
		return -1;
	}
	if (MBR_SetDatabase(argv[1])<0)
	{
		char msg[200];
		MBR_LastError(msg,200);
		fprintf(stderr,"Mbrplay Error :\n%s\n",msg);
		return -1;
	}
	int size=MBR_GetDatabaseAllInfo(NULL,0);
	char *buffer=new char[size+1];
	MBR_GetDatabaseAllInfo(buffer,size+1);
	puts(buffer);
	MBR_MBRUnload();
	return 0;
}