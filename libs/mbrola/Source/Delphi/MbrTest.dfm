object Form1: TForm1
  Left = 259
  Top = 120
  Width = 329
  Height = 142
  Caption = 'MBRDelphi'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = OnCreate
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 8
    Top = 8
    Width = 46
    Height = 13
    Caption = 'Database'
  end
  object Label2: TLabel
    Left = 8
    Top = 40
    Width = 16
    Height = 13
    Caption = 'File'
  end
  object Button1: TButton
    Left = 160
    Top = 80
    Width = 75
    Height = 25
    Caption = 'Play'
    TabOrder = 0
    OnClick = Button1Click
  end
  object sFileName: TEdit
    Left = 72
    Top = 40
    Width = 217
    Height = 21
    TabOrder = 1
  end
  object sDatabase: TComboBox
    Left = 72
    Top = 8
    Width = 241
    Height = 21
    ItemHeight = 13
    TabOrder = 2
    OnChange = OnChange
  end
  object tMore: TButton
    Left = 288
    Top = 40
    Width = 25
    Height = 25
    Caption = '...'
    TabOrder = 3
    OnClick = tMoreClick
  end
  object tStop: TButton
    Left = 240
    Top = 80
    Width = 75
    Height = 25
    Caption = 'Stop'
    TabOrder = 4
    OnClick = tStopClick
  end
  object tOpenDialog: TOpenDialog
    Filter = '*.pho'
    Left = 8
    Top = 88
  end
end
