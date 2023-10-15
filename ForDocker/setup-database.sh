# Wait for SQL Server to start
sleep 15s

# Run SQL scripts to create the database and tables
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "$MSSQL_SA_PASSWORD" -i /usr/src/app/default.sql
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "$MSSQL_SA_PASSWORD" -d LibraryDB -i /usr/src/app/create-tables.sql

# Keep the container running
tail -f /dev/null