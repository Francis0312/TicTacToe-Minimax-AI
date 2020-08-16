CoordMode, Mouse, Screen
SetDefaultMouseSpeed, 0

^t::
	Send, playerOne{Enter}
	Sleep,250
	Send, playerTwo{Enter}
	Sleep,250

	inputString = 332211
	
	Loop, Parse, inputString
	{
		SendInput, %A_LoopField%{Enter}
		Sleep, 250
	}
	
	inputString2 = 211213313223
	
	Loop, Parse, inputString2
	{
		SendInput, %A_LoopField%{Enter}
		Sleep, 250
	}

^q::
