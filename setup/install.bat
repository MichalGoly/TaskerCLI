@echo off

:indir
set /p installdir= Please input installation directory.
set /p confirm= Install in %installdir%? [Y to continue]
if "%confirm%" == "Y" (goto :dbdir) else (goto :indir)
:dbdir
set /p datadir= Please input database directory.
set /p confirm= Install in %datadir%? [Y to continue]
if "%confirm%" == "Y" (goto :remdir) else (goto :dbdir)
:remdir
set /p remdir= Please input remote database details [example.com:port/database]
set /p user= Please input database username
set /p pass= Please input database password
set /p confirm= Connect to %remdir% as %user%? [Y to continue]
if "%confirm%" == "Y" (goto :build) else (goto :remdir)
:build
md "%installdir%\src\main\resources\META-INF"
echo "jdbc.filename=jdbc:sqlite:%datadir%/SQLite.db" > "%installdir%\src\main\resources\META-INF\sqlite.properties"

echo "jdbc.url=jdbc::mysql://%remdir%" > "%installdir%\src\main\resources\META-INF\mysql.properties"
echo "jdbc.username=%user%" >> "%installdir%\src\main\resources\META-INF\mysql.properties"
echo "jdbc.password=%password%" >> "%installdir%\src\main\resources\META-INF\mysql.properties"
copy TaskerCLI.jar %installdir%
set /p complete= Finished installation to %installdir%, press return to exit.