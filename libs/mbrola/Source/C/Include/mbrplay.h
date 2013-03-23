/*
 * FPMs-TCTS SOFTWARE LIBRARY
 *
 * File    : mbrplay.cpp
 * Purpose : MbrPlay main program
 * Author  : Alain Ruelle
 * Email   : ruelle@multitel.be
 *
 * Copyright (c) 1997 Faculte Polytechnique de Mons (TCTS lab)
 * All rights reserved.
 * PERMISSION IS HEREBY DENIED TO USE, COPY, MODIFY, OR DISTRIBUTE THIS
 * SOFTWARE OR ITS DOCUMENTATION FOR ANY PURPOSE WITHOUT WRITTEN
 * AGREEMENT OR ROYALTY FEES.
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
#define MBROUT_WAVE			    2048
#define MBROUT_AU				4096
#define MBROUT_AIFF				8192

#define MBROUT_ALAW				16384
#define MBROUT_MULAW			32768

#define MBROUT_DISABLED			65536

#define MBR_PHONEME				131072
#define MBR_WORD				262144
#define MBR_SENTENCE			524288
#define MBR_PARAGRAPH			1048576
#define MBR_TAG					2097152

#define MBRERR_NOMBROLADLL			-12
#define MBRERR_NOREGISTRY			-11
#define MBRERR_NORESOURCE			-10
#define MBRERR_NOTHREAD				-9
#define MBRERR_DATABASENOTVALID		-8
#define MBRERR_CANTOPENDEVICEOUT	-7
#define MBRERR_ERRORDEVICEOUT		-6
#define MBRERR_BADCOMMAND			-5
#define MBRERR_CANTOPENFILE			-4
#define MBRERR_WRITEERROR			-3
#define MBRERR_MBROLAERROR			-2
#define MBRERR_CANCELLEDBYUSER		-1
#define MBRERR_NOERROR				0

// Custom Windows Messages
#ifdef __MBRPLAY_DLL_
#define WM_MBR_INIT				0
#define WM_MBR_READ				1
#define WM_MBR_WAIT				2
#define WM_MBR_WRITE			3
#define WM_MBR_END				4
#define WM_MBR_TAG      		5
#else
#define WM_MBR_INIT				(WM_USER+0x1BFF)
#define WM_MBR_READ				(WM_USER+0x1C00)
#define WM_MBR_WAIT				(WM_USER+0x1C01)
#define WM_MBR_WRITE			(WM_USER+0x1C02)
#define WM_MBR_END				(WM_USER+0x1C03)
#define WM_MBR_TAG      		(WM_USER+0x1C04)
#endif

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

typedef int (WINAPI *LPPLAYCALLBACKPROC)(UINT msg, WPARAM wParam, LPARAM lParam);
typedef BOOL (WINAPI *LPENUMDATABASECALLBACK)(LPCTSTR lpszID,DWORD dwUserData);

#ifdef __MBR_EXPORT
#define DECLSPEC __declspec(dllexport)
#else
#define DECLSPEC __declspec(dllimport)
#endif

extern "C"
{
// Synthesizer Input/Output Related Functions
void DECLSPEC WINAPI MBR_MBRUnload();
LONG DECLSPEC WINAPI MBR_Play(LPCTSTR lpszText, DWORD dwFlags,LPCTSTR lpszOutFile,DWORD dwCallback);
LONG DECLSPEC WINAPI MBR_Stop();
LONG DECLSPEC WINAPI MBR_WaitForEnd();
LONG DECLSPEC WINAPI MBR_SetPitchRatio(float fPitch);
LONG DECLSPEC WINAPI MBR_SetDurationRatio(float fDuration);
LONG DECLSPEC WINAPI MBR_SetVoiceFreq(LONG lFreq);
LONG DECLSPEC WINAPI MBR_SetVolumeRatio(float fVol);
float DECLSPEC WINAPI MBR_GetPitchRatio();
float DECLSPEC WINAPI MBR_GetDurationRatio();
LONG DECLSPEC WINAPI MBR_GetVoiceFreq();
float DECLSPEC WINAPI MBR_GetVolumeRatio();
LONG DECLSPEC WINAPI MBR_SetNoError(BOOL bNoError);
BOOL DECLSPEC WINAPI MBR_GetNoError();
LONG DECLSPEC WINAPI MBR_SetDatabase(LPCTSTR lpszID);
LONG DECLSPEC WINAPI MBR_SetDatabaseEx(LPCTSTR lpszID,LPCTSTR lpszRename,LPCTSTR lpszCopy);
LONG DECLSPEC WINAPI MBR_GetDatabase(LPTSTR lpID, DWORD dwSize);
BOOL DECLSPEC WINAPI MBR_IsPlaying();
LONG DECLSPEC WINAPI MBR_LastError(LPTSTR lpszError,DWORD dwSize);
// Syntheszier General informations
void DECLSPEC WINAPI MBR_GetVersion(LPTSTR lpVersion, DWORD dwSize);
// Current Database Info
LONG DECLSPEC WINAPI MBR_GetDefaultFreq();
LONG DECLSPEC WINAPI MBR_GetDatabaseInfo(DWORD idx, LPTSTR lpMsg, DWORD dwSize);
LONG DECLSPEC WINAPI MBR_GetDatabaseAllInfo(LPTSTR lpMsg, DWORD dwSize);
// Registry Related Functions
LONG DECLSPEC WINAPI MBR_RegEnumDatabase(LPTSTR lpszData,DWORD dwSize);
LONG DECLSPEC WINAPI MBR_RegEnumDatabaseCallback(LPENUMDATABASECALLBACK lpedCallback,DWORD dwUserData);
LONG DECLSPEC WINAPI MBR_RegGetDatabaseLabel(LPCTSTR lpszID, LPTSTR lpLabel, DWORD dwSize);
LONG DECLSPEC WINAPI MBR_RegGetDatabasePath(LPCTSTR lpszID, LPTSTR lpPath, DWORD dwSize);
LONG DECLSPEC WINAPI MBR_RegGetDatabaseCount();
LONG DECLSPEC WINAPI MBR_RegGetDefaultDatabase(LPTSTR lpID, DWORD dwSize);
LONG DECLSPEC WINAPI MBR_RegSetDefaultDatabase(LPCTSTR lpszID);
BOOL DECLSPEC WINAPI MBR_RegisterDatabase(LPCTSTR dbId,LPCTSTR dbPath,LPCTSTR dbLabel,BOOL isDef,LPTSTR lpBuffer,DWORD dwSize);
BOOL DECLSPEC WINAPI MBR_UnregisterDatabase(LPCTSTR dbId);
BOOL DECLSPEC WINAPI MBR_UnregisterAll();
BOOL DECLSPEC WINAPI MBR_DatabaseExist(LPCTSTR lpszID);
// Registry Releated Functions, accessed by index
BOOL DECLSPEC WINAPI MBR_RegIdxGetDatabaseId(LONG nIdx, LPTSTR lpszId, DWORD dwSize);
BOOL DECLSPEC WINAPI MBR_RegIdxGetDatabasePath(LONG nIdx, LPTSTR lpszPath, DWORD dwSize);
BOOL DECLSPEC WINAPI MBR_RegIdxGetDatabaseLabel(LONG nIdx, LPTSTR lpszLabel, DWORD dwSize);
LONG DECLSPEC WINAPI MBR_RegIdxGetDatabaseIndex(LPCTSTR lpszID);
LONG DECLSPEC WINAPI MBR_RegIdxGetDefaultDatabase();
// Control Panel Access
DWORD DECLSPEC WINAPI MBR_StartControlPanel(HWND hParent);
// Callback Message Base
LONG DECLSPEC WINAPI MBR_SetCallbackMsgBase(DWORD dwBase);

// New Registry functions
LONG DECLSPEC WINAPI MBR_RegGetDatabaseDistantPath(LPCTSTR lpszID, LPTSTR lpPath, DWORD dwSize);
BOOL DECLSPEC WINAPI MBR_RegIsDatabaseNew(LPCTSTR lpszID);
BOOL DECLSPEC WINAPI MBR_RegisterDatabaseEx(LPCTSTR lpszId, LPCTSTR lpszPath, LPCTSTR lpszLabel, LPCTSTR lpszDistantPath, BOOL bIsNew, BOOL bIsDef, LPTSTR lpBuffer, DWORD dwSize);

BOOL DECLSPEC WINAPI MBR_RegIdxGetDatabaseDistantPath(LONG nIdx, LPTSTR lpDistantPath, DWORD dwSize);
BOOL DECLSPEC WINAPI MBR_RegIdxIsDatabaseNew(LONG nIdx);

LONG DECLSPEC WINAPI MBR_RegEnumAllDatabaseCallback(LPENUMDATABASECALLBACK lpedCallback,DWORD dwUserData);

} // extern "C"

#endif // __MBRPLAY_H__