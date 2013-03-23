/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File    : outbuf.cpp
 * Purpose : example file 7
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * play a pho file through a short buffer and message callback
 *
 * Copyright (c) 1998 Faculte Polytechnique de Mons (TCTS lab)
 */
#include <stdio.h>
#include <windows.h>
#include "mbrplay.h"

FILE* fout;
char fname[_MAX_PATH];

int WINAPI mbrCallback(UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch(msg)
	{
	case WM_MBR_INIT:
		fout=fopen(fname,"wb");
		if (!fout)
			return -1;
		break;
	case WM_MBR_WRITE:
		fwrite((short*)wParam,sizeof(short),lParam,fout);
		break;
	case WM_MBR_END:
		fclose(fout);
		break;
	}
	return 0;
}

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
	strcpy(fname,argv[3]);
	MBR_Play(argv[2],
			MBR_BYFILE|MBR_WAIT|MBR_CALLBACK|
			MBR_MSGINIT|MBR_MSGWRITE|MBR_MSGEND|
			MBROUT_DISABLED,
			NULL,(DWORD)mbrCallback);
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