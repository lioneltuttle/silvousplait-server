@echo off
REM Contournement : JAVA_HOME global parfois vers un JRE obsolete ou mvnw bloque sous PowerShell.
REM Lancer depuis cmd.exe : cd backend puis run-quarkus-dev.bat
REM Priorite JDK 21 (stack SVP), puis chemin JDK 25 si present.

set "JAVA_HOME="
if exist "C:\Program Files\Java\jdk-21\bin\java.exe" set "JAVA_HOME=C:\Program Files\Java\jdk-21"
if "%JAVA_HOME%"=="" if exist "C:\Program Files\Java\jdk-25.0.2\bin\java.exe" set "JAVA_HOME=C:\Program Files\Java\jdk-25.0.2"
if "%JAVA_HOME%"=="" (
  echo Aucun JDK trouve sous C:\Program Files\Java\jdk-21 ou jdk-25.0.2.
  echo Installe JDK 21 ^(recommande pour Quarkus SVP^) ou edite ce script avec ton chemin.
  exit /b 1
)
call "%JAVA_HOME%\bin\java.exe" -version >nul 2>&1
if errorlevel 1 (
  echo JAVA_HOME invalide : %JAVA_HOME%
  exit /b 1
)
call ..\mvnw.cmd quarkus:dev
