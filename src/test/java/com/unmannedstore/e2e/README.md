# End-to-End Tests for Unmanned Store

This package contains end-to-end tests for the Unmanned Store application using Selenium WebDriver.

## Prerequisites

The test will automatically start the backend and frontend servers if they are not already running. However, you should ensure that:

1. Java 17 or higher is installed
2. Maven is installed and configured
3. Node.js and npm are installed
4. The ports 8080 (backend) and 3000 (frontend) are not being used by other applications

> **Note:** While manual server startup is no longer required, you can still start the servers manually if you prefer, using the instructions below.

## Starting the Application

### Backend (Spring Boot)

You can start and verify the backend in one of two ways:

#### Option 1: Using the provided scripts (Recommended)

1. To check if the backend is running:
   ```
   cd C:\Unmanned Store\unmanned-store-junie\src\test\java\com\unmannedstore\e2e
   check-backend.bat
   ```

   This script will check if the backend is running and provide instructions if it's not.

2. To start the backend automatically:
   ```
   cd C:\Unmanned Store\unmanned-store-junie\src\test\java\com\unmannedstore\e2e
   start-backend.bat
   ```

   This script will start the Spring Boot backend, wait for it to initialize, and then verify that it's running.

#### Option 2: Manual startup and verification

1. Navigate to the project root directory:
   ```
   cd C:\Unmanned Store\unmanned-store-junie
   ```

2. Start the Spring Boot application using Maven:
   ```
   mvn spring-boot:run
   ```

3. Verify the backend is running by accessing:
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - H2 Database Console: http://localhost:8080/h2-console

### Frontend (React.js)

You can start the frontend in one of two ways:

#### Option 1: Using the provided script (Recommended)

1. Run the start-frontend.bat script from the e2e package:
   ```
   cd C:\Unmanned Store\unmanned-store-junie\src\test\java\com\unmannedstore\e2e
   start-frontend.bat
   ```

This script will automatically navigate to the frontend directory and start the React application on port 3000.

#### Option 2: Manual startup

1. Navigate to the frontend project directory:
   ```
   cd C:\Unmanned Store\unmanned-store-junie\src\main\frontend\unmanned-store-ui
   ```

2. Install dependencies (first time only):
   ```
   npm install
   ```

3. Start the React development server:
   ```
   npm start
   ```

4. Verify the frontend is running by accessing:
   - http://localhost:3000

## Running the Tests

### Automatic Server Startup (New Feature)

The test now includes automatic server startup functionality. When you run the test, it will:

1. Check if the backend is running on port 8080
2. If the backend is not running, it will start it automatically
3. Check if the frontend is running on port 3000
4. If the frontend is not running, it will start it automatically
5. Run the E2E tests
6. Automatically shut down the servers it started when the test completes

To run the test with automatic server startup:

```
mvn test -Dtest=ShoppingFlowE2ETest
```

This is now the recommended approach as it handles everything automatically and cleans up after itself.

### Alternative Options (Legacy)

The following scripts are still available but are no longer necessary due to the automatic server startup feature:

#### Option 1: Using the complete all-in-one script

```
cd C:\Unmanned Store\unmanned-store-junie\src\test\java\com\unmannedstore\e2e
run-all.bat
```

This script will:
1. Start the Spring Boot backend automatically
2. Wait for the backend to initialize
3. Verify that the backend is running
4. Start the React frontend automatically
5. Wait for the frontend to initialize
6. Run the E2E tests

**Note:** Unlike the automatic server startup feature, the backend and frontend will continue running in separate windows after the tests complete, which you can close when you're done.

#### Option 2: Using the frontend-only script

If you already have the backend running and only need to start the frontend:

```
cd C:\Unmanned Store\unmanned-store-junie\src\test\java\com\unmannedstore\e2e
run-e2e-tests.bat
```

This script will:
1. Check if the backend is running on port 8080
2. Start the React frontend automatically on port 3000
3. Wait for the frontend to initialize
4. Run the E2E tests

## Test Coverage

The end-to-end tests cover the following critical user flows:

1. **Browsing Products**
   - Verifies that products are displayed on the home page

2. **Adding Products to Basket**
   - Adds products to the basket
   - Verifies that success notifications are displayed

3. **Viewing Basket Contents**
   - Navigates to the basket page
   - Verifies that the basket contains the added products

4. **Removing Items from Basket**
   - Removes an item from the basket
   - Verifies that the item is removed

5. **Checkout Process**
   - Proceeds to checkout
   - Fills in payment information
   - Completes the purchase
   - Verifies that the order completion message is displayed

## Troubleshooting

If the tests fail, check the following:

1. Server startup issues:
   - The test will now attempt to start the backend and frontend automatically if they're not running
   - Check the console output for any server startup errors (look for lines starting with `[DEBUG_LOG]`)
   - Ensure that Java, Maven, Node.js, and npm are properly installed and configured
   - Make sure the ports 8080 (backend) and 3000 (frontend) are not being used by other applications
   - If the servers fail to start automatically, you can try starting them manually using the provided scripts

2. Browser compatibility issues:
   - The test will automatically try to use Chrome, Firefox, or Edge (in that order)
   - If none of these browsers are compatible with the current Selenium version, the test will be skipped
   - If you're using Chrome version 137 or newer, you might see warnings about CDP implementation
   - These warnings are expected and shouldn't affect the test execution

3. If you're seeing errors related to Chrome version compatibility:
   - The test will automatically try alternative browsers
   - You can also try installing an older version of Chrome that's compatible with the current Selenium version
   - Or update the Selenium dependencies in pom.xml to match your Chrome version

4. Process termination issues:
   - If the test fails to terminate the server processes it started, you may need to manually stop them
   - Look for Java and Node.js processes in Task Manager and end them if necessary
   - This can happen if the test is interrupted or if there's an error during the teardown process

5. Check the console output for any error messages:
   - Look for lines starting with `[DEBUG_LOG]` for detailed information about what's happening
   - The test provides clear error messages about what went wrong and what needs to be fixed
