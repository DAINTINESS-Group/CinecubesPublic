/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File    : mbrreg.cpp
 * Purpose : example file 8
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * register or unregister a database from the registry
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
		fputs("Format : mbrreg mod [ID] [path] [label]\n",stderr);
		return -1;
	}

	if (strchr(argv[1],'r'))
	{
		if (argc<5)
		{
			fputs("Error, missing informations\n",stderr);
			return -1;
		}
		char buffer[_MAX_PATH];
		BOOL isDefault=(strchr(argv[1],'d')!=0);
		if (!MBR_RegisterDatabase(argv[2],argv[3],argv[4],isDefault,buffer,_MAX_PATH))
		{
			fputs("Error, Can't register database\n",stderr);
			return -1;
		}
		printf("Database registred, ID=%s\n",buffer);
		MBR_MBRUnload();
		return 0;
	}
	else if (strchr(argv[1],'u'))
	{
		if (strchr(argv[1],'a'))
		{
			puts("Are you sure you want to unregister all the databases ?");
			int ret=getchar();
			if ((ret=='y')||(ret=='Y'))
			{
				MBR_UnregisterAll();
			}
			MBR_MBRUnload();
			return 0;
		}
		else
		{
			if (argc<3)
			{
				fputs("Error, missing informations\n",stderr);
				return -1;
			}
			MBR_UnregisterDatabase(argv[2]);
			MBR_MBRUnload();
			return 0;
		}
	}
	MBR_MBRUnload();
	return 0;
}