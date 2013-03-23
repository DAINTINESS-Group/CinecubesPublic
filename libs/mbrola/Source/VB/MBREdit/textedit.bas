Attribute VB_Name = "TEXTEDIT"
Public Const Tbt$ = "MBREdit"
Public Const Fc$ = Tbt$ + " 1.1: "
Dim ArrayNum As Integer ' Index value for the menu control array mnuFileArray.
Public File$ ' This variable keeps track of the File$ information for opening and closing files.
Public Const MB_YESNO = 4, MB_ICONQUESTION = 32, IDNO = 7, MB_DEFBUTTON2 = 256


Sub CloseFile(File$)
Dim F As Integer
On Error GoTo CloseError                ' If there is an error, display the error message below.
    
    
    F = FreeFile
    Open File$ For Output As F       ' Otherwise, open the file name for output.
    Print #F, frmEditor!txtEdit.Text    ' Print the current text to the opened file.
    Close F                             ' Close the file
    File$ = "Untitled" ' Reset the caption of the main form
    Exit Sub
CloseError:
    MsgBox "Error occurred trying to close file, please retry.", 48
    Exit Sub
End Sub

Sub DoUnLoadPreCheck(unloadmode As Integer)
    If unloadmode = 0 Or unloadmode = 3 Then
            
            Unload frmEditor
            End
    End If
End Sub

Sub OpenFile(File$)
On Error GoTo Ann
Dim F As Integer

'    If fc$ + Filename = frmEditor.Caption Then  ' Avoid opening the file if already loaded.
'        Exit Sub
'    Else
        On Error GoTo errHandler
            F = FreeFile
            If frmEditor.txtEdit.Text <> "" Then
            Close F
            frmEditor.txtEdit.Text = ""
            End If
            Open File$ For Input As F                    ' Open file selected on File Open About.
            frmEditor!txtEdit.TextRTF = Input$(LOF(F), F)
            Close F                                         ' Close file.
            frmEditor!mnuFileItem(3).Enabled = True         ' Enable the Close menu item
            UpdateMenu
            frmEditor.Caption = Fc$ + File$
            Exit Sub
            frmEditor.txtEdit.Font = "MS Sans Sérif"
    'End If
errHandler:
        MsgBox "Error encountered while trying to open file, please retry.", 48, Tbt$
        Close F
        Exit Sub
Ann:
' user pressed cancel button
    Exit Sub

End Sub

Sub UpdateMenu()
    frmEditor!mnuFileArray(0).Visible = True            ' Make the initial element visible / display separator bar.
    ArrayNum = ArrayNum + 1                             ' Increment index property of control array.
    ' Check to see if Filename is already on menu list.
    For i = 0 To ArrayNum - 1
        If frmEditor!mnuFileArray(i).Caption = File$ Then
            ArrayNum = ArrayNum - 1
            Exit Sub
        End If
    Next i
    
    ' If filename is not on menu list, add menu item.
    Load frmEditor!mnuFileArray(ArrayNum)               ' Create a new menu control.
    frmEditor!mnuFileArray(ArrayNum).Caption = File$ ' Set the caption of the new menu item.
    frmEditor!mnuFileArray(ArrayNum).Visible = True     ' Make the new menu item visible.
       
End Sub
