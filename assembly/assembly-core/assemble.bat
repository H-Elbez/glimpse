@echo off
set ASSEMBLY_DIR=%~dp0
if "%ASSEMBLY_DIR:~-1%" == "\" set ASSEMBLY_DIR=%ASSEMBLY_DIR:~0,-1%
mvn -f "%ASSEMBLY_DIR%\..\..\pom.xml" clean install && mvn -f "%ASSEMBLY_DIR%\pom.xml" clean package
