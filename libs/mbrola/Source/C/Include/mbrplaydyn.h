/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File :    folderdialog.cpp
 * Purpose : MbrPlay functions & constants defines
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * Copyright (c) 1997-1998 Faculte Polytechnique de Mons (TCTS lab)
 * All rights reserved.
 *
 * Dynamically Imported Functions
 *
 */
#ifndef __MBRPLAY_H__
#define __MBRPLAY_H__

#define MBR_MSGINIT				1
#define MBR_MSGREAD				2
#define MBR_MSGWAIT				4
#define MBR_MSGWRITE			8
#define MBR_MSGEND				16
#define MBR_MSGALL				31
#define MBR_BYFILE				32
#define MBR_WAIT				64
#define MBR_CALLBACK			128
#define MBR_QUEUED				256
#define MBR_ASPHS				512

#define MBROUT_SOUNDBOARD		0
#define MBROUT_RAW				1024
#define MBROUT_WAVE				2048
#define MBROUT_AU				4096

#define MBROUT_ALAW				16384
#define MBROUT_MULAW			32768

#define MBR_PHONEME				131072
#define MBR_WORD				262144
#define MBR_SENTENCE			524288
#define MBR_PARAGRAPH			1048576
#define MBR_TAG					2097152

#define MBRERR_NOMBROLADLL			-11
#define MBRERR_NOREGISTRY			-10
#define MBRERR_NORESOURCE			-9
#define MBRERR_NOTHREAD				-8
#define MBRERR_DATABASENOTVALID		-7
#define MBRERR_CANTOPENDEVICEOUT	-6
#define MBRERR_BADCOMMAND			-5
#define MBRERR_CANTOPENFILE			-4
#define MBRERR_WRITEERROR			-3
#define MBRERR_MBROLAERROR			-2
#define MBRERR_CANCELLEDBYUSER		-1
#define MBRERR_NOERROR				0

// Mbrola Windows Messages (used for notification)
#define WM_MBR_INIT				(WM_USER+0x1BFF)
#define WM_MBR_READ				(WM_USER+0x1C00)
#define WM_MBR_WAIT				(WM_USER+0x1C01)
#define WM_MBR_WRITE			(WM_USER+0x1C02)
#define WM_MBR_END				(WM_USER+0x1C03)
#define WM_MBR_TAG      		(WM_USER+0x1C04)

typedef struct
{
    DWORD dwType;
    DWORD dwTime;
    DWORD dwDuration;
    char* sTagID;
    char* sTagMsg;
} TAGCALLBACKINFO;

#define MBRTAG_PHONEME 		1
#define MBRTAG_WORD         2
#define MBRTAG_SENTENCE     4
#define MBRTAG_PARAGRAPH    8     
#define MBRTAG_TAG          16

// Callback Function type
typedef int (WINAPI *LPPLAYCALLBACKPROC)(UINT msg, WPARAM wParam, LPARAM lParam);
typedef BOOL (WINAPI *LPENUMDATABASECALLBACK)(LPCTSTR lpszDatabase, DWORD dwUserData);

// Typedef for function importing
typedef LONG (WINAPI *LPLCDCD)(LPCTSTR,DWORD,LPCTSTR,DWORD);
typedef LONG (WINAPI *LPLV)();
typedef LONG (WINAPI *LPLF)(float);
typedef LONG (WINAPI *LPLL)(LONG);
typedef float (WINAPI *LPFV)();
typedef LONG (WINAPI *LPLC)(LPCTSTR);
typedef LONG (WINAPI *LPLTD)(LPTSTR,DWORD);
typedef BOOL (WINAPI *LPBV)();
typedef void (WINAPI *LPVTD)(LPTSTR,DWORD);
typedef LONG (WINAPI *LPLDTD)(DWORD,LPTSTR,DWORD);
typedef LONG (WINAPI *LPLED)(LPENUMDATABASECALLBACK,DWORD);
typedef LONG (WINAPI *LPLCTD)(LPCTSTR,LPTSTR,DWORD);
typedef BOOL (WINAPI *LPBCCCBTD)(LPCTSTR,LPCTSTR,LPCTSTR,BOOL,LPTSTR,DWORD);
typedef BOOL (WINAPI *LPBCT)(LPCTSTR);
typedef BOOL (WINAPI *LPBLTD)(LONG,LPTSTR,DWORD);
typedef DWORD (WINAPI *LPDWHW)(HWND);
typedef LONG (WINAPI *LPLDW)(DWORD);
typedef LONG (WINAPI *LPLB)(BOOL);
typedef LONG (WINAPI *LPLCCC)(LPCTSTR,LPCTSTR,LPCTSTR);
typedef VOID (WINAPI *LPVV)();

extern LPLCDCD MBR_Play;
extern LPLV MBR_Stop;
extern LPLV MBR_WaitForEnd;
extern LPLF MBR_SetPitchRatio;
extern LPLF MBR_SetDurationRatio;
extern LPLL MBR_SetVoiceFreq;
extern LPLF MBR_SetVolumeRatio;
extern LPFV MBR_GetPitchRatio;
extern LPFV MBR_GetDurationRatio;
extern LPLV MBR_GetVoiceFreq;
extern LPFV MBR_GetVolumeRatio;
extern LPLB MBR_SetNoError;
extern LPBV MBR_GetNoError;
extern LPLC MBR_SetDatabase;
extern LPLCCC MBR_SetDatabaseEx;
extern LPLTD MBR_GetDatabase;
extern LPBV MBR_IsPlaying;
extern LPLTD MBR_LastError;
extern LPVTD	MBR_GetVersion;
extern LPLV MBR_GetDefaultFreq;
extern LPLDTD MBR_GetDatabaseInfo;
extern LPLTD MBR_GetDatabaseAllInfo;
extern LPLTD MBR_RegEnumDatabase;
extern LPLED MBR_RegEnumDatabaseCallback;
extern LPLCTD MBR_RegGetDatabaseLabel;
extern LPLCTD MBR_RegGetDatabasePath;
extern LPLV MBR_RegGetDatabaseCount;
extern LPLTD MBR_RegGetDefaultDatabase;
extern LPLC MBR_RegSetDefaultDatabase;
extern LPBCCCBTD MBR_RegisterDatabase;
extern LPBCT MBR_UnregisterDatabase;
extern LPBV MBR_UnregisterAll;
extern LPBCT MBR_DatabaseExist;
extern LPBLTD MBR_RegIdxGetDatabaseId;
extern LPBLTD MBR_RegIdxGetDatabasePath;
extern LPBLTD MBR_RegIdxGetDatabaseLabel;
extern LPLC MBR_RegIdxGetDatabaseIndex;
extern LPLV MBR_RegIdxGetDefaultDatabase;
extern LPDWHW MBR_StartControlPanel;
extern LPLDW MBR_SetCallbackMsgBase;

BOOL MBR_Load();
void MBR_Unload();

#endif