@echo off
echo Starting Spring Boot backend...

cd %~dp0..\..\..\..\..\..
start cmd /k "mvn spring-boot:run"

echo Waiting for backend to start (30 seconds)...
timeout /t 30

echo Checking if backend is running...
call %~dp0check-backend.bat

echo.
echo If the backend is now running, you can proceed with running the E2E tests.