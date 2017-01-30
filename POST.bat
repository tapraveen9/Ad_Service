@ECHO off
REM POST call
setlocal EnableDelayedExpansion
SET /p arg1="enter ad id: "
SET /p arg2="enter the ad description: "
SET /p arg3="enter the time duration: "
SET "arg_2="%arg2%""
java AdServiceClient POST %arg1% %arg_2% %arg3%
endlocal
ECHO on