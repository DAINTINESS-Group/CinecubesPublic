/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File    : play
 * Purpose : example file 4
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * play a pho file or a phs file into a raw file
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
	if (argc<4)
	{
		fputs("Not enough parameters\n",stderr);
		return -1;
	}
	if (MBR_SetDatabase(argv[1])<0)
	{
		char msg[200];
		MBR_LastError(msg,200);
		fprintf(stderr,"Mbrplay Error :\n%s\n",msg);
		return -1;
	}
	char *pos=strrchr(argv[2],'.');
	if (strcmp(pos,".phs")==0)
		MBR_Play(argv[2],MBR_BYFILE|MBR_ASPHS|MBROUT_RAW|MBR_WAIT,argv[3],NULL);
	else
		MBR_Play(argv[2],MBR_BYFILE|MBROUT_RAW|MBR_WAIT,argv[3],NULL);
	if (MBR_LastError(NULL,0)<0)
	{
		char msg[200];
		MBR_LastError(msg,200);
		fprintf(stderr,"Mbrplay Error :\n%s\n",msg);
		return -1;
	}
	MBR_MBRUnload();
	return 0;
}