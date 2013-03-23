VERSION 5.00
Begin VB.Form Form1 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "Sampa"
   ClientHeight    =   3945
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   4680
   Icon            =   "frmSampa.frx":0000
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   3945
   ScaleWidth      =   4680
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton Command2 
      Caption         =   "Print"
      Height          =   315
      Left            =   3480
      TabIndex        =   2
      ToolTipText     =   "Send the sampa alphabet to the current printer"
      Top             =   3480
      Width           =   615
   End
   Begin VB.CommandButton Command1 
      Caption         =   "&Ok"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   1800
      TabIndex        =   1
      Top             =   3480
      Width           =   975
   End
   Begin VB.TextBox Text1 
      Height          =   3255
      Left            =   0
      Locked          =   -1  'True
      MultiLine       =   -1  'True
      ScrollBars      =   2  'Vertical
      TabIndex        =   0
      TabStop         =   0   'False
      Top             =   0
      Width           =   4695
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim samp As String


Private Sub Command1_Click()
Unload Form1

End Sub

Private Sub Command2_Click()
 
        Printer.Print Text1.Text
        
        Printer.EndDoc
End Sub

Private Sub Form_Load()
samp = String(1024, 0)

Form1.Caption = "Sampa alphabet"
res = MBR_SetDatabase(frmEditor.Lng.Text)
Faute (res)

sp = MBR_GetDatabaseInfo(0, samp, 1024)
sa = "sampa"
msa = "SAMPA"
sma = "Sampa"
If InStr(samp, sa) Or InStr(samp, msa) _
Or InStr(samp, msa) Then
Text1.Text = samp
End If
End Sub

