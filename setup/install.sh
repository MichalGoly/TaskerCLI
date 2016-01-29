#!/bin/bash

# requires SQLite.db, TaskerCLI.jar to be in the same directory as this script

export confirm="N"

while [ "$confirm" != "Y" ]
do
echo "Please input installation directory"
read outdir
echo "installing to $outdir, correct? [Y to continue]"
read confirm
done

export confirm="N"

while [ "$confirm" != "Y" ]
do
echo "Input credentials for remote database"
echo "Host [example.com:port/database]:"
read remotehost
echo "Username:"
read remoteuser
echo "Password:"
read -s password
echo "connect to $remotehost as user $remoteuser, correct? [Y to continue] "
read confirm
done


mkdir -p "$outdir"
printf "jdbc.filename=jdbc:sqlite:$outdir/SQLite.db" > "$outdir/sqlite.properties"
printf "jdbc.url=jdbc::mysql://$remotehost\njdbc.username=$remoteuser\njdbc.password=$password" > "$outdir/mysql.properties"

cp ./TaskerCLI.jar $outdir/TaskerCLI.jar
cp ./SQLite.db $outdir/SQLite.db

echo "you may re-run this script if you have made any errors"
echo "TaskerCLI.jar Installed to $outdir, wait 10 seconds or press enter to exit"
read -t 10