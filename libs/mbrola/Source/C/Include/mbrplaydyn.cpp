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
 * Dynamically Imported functions
 *
 */
#include <windows.h>
#include "mbrplaydyn.h"

HINSTANCE hMbrInst;
LPLCDCD MBR_Play;
LPLV MBR_Stop;
LPLV MBR_WaitForEnd;
LPLF MBR_SetPitchRatio;
LPLF MBR_SetDurationRatio;
LPLL MBR_SetVoiceFreq;
LPLF MBR_SetVolumeRatio;
LPFV MBR_GetPitchRatio;
LPFV MBR_GetDurationRatio;
LPLV MBR_GetVoiceFreq;
LPFV MBR_GetVolumeRatio;
LPLB MBR_SetNoError;
LPBV MBR_GetNoError;
LPLC MBR_SetDatabase;
LPLCCC MBR_SetDatabaseEx;
LPLTD MBR_GetDatabase;
LPBV MBR_IsPlaying;
LPLTD MBR_LastError;
LPVTD	MBR_GetVersion;
LPLV MBR_GetDefaultFreq;
LPLDTD MBR_GetDatabaseInfo;
LPLTD MBR_GetDatabaseAllInfo;
LPLTD MBR_RegEnumDatabase;
LPLED MBR_RegEnumDatabaseCallback;
LPLCTD MBR_RegGetDatabaseLabel;
LPLCTD MBR_RegGetDatabasePath;
LPLV MBR_RegGetDatabaseCount;
LPLTD MBR_RegGetDefaultDatabase;
LPLC MBR_RegSetDefaultDatabase;
LPBCCCBTD MBR_RegisterDatabase;
LPBCT MBR_UnregisterDatabase;
LPBV MBR_UnregisterAll;
LPBCT MBR_DatabaseExist;
LPBLTD MBR_RegIdxGetDatabaseId;
LPBLTD MBR_RegIdxGetDatabasePath;
LPBLTD MBR_RegIdxGetDatabaseLabel;
LPLC MBR_RegIdxGetDatabaseIndex;
LPLV MBR_RegIdxGetDefaultDatabase;
LPDWHW MBR_StartControlPanel;
LPLDW MBR_SetCallbackMsgBase;
LPVV MBR_MBRUnload;

