#!/bin/bash


export confirm="N"

while [ "$confirm" != "Y" ]
do
echo "Please input installation directory"
read outdir
echo "installing to $outdir, correct? [Y to continue]"
read confirm
done

mkdir -p "$outdir/src/main/resources/META-INF"

export confirm="N"

while [ "$confirm" != "Y" ]
do
echo "Input a directory for the local database"
read dbdir
echo "local database will be located in $dbdir, correct? [Y to continue]"
read confirm
done

printf "jdbc.filename=jdbc:sqlite:$dbdir/SQLite.db" > "$outdir/src/main/resources/META-INF/sqlite.properties"

export confirm="N"

while [ "$confirm" != "Y" ]
do
echo "Input credentials for remote database"
echo "Host [example.host.com:port/database]:"
read remotehost
echo "Username:"
read remoteuser
echo "Password:"
read password
echo "connect to $remotehost as user $remoteuser, correct? [Y to continue] "
read confirm
done
printf "jdbc.url=jdbc::mysql://$remotehost\njdbc.username=$remoteuser\njdbc.password=$password" > $outdir/src/main/resources/META-INF/mysql.properties

cp TaskerCLI.jar $outdir/TaskerCLI.jar

echo "TaskerCLI.jar Installed to $outdir, wait 10 seconds or press enter to exit"
read -t 10