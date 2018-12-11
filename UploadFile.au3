#cs ----------------------------------------------------------------------------

 AutoIt Version: 3.3.10.2
 Author:         myName

 Script Function:
	Template AutoIt script.

#ce ----------------------------------------------------------------------------
#include <Excel.au3>
#include <GUIConstantsEx.au3>
#include <File.au3>
#include <MsgBoxConstants.au3>
; Script Start - Add your code below here

;$oExcel = _ExcelBookOpen("C:\DTCanada\XML\VerifyXMLDetails_Testdata.xls")
;local $sFilePath = "C:\\DTCanada\XML\AppNo.txt"

;Local $hFileOpen = FileOpen($sFilePath, $FO_READ)
 ;   If $hFileOpen = -1 Then
 ;       MsgBox($MB_SYSTEMMODAL, "", "An error occurred when reading the file.")
 ;       Return False
 ;  EndIf

;Local $sFileRead = FileRead($hFileOpen)
    ; Close the handle returned by FileOpen.
;FileClose($hFileOpen)
Local $Pathname=$CmdLine[1]
Local $Filename=$CmdLine[2]
;Local $Pathname="C:\"
;Local $Filename="Baseline_VDS.pdf"
WinWaitActive("Open")
;ControlSetText("Open", "", "Edit1", "C:\SeleniumJava\workspace\BMWMessageTest\src\test\java\functionTest\QA\Baseline_VDS.pdf")

ControlClick("Open", "", "[CLASS:ToolbarWindow32; INSTANCE:2]", "right")
ControlSend("Open", "", "[CLASS:ToolbarWindow32; INSTANCE:2]", "e") ; Choose "Edit Address" option
Sleep(250)
Send("{DEL}")
Sleep(250)
Send($Pathname & "{ENTER}")
Sleep(2000)
ControlFocus("Open", "", "Edit1")
ControlSetText("Open", "", "Edit1", $Filename)
Sleep(1000)
Send("{ENTER}")
WinClose("Open")

