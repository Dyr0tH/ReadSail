# Use the official Microsoft SQL Server 2022 image from Microsoft Container Registry
FROM mcr.microsoft.com/mssql/server:2022-latest

# Set environment variables for SQL Server
ENV ACCEPT_EULA=Y
# Change this with your own password
ENV MSSQL_SA_PASSWORD=yourStrongPassword

# Expose port 1433 for SQL Server
EXPOSE 1433

# Create a directory inside the container to copy your SQL scripts
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Copy your SQL setup scripts into the container
COPY ForDocker/sql-scripts /usr/src/app

# Copy the setup-database.sh script into the container
COPY ForDocker/setup-database.sh /usr/src/app

# Run the SQL setup scripts when the container starts
CMD /opt/mssql/bin/sqlservr & /usr/src/app/setup-database.sh
