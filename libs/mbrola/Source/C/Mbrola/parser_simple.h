/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File:    parser_simple.h
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

#ifndef PARSER_SIMPLE_H
#define PARSER_SIMPLE_H

#include "parser.h"

Parser* init_ParserSimple(char* file_name);
/*
 * Constructor of the parser. Parse a text file of the form
 *    PHONEME LENGTH PITCH_AT_BEGINNING PITCH_AT_END
 */

#endif
