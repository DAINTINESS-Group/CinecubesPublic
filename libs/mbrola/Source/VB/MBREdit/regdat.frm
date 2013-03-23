VERSION 5.00
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.1#0"; "COMDLG32.OCX"
Begin VB.Form Regdat 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "Add database"
   ClientHeight    =   3195
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   4680
   Icon            =   "Regdat.frx":0000
   LinkTopic       =   "Form4"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   3195
   ScaleWidth      =   4680
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  'CenterScreen
   Begin VB.CheckBox Check1 
      Height          =   255
      Left            =   2880
      TabIndex        =   9
      Top             =   960
      Width           =   255
   End
   Begin MSComDlg.CommonDialog CMDialog2 
      Left            =   4080
      Top             =   1680
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   327680
      DialogTitle     =   "Open a database"
      Filter          =   "Database|*.*"
   End
   Begin VB.CommandButton Command3 
      Caption         =   "..."
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   255
      Left            =   2400
      TabIndex        =   8
      Top             =   480
      Width           =   375
   End
   Begin VB.CommandButton Command2 
      Caption         =   "&Cancel"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   495
      Left            =   2520
      TabIndex        =   7
      Top             =   2640
      Width           =   1095
   End
   Begin VB.TextBox lb 
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   285
      Left            =   120
      TabIndex        =   6
      Top             =   2040
      Width           =   1575
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
      Height          =   495
      Left            =   1080
      TabIndex        =   5
      Top             =   2640
      Width           =   975
   End
   Begin VB.TextBox id 
      BackColor       =   &H80000004&
      Enabled         =   0   'False
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   285
      Left            =   120
      TabIndex        =   3
      Top             =   1320
      Width           =   615
   End
   Begin VB.TextBox chem 
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   285
      Left            =   120
      TabIndex        =   1
      Top             =   480
      Width           =   2175
   End
   Begin VB.Label Label4 
      AutoSize        =   -1  'True
      Caption         =   "Default Database"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   195
      Left            =   3120
      TabIndex        =   10
      Top             =   960
      Width           =   1500
   End
   Begin VB.Label Label3 
      Caption         =   "Label:"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   255
      Left            =   120
      TabIndex        =   4
      Top             =   1680
      Width           =   1095
   End
   Begin VB.Label Label2 
      Caption         =   "ID:"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   255
      Left            =   120
      TabIndex        =   2
      Top             =   960
      Width           =   495
   End
   Begin VB.Label Label1 
      Caption         =   "Path:"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   255
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   975
   End
End
Attribute VB_Name = "Regdat"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim lbdb As String
Dim reg As String

Private Sub Check1_Click()
Check1.Value = 1
End Sub

Private Sub Command1_Click()
reg = String(1024, 0)
nodb = MBR_RegGetDatabaseCount()
If chem.Text = "" Then
rf = MsgBox("Database file name can't be empty !", vbOKOnly + 48, Tbt$)
Exit Sub
End If
If nodb <= 0 Then
Check1.Value = 1
End If
res = MBR_RegisterDatabase(id.Text, chem.Text, lb.Text, Check1.Value, reg, 1024)
If nodb <= 0 Then
frmEditor.Lng.Text = id.Text
frmEditor.Labeldb.Caption = lb.Text

End If
frmEditor.Lng.AddItem reg
frmEditor.mnuSetItem(1).Enabled = True
Unload Regdat
End Sub

Private Sub Command2_Click()
Unload Regdat
End Sub

Private Sub Command3_Click()
lbdb = String(1024, 0)
CMDialog2.ShowOpen

CMDialog2.InitDir = App.Path

       
df = CMDialog2.Filename
chem.Text = df
trouve = InStr(df, "\")
id.Text = Right$(df, trouve)

lb.Text = id.Text

End Sub

