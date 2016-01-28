#!/bin/bash


confirm=N

while [$confirm != "Y"]
do
echo "Please input installation directory"
read outdir
echo "installing to $outdir, correct? [Y to continue]"
read -n confirm
done
mkdir -p "$outdir/src/main/resources/META-INF"
metadir= "$outdir/src/main/resources/META-INF"


while [$confirm != "Y"]
do
echo "Input a directory for the local database"
read dbdir
echo "local database will be located in $dbdir"
read -n confirm
done
printf -v "jdbc.filename=jdbc:sqlite:%s/SQLite.db" "$dbdir"
echo $VAR > "$metadir/sqlite.properties"


while [$confirm != "Y"]
do
echo "Input credentials for remote database"
echo -n "Host [example.host.com:port/database]:"
read remotehost
echo -n "Username:"
read remoteuser
echo "Password:"
read -s password
echo "connect to $remotehost as user $remoteuser, correct? [Y to continue] "
read -n confirm
done
printf -v "jdbc.url=jdbc::mysql://%s\njdbc.username=%s\njdbc.password=%s" "$remotehost" "$remoteuser" "$password"

echo $VAR > "$metadir/mysql.properties"

cp TaskerCLI.jar $outdir/TaskerCLI.jar

echo "TaskerCLI.jar Installed to $outdir, wait 10 seconds or press enter to exit"
read -t 10