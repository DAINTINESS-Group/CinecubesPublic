VERSION 5.00
Begin VB.Form frminda 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "Insert database name"
   ClientHeight    =   1020
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   4680
   Icon            =   "frminda.frx":0000
   LinkTopic       =   "Form4"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   1020
   ScaleWidth      =   4680
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  'CenterScreen
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
      Height          =   375
      Left            =   2400
      TabIndex        =   4
      Top             =   600
      Width           =   975
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
      Left            =   1200
      TabIndex        =   3
      Top             =   600
      Width           =   975
   End
   Begin VB.ComboBox Lng 
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   315
      ItemData        =   "frminda.frx":030A
      Left            =   1200
      List            =   "frminda.frx":030C
      Sorted          =   -1  'True
      TabIndex        =   0
      ToolTipText     =   "ID"
      Top             =   120
      Width           =   735
   End
   Begin VB.Label Label5 
      AutoSize        =   -1  'True
      Caption         =   "Language:"
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
      Left            =   240
      TabIndex        =   2
      Top             =   120
      Width           =   915
   End
   Begin VB.Label Labeldb 
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   315
      Left            =   2040
      TabIndex        =   1
      ToolTipText     =   "Label"
      Top             =   120
      Width           =   2355
   End
End
Attribute VB_Name = "frminda"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim lbdb As String

Dim defdb As String

Dim dbreg As String

Private Sub Command1_Click()
frmEditor.txtEdit.SelText = "Database=" & Lng.Text + Chr(13) + Chr(10)
Unload frminda
End Sub

Private Sub Command2_Click()
Unload frminda
End Sub

Private Sub Form_Load()

defdb = String(1024, 0)

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
    
    'Initialise le chemin et le lecteur
    'courants comme etant le chemin du
    'programme
    
        ChDir App.Path
    ChDrive App.Path

End Sub


Private Sub Lng_Click()
lbdb = String(1024, 0)

res = MBR_RegGetDatabaseLabel(Lng.Text, lbdb, 1024)
Labeldb.Caption = lbdb
End Sub
