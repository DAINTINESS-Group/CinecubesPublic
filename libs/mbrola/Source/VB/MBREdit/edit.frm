VERSION 5.00
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.1#0"; "comdlg32.ocx"
Object = "{3B7C8863-D78F-101B-B9B5-04021C009402}#1.1#0"; "RICHTX32.OCX"
Begin VB.Form frmEditor 
   AutoRedraw      =   -1  'True
   BorderStyle     =   1  'Fixed Single
   ClientHeight    =   7455
   ClientLeft      =   4365
   ClientTop       =   615
   ClientWidth     =   8160
   ClipControls    =   0   'False
   BeginProperty Font 
      Name            =   "MS Sans Serif"
      Size            =   8.25
      Charset         =   0
      Weight          =   700
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   ForeColor       =   &H00C0C0C0&
   Icon            =   "edit.frx":0000
   LinkTopic       =   "Form2"
   MaxButton       =   0   'False
   PaletteMode     =   1  'UseZOrder
   ScaleHeight     =   7455
   ScaleWidth      =   8160
   StartUpPosition =   2  'CenterScreen
   WhatsThisHelp   =   -1  'True
   Begin VB.Frame Frame2 
      BorderStyle     =   0  'None
      Height          =   495
      Left            =   3240
      TabIndex        =   26
      Top             =   6960
      Width           =   3975
      Begin VB.OptionButton Aop 
         Caption         =   "A-law"
         Enabled         =   0   'False
         Height          =   255
         Index           =   1
         Left            =   2280
         TabIndex        =   29
         ToolTipText     =   "the output is filtered with an A-law 8000Hz coding (only for raw & wave files)"
         Top             =   120
         Width           =   1215
      End
      Begin VB.OptionButton Muop 
         Caption         =   "Mu-law"
         Enabled         =   0   'False
         Height          =   255
         Index           =   1
         Left            =   1200
         TabIndex        =   28
         ToolTipText     =   "the output is filtered with an mu-law 8000Hz coding (only for raw & wave files)"
         Top             =   120
         Width           =   1215
      End
      Begin VB.OptionButton Option3 
         Caption         =   "Default"
         Enabled         =   0   'False
         Height          =   255
         Index           =   0
         Left            =   120
         TabIndex        =   27
         Top             =   120
         Value           =   -1  'True
         Width           =   1215
      End
   End
   Begin VB.Frame Frame1 
      BorderStyle     =   0  'None
      Height          =   375
      Left            =   1560
      TabIndex        =   23
      Top             =   5760
      Width           =   1935
      Begin VB.OptionButton phsp 
         Caption         =   "phs"
         Height          =   255
         Index           =   1
         Left            =   960
         TabIndex        =   25
         Top             =   0
         Width           =   1095
      End
      Begin VB.OptionButton phop 
         Caption         =   "pho"
         Height          =   255
         Index           =   0
         Left            =   120
         TabIndex        =   24
         Top             =   0
         Value           =   -1  'True
         Width           =   1095
      End
   End
   Begin VB.ComboBox out 
      Enabled         =   0   'False
      Height          =   315
      ItemData        =   "edit.frx":030A
      Left            =   2040
      List            =   "edit.frx":031A
      TabIndex        =   11
      Text            =   ".WAV"
      Top             =   7080
      Width           =   975
   End
   Begin VB.CheckBox Check1 
      Caption         =   "Check1"
      Height          =   255
      Left            =   480
      TabIndex        =   10
      ToolTipText     =   "Put the result in a audio file"
      Top             =   7080
      Width           =   135
   End
   Begin MSComDlg.CommonDialog CMDialog1 
      Left            =   6720
      Top             =   5040
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   327680
      CancelError     =   -1  'True
      DialogTitle     =   "Open"
      FontSize        =   0
      MaxFileSize     =   2000
   End
   Begin RichTextLib.RichTextBox txtEdit 
      Height          =   4575
      Left            =   0
      TabIndex        =   0
      Top             =   1080
      Width           =   8175
      _ExtentX        =   14420
      _ExtentY        =   8070
      _Version        =   327680
      BorderStyle     =   0
      ScrollBars      =   3
      AutoVerbMenu    =   -1  'True
      TextRTF         =   $"edit.frx":0336
      BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
         Name            =   "MS Sans Serif"
         Size            =   12
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
   End
   Begin VB.TextBox Test 
      Height          =   285
      Left            =   0
      TabIndex        =   20
      Text            =   "0"
      Top             =   6480
      Visible         =   0   'False
      Width           =   255
   End
   Begin VB.ComboBox Lng 
      Height          =   315
      ItemData        =   "edit.frx":0401
      Left            =   2400
      List            =   "edit.frx":0403
      Sorted          =   -1  'True
      TabIndex        =   3
      ToolTipText     =   "ID"
      Top             =   600
      Width           =   735
   End
   Begin VB.HScrollBar freq 
      Height          =   255
      Left            =   5640
      Max             =   32000
      Min             =   3000
      SmallChange     =   100
      TabIndex        =   9
      Top             =   6600
      Value           =   16000
      Width           =   1935
   End
   Begin VB.TextBox fr 
      Height          =   285
      Left            =   6600
      TabIndex        =   8
      ToolTipText     =   "Frequency"
      Top             =   6240
      Width           =   735
   End
   Begin VB.PictureBox picToolbar 
      Appearance      =   0  'Flat
      BackColor       =   &H80000004&
      ForeColor       =   &H8000000F&
      Height          =   375
      Left            =   0
      ScaleHeight     =   345
      ScaleWidth      =   8145
      TabIndex        =   15
      Top             =   0
      Width           =   8175
      Begin VB.Image imgPasteUp 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   7080
         Picture         =   "edit.frx":0405
         Top             =   120
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgPasteDn 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   7320
         Picture         =   "edit.frx":058F
         Top             =   120
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgPaste 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   2640
         Picture         =   "edit.frx":0719
         ToolTipText     =   "Paste"
         Top             =   0
         Width           =   360
      End
      Begin VB.Image imgCopyUp 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   7200
         Picture         =   "edit.frx":08A3
         Top             =   120
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgCopyDn 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   6480
         Picture         =   "edit.frx":0A2D
         Top             =   120
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgCopy 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   2280
         Picture         =   "edit.frx":0BB7
         ToolTipText     =   "Copy"
         Top             =   0
         Width           =   360
      End
      Begin VB.Image imgCutUp 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   6120
         Picture         =   "edit.frx":0D41
         Top             =   240
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgCutDn 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   5880
         Picture         =   "edit.frx":0ECB
         Top             =   240
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgCut 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   1920
         Picture         =   "edit.frx":1055
         ToolTipText     =   "Cut"
         Top             =   0
         Width           =   360
      End
      Begin VB.Image imgimpUp 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   6480
         Picture         =   "edit.frx":11DF
         Top             =   240
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgimpDn 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   6000
         Picture         =   "edit.frx":1369
         Top             =   120
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgimp 
         Appearance      =   0  'Flat
         Height          =   240
         Left            =   1440
         Picture         =   "edit.frx":14F3
         ToolTipText     =   "Print"
         Top             =   0
         Width           =   240
      End
      Begin VB.Image imgFileSaveButtonUp 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   6600
         Picture         =   "edit.frx":15F5
         Top             =   0
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgFileSaveButtonDn 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   6480
         Picture         =   "edit.frx":177F
         Top             =   0
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgFileSaveButton 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   840
         Picture         =   "edit.frx":1909
         ToolTipText     =   "Save"
         Top             =   0
         Width           =   360
      End
      Begin VB.Image imgFileOpenButtonUp 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   6000
         Picture         =   "edit.frx":1A93
         Top             =   0
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgFileOpenButtonDn 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   5880
         Picture         =   "edit.frx":1C1D
         Top             =   120
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgFileOpenButton 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   480
         Picture         =   "edit.frx":1DA7
         ToolTipText     =   "Open"
         Top             =   0
         Width           =   360
      End
      Begin VB.Image imgFileNewButton 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   120
         Picture         =   "edit.frx":1F31
         ToolTipText     =   "New"
         Top             =   0
         Width           =   360
      End
      Begin VB.Image imgFileNewButtonDn 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   7320
         Picture         =   "edit.frx":20BB
         Top             =   240
         Visible         =   0   'False
         Width           =   360
      End
      Begin VB.Image imgFileNewButtonUp 
         Appearance      =   0  'Flat
         Height          =   330
         Left            =   5880
         Picture         =   "edit.frx":2245
         Top             =   0
         Visible         =   0   'False
         Width           =   360
      End
   End
   Begin VB.TextBox pit 
      Height          =   285
      Left            =   1680
      TabIndex        =   4
      ToolTipText     =   "Pitch"
      Top             =   6240
      Width           =   495
   End
   Begin VB.TextBox durat 
      Height          =   285
      Left            =   4080
      TabIndex        =   6
      ToolTipText     =   "Time"
      Top             =   6240
      Width           =   495
   End
   Begin VB.HScrollBar pitch 
      Height          =   255
      Left            =   480
      Max             =   1000
      Min             =   15
      TabIndex        =   5
      Top             =   6600
      Value           =   100
      Width           =   1935
   End
   Begin VB.HScrollBar dur 
      Height          =   255
      Left            =   3000
      Max             =   1000
      Min             =   15
      TabIndex        =   7
      Top             =   6600
      Value           =   100
      Width           =   1935
   End
   Begin VB.PictureBox Picture1 
      Appearance      =   0  'Flat
      AutoSize        =   -1  'True
      BackColor       =   &H80000005&
      ForeColor       =   &H80000008&
      Height          =   570
      Left            =   0
      Picture         =   "edit.frx":23CF
      ScaleHeight     =   540
      ScaleWidth      =   1140
      TabIndex        =   12
      Top             =   360
      Width           =   1170
   End
   Begin VB.CommandButton btnStop 
      Appearance      =   0  'Flat
      Caption         =   "S&top"
      Height          =   495
      Left            =   6960
      TabIndex        =   2
      ToolTipText     =   "Stop"
      Top             =   480
      Width           =   1095
   End
   Begin VB.CommandButton btnPlay 
      Appearance      =   0  'Flat
      Caption         =   "S&peak"
      Height          =   495
      Left            =   5760
      TabIndex        =   1
      Tag             =   "DBTTIP:Speak"
      ToolTipText     =   "Play"
      Top             =   480
      Width           =   1095
   End
   Begin VB.Label Label7 
      Caption         =   "Type of file:"
      Height          =   255
      Left            =   480
      TabIndex        =   22
      Top             =   5760
      Width           =   1095
   End
   Begin VB.Label Label6 
      AutoSize        =   -1  'True
      Caption         =   "Output format:"
      Height          =   195
      Left            =   720
      TabIndex        =   21
      Top             =   7080
      Width           =   1230
   End
   Begin VB.Label Labeldb 
      Height          =   315
      Left            =   3240
      TabIndex        =   19
      ToolTipText     =   "Label"
      Top             =   600
      Width           =   2355
   End
   Begin VB.Label Label5 
      AutoSize        =   -1  'True
      Caption         =   "Language:"
      Height          =   195
      Left            =   1440
      TabIndex        =   18
      Top             =   600
      Width           =   915
   End
   Begin VB.Label Label4 
      AutoSize        =   -1  'True
      Caption         =   "Frequency:"
      Height          =   195
      Left            =   5640
      TabIndex        =   17
      Top             =   6240
      Width           =   960
   End
   Begin VB.Label Label3 
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   255
      Left            =   120
      TabIndex        =   16
      Top             =   960
      Visible         =   0   'False
      Width           =   2295
   End
   Begin VB.Label Label2 
      Appearance      =   0  'Flat
      AutoSize        =   -1  'True
      Caption         =   "Pitch  factor:"
      Height          =   255
      Left            =   480
      TabIndex        =   13
      Top             =   6240
      Width           =   1095
   End
   Begin VB.Label Label1 
      Appearance      =   0  'Flat
      AutoSize        =   -1  'True
      Caption         =   "Time factor:"
      Height          =   255
      Left            =   3000
      TabIndex        =   14
      Top             =   6240
      Width           =   1095
   End
   Begin VB.Menu mnuFile 
      Caption         =   "&File"
      Begin VB.Menu mnuFileItem 
         Caption         =   "&New"
         Index           =   0
      End
      Begin VB.Menu mnuFileItem 
         Caption         =   "&Open..."
         Index           =   1
      End
      Begin VB.Menu mnuFileItem 
         Caption         =   "Save &As..."
         Index           =   2
      End
      Begin VB.Menu mnuFileItem 
         Caption         =   "&Close"
         Enabled         =   0   'False
         Index           =   3
      End
      Begin VB.Menu mnuFileItem 
         Caption         =   "-"
         Index           =   4
      End
      Begin VB.Menu mnuFileItem 
         Caption         =   "Print"
         Index           =   5
      End
      Begin VB.Menu mnuFileItem 
         Caption         =   "-"
         Index           =   6
      End
      Begin VB.Menu mnuFileItem 
         Caption         =   "E&xit"
         Index           =   7
      End
      Begin VB.Menu mnuFileArray 
         Caption         =   "-"
         Index           =   0
         Visible         =   0   'False
      End
   End
   Begin VB.Menu mnuedit 
      Caption         =   "&Edit"
      Begin VB.Menu mnuEditItem 
         Caption         =   "Undo            Ctrl+Z"
         Enabled         =   0   'False
         Index           =   0
      End
      Begin VB.Menu mnuEditItem 
         Caption         =   "-"
         Index           =   1
      End
      Begin VB.Menu mnuEditItem 
         Caption         =   "Cut               Ctrl+X"
         Enabled         =   0   'False
         Index           =   2
      End
      Begin VB.Menu mnuEditItem 
         Caption         =   "Copy            Ctrl+C"
         Index           =   3
      End
      Begin VB.Menu mnuEditItem 
         Caption         =   "Paste            Ctrl+V"
         Index           =   4
      End
   End
   Begin VB.Menu mnuSet 
      Caption         =   "&Settings"
      Index           =   0
      Begin VB.Menu mnuSetItem 
         Caption         =   "A&dd database"
         Index           =   0
      End
      Begin VB.Menu mnuSetItem 
         Caption         =   "&Remove database"
         Index           =   1
      End
   End
   Begin VB.Menu mnuSampa 
      Caption         =   "S&ampa"
   End
   Begin VB.Menu mnuIns 
      Caption         =   "&Insert"
      Begin VB.Menu mnuInsItem 
         Caption         =   "Database"
         Index           =   0
         Shortcut        =   ^D
      End
      Begin VB.Menu mnuInsItem 
         Caption         =   "File name"
         Index           =   1
         Shortcut        =   ^F
      End
   End
   Begin VB.Menu mnuAbout 
      Caption         =   "&About..."
      Begin VB.Menu mnuAboutItem 
         Caption         =   "MBR&Edit"
         Index           =   0
      End
      Begin VB.Menu mnuAboutItem 
         Caption         =   "Database"
         Index           =   1
      End
      Begin VB.Menu mnuAboutItem 
         Caption         =   "Version of the synthetiser"
         Index           =   2
      End
   End
