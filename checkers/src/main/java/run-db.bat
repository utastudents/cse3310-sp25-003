@echo off

echo Compiling...
javac uta/cse3310/DB/*.java

echo Running application...
java -cp ".;uta\cse3310\DB\sqlite-jdbc-3.49.1.0.jar" uta.cse3310.DB.DB

