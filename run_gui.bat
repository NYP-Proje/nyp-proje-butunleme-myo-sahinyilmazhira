@echo off
echo ===================================================
echo   Gider Takip Sistemi - JavaFX Launcher (GUI)
echo ===================================================

:: Detect JDK path
set "JAVA_PATH="

:: 1. Check OpenJDK 25 in User Profile directory
if exist "%USERPROFILE%\.jdks\openjdk-25.0.1\bin" (
    set "JAVA_PATH=%USERPROFILE%\.jdks\openjdk-25.0.1\bin"
)

:: 2. Check Java folder in Program Files
if "%JAVA_PATH%"=="" (
    if exist "%ProgramFiles%\Java" (
        for /d %%d in ("%ProgramFiles%\Java\jdk-*") do (
            if exist "%%d\bin\java.exe" set "JAVA_PATH=%%d\bin"
        )
    )
)

:: 3. Check if java is in System PATH
if "%JAVA_PATH%"=="" (
    where java >nul 2>nul
    if %ERRORLEVEL% equ 0 (
        set "JAVA_EXE=java"
        set "JAVAC_EXE=javac"
        goto :start
    ) else (
        echo [ERROR] Java JDK not found on this system!
        echo Please install Java JDK or run from IntelliJ.
        pause
        exit /b
    )
)

set "JAVA_EXE=%JAVA_PATH%\java.exe"
set "JAVAC_EXE=%JAVA_PATH%\javac.exe"

:start
echo Java executable: %JAVA_EXE%

:: Go to project folder
cd /d "%~dp0\Ntp"

:: Compile Java files
echo.
echo [1/2] Compiling Java source files...
if not exist "out\production\Ntp" mkdir "out\production\Ntp"
"%JAVAC_EXE%" -encoding UTF-8 -cp "lib/*" -d out/production/Ntp src/Ntp/*.java
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Compilation failed!
    pause
    exit /b
)
echo [SUCCESS] Compilation completed.

:: Launch application
echo.
echo [2/2] Launching JavaFX GUI...
"%JAVA_EXE%" "-Djava.library.path=lib" -cp "out/production/Ntp;lib/*" Ntp.AppLauncher
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Application exited with error code %ERRORLEVEL%
    pause
)