End
Attribute VB_Name = "frmEditor"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
'Déclare les variables et les buffers
Const MB_YESNO = 4, MB_ICONQUESTION = 32, IDNO = 7, MB_DEFBUTTON2 = 256

Public d As Single
Public p As Single

Dim ver As String
Dim info As String
Dim reg As String
Dim dbreg As String
Dim defdb As String
Dim lbdb As String

Private Sub btnPlay_Click()
'Repère si une database est déjà dans la
'Registery
If MBR_RegGetDatabaseCount = 0 Then
    ik = MsgBox("Register a database first !", vbOKOnly + 48, Tbt$)
    Exit Sub
End If

'Vérifie s'il s'agit d'un fichier phs ou pas
phext = Right$(File$, 3)
If phext = "phs" Or phsp(1).Value = True _
And frmEditor.Caption = Fc$ + "Untitled" Then
    ph = MBR_ASPHS
    Else
    PHS = ""
End If
fq = freq.Value

For i = 0 To 2
mnuFileItem(i).Enabled = False
Next i
pitch.Enabled = False
pit.Enabled = False
durat.Enabled = False
dur.Enabled = False
fr.Enabled = False
freq.Enabled = False
Lng.Enabled = False
Aop(1).Enabled = False
Muop(1).Enabled = False
Option3(0).Enabled = False
out.Enabled = False
Check1.Enabled = False
phop(0).Enabled = False
phsp(1).Enabled = False

