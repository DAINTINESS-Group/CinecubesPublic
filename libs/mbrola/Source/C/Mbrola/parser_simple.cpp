/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File:    parser_simple.c
 * Purpose: parse a simple "pho file" (demonstration of the mbrola DLL)
 *          Instanciation of parser.h
 *
 * Author:  Vincent Pagel
 * Email :  mbrola@tcts.fpms.ac.be
 *
 * Copyright (c) 98 Faculte Polytechnique de Mons (TCTS lab)
 *
 * 18/09/98 : Created
 */
#include <stdio.h>
#include <windows.h>
#include "mbrola.h"
#include "parser_simple.h"

typedef enum {
  OK,           /* the phoneme comes from the file     */
  FIRST_HALF,   /* reveal 1st half of the last phoneme */
  SECOND_HALF,  /* reveal 2nd half of the last phoneme */
} ParseState;

typedef struct Simple
{
	FILE* input;
	ParseState state;
} Simple;

/* Convenient macro to access to private data */
#define input(X)  ( ((Simple*)X->self)->input)
#define state(X)  (((Simple*)X->self)->state)

static Simple* init_Simple(FILE* input)
{
	Simple* my_simple= (Simple*) malloc(sizeof(Simple));
	my_simple->state= OK;
	my_simple->input=input;
	return my_simple;
}


static void close_Simple(Simple* sp)
{
	fclose( sp->input );
	free(sp);
}


static void reset_ParserSimple(Parser* parse)
{
  /* nothing to do */
  fseek( input(parse),0,SEEK_SET);
  state(parse)=OK;
}

static StatePhone nextphone_ParserSimple(Parser* parse, LPPHONE* ph)
{
  char phoneme[255]; /* phoneme name */
  float length;    /* length in milliseconds */
  float pitch0;    /* pitch at 0%   */
  float pitch100;  /* pitch at 100% */
  
  switch ( state(parse))
  {
  case OK:
		if ( fscanf(input(parse)," %s %f %f %f ",phoneme,&length,&pitch0,&pitch100 ) ==4 )
		 {
			*ph= init_Phone(phoneme,length);
			appendf0_Phone(*ph, 0.0  , pitch0);
			appendf0_Phone(*ph, 100.0, pitch100);
			return PHO_OK;
		 }
		else	
		 {
			/* Eof add a trailing silence to reveal 1st half of the last phoneme */
			*ph= init_Phone("_",0.0);
			appendf0_Phone(*ph, 0.0  , 100.0);
			appendf0_Phone(*ph, 100.0, 100.0);
			state(parse)=FIRST_HALF;
			return PHO_OK;
		 }
		break;
  
  case FIRST_HALF:
  	  /* Eof add another trailing silence to reveal second half of the last phoneme */
		*ph= init_Phone("_",0.0);
		appendf0_Phone(*ph, 0.0  , 100.0);
		appendf0_Phone(*ph, 100.0, 100.0);
		state(parse)=SECOND_HALF;
		return PHO_OK;
	  break;
  case SECOND_HALF:
  default:
	  return PHO_EOF;
  }
}

static void close_ParserSimple(Parser* parse)
	  /* Destructor */
{
  close_Simple( (Simple*) parse->self);
  free(parse);
}

Parser* init_ParserSimple(char* input_name)
/*
 * Constructor of the parser. Parse a text file of the form
 *    PHONEME LENGTH PITCH_AT_BEGINNING PITCH_AT_END
 */
{
  FILE* input;
  Parser* parse;
  Simple* my_simple;

  /* open the text file */
  input=fopen(input_name,"rt");
  if (!input)
	 return NULL;
  my_simple= init_Simple(input);

  parse= (Parser*) malloc( sizeof( struct Parser) );
  parse->reset_Parser= reset_ParserSimple;
  parse->close_Parser= close_ParserSimple;
  parse->nextphone_Parser= nextphone_ParserSimple; 
  parse->self= (void*) my_simple;
  return(parse);
}

