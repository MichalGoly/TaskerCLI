@echo off

REM SQLite.db and TaskerCLI.jar should be in the same directory as this script.

:indir
set /p installdir= Please input installation directory.
set /p confirm= Install in %installdir%? [Y to continue]
if "%confirm%" == "Y" (goto :remdir) else (goto :indir)
:remdir
set /p remdir= Please input remote database details [example.com:port/database]
set /p user= Please input database username
set /p pass= Please input database password
set /p confirm= Connect to %remdir% as %user%? [Y to continue]
if "%confirm%" == "Y" (goto :build) else (goto :remdir)
:build
md "%installdir%"
echo jdbc.filename=jdbc:sqlite:SQLite.db > "%installdir%\sqlite.properties"

echo jdbc.url=jdbc:mysql://%remdir% > "%installdir%\mysql.properties"
echo jdbc.username=%user% >> "%installdir%\mysql.properties"
echo jdbc.password=%password% >> "%installdir%\mysql.properties"

copy SQLite.db %installdir%
copy TaskerCLI.jar %installdir%

set /p complete= Finished installation to %installdir%, re-run to correct any errors, press return to exit.