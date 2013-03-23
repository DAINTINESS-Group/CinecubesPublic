VERSION 5.00
Begin VB.Form unreg 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "Remove database"
   ClientHeight    =   1845
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   2820
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
   Icon            =   "unreg.frx":0000
   LinkTopic       =   "Form4"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   PaletteMode     =   1  'UseZOrder
   ScaleHeight     =   1845
   ScaleWidth      =   2820
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton Command2 
      Caption         =   "&Cancel"
      Height          =   495
      Left            =   1560
      TabIndex        =   4
      Top             =   1200
      Width           =   855
   End
   Begin VB.CommandButton Command1 
      Caption         =   "&Ok"
      Height          =   495
      Left            =   360
      TabIndex        =   3
      Top             =   1200
      Width           =   855
   End
   Begin VB.ComboBox Combo1 
      Height          =   315
      ItemData        =   "unreg.frx":030A
      Left            =   960
      List            =   "unreg.frx":030C
      Sorted          =   -1  'True
      TabIndex        =   2
      ToolTipText     =   "ID"
      Top             =   120
      Width           =   1455
   End
   Begin VB.Label Labeldb 
      Height          =   555
      Left            =   1080
      TabIndex        =   5
      ToolTipText     =   "Label"
      Top             =   600
      Width           =   1275
   End
   Begin VB.Label Label2 
      Caption         =   "Label:"
      Height          =   255
      Left            =   240
      TabIndex        =   1
      Top             =   600
      Width           =   855
   End
   Begin VB.Label Label1 
      Caption         =   "ID:"
      Height          =   255
      Left            =   240
      TabIndex        =   0
      Top             =   120
      Width           =   375
   End
End
Attribute VB_Name = "unreg"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim dbreg As String
Dim lbdb As String
Dim defdb As String


Private Sub Combo1_click()

lbdb = String(1024, 0)

res = MBR_RegGetDatabaseLabel(Combo1.Text, lbdb, 1024)
Faute (res)

Labeldb.Caption = lbdb

End Sub

Private Sub Command1_Click()
If Combo1.Text = frmEditor.Lng.Text Then
th = MsgBox("This database is the current database." & Chr$(10) + Chr$(13) & "Are you sure you want remove this database ?", vbYesNo + 256 + 48, Tbt$)
If th = 7 Then
Exit Sub
End If
End If
MBR_UnregisterDatabase (Combo1.Text)
If Combo1.Text = frmEditor.Lng.Text And _
MBR_RegGetDatabaseCount() <> 0 Then
res = MBR_RegGetDefaultDatabase(defdb, 1024)
Faute (res)

frmEditor.Lng.Text = defdb
lbdb = String(1024, 0)

'Affecte les labels aux db

res = MBR_RegGetDatabaseLabel(frmEditor.Lng.Text, lbdb, 1024)
Faute (res)
    
frmEditor.Labeldb.Caption = lbdb
End If
dbreg = String(1024, 0)
aud = String(1024, 0)
encdb = frmEditor.Lng.Text
nb = MBR_RegGetDatabaseCount()
nbrdb = MBR_RegGetDatabaseCount() - 1
For i = 0 To nb
frmEditor.Lng.RemoveItem 0
Next i
      For x = 0 To nbrdb
     res = MBR_RegIdxGetDatabaseId(x, dbreg, 1024)
     frmEditor.Lng.AddItem dbreg
     Next x
    
    frmEditor.Lng.Refresh
    frmEditor.Lng.Text = encdb
     If MBR_RegGetDatabaseCount() = 0 Then
frmEditor.mnuSetItem(1).Enabled = False
frmEditor.Lng.Text = ""
frmEditor.Labeldb.Caption = ""
End If
Unload unreg
End Sub

Private Sub Command2_Click()
Unload unreg
End Sub

Private Sub Form_Load()

defdb = String(1024, 0)

'Affecte les n° d'index de 0 au nombre
'de db trouvées moins un

nbrdb = MBR_RegGetDatabaseCount() - 1

'Affecte la db par défaut à lng.text

res = MBR_RegGetDefaultDatabase(defdb, 1024)
Faute (res)

    Combo1.Text = defdb
    
lbdb = String(1024, 0)

'Affecte les labels aux db

res = MBR_RegGetDatabaseLabel(Combo1.Text, lbdb, 1024)
Faute (res)

Labeldb.Caption = lbdb
     
     'Cherche les ID des db contenues dans
     'la Registery
     
     dbreg = String(1024, 0)
     For x = 0 To nbrdb
     res = MBR_RegIdxGetDatabaseId(x, dbreg, 1024)
     Combo1.AddItem dbreg
     Next x

End Sub

