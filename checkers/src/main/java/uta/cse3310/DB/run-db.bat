@echo off
mkdir build 2>nul

echo Compiling...
javac *.java 

echo Running application...
java -cp ".;sqlite-jdbc-3.49.1.0.jar" .\DB.java
