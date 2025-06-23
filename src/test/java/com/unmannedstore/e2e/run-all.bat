@echo off
echo Starting Unmanned Store Complete E2E Test Suite...

echo Step 1: Starting Spring Boot backend...
cd %~dp0..\..\..\..\..\..
start cmd /k "mvn spring-boot:run"

echo Waiting for backend to start (30 seconds)...
timeout /t 30

echo Step 2: Checking if backend is running...
call %~dp0check-backend.bat
echo.

echo Step 3: Starting React frontend...
start cmd /k "cd %~dp0..\..\..\..\..\..\main\frontend\unmanned-store-ui && npm start"

echo Waiting for frontend to start (30 seconds)...
timeout /t 30

echo Step 4: Running E2E tests...
cd %~dp0..\..\..\..\..\..
mvn test -Dtest=ShoppingFlowE2ETest

echo Tests completed.
echo.
echo Note: The backend and frontend are still running in separate windows.
echo You can close those windows when you're done testing.