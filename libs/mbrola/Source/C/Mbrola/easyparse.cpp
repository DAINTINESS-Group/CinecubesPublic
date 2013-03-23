/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File    : easyparse.cpp
 * Purpose : example file 4
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * Read a file containing pho informations through a customized parser
 * save the result to an audio file.
 *
 * Copyright (c) 1998 Faculte Polytechnique de Mons (TCTS lab)
 */
#include <stdio.h>
#include <windows.h>
#include "mbrola.h"
#include "parser_simple.h"

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
		fputs("\nFormat : easyparse database inputpho outputraw\n",stderr);
		unload_MBR();
		return -1;
	}
	// Try to load database
	if (init_MBR(argv[1])<0)
	{
		fprintf(stderr,"\nCan't find %s database\n",argv[1]);
		unload_MBR();
		return -1;
	}

	short bufferout[4096];
	FILE *fout=fopen(argv[3],"wb");
	int nbr;
	
	// Try to create output file
	if (!fout)
	{
		fprintf(stderr,"\nCan't open output file\n");
		close_MBR();
		unload_MBR();
		return -1;
	}

	// Create an instance of our custom parser.
	Parser* easy_parse=init_ParserSimple(argv[2]);
	if (!easy_parse)
	{
		fprintf(stderr,"\nCan't open input file\n");
		close_MBR();
		unload_MBR();
		return -1;
	}

	// Set the new parser
	setParser_MBR(easy_parse);

	// read synthesized datas
	while((nbr=read_MBR(bufferout,4096))>0)
		fwrite(bufferout,sizeof(short),nbr,fout);
	if (nbr<0)
	{
		char msg[200];
		lastErrorStr_MBR(msg,260);
		fprintf(stderr,"\nMbrola Error :\n%s",msg);
		close_MBR();
		unload_MBR();
		return -1;
	}
	fclose(fout);
	close_MBR();
	unload_MBR();
	return 0;
}