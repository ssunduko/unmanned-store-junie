@echo off
echo Starting Unmanned Store E2E Tests...

echo Step 1: Checking if backend is running...
call %~dp0check-backend.bat
echo.

echo Step 2: Starting React frontend...
start cmd /k "cd %~dp0..\..\..\..\..\..\main\frontend\unmanned-store-ui && npm start"

echo Waiting for frontend to start (30 seconds)...
timeout /t 30

echo Step 3: Running E2E tests...
cd %~dp0..\..\..\..\..\..
mvn test -Dtest=ShoppingFlowE2ETest

echo Tests completed.
