@ECHO off
REM GET call
setlocal EnableDelayedExpansion
SET /p arg1="enter partner id: "
java AdServiceClient GET %arg1%
endlocal
ECHO on