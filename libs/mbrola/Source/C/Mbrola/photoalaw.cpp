/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File    : photoalaw.cpp
 * Purpose : example file 3
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * Reads a .pho, synthesize it with the mbrola.dll and save
 * the result to an audio file, and apply an a-law 8 bits filter to the 
 * synthesized datas.
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
	if (argc<4)
	{
		fputs("\nFormat : photoraw database inputpho outputraw\n",stderr);
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

	char bufferin[_MAX_PATH];
	short bufferout[4096];
	FILE *fin=fopen(argv[2],"rt");
	FILE *fout=fopen(argv[3],"wb");
	int nbr;

	if (!(fin && fout))
	{
		fprintf(stderr,"\nCan't open IO files\n");
		close_MBR();
		unload_MBR();
		return -1;
	}

	while(!feof(fin))
	{
		fgets(bufferin,260,fin);
		while((write_MBR(bufferin)>0)&&(!feof(fin)))
			fgets(bufferin,260,fin);
		while((nbr=readType_MBR(bufferout,4096,ALAW))>0)
			fwrite(bufferout,sizeof(short),nbr,fout);
		if (nbr<0)
		{
			lastErrorStr_MBR(bufferin,260);
			fprintf(stderr,"\nMbrola Error :\n%s",bufferin);
			close_MBR();
			unload_MBR();
			return -1;
		}
	}
	flush_MBR();
		while((nbr=readType_MBR(bufferout,4096,ALAW))>0)
			fwrite(bufferout,sizeof(short),nbr,fout);
	if (nbr<0)
	{
		lastErrorStr_MBR(bufferin,260);
		fprintf(stderr,"\nMbrola Error :\n%s",bufferin);
		close_MBR();
		unload_MBR();
		return -1;
	}
	fclose(fin);
	fclose(fout);
	close_MBR();
	unload_MBR();
	return 0;
}