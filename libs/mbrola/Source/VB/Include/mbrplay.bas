Attribute VB_Name = "MbrPlay"

' *
' * FPMs-TCTS SOFTWARE LIBRARY
' *
' * File    : mbrplay.bas
' * Purpose : MbrPlay Module
' * Author  : Alain Ruelle
' * Email   : ruelle@tcts.fpms.ac.be
' *
' * Copyright (c) 1997 Faculte Polytechnique de Mons (TCTS lab)
' * All rights reserved.
' * PERMISSION IS HEREBY DENIED TO USE, COPY, MODIFY, OR DISTRIBUTE THIS
' * SOFTWARE OR ITS DOCUMENTATION FOR ANY PURPOSE WITHOUT WRITTEN
' * AGREEMENT OR ROYALTY FEES.
' *

Public Const WM_USER = &H400

' MBR_Play flags
Public Const MBR_MSGINIT As Long = 1
Public Const MBR_MSGREAD As Long = 2
Public Const MBR_MSGWAIT As Long = 4
Public Const MBR_MSGWRITE As Long = 8
Public Const MBR_MSGEND As Long = 16
Public Const MBR_MSGALL As Long = 31
Public Const MBR_BYFILE As Long = 32
Public Const MBR_WAIT As Long = 64
Public Const MBR_CALLBACK As Long = 128
Public Const MBR_QUEUED As Long = 256
Public Const MBR_ASPHS As Long = 512

' MBR_SetOutputMode & MBR_GetOutputMode flags
Public Const MBROUT_SOUNDBOARD  As Long = 0
Public Const MBROUT_RAW As Long = 1024
Public Const MBROUT_WAVE As Long = 2048
Public Const MBROUT_AU As Long = 4096
Public Const MBROUT_AIFF As Long = 8192
Public Const MBROUT_ALAW As Long = 16384
Public Const MBROUT_MULAW As Long = 32768
Public Const MBROUT_DISABLED As Long = 65536

' Mbrola Errors
Public Const MBRERR_NOMBROLADLL                 As Long = -11
Public Const MBRERR_NOREGISTRY                  As Long = -10
Public Const MBRERR_NORESOURCE                  As Long = -9
Public Const MBRERR_NOTHREAD                    As Long = -8
Public Const MBRERR_DATABASENOTVALID    As Long = -7
Public Const MBRERR_CANTOPENDEVICEOUT   As Long = -6
Public Const MBRERR_BADCOMMAND                  As Long = -5
Public Const MBRERR_CANTOPENFILE                As Long = -4
Public Const MBRERR_WRITEERROR                  As Long = -3
Public Const MBRERR_MBROLAERROR                 As Long = -2
Public Const MBRERR_CANCELLEDBYUSER             As Long = -1
Public Const MBRERR_NOERROR                             As Long = 0

' Mbrola Windows Messages (used for notification)
Public Const WM_MBR_INIT As Long = (WM_USER + &H1BFF)
Public Const WM_MBR_READ As Long = (WM_USER + &H1C00)
Public Const WM_MBR_WAIT As Long = (WM_USER + &H1C01)
Public Const WM_MBR_WRITE As Long = (WM_USER + &H1C02)
Public Const WM_MBR_END As Long = (WM_USER + &H1C03)

