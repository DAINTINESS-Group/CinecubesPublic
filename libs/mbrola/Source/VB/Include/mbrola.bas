Attribute VB_Name = "Mbrola"

' *
' * FPMs-TCTS SOFTWARE LIBRARY
' *
' * File    : mbrola.bas
' * Purpose : Mbrola module
' * Author  : Alain Ruelle
' * Email   : ruelle@tcts.fpms.ac.be
' *
' * Copyright (c) 1997-98 Faculte Polytechnique de Mons (TCTS lab)
' * All rights reserved.
' * PERMISSION IS HEREBY DENIED TO USE, COPY, MODIFY, OR DISTRIBUTE THIS
' * SOFTWARE OR ITS DOCUMENTATION FOR ANY PURPOSE WITHOUT WRITTEN
' * AGREEMENT OR ROYALTY FEES.
' *

Public Const LIN16 as Byte = 0
Public Const LIN8 as Byte = 1
Public Const ULAW as Byte = 2
Public Const ALAW as Byte = 3

' reset_MBR : Re-initialize the synthesizer
Declare Sub reset_MBR Lib "mbrola.dll" ()

' init_MBR : Initalize the synthesizer and load a database
Declare Function init_MBR Lib "mbrola.dll" (ByVal dbaname As String) As Long

' init_rename_MBR : Initialize the synthesizer, load a database, make phoneme replacement and cloning
Declare Function init_rename_MBR Lib "mbrola.dll" (ByVal lpszDbPath as String,ByVal lpszRenaming as String, ByVal lpszCloning as String) As Long

' close_MBR : Close the synthesizer (necessary to free memory)
Declare Sub close_MBR Lib "mbrola.dll" ()

' read_MBR : read synthesized data
' read_MBR needs a 16 bits signed buffer, so to read nb_wanted data from the buffer,
' you need to define a string that has a nb_wanted*2 size (because strings are characters -> 8 bits)
Declare Function read_MBR Lib "mbrola.dll" (ByVal buffer_out As String, ByVal nb_wanted As Long) As Long

' readtype_MBR : read synthesized data, and apply a filter (a-law,u-law), or a 8-bit conversion
Declare Function readtype_MBR Lib "mbrola.dll" (ByVal buffer_out As String, ByVal nb_wanted As Long, ByVal btyp as Byte) As Long

' write_MBR : write phonemes line to the synthesizer
Declare Function write_MBR Lib "mbrola.dll" (ByVal buffer_in As String) As Long

' lastError_MBR : Get the last error code from the synthesizer
Declare Function lastError_MBR Lib "mbrola.dll" () As Long

' lastErrorStr_MBR : Get the message associated with the last error
Declare Function lastErrorStr_MBR Lib "mbrola.dll" (ByVal msg As String, ByVal n As Long) As Long

' flush_MBR : write a flush code to the synthesizer
Declare Function flush_MBR Lib "mbrola.dll" () As Long

' getVersion_MBR : get the version of the Mbrola Synthesizer
Declare Function getVersion_MBR Lib "mbrola.dll" (ByVal msg As String, ByVal n As Long) As Long

' setNoError_MBR : set the NoError mode of the Mbrola synthesizer
Declare Function setNoError_MBR Lib "mbrola.dll" (ByVal noError as Long)

' getNoError_MBR : get the NoError state of the Mbrola Synthesizer
Declare Function getNoError_MBR Lib "mbrola.dll" () as Long

' setFreq_MBR : Set the Vocal tract parameters for the currently loaded database
Declare Function setFreq_MBR Lib "mbrola.dll" (ByVal nFreq as Long)

' getFreq_MBR : return the sample rate of the phoneme database currently loaded.
Declare Function getFreq_MBR Lib "mbrola.dll" () As Long

' setVolumeRatio_MBR : Set the volume ratio
Declare Function setVolumeRatio_MBR Lib "mbrola.dll" (ByVal fVol as Single)

' getVolumeRatio_MBR : Get the volume ratio
Declare Function getVolumeRatio_MBR Lib "mbrola.dll" () as Long

' getDatabaseInfo_MBR : returns informations about the currently loaded database
Declare Function getDatabaseInfo_MBR Lib "mbrola.dll" (ByVal msg As String, ByVal n as Long, ByVal idx as Long) As Long

' return the formatted error message
Public Function lastErrorFormatted_MBR() As String
    Dim ret As String
    Dim i As Long
    ' The max size of a message is 200
    ret = String$(200, 0)
    i = lastErrorStr_MBR(ret, 200)
    lastErrorStr_MBR = Left(ret, i)
End Function

' read16_MBR read a buffer from the synthesizer and write it into an array of integer
' this function replace the read_MBR that read a buffer but place it in a string
Public Function read16_MBR(buffer_in() As Integer, ByVal nb_wanted As Long) As Long

    Dim cbuffer As String
    Dim i, ret, tmp As Long
            
    cbuffer = String$(nb_wanted * 2, 0)
    ret = read_MBR(cbuffer, nb_wanted)
    If ret > 0 Then
        For i = 0 To ret - 1
            ' the read_MBR returns a buffer of signed 16bits integers
            ' Asc returns the ascii code of the character (between 0 and 255, 8 bits unsigned)
            ' We make a 16bits unsigned integer with 2 ascii code
            tmp = (CLng(Asc(Mid(cbuffer, (i * 2) + 1, 1))) * 256) + Asc(Mid(cbuffer, (i * 2) + 2, 1))
            ' But we need a 16bits signed integer, so we check if it's up to 32767
            If tmp > 32767 Then
                ' if so, we subtract 65536 to the value (to cancel the effect of the leading sign bit)
                buffer_in(i) = CInt(tmp - 65536)
            Else
                ' otherwise, we can put the value in the array
                buffer_in(i) = CInt(tmp)
            End If
        Next
    End If
    read16_MBR = ret
End Function