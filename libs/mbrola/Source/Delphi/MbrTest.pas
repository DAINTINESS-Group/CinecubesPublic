unit MbrTest;

{ FPMs-TCTS SOFTWARE LIBRARY                                                }
{ File : mbrplay.pas                                                        }
{ Purpose : Mbrola demonstration program for Delphi                         }
{ Author : Alain Ruelle                                                     }
{ Email : ruelle@tcts.fpms.ac.be                                            }
{                                                                           }
{ Copyright (c) 2000 Faculte Polytechnique de Mons (TCTS lab)               }
{ All rights reserved.                                                      }
{ PERMISSION IS HEREBY DENIED TO USE, COPY, MODIFY, OR DISTRIBUTE THIS      }
{ SOFTWARE OR ITS DOCUMENTATION FOR ANY PURPOSE WITHOUT WRITTEN             }
{ AGREEMENT OR ROYALTY FEES.                                                }

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  MbrPlay, StdCtrls;

function DatabaseCallback(lpszID: PChar;
                          dwUserData: pointer): Bool ; cdecl;

type
  TForm1 = class(TForm)
    Button1: TButton;
    sFileName: TEdit;
    Label1: TLabel;
    Label2: TLabel;
    sDatabase: TComboBox;
    tMore: TButton;
    tStop: TButton;
    tOpenDialog: TOpenDialog;
    procedure Button1Click(Sender: TObject);
    procedure OnCreate(Sender: TObject);
    procedure OnChange(Sender: TObject);
    procedure tStopClick(Sender: TObject);
    procedure tMoreClick(Sender: TObject);
  private
    { Déclarations privées }
  public
    { Déclarations publiques }
  end;

var
  Form1: TForm1;

implementation

{$R *.DFM}

procedure TForm1.Button1Click(Sender: TObject);
var
     pText: pchar;
     str: string[250];
begin
     pText:=@str[1];
     if (Mbr_Play(PChar(sFileName.Text),
                  MBR_BYFILE or MBR_WAIT,
                  PChar(''),
                  0) <> 0) then
     begin
          Mbr_LastError(pText,250);
          MessageBox(Form1.Handle,pText,'Error',IDOK);
     end;
end;

procedure TForm1.OnCreate(Sender: TObject);
var
   sBuffer:string[250];
   pError:pchar;
begin

     MBR_RegEnumDatabaseCallback(@DatabaseCallback,longint(@sDatabase));
     if (sDatabase.Items.Count=0) then
     begin
          MessageBox(Form1.Handle,'No database Registred','Error',IDOK);
          Close();
     end
     else
     begin
         sDatabase.itemindex:=MBR_RegIdxGetDefaultDatabase();
         if (Mbr_SetDatabase(PChar(sDatabase.Text)) <> 0) then
         begin
             pError:=@sBuffer[1];
             Mbr_LastError(pError,250);
             MessageBox(Form1.Handle,pError,'Error',IDOK);
         end;
     end;
end;

function DatabaseCallback(lpszID: PChar;
                          dwUserData: pointer): Bool ; cdecl;
begin
     TComboBox(dwUserData^).Items.Add(String(lpszID));
     DatabaseCallback:=true;
end;

procedure TForm1.OnChange(Sender: TObject);
var
   sBuffer:string[250];
   pError:pchar;
begin
     if (Mbr_SetDatabase(PChar(sDatabase.Text)) <> 0) then
     begin
          pError:=@sBuffer[1];
          Mbr_LastError(pError,250);
          MessageBox(Form1.Handle,pError,'Error',IDOK);
     end;
end;

procedure TForm1.tStopClick(Sender: TObject);
begin
     MBR_Stop();
end;

procedure TForm1.tMoreClick(Sender: TObject);
begin
     tOpenDialog.Execute();
     sFileName.Text:=tOpenDialog.FileName;
end;

end.