btnStop.Enabled = True
btnPlay.Enabled = False
    
'Affecte la db
       
res = MBR_SetDatabase(Lng.Text)
Faute (res) 'Routine d'erreur

    
    'Affecte la valeur du pitch
    
res = MBR_SetPitchRatio(p)

Faute (res)

    'Affecte la valeur de la durée
    
res = MBR_SetDurationRatio(d)
Faute (res)
   
    'Affecte la valeur de la fréquence
    
res = MBR_SetVoiceFreq(fq)
Faute (res)


  'Lit le contenu de la zone de texte
  'en faisant appel à Mbrplay.dll
    
res = MBR_Play(txtEdit.Text, MBR_WAIT + ph, MBROUT_SOUNDBOARD, 0)
Faute (res)

'Affecte les valeurs de MBR_OUT si un fichier
'son doit etre enregistré

If Check1.Value = 1 Then
  If out.Text = ".WAV" Then
    wt = MBROUT_WAVE
    If Aop(1).Value = True Then
        flaw = MBROUT_ALAW
        ElseIf Muop(1).Value = True Then
        flaw = MBROUT_MULAW
        ElseIf Option3(0).Value = True Then
        flaw = 0
    End If
    ElseIf out.Text = ".RAW" Then
    wt = MBROUT_RAW
    If Aop(1).Value = True Then
        flaw = MBROUT_ALAW
        ElseIf Muop(1).Value = True Then
        flaw = MBROUT_MULAW
        ElseIf Option3(0).Value = True Then
        flaw = 0
  End If
    ElseIf out.Text = ".AU" Then
    wt = MBROUT_AU
    flaw = 0
    ElseIf out.Text = ".AIFF" Then
    wt = MBROUT_AIFF
    flaw = 0
  End If
