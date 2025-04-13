@echo off
echo "how can there be a batch file in a project that requires using linux?"
echo Going to java Directory
cd ..\..\..\

echo Compiling...
javac uta/cse3310/DB/*.java

echo Running application...
java -cp ".;uta\cse3310\DB\sqlite-jdbc-3.49.1.0.jar" uta.cse3310.DB.DB