BOOL MBR_Load()
{
	BOOL ret=FALSE;
	if (hMbrInst=LoadLibrary("mbrplay.dll"))
	{
		MBR_Play=(LPLCDCD) GetProcAddress(hMbrInst,"_MBR_Play@16");
		MBR_Stop=(LPLV) GetProcAddress(hMbrInst,"_MBR_Stop@0");
		MBR_WaitForEnd=(LPLV) GetProcAddress(hMbrInst,"_MBR_WaitForEnd@0");
		MBR_SetPitchRatio=(LPLF) GetProcAddress(hMbrInst,"_MBR_SetPitchRatio@4");
		MBR_SetDurationRatio=(LPLF) GetProcAddress(hMbrInst,"_MBR_SetDurationRatio@4");
		MBR_SetVoiceFreq=(LPLL) GetProcAddress(hMbrInst,"_MBR_SetVoiceFreq@4");
		MBR_SetVolumeRatio=(LPLF) GetProcAddress(hMbrInst,"_MBR_SetVolumeRatio@4");
		MBR_GetPitchRatio=(LPFV) GetProcAddress(hMbrInst,"_MBR_GetPitchRatio@0");
		MBR_GetDurationRatio=(LPFV) GetProcAddress(hMbrInst,"_MBR_GetDurationRatio@0");
		MBR_GetVoiceFreq=(LPLV) GetProcAddress(hMbrInst,"_MBR_GetVoiceFreq@0");
		MBR_GetVolumeRatio=(LPFV) GetProcAddress(hMbrInst,"_MBR_GetVolumeRatio@0");
		MBR_SetNoError=(LPLB) GetProcAddress(hMbrInst,"_MBR_SetNoError@4");
		MBR_GetNoError=(LPBV) GetProcAddress(hMbrInst,"_MBR_GetNoError@0");
		MBR_SetDatabase=(LPLC) GetProcAddress(hMbrInst,"_MBR_SetDatabase@4");
		MBR_SetDatabaseEx=(LPLCCC) GetProcAddress(hMbrInst,"_MBR_SetDatabaseEx@12");
		MBR_GetDatabase=(LPLTD) GetProcAddress(hMbrInst,"_MBR_GetDatabase@8");
		MBR_IsPlaying=(LPBV) GetProcAddress(hMbrInst,"_MBR_IsPlaying@0");
		MBR_LastError=(LPLTD) GetProcAddress(hMbrInst,"_MBR_LastError@8");
		MBR_GetVersion=(LPVTD) GetProcAddress(hMbrInst,"_MBR_GetVersion@8");
		MBR_GetDefaultFreq=(LPLV) GetProcAddress(hMbrInst,"_MBR_GetDefaultFreq@0");
		MBR_GetDatabaseInfo=(LPLDTD) GetProcAddress(hMbrInst,"_MBR_GetDatabaseInfo@12");
		MBR_GetDatabaseAllInfo=(LPLTD) GetProcAddress(hMbrInst,"_MBR_GetDatabaseAllInfo@8");
		MBR_RegEnumDatabase=(LPLTD) GetProcAddress(hMbrInst,"_MBR_RegEnumDatabase@8");
		MBR_RegEnumDatabaseCallback=(LPLED) GetProcAddress(hMbrInst,"_MBR_RegEnumDatabaseCallback@8");
		MBR_RegGetDatabaseLabel=(LPLCTD) GetProcAddress(hMbrInst,"_MBR_RegGetDatabaseLabel@12");
		MBR_RegGetDatabasePath=(LPLCTD) GetProcAddress(hMbrInst,"_MBR_RegGetDatabasePath@12");
		MBR_RegGetDatabaseCount=(LPLV) GetProcAddress(hMbrInst,"_MBR_RegGetDatabaseCount@0");
		MBR_RegGetDefaultDatabase=(LPLTD) GetProcAddress(hMbrInst,"_MBR_RegGetDefaultDatabase@8");
		MBR_RegSetDefaultDatabase=(LPLC) GetProcAddress(hMbrInst,"_MBR_RegSetDefaultDatabase@4");
		MBR_RegisterDatabase=(LPBCCCBTD) GetProcAddress(hMbrInst,"_MBR_RegisterDatabase@24");
		MBR_UnregisterDatabase=(LPBCT) GetProcAddress(hMbrInst,"_MBR_UnregisterDatabase@4");
		MBR_UnregisterAll=(LPBV) GetProcAddress(hMbrInst,"_MBR_UnregisterAll@0");
		MBR_DatabaseExist=(LPBCT) GetProcAddress(hMbrInst,"_MBR_DatabaseExist@4");
		MBR_RegIdxGetDatabaseId=(LPBLTD) GetProcAddress(hMbrInst,"_MBR_RegIdxGetDatabaseId@12");
		MBR_RegIdxGetDatabasePath=(LPBLTD) GetProcAddress(hMbrInst,"_MBR_RegIdxGetDatabasePath@12");
		MBR_RegIdxGetDatabaseLabel=(LPBLTD) GetProcAddress(hMbrInst,"_MBR_RegIdxGetDatabaseLabel@12");
		MBR_RegIdxGetDatabaseIndex=(LPLC) GetProcAddress(hMbrInst,"_MBR_RegIdxGetDatabaseIndex@4");
		MBR_RegIdxGetDefaultDatabase=(LPLV) GetProcAddress(hMbrInst,"_MBR_RegIdxGetDefaultDatabase@0");
		MBR_StartControlPanel=(LPDWHW) GetProcAddress(hMbrInst,"_MBR_StartControlPanel@4");
		MBR_SetCallbackMsgBase=(LPLDW) GetProcAddress(hMbrInst,"_MBR_SetCallbackMsgBase@4");
		MBR_MBRUnload=(LPVV) GetProcAddress(hMbrInst,"_MBR_MBRUnload@0");
		ret=(MBR_Play
      		&& MBR_Stop
            && MBR_WaitForEnd
            && MBR_SetPitchRatio
            && MBR_SetDurationRatio
            && MBR_SetVoiceFreq
			&& MBR_SetVolumeRatio
            && MBR_GetPitchRatio
            && MBR_GetDurationRatio
            && MBR_GetVoiceFreq
			&& MBR_GetVolumeRatio
			&& MBR_SetNoError
			&& MBR_GetNoError
			&& MBR_SetDatabase
			&& MBR_SetDatabaseEx
            && MBR_GetDatabase
            && MBR_IsPlaying
            && MBR_LastError
            && MBR_GetVersion
            && MBR_GetDefaultFreq
            && MBR_GetDatabaseInfo
            && MBR_GetDatabaseAllInfo
            && MBR_RegEnumDatabase
            && MBR_RegEnumDatabaseCallback
            && MBR_RegGetDatabaseLabel
            && MBR_RegGetDatabasePath
            && MBR_RegGetDatabaseCount
            && MBR_RegGetDefaultDatabase
            && MBR_RegSetDefaultDatabase
            && MBR_RegisterDatabase
            && MBR_UnregisterDatabase
            && MBR_UnregisterAll
            && MBR_DatabaseExist
            && MBR_RegIdxGetDatabaseId
            && MBR_RegIdxGetDatabasePath
            && MBR_RegIdxGetDatabaseLabel
            && MBR_RegIdxGetDatabaseIndex
            && MBR_RegIdxGetDefaultDatabase
			&& MBR_StartControlPanel
			&& MBR_SetCallbackMsgBase
			&& MBR_MBRUnload);
		if (!ret)
			free(hMbrInst);
	}
	return ret;
}

void MBR_Unload()
{
	if (hMbrInst)
	{
		MBR_MBRUnload();
	   	FreeLibrary(hMbrInst);
	}
}