On Error GoTo cont
If frmEditor.Caption <> Fc$ + "Untitled" Then
    ext = InStr(File$, ".")
    nom = Left$(File$, ext - 1) + out.Text
    Else
    nom = ""
End If
 
CMDialog1.filename = nom
CMDialog1.DialogTitle = "Save sound file as"
CMDialog1.Filter = out.Text & "|(*" & out.Text
CMDialog1.Action = 2
milou = CMDialog1.filename

If Dir(milou) <> "" Then
'Si le fichier existe déjà
    response = MsgBox(milou & " already exist." & Chr$(13) & Chr$(10) & "Overwrite existing file?", MB_YESNO + MB_QUESTION + MB_DEFBUTTON2, Tbt$)
    If response = IDNO Then GoTo cont
End If
  
'Lecture
res = MBR_Play(txtEdit.Text, MBR_WAIT + ph + wt + flaw, milou, 0)
Faute (res)

    End If

cont:
'Attend la fin de la lecture

MBR_WaitForEnd
    
btnStop.Enabled = False
btnPlay.Enabled = True
pitch.Enabled = True
pit.Enabled = True
durat.Enabled = True
dur.Enabled = True
fr.Enabled = True
freq.Enabled = True
Check1.Enabled = True
phop(0).Enabled = True
phsp(1).Enabled = True

If Check1.Value = 1 Then
    out.Enabled = True
End If

For i = 0 To 2
mnuFileItem(i).Enabled = True
Next i
If phext <> "phs" Then
    Lng.Enabled = True
End If
If phsp(1).Value = True Then
    Lng.Enabled = False
    Else
    Lng.Enabled = True
End If

'Si le format wav ou raw sont actives
'rend disponibles les boutons
'Normal, mulaw ou alaw

If Check1.Value = 1 _
And out.Text = ".WAV" Or out.Text = ".RAW" Then
    Muop(1).Enabled = True
    Aop(1).Enabled = True
    Option3(0).Enabled = True
End If
mnuEditItem(0).Enabled = False
mnuEditItem(0).Caption = "Undo            Ctrl+Z"
txtEdit.SetFocus
End Sub

Private Sub btnStop_Click()
    
    'Stoppe la lecture
    
    res = MBR_Stop()
    Faute (res)

    btnPlay.Enabled = True
If phsp(1).Value = 1 Then
    Lng.Enabled = False
    Else
    Lng.Enabled = True
End If
End Sub

Private Sub Check1_Click()
phext = Right$(File$, 3)
If Check1.Value = 1 Then
out.Enabled = True
If out.Text = ".WAV" Or out.Text = ".RAW" Then
Muop(1).Enabled = True
Aop(1).Enabled = True
Option3(0).Enabled = True
Else
Muop(1).Enabled = False
Aop(1).Enabled = False
Option3(0).Enabled = False
End If
If phext = "phs" Then
Muop(1).Enabled = False
Aop(1).Enabled = False
Option3(0).Enabled = False
End If
Else
out.Enabled = False
Muop(1).Enabled = False
Aop(1).Enabled = False
Option3(0).Enabled = False
End If

End Sub

Private Sub dur_Change()
    
    'Affecte une valeur
    
    d = dur.Value / 100
       durat.Text = d
End Sub


Private Sub durat_Change()
On Error Resume Next
If durat.Text <> "" Then
dur.Value = durat.Text * 100
End If
End Sub


