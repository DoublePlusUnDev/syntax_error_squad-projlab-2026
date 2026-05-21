@echo off

if not exist out mkdir out

REM Build a list of all Java source files
dir /S /B src\*.java > sources.txt

REM Compile everything together
javac -d out @sources.txt

REM Run the program
java -cp out Prototype

REM Cleanup
del sources.txt