' Extern Function Declaration
Declare Function MBR_Play Lib "mbrplay.dll" Alias "_MBR_Play@16" (ByVal lpszText As String, ByVal dwFlags As Long, ByVal lpszOutFile As String, ByVal dwCallback As Long) As Long
Declare Function MBR_Stop Lib "mbrplay.dll" Alias "_MBR_Stop@0" () As Long
Declare Function MBR_WaitForEnd Lib "mbrplay.dll" Alias "_MBR_WaitForEnd@0" () As Long
Declare Function MBR_SetPitchRatio Lib "mbrplay.dll" Alias "_MBR_SetPitchRatio@4" (ByVal fPitch As Single) As Long
Declare Function MBR_SetDurationRatio Lib "mbrplay.dll" Alias "_MBR_SetDurationRatio@4" (ByVal fDuration As Single) As Long
Declare Function MBR_SetVoiceFreq Lib "mbrplay.dll" Alias "_MBR_SetVoiceFreq@4" (ByVal lFreq As Long) As Long
Declare Function MBR_SetVolumeRatio Lib "mbrplay.dll" Alias "_MBR_SetVolumeRatio@4" (ByVal fVol As Single) As Long
Declare Function MBR_SetNoError Lib "mbrplay.dll" Alias "_MBR_SetNoError@4" (ByVal bSet As Long) As Long
Declare Function MBR_GetPitchRatio Lib "mbrplay.dll" Alias "_MBR_GetPitchRatio@0" () As Single
Declare Function MBR_GetDurationRatio Lib "mbrplay.dll" Alias "_MBR_GetDurationRatio@0" () As Single
Declare Function MBR_GetVoiceFreq Lib "mbrplay.dll" Alias "_MBR_GetVoiceFreq@0" () As Long
Declare Function MBR_GetVolumeRatio Lib "mbrplay.dll" Alias "_MBR_GetVolumeRatio@0" () As Single
Declare Function MBR_GetNoError Lib "mbrplay.dll" Alias "_MBR_GetNoError@0" () As Long
Declare Function MBR_SetDatabase Lib "mbrplay.dll" Alias "_MBR_SetDatabase@4" (ByVal lpszID As String) As Long
Declare Function MBR_SetDatabaseEx Lib "mbrplay.dll" Alias "_MBR_SetDatabaseEx@12" (ByVal lpszID As String, ByVal lpszReplace As String, ByVal lpszClone As String) As Long
Declare Function MBR_GetDatabase Lib "mbrplay.dll" Alias "_MBR_GetDatabase@8" (ByVal lpID As String, ByVal dwSize As Long) As Long
Declare Function MBR_IsPlaying Lib "mbrplay.dll" Alias "_MBR_IsPlaying@0" () As Long
Declare Function MBR_LastError Lib "mbrplay.dll" Alias "_MBR_LastError@8" (ByVal lpszError As String, ByVal dwSize As Long) As Long
Declare Function MBR_GetVersion Lib "mbrplay.dll" Alias "_MBR_GetVersion@8" (ByVal lpVersion As String, ByVal dwSize As Long) As Long
Declare Function MBR_GetDefaultFreq Lib "mbrplay.dll" Alias "_MBR_GetDefaultFreq@0" () As Long
Declare Function MBR_GetDatabaseInfo Lib "mbrplay.dll" Alias "_MBR_GetDatabaseInfo@12" (ByVal idx As Long, ByVal lpMsg As String, ByVal dwSize As Long) As Long
Declare Function MBR_GetDatabaseAllInfo Lib "mbrplay.dll" Alias "_MBR_GetDatabaseAllInfo@8" (ByVal lpMsg As String, ByVal dwSize As Long) As Long
Declare Function MBR_RegEnumDatabase Lib "mbrplay.dll" Alias "_MBR_RegEnumDatabase@8" (ByVal lpszData As String, ByVal dwSize As Long) As Long
Declare Function MBR_RegEnumDatabaseCallback Lib "mbrplay.dll" Alias "_MBR_RegEnumDatabaseCallback@8" (lpszID As Any, ByVal dwUser As Long) As Long
Declare Function MBR_RegGetDatabaseLabel Lib "mbrplay.dll" Alias "_MBR_RegGetDatabaseLabel@12" (ByVal lpszID As String, ByVal lpLabel As String, ByVal dwSize As Long) As Long
Declare Function MBR_RegGetDatabasePath Lib "mbrplay.dll" Alias "_MBR_RegGetDatabasePath@12" (ByVal lpszID As String, ByVal lpPath As String, ByVal dwSize As Long) As Long
Declare Function MBR_RegGetDatabaseCount Lib "mbrplay.dll" Alias "_MBR_RegGetDatabaseCount@0" () As Long
Declare Function MBR_RegGetDefaultDatabase Lib "mbrplay.dll" Alias "_MBR_RegGetDefaultDatabase@8" (ByVal lpID As String, ByVal dwSize As Long) As Long
Declare Function MBR_RegSetDefaultDatabase Lib "mbrplay.dll" Alias "_MBR_RegSetDefaultDatabase@4" (ByVal lpszID As String) As Long
Declare Function MBR_RegisterDatabase Lib "mbrplay.dll" Alias "_MBR_RegisterDatabase@24" (ByVal dbId As String, ByVal dbPath As String, ByVal dbLabel As String, ByVal isDef As Long, ByVal lpBuffer As String, ByVal dwSize As Long) As Long
Declare Function MBR_UnregisterDatabase Lib "mbrplay.dll" Alias "_MBR_UnregisterDatabase@4" (ByVal dbId As String) As Long
Declare Function MBR_UnregisterAll Lib "mbrplay.dll" Alias "_MBR_UnregisterAll@0" () As Long
Declare Function MBR_DatabaseExist Lib "mbrplay.dll" Alias "_MBR_DatabaseExist@4" (ByVal lpszID As String) As Long
Declare Function MBR_RegIdxGetDatabaseId Lib "mbrplay.dll" Alias "_MBR_RegIdxGetDatabaseId@12" (ByVal nIdx As Long, ByVal lpszID As String, ByVal dwSize As Long) As Long
Declare Function MBR_RegIdxGetDatabasePath Lib "mbrplay.dll" Alias "_MBR_RegIdxGetDatabasePath@12" (ByVal nIdx As Long, ByVal pszPath As String, ByVal dwSize As Long) As Long
Declare Function MBR_RegIdxGetDatabaseLabel Lib "mbrplay.dll" Alias "_MBR_RegIdxGetDatabaseLabel@12" (ByVal nIdx As Long, ByVal lpszLabel As String, ByVal dwSize As Long) As Long
Declare Function MBR_RegIdxGetDatabaseIndex Lib "mbrplay.dll" Alias "_MBR_RegIdxGetDatabaseIndex@12" (ByVal lpszID As String) As Long
Declare Function MBR_RegIdxGetDefaultDatabase Lib "mbrplay.dll" Alias "_MBR_RegIdxGetDefaultDatabase@0" () As Long
Declare Function MBR_StartControlPanel Lib "mbrplay.dll" Alias "_MBR_StartControlPanel@4" (Hwnd As Long) As Long
Declare Function MBR_SetCallbackMsgBase Lib "mbrlay.dll" Alias "_MBR_SetCallbackMsgBase@4" (dwBase As Long) As Long
Declare Function MBR_MBRUnload Lib "mbrplay.dll" Alias "_MBR_MBRUnload@0" ()
