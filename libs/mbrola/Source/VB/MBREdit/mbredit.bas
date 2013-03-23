Attribute VB_Name = "Module1"
Public aud As String
Public Sub Faute(Resultat)
aud = String(1024, 0)
er = MBR_LastError(aud, 1024)
If Resultat <> 0 Then
    i = MsgBox(aud, vbOKOnly, Tbt$)
End If
End Sub

Public Sub Verify(Variable)
With frmEditor
If Variable = "phs" Then

.Lng.Enabled = False
.phsp(1).Value = True
.mnuIns.Enabled = True

Else
.Lng.Enabled = True
.phop(0).Value = True
.mnuIns.Enabled = False

End If
End With
End Sub

Public Sub Enrsous(F$)
With frmEditor
         .CMDialog1.DialogTitle = "Save As"
        .CMDialog1.Filter = "Mbrola (*.pho)|*.pho|Mbrola (*.phs)|*.phs"
        If .phsp(1).Value = True Then
    .CMDialog1.FilterIndex = 2
    .CMDialog1.DefaultExt = "phs"
    Else
    .CMDialog1.FilterIndex = 1
    .CMDialog1.DefaultExt = "pho"
    End If
        ' Specify default filter
        
        ' display the File Open dialog
     
30         .CMDialog1.ShowSave
               Select Case .CMDialog1.FilterIndex()
            
            Case 1
            nwext = "pho"
            Case 2
            nwext = "phs"
            
        End Select
        
        File$ = Left$(.CMDialog1.filename, Len(.CMDialog1.filename) - 3) + nwext
                      
         
               If Dir(File$) <> "" Then       ' File already exists, so ask if overwriting is desired.
     response = MsgBox(File$ & " already exist." & Chr$(13) & Chr$(10) & "Overwrite existing file?", MB_YESNO + MB_QUESTION + MB_DEFBUTTON2, Tbt$)
    If response = IDNO Then GoTo 30
    End If
        
        
        CloseFile (File$)
        End With
End Sub