Private Sub durat_LostFocus()
If durat.Text > 10 Or durat.Text < 0.15 Then
ik = MsgBox("The time value must be between 0.15 and 10", _
0 + 48, Tbt$)
durat.SetFocus
End If
End Sub

Private Sub Form_Load()

defdb = String(1024, 0)
frmEditor.Caption = Fc$ + "Untitled"
'Affecte les n° d'index de 0 au nombre
'de db trouvées moins un

nbrdb = MBR_RegGetDatabaseCount() - 1

If MBR_RegGetDatabaseCount() > 0 Then
 
'Affecte la db par défaut à lng.text

res = MBR_RegGetDefaultDatabase(defdb, 1024)
Faute (res)

Lng.Text = defdb
lbdb = String(1024, 0)

'Affecte les labels aux db

res = MBR_RegGetDatabaseLabel(Lng.Text, lbdb, 1024)
Faute (res)

    
Labeldb.Caption = lbdb
     
     'Cherche les ID des db contenues dans
     'la Registery
     
     dbreg = String(1024, 0)
    ' If nbrdb = -1 Then

      For x = 0 To nbrdb
     res = MBR_RegIdxGetDatabaseId(x, dbreg, 1024)
     Lng.AddItem dbreg
     Next x
     End If
     durat.Text = "1"
    pit.Text = "1"
    
    fr.Text = "16000"

    p = 1
    d = 1
    
    'Initialise le chemin et le lecteur
    'courants comme etant le chemin du
    'programme
    
        ChDir App.Path
    ChDrive App.Path
   If MBR_RegGetDatabaseCount() = 0 Then
mnuSetItem(1).Enabled = False
End If
     btnPlay.Enabled = False
     btnStop.Enabled = False
     mnuIns.Enabled = False
     'Valeur indiquant si le texte a changé:
     '0 si non, 1 si oui
     
     Test.Text = 0
     frmEditor.Show
 newfile.Show 1
End Sub

Private Sub Form_QueryUnload(Cancel As Integer, unloadmode As Integer)

'Quand l'utilisateur ferme le programme
'en cliquant sur la croix
On Error GoTo err
If txtEdit.Text <> "" And _
frmEditor.Caption = Fc$ + "Untitled" Then
t = MsgBox("Do you want save the changements in Untitled ?", 51, "Save")
    If t = 6 Then
    CMDialog1.filename = ""
    Enrsous (File$)
     ElseIf t = 7 Then
    End
    ElseIf t = 2 Then
    Cancel = True
    Exit Sub
    End If
    Test.Text = 0
    End If
    If Test.Text = 1 Then
    t = MsgBox("Do you want save the changements in " & File$ & " ?", 3 + 48, "Save")
    
    If t = 6 Then
    CloseFile (File$)
     ElseIf t = 7 Then
    End
    ElseIf t = 2 Then
    Cancel = True
    Exit Sub
    End If
End If

' The general procedure DoUnLoadPreCheck handles the possible unload options
' for all three forms in this sample application.
    DoUnLoadPreCheck unloadmode
err:
  Exit Sub
End Sub



'Private Sub Form_Resize()
'
'
'    txtEdit.Width = ScaleWidth
'    picToolbar.Width = ScaleWidth
'
'    If WindowState = 1 Then
'    txtEdit.Height = ScaleHeight
'    Else
'    txtEdit.Height = ScaleHeight - 1800
'    End If
'    btnPlay.Left = ScaleWidth - 2500
'
'    btnStop.Left = ScaleWidth - 1110
'    Label1.Left = ScaleWidth - 4725
'
'    durat.Left = ScaleWidth - 3525
'    dur.Left = ScaleWidth - 4725
'    Label2.Top = ScaleHeight - 602.3
'    pit.Top = ScaleHeight - 602.3
'    pitch.Top = ScaleHeight - 266
'    Label1.Top = ScaleHeight - 602.3
'    durat.Top = ScaleHeight - 602.3
'    dur.Top = ScaleHeight - 266
'    Label4.Left = ScaleWidth - 2445
'
'    fr.Left = ScaleWidth - 1725
'    freq.Left = ScaleWidth - 2445
'    Label4.Top = ScaleHeight - 602.3
'    fr.Top = ScaleHeight - 602.3
'    freq.Top = ScaleHeight - 266
'End Sub

Private Sub Form_Unload(Cancel As Integer)
UpdateMenu
MBR_MBRUnload
End Sub

Private Sub fr_Change()
On Error Resume Next

If fr.Text <> "" Then
freq.Value = fr.Text
End If
End Sub

Private Sub fr_LostFocus()
If fr.Text > 30000 Or fr.Text < 3000 Then
ik = MsgBox("The frequency value must be between 3000 and 30000", _
0 + 48, Tbt$)
fr.SetFocus
End If
End Sub

Private Sub freq_Change()
fq = freq.Value
fr.Text = fq
End Sub

Private Sub imgCopy_Click()
    imgCopy.Refresh
    Clipboard.Clear
    Clipboard.SetText txtEdit.SelText ' Copy selected text to Clipboard.

End Sub

