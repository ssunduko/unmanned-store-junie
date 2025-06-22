# Unmanned Store UI

This is the frontend user interface for the Unmanned Store application, built with React.js.

## Features

The UI implements the following critical user flows from the Unmanned Store requirements:

### Customer Shopping Experience Flow

- Browse products with details (name, price, image, description)
- Add products to basket
- View basket contents
- Remove items from basket
- See running total and item count

### Customer Checkout Experience Flow

- Review basket contents
- Enter payment information
- Complete purchase
- Receive order confirmation

## Project Structure

```
unmanned-store-ui/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   ├── ProductList.js
│   │   ├── ShoppingBasket.js
│   │   └── Checkout.js
│   ├── App.js
│   ├── App.css
│   ├── index.js
│   └── index.css
└── package.json
```

## API Integration

The UI connects to the following backend API endpoints:

- `GET /api/products` - Get all products
- `GET /api/stores/{storeId}/baskets/{basketId}/items` - Get basket contents
- `POST /api/stores/{storeId}/baskets/{basketId}/items` - Add item to basket
- `DELETE /api/stores/{storeId}/baskets/{basketId}/items/{itemId}` - Remove item from basket

## Implementation Notes

- The application uses React Router for navigation between pages
- React Bootstrap is used for UI components
- Axios is used for API requests
- The checkout process is simulated since there's no specific checkout endpoint in the backend API

## Future Enhancements

- Implement user authentication flow
- Add store entry management
- Integrate with real payment processing
- Add product search and filtering
- Implement responsive design for mobile devices

# How to Start and Access the Unmanned Store Application

The Unmanned Store application consists of two parts: a Spring Boot backend and a React.js frontend. Here are the instructions to start and access both components.

## Starting the Backend (Spring Boot)

1. **Prerequisites**:
   - Java 17 or higher
   - Maven

2. **Navigate to the project root directory**:
   ```
   cd C:\Unmanned Store\unmanned-store-junie
   ```

3. **Start the Spring Boot application using Maven**:
   ```
   mvn spring-boot:run
   ```

4. **Verify the backend is running**:
   - The console should show that the application has started successfully
   - The backend will be running on port 8080
   - You can access the Swagger UI at: http://localhost:8080/swagger-ui.html
   - You can also access the H2 database console at: http://localhost:8080/h2-console
      - JDBC URL: jdbc:h2:mem:unmannedstore
      - Username: sa
      - Password: (leave empty)

## Starting the Frontend (React.js)

1. **Prerequisites**:
   - Node.js (v14 or higher)
   - npm (v6 or higher)

2. **Navigate to the frontend project directory**:
   ```
   cd C:\Unmanned Store\unmanned-store-junie\src\main\frontend\unmanned-store-ui
   ```

3. **Install dependencies** (first time only):
   ```
   npm install
   ```

4. **Start the React development server**:
   ```
   npm start
   ```

5. **Verify the frontend is running**:
   - The console should show that the application has started successfully
   - A browser window should automatically open
   - If not, open your browser and navigate to: http://localhost:3000

## Accessing the Application

1. **Frontend UI**: http://localhost:3000
   - Browse products on the home page
   - Add products to your basket
   - View your basket contents
   - Proceed to checkout

2. **Backend API**: http://localhost:8080
   - REST API endpoints
   - Swagger documentation: http://localhost:8080/swagger-ui.html
   - H2 Database console: http://localhost:8080/h2-console

3. **API Endpoints**:
   - `GET /api/products` - Get all products
   - `GET /api/stores/{storeId}/baskets/{basketId}/items` - Get basket contents
   - `POST /api/stores/{storeId}/baskets/{basketId}/items` - Add item to basket
   - `DELETE /api/stores/{storeId}/baskets/{basketId}/items/{itemId}` - Remove item from basket

## Troubleshooting

- If the backend fails to start, check if port 8080 is already in use
- If the frontend fails to start, check if port 3000 is already in use
- Make sure both the backend and frontend are running simultaneously for the application to work properly
- The frontend is configured to proxy API requests to the backend, so the backend must be running for the frontend to function correctly

