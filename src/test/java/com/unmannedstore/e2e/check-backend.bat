@echo off
echo Checking if Spring Boot backend is running on port 8080...

powershell -Command "try { $response = Invoke-WebRequest -Uri 'http://localhost:8080/swagger-ui.html' -UseBasicParsing -TimeoutSec 5; if ($response.StatusCode -eq 200) { Write-Host 'Backend is running on port 8080.' -ForegroundColor Green } else { Write-Host 'Backend is not running properly on port 8080.' -ForegroundColor Red } } catch { Write-Host 'Backend is not running on port 8080. Please start the Spring Boot application.' -ForegroundColor Red; Write-Host 'Run the following command from the project root:' -ForegroundColor Yellow; Write-Host 'mvn spring-boot:run' -ForegroundColor Yellow }"

echo.
echo If the backend is not running, please start it using:
echo cd C:\Unmanned Store\unmanned-store-junie
echo mvn spring-boot:run