Private Sub imgCopy_MouseDown(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgCopy.Picture = imgCopyDn.Picture
End Sub

Private Sub imgCopy_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgCopy.Picture = imgCopyUp.Picture
End Sub

Private Sub imgCut_Click()
    imgCut.Refresh
    Clipboard.Clear
    Clipboard.SetText txtEdit.SelText ' Copy selected text to Clipboard.
    txtEdit.SelText = "" ' Clear selected text from the document.

End Sub

Private Sub imgCut_MouseDown(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgCut.Picture = imgCutDn.Picture
End Sub

Private Sub imgCut_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgCut.Picture = imgCutUp.Picture
End Sub

Private Sub imgFileNewButton_Click()
    imgFileNewButton.Refresh
   mnuFileItem_Click (0)
End Sub

Private Sub imgFileNewButton_MouseDown(Button As Integer, Shift As Integer, x As Single, y As Single)
imgFileNewButton.Picture = imgFileNewButtonDn.Picture
End Sub

Private Sub imgFileNewButton_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgFileNewButton.Picture = imgFileNewButtonUp.Picture
End Sub

Private Sub imgFileOpenButton_Click()

    imgFileOpenButton.Refresh

mnuFileItem_Click (1)
err:
Exit Sub
End Sub

Private Sub imgFileOpenButton_MouseDown(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgFileOpenButton.Picture = imgFileOpenButtonDn.Picture
End Sub

Private Sub imgFileOpenButton_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgFileOpenButton.Picture = imgFileOpenButtonUp.Picture
End Sub

Private Sub imgFileSaveButton_Click()
    imgFileSaveButton.Refresh
On Error GoTo err
        If frmEditor.Caption = Fc$ + "Untitled" Then
    CMDialog1.filename = ""
    CMDialog1.DialogTitle = "Save As"
  CMDialog1.Filter = "All Files (*.*)|*.*|Text Files (*.txt)|*.txt|Batch Files (*.bat)|*.bat|Mbrola (*.pho)|*.pho|Mbrola (*.phs)|*.phs"
      If phsp(1).Value = True Then
    CMDialog1.FilterIndex = 5
    CMDialog1.DefaultExt = "PHS"
    Else
    CMDialog1.FilterIndex = 4
    CMDialog1.DefaultExt = "PHO"
    End If
        ' Specify default filter
17         CMDialog1.ShowSave
        File$ = CMDialog1.filename
                  If Dir(File$) <> "" Then       ' File already exists, so ask if overwriting is desired.
     response = MsgBox(File$ & " already exist." & Chr$(13) & Chr$(10) & "Overwrite existing file?", MB_YESNO + MB_QUESTION + MB_DEFBUTTON2, Tbt$)
    If response = IDNO Then GoTo 17
    End If
        CloseFile (File$)
        Test.Text = 0
    mnuEditItem(0).Enabled = False
    mnuEditItem(0).Caption = "Undo            Ctrl+Z"

Else
    CloseFile (File$)
    Test.Text = 0
    mnuEditItem(0).Enabled = False
    mnuEditItem(0).Caption = "Undo            Ctrl+Z"
End If
err:
Exit Sub
End Sub

Private Sub imgFileSaveButton_MouseDown(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgFileSaveButton.Picture = imgFileSaveButtonDn.Picture
End Sub

Private Sub imgFileSaveButton_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgFileSaveButton.Picture = imgFileSaveButtonUp.Picture
End Sub

Private Sub imgimp_Click()
    imgimp.Refresh
        
        Printer.Print txtEdit.Text
        
        Printer.EndDoc
mnuEditItem(0).Enabled = False
mnuEditItem(0).Caption = "Undo            Ctrl+Z"


End Sub

Private Sub imgimp_MouseDown(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgimp.Picture = imgimpDn.Picture
    End Sub

Private Sub imgimp_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgimp.Picture = imgimpUp.Picture
End Sub

Private Sub imgPaste_Click()
    imgPaste.Refresh
     txtEdit.SelText = Clipboard.GetText() ' Paste Clipboard text (if any) into document.
End Sub

Private Sub imgPaste_MouseDown(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgPaste.Picture = imgPasteDn.Picture
End Sub

Private Sub imgPaste_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
    imgPaste.Picture = imgPasteUp.Picture
End Sub

Private Sub Label3_Click()
stat = Label3.Caption
End Sub

Private Sub Lng_Click()
lbdb = String(1024, 0)

res = MBR_RegGetDatabaseLabel(Lng.Text, lbdb, 1024)
Labeldb.Caption = lbdb

End Sub

Private Sub mnuAboutItem_Click(Index As Integer)

info = String(1024, 0)
ver = String(1024, 0)

Select Case Index
Case 0
    Form2.Show 1
    Case 1
    If MBR_RegGetDatabaseCount <= 0 Then
    zot = MsgBox("No database loaded.", vbOKOnly + 48, Tbt$)
    Exit Sub
    End If
    res = MBR_SetDatabase(Lng.Text)
    Faute (res)

        dat = MBR_GetDatabaseAllInfo(info, 1024)
        dbinfo.Text1.Text = info
        dbinfo.Caption = "Database infos for << " & Labeldb.Caption & " >>"
        dbinfo.Show 1
     Case 2
     synt = MBR_GetVersion(ver, 1024)
     v = MsgBox("MBROLA Synthetiser" + Chr$(10) + _
     Chr$(13) + "version " + ver, _
     vbOKOnly, Tbt$)
        End Select
End Sub

Private Sub mnuEdit_Click()
    ' Disable Cut and Copy if no text selected.
    mnuEditItem(2).Enabled = (txtEdit.SelLength > 0)
    mnuEditItem(3).Enabled = (txtEdit.SelLength > 0)
End Sub

Private Sub mnuEditItem_Click(Index As Integer)
    
   
    Select Case Index
        Case 0
        SendKeys "^z"
        Test.Text = 1
        If mnuEditItem(0).Caption = "Undo            Ctrl+Z" Then
        mnuEditItem(0).Caption = "Redo            Ctrl+Z"
        Else
        mnuEditItem(0).Caption = "Undo            Ctrl+Z"
        End If
        Case 2 ' If Index = 0, user chose "Cut"
            Clipboard.Clear
            Clipboard.SetText txtEdit.SelText ' Copy selected text to Clipboard.
            txtEdit.SelText = "" ' Clear selected text from the document.
        Test.Text = 1
        Case 3 ' If Index = 1, user chose "Copy"
            Clipboard.Clear
            Clipboard.SetText txtEdit.SelText ' Copy selected text to Clipboard.
        Case 4 ' If Index = 2, user chose "Paste"
            txtEdit.SelText = Clipboard.GetText() ' Paste Clipboard text (if any) into document.
   Test.Text = 1

    End Select
End Sub

Private Sub mnuFileArray_Click(Index As Integer)
                  If Test.Text = 1 And _
frmEditor.Caption = Fc$ + "Untitled" Then
t = MsgBox("Do you want save the changements in Untitled ?", 4 + 48, "Save")
    If t = 6 Then

  CMDialog1.Filter = "All Files (*.*)|*.*|Text Files (*.txt)|*.txt|Batch Files (*.bat)|*.bat|Mbrola (*.pho;*.phs)|*.pho;*.phs"
        ' Specify default filter
        CMDialog1.FilterIndex = 4
        ' display the File Open dialog
        CMDialog1.Action = 2
        File$ = CMDialog1.filename
        CloseFile (File$)

    End If
    End If
    If Test.Text = 1 And _
frmEditor.Caption <> Fc$ + "Untitled" Then
    t = MsgBox("Do you want save the changements in " & File$ & " ?", 4 + 48, "Save")

    If t = 6 Then
    CloseFile (File$)

  End If
End If
' Open the selected file.
    If Index >= 0 Then
        OpenFile (mnuFileArray(Index).Caption)
    Test.Text = 0
    End If
            phext = Right$(mnuFileArray(Index).Caption, 3)
Verify (phext)
End Sub

Private Sub mnuFileItem_Click(Index As Integer)
      
 On Error GoTo err

Select Case Index   ' Check index value of selected menu item.
    
    Case 0  ' If index = 0, the user chose "New"
        If Test.Text = 1 And _
frmEditor.Caption = Fc$ + "Untitled" Then
'Si le texte a été modifié et si le fichier
'n'a pas encore de nom

t = MsgBox("Do you want save the changements in Untitled ?", 3 + 48, "Save")
    If t = 6 Then
    CMDialog1.filename = ""
    Enrsous (File$)

    ElseIf t = 2 Then
    Exit Sub
    End If
    End If
    If Test.Text = 1 And _
frmEditor.Caption <> Fc$ + "Untitled" Then
    t = MsgBox("Do you want save the changements in " & File$ & " ?", 3 + 48, "Save")
    
    If t = 6 Then
    CloseFile (File$)
  ElseIf t = 2 Then
  Exit Sub
  End If
End If
Lng.Enabled = True

        txtEdit.Text = ""       ' Clear text box
        Test.Text = 0
        File$ = "Untitled"   ' Set the title bar caption to fc$ +"Untitled"
        frmEditor.Caption = Fc$ & File$
        If out.Enabled = True And _
        out.Text = ".WAV" Or out.Text = ".RAW" Then
Muop(1).Enabled = True
Aop(1).Enabled = True
Option3(0).Enabled = True

End If
newfile.Show 1
mnuEditItem(0).Enabled = False
mnuEditItem(0).Caption = "Undo            Ctrl+Z"
    
    Case 1  ' If index = 1, the user chose "Open..."
        
              If Test.Text = 1 And _
frmEditor.Caption = Fc$ + "Untitled" Then
t = MsgBox("Do you want save the changements in Untitled ?", 3 + 48, "Save")
    If t = 6 Then
    CMDialog1.filename = ""
    Enrsous (File$)
    ElseIf t = 2 Then
    Exit Sub
    End If
    End If
    If Test.Text = 1 And _
frmEditor.Caption <> Fc$ + "Untitled" Then
    t = MsgBox("Do you want save the changements in " & File$ & " ?", 3 + 48, "Save")
    
    If t = 6 Then
    CloseFile (File$)
   ElseIf t = 2 Then
    Exit Sub
  End If
End If
If Test.Text = 0 And frmEditor.Caption = Fc$ + "Untitled" Then
End If
CMDialog1.filename = ""
        CMDialog1.DialogTitle = "Open"
        CMDialog1.Filter = "All Files (*.*)|*.*|Text Files (*.txt)|*.txt|Mbrola (*.pho;*.phs)|*.pho;*.phs"
        ' Specify default filter
        CMDialog1.FilterIndex = 3
        ' display the File Open dialog
        CMDialog1.Action = 1
        File$ = CMDialog1.filename
        OpenFile (File$)
        
        Test.Text = 0
        btnPlay.Enabled = True
        Lng.Enabled = True
        phext = Right$(File$, 3)
        Verify (phext)

mnuEditItem(0).Enabled = False
mnuEditItem(0).Caption = "Undo            Ctrl+Z"
    
    
    Case 2  ' If index = 2, the user chose "Save As..."
         CMDialog1.filename = File$
         Enrsous (File$)
         frmEditor.Caption = Fc$ & File$
        Test.Text = 0
        phext = Right$(File$, 3)
        
        Verify (phext)
        
        mnuEditItem(0).Enabled = False
        mnuEditItem(0).Caption = "Undo            Ctrl+Z"
    
    
    Case 3  ' If index = 3, the user chose "Close"
                     If Test.Text = 1 And _
frmEditor.Caption = Fc$ + "Untitled" Then
t = MsgBox("Do you want save the changements in Untitled ?", 3 + 48, "Save")
    If t = 6 Then
    CMDialog1.filename = ""
    Enrsous (File$)
     ElseIf t = 2 Then
    Exit Sub
    End If
    End If
    If Test.Text = 1 And _
frmEditor.Caption <> Fc$ + "Untitled" Then
    t = MsgBox("Do you want save the changements in " & File$ & " ?", 3 + 48, "Save")
    
    If t = 6 Then
    CloseFile (File$)
   ElseIf t = 2 Then
    Exit Sub
  End If
End If

txtEdit.Text = ""                             ' Clear text box
        frmEditor.Caption = Fc$ + "Untitled"  ' Refresh caption of form
        mnuFileItem(3).Enabled = False
        

    Test.Text = 0
    Case 4  ' This menu item is a separator bar, no code needs to be written here
            ' because it cannot be selected and therefore cannot receive a Click event.
    Case 5
        'If Index = 3, the user choose "Print"
'        CMDialog1.Flags = cdlPDPrintSetup
        
'        CMDialog1.DialogTitle = "Print"
'        CMDialog1.PrinterDefault = True
'        CMDialog1.Action = 5
        
        Printer.Print txtEdit.Text
        
        Printer.EndDoc
       
mnuEditItem(0).Enabled = False
mnuEditItem(0).Caption = "Undo            Ctrl+Z"
    
    Case 6  ' This menu item is a separator bar, no code needs to be written here
            ' because it cannot be selected and therefore cannot receive a Click event.

    Case 7 'Exit

Form_QueryUnload 0, 0

mnuEditItem(0).Enabled = False
mnuEditItem(0).Caption = "Undo            Ctrl+Z"
End Select
err:
' user pressed cancel button
   Exit Sub
End Sub
Private Sub mnuInsItem_Click(Index As Integer)
Select Case Index
    
    Case 0
    frminda.Show 1
    
    Case 1
On Error GoTo err
        
    CMDialog1.DialogTitle = "Insert a file name"
        CMDialog1.Filter = "Mbrola (*.pho;*.phs)|*.pho;*.phs"
        CMDialog1.FilterIndex = 0
     CMDialog1.ShowOpen
        fich = CMDialog1.filename
        txtEdit.SelText = fich + Chr$(13) + Chr$(10)
    End Select
    
err:
    Exit Sub
End Sub

Private Sub mnuSampa_Click()
 If MBR_RegGetDatabaseCount <= 0 Then
    zot = MsgBox("No database loaded.", vbOKOnly + 48, Tbt$)
    Exit Sub
    End If
    Form1.Show 1
    
End Sub

Private Sub mnuSetItem_Click(Index As Integer)
Select Case Index
Case 0
Regdat.Show 1
Case 1
unreg.Show 1
End Select
End Sub

Private Sub out_Click()
phext = Right$(File$, 3)
If out.Enabled = True Then
If out.Text = ".WAV" Or out.Text = ".RAW" Then
Muop(1).Enabled = True
Aop(1).Enabled = True
Option3(0).Enabled = True

Else
Muop(1).Enabled = False
Aop(1).Enabled = False
Option3(0).Enabled = False
End If
End If
End Sub




Private Sub phop_GotFocus(Index As Integer)
fi = Right$(frmEditor.Caption, 3)
If fi = "phs" Then
r = MsgBox("The open file is a phs file !", 0 + 48, Tbt$)
phsp(1).Value = True
Exit Sub
End If
'If phsp(1).Value = True Then
'Lng.Enabled = False
'mnuIns.Enabled = True
'Else
Lng.Enabled = True
mnuIns.Enabled = False
'End If

End Sub

Private Sub phsp_GotFocus(Index As Integer)
fi = Right$(frmEditor.Caption, 3)
If fi = "pho" Then
r = MsgBox("The open file is a pho file !", 0 + 48, Tbt$)
phop(0).Value = True
Exit Sub
End If
'If phsp(1).Value = True Then
Lng.Enabled = False
mnuIns.Enabled = True
'Else
'Lng.Enabled = True
'mnuIns.Enabled = False
'End If

End Sub

Private Sub pit_Change()
On Error Resume Next
If pit.Text <> "" Then
pitch.Value = pit.Text * 100
End If
End Sub

Private Sub pit_LostFocus()
If pit.Text > 10 Or pit.Text < 0.15 Then
ik = MsgBox("The pitch value must be between 0.15 and 10", _
0 + 48, Tbt$)
pitch.SetFocus
End If
End Sub

Private Sub pitch_Change()
    
    p = pitch.Value / 100
    pit.Text = p
End Sub

Private Sub txtEdit_Change()
    If txtEdit.Text = "" Then
    btnPlay.Enabled = False
    Else
    btnPlay.Enabled = True
    frmEditor.mnuFileItem(3).Enabled = True
    End If
    If txtEdit.Text <> "" Then
    Test.Text = 1
    mnuEditItem(0).Enabled = True
    End If
    
    End Sub


Private Sub txtEdit_MouseDown(Button As Integer, Shift As Integer, x As Single, y As Single)
If Button = 2 Then
PopupMenu mnuedit, 4
Else
Exit Sub
End If
End Sub
