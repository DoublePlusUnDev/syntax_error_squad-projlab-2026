@echo off
javac -d out src\*.java
java -cp out Skeleton
pause