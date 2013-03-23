/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File    : listdb
 * Purpose : example file 6
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * list the databases registered in the system
 *
 * Copyright (c) 1998 Faculte Polytechnique de Mons (TCTS lab)
 */
#include <stdio.h>
#include <windows.h>
#include "mbrplay.h"

BOOL WINAPI dbCallback(LPCTSTR lpszDatabase, DWORD dwUserData)
{
	char label[_MAX_PATH];
	char path[_MAX_PATH];
	char defdb[_MAX_PATH];

	MBR_RegGetDefaultDatabase(defdb,_MAX_PATH);
	MBR_RegGetDatabaseLabel(lpszDatabase,label,_MAX_PATH);
	MBR_RegGetDatabasePath(lpszDatabase,path,_MAX_PATH);
	if (strcmp(lpszDatabase,defdb)==0)
		printf("*");
	printf("%s\tLabel : %s\tPath : %s\n",lpszDatabase,label,path);
	return TRUE;
}

int main()
{
	if (MBR_LastError(NULL,0)<0)
	{
		char msg[200];
		MBR_LastError(msg,200);
		fprintf(stderr,"Mbrplay Error :\n%s\n",msg);
		return -1;
	}
	MBR_RegEnumDatabaseCallback(dbCallback,NULL);
	MBR_MBRUnload();
	return 0;
}