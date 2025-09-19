@echo off
echo === Building Airline Booking System ===
echo.
echo This will clean and build all modules...
echo.
pause

mvn clean install

if %ERRORLEVEL% EQU 0 (
    echo.
    echo === Build Successful! ===
    echo.
    echo Next steps:
    echo 1. Run start-all-services.bat to start services
    echo 2. Wait for all services to start
    echo 3. Run start-client.bat to test the system
    echo.
) else (
    echo.
    echo === Build Failed! ===
    echo Please check the error messages above.
    echo.
)

pause