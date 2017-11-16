@echo off
echo set classpath
SET PeojectPath=%cd%
SET CLASSPATH=%PeojectPath%;%CLASSPATH%
echo change the directory to classes
echo clean the project
call mvn clean
echo run project testcases
call mvn test


echo copy the log file to the specific folder
mkdir C:\NSX6xLog
xcopy /s/h/e/y %PeojectPath%\target\surefire-reports\*.* C:\NSX6xLog