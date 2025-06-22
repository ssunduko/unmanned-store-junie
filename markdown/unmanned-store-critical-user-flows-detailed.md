# Unmanned Store Critical User Flows Detailed

- [Customer Registration Flow](#customer-registration-flow)
  - [Purpose](#purpose)
  - [Prerequisites](#prerequisites)
  - [Flow Steps](#flow-steps)
  - [Success Criteria](#success-criteria)
- [Customer Authentication Flow](#customer-authentication-flow)
  - [Purpose](#purpose)
  - [Prerequisites](#prerequisites)
  - [Flow Steps](#flow-steps)
  - [Success Criteria](#success-criteria)
- [Customer Store Entry Flow](#customer-store-entry-flow)
  - [Purpose](#purpose)
  - [Prerequisites](#prerequisites)
  - [Flow Steps](#flow-steps)
  - [Success Criteria](#success-criteria)
- [Customer Shopping Basket Issuance Flow](#customer-shopping-basket-issuance-flow)
  - [Purpose](#purpose)
  - [Prerequisites](#prerequisites)
  - [Flow Steps](#flow-steps)
  - [Success Criteria](#success-criteria)
- [Customer Shopping Experience Flow](#customer-shopping-experience-flow)
  - [Purpose](#purpose)
  - [Prerequisites](#prerequisites)
  - [Flow Steps](#flow-steps)
  - [Success Criteria](#success-criteria)
- [Customer Checkout Experience Flow](#customer-checkout-experience-flow)
  - [Purpose](#purpose)
  - [Prerequisites](#prerequisites)
  - [Flow Steps](#flow-steps)
  - [Success Criteria](#success-criteria)
- [Customer Request for Help Flow](#customer-request-for-help-flow)
  - [Purpose](#purpose)
  - [Prerequisites](#prerequisites)
  - [A. Cleanup Request Flow](#a-cleanup-request-flow)
  - [B. Carry-out Assistance Flow](#b-carry-out-assistance-flow)
  - [C. Product Issue Flow](#c-product-issue-flow)
  - [D. Emergency Assistance Flow](#d-emergency-assistance-flow)
  - [Success Criteria (All Help Flows)](#success-criteria-all-help-flows)
- [Robot Restocking Flow (Operator-Initiated)](#robot-restocking-flow-operator-initiated)
  - [Purpose](#purpose)
  - [Prerequisites](#prerequisites)
  - [Flow Steps](#flow-steps)
  - [Success Criteria](#success-criteria)
- [System Integration Notes](#system-integration-notes)
  - [Key Technologies](#key-technologies)
  - [Data Flow](#data-flow)
  - [Failure Scenarios](#failure-scenarios)
- [Performance Metrics](#performance-metrics)
  - [Customer Experience KPIs](#customer-experience-kpis)
  - [Operational KPIs](#operational-kpis)

## Customer Registration Flow

### Purpose

Enable new customers to create an account for accessing unmanned stores through turnstile entry.

### Prerequisites

- None (first-time customer)
- Valid email address
- Payment method

### Flow Steps

1. **Landing Page**
  - Customer selects "New Customer Registration"
  - System displays registration importance notice
2. **Personal Information Entry**
  - **Required Fields:**
    - Full name
    - Email address
    - Phone number
    - Password (minimum 8 characters)
    - Date of birth (for age-restricted items)
  - Accept Terms of Service and Privacy Policy
  - Submit form
3. **Identity Verification Setup**
  - **Choose authentication method:**
    - 👤 Face Recognition - Fastest turnstile entry
    - 👆 Fingerprint - Touch-based authentication
    - 📱 Mobile App - QR code scanning
  - System captures biometric data or links mobile app
  - Confirmation of method selection
4. **Payment Method Setup**
  - Enter credit/debit card details:
    - Card number
    - Cardholder name
    - Expiry date
    - CVV
  - Option to set as default payment method
  - Validate payment method
5. **Registration Complete**
  - Success confirmation screen
  - Account activation notice
  - Instructions for store entry
  - Option to visit store immediately

### Success Criteria

- Customer account created in system
- Authentication method configured
- Payment method validated and stored
- Customer can access any 24X7 store location

## Customer Authentication Flow

### Purpose

Verify registered customer identity to grant store access through turnstile.

### Prerequisites

- Completed registration
- Chosen authentication method available

### Flow Steps

1. **Store Approach**
  - Customer arrives at store entrance
  - Views turnstile with authentication options
  - Turnstile displays locked status (🚫)
2. **Authentication Method Selection** **Option A: Face Recognition**
  - Select face recognition button
  - Position face in camera view
  - System scans and matches biometric data
  - Processing time: 2-3 seconds**Option B: Fingerprint**
  - Select fingerprint option
  - Place registered finger on scanner
  - System verifies fingerprint match
  - Processing time: 1-2 seconds**Option C: Mobile App**
  - Select mobile app option
  - Scan displayed QR code with app
  - OR enter 6-digit manual code
  - App sends authentication token
  - Processing time: 3-5 seconds
3. **Authentication Processing**
  - System verifies customer registration
  - Checks for active bans or restrictions
  - Validates payment method on file
4. **Authentication Result** **Success Path:**
  - Green light on turnstile
  - Barrier rotates to open position
  - 15-second timer starts
  - Welcome message displays**Failure Path:**
  - Red light remains
  - Error message displayed
  - Options: retry, try different method, or register

### Success Criteria

- Customer identity verified
- Turnstile unlocked
- Session initiated with customer ID
- Entry timer activated

## Customer Store Entry Flow

### Purpose

Manage physical entry through turnstile and activate tracking systems.

### Prerequisites

- Successful authentication
- Turnstile in unlocked state

### Flow Steps

1. **Turnstile Unlock Confirmation**
  - Visual indicator changes to green (✅)
  - Audible beep or chime
  - "Please enter" message displays
  - 15-second countdown visible
2. **Physical Entry**
  - Customer pushes through turnstile
  - Turnstile rotates one position
  - Entry sensor confirms passage
  - Turnstile automatically locks behind customer
3. **Tracking System Activation**
  - RFID readers activate for customer session
  - Camera system begins tracking customer
  - Customer ID linked to all sensor data
  - Real-time location tracking initiated
4. **Entry Confirmation**
  - Welcome message with customer name
  - Store navigation tips displayed
  - Direction to basket pickup area
  - Help button location highlighted

### Success Criteria

- Customer physically inside store
- All tracking systems active
- Session properly initialized
- Customer aware of next steps

## Customer Shopping Basket Issuance Flow

### Purpose

Assign a smart basket to customer for automatic item tracking during shopping.

### Prerequisites

- Customer inside store
- Available smart baskets
- Active customer session

### Flow Steps

1. **Basket Selection Area Arrival**
  - Customer enters basket zone
  - Display shows available baskets:
    - Basket A-042 (Nearest) 🟢
    - Basket A-043 🟢
    - Basket A-044 🟢
  - Instructions for basket selection
2. **Basket Pickup**
  - Customer selects any available basket
  - Physical removal from charging dock
  - RFID tag on basket detected
  - Weight sensor confirms removal
3. **Basket-Customer Linking**
  - System associates basket ID with customer session
  - Confirmation message: "Basket A-042 linked to your session"
  - Basket display activates showing:
    - Customer name
    - Empty basket status
    - Battery level
    - Connection status
4. **Basket Activation**
  - Item tracking sensors activate
  - Running total display initializes at $0.00
  - Real-time sync with store systems confirmed
  - Ready for shopping indicator

### Success Criteria

- Basket successfully linked to customer
- All tracking features operational
- Customer can see basket status
- System ready to track items

## Customer Shopping Experience Flow

### Purpose

Enable autonomous shopping with automatic item tracking and basket management.

### Prerequisites

- Active smart basket
- Customer authenticated and tracked
- Products properly tagged with RFID

### Flow Steps

1. **Product Browsing**
  - Customer navigates store aisles
  - Overhead displays show:
    - Current location
    - Tracking status (📡 RFID Active | 📷 Camera Tracking)
  - Product shelves display:
    - Item names and prices
    - Stock level indicators
    - "Low Stock" warnings where applicable
2. **Item Addition**
  - Customer picks up product
  - RFID tag detected leaving shelf
  - System identifies product and price
  - Basket display updates:
    - Item added notification
    - Running total increases
    - Item count updates
  - Product tile shows "in basket" status
3. **Item Removal**
  - Customer returns item to shelf
  - RFID detects item placement
  - System removes from basket
  - Updates:
    - Item removed notification
    - Running total decreases
    - Visual feedback on display
4. **Inventory Monitoring**
  - Low stock items trigger operator alerts
  - Customer sees stock warnings
  - System tracks popular items
  - Restock robots deployed as needed
5. **Shopping Completion Decision**
  - Review basket contents on display
  - Check running total
  - Options:
    - Continue shopping
    - Proceed to checkout
    - Request help

### Success Criteria

- All items accurately tracked
- Real-time basket updates
- No manual scanning required
- Customer confident in basket accuracy

## Customer Checkout Experience Flow

### Purpose

Complete purchase through automated payment processing and authorize store exit.

### Prerequisites

- Items in basket
- Valid payment method on file
- Customer ready to leave

### Flow Steps

1. **Exit Zone Entry**
  - Customer approaches designated checkout area
  - Multiple verification systems activate:
    - Overhead cameras scan basket contents
    - RFID readers perform final item check
    - Weight sensors verify expected vs actual
  - "Finalizing basket" message displays
2. **Order Review Screen**
  - Detailed receipt preview:
    - Itemized list with individual prices
    - Subtotal calculation
    - Tax calculation
    - **Total amount due**
  - Payment method display:
    - Card type and last 4 digits
    - Option to change (if multiple on file)
  - Confirmation prompt
3. **Payment Processing**
  - Customer confirms purchase
  - Payment gateway activation:
    - Secure charge to registered card
    - Processing animation displays
    - Typical duration: 3-5 seconds
  - Background fraud checks
4. **Payment Confirmation**
  - Success notification displays:
    - ✅ Payment approved
    - Transaction ID
    - Total amount charged
  - Receipt generation:
    - Digital copy to email
    - Mobile app notification
    - Option to view/download
5. **Exit Authorization**
  - Exit turnstile unlocks
  - Green light and directional arrow
  - 15-second exit window
  - Thank you message
  - Store satisfaction survey prompt (optional)

### Success Criteria

- Payment successfully processed
- Receipt delivered digitally
- Exit path clear
- Transaction recorded in system

## Customer Request for Help Flow

### Purpose

Provide automated assistance for various customer needs without human staff.

### Prerequisites

- Customer in store
- Help system operational
- Robots/systems available

### A. Cleanup Request Flow

1. **Initiate Help**
  - Press floating help button (?)
  - Chat interface opens
  - Select "🧹 Cleanup Request"
2. **Specify Issue Type**
  - Options presented:
    - 💧 Liquid spill
    - 🚫 Broken item/glass
    - 🗑️ Trash overflow
    - ❓ Other issue
  - Customer selects applicable option
3. **Location Confirmation**
  - System identifies customer location via tracking
  - Confirms cleanup location
  - Marks area in system
4. **Robot Dispatch**
  - Cleanup robot assigned (e.g., CL-03)
  - Customer receives:
    - Robot ID and ETA (typically 2 minutes)
    - Safety notice to avoid area
    - Progress updates
5. **Resolution**
  - Robot arrives and performs cleanup
  - Area marked safe when complete
  - Customer notified of completion

### B. Carry-out Assistance Flow

1. **Initiate Help**
  - Press help button
  - Select "🛍️ Carry-out Help"
2. **Select Assistance Type**
  - Options:
    - 🛒 Extra shopping cart needed
    - 🚗 Help loading to vehicle
    - 📦 Schedule delivery instead
3. **Service Configuration**
  - For vehicle loading:
    - Specify vehicle location
    - Estimate completion time
  - For extra cart:
    - Confirm current location
    - Cart delivery point
4. **Robot Assignment**
  - Carry-out robot (e.g., CO-01) assigned
  - Instructions provided:
    - Where to meet robot
    - When assistance available
    - How to interact with robot
5. **Assistance Completion**
  - Robot provides requested help
  - Customer confirms completion
  - Robot returns to standby

### C. Product Issue Flow

1. **Problem Identification**
  - Press help button
  - Select "📦 Product Issue"
2. **Issue Type Selection**
  - 📝 Item not scanning properly
  - ❌ Wrong price displayed
  - 🚨 Damaged product found
  - ⏭️ Can't find product
3. **Resolution Options**
  - Manual item addition
  - Price verification
  - Product location assistance
  - Skip problematic item
4. **System Response**
  - Operator alert generated
  - Temporary override if needed
  - Alternative solutions offered

### D. Emergency Assistance Flow

1. **Emergency Activation**
  - Press help button
  - Select "🚨 Emergency"
2. **Immediate System Response**
  - All turnstiles unlock instantly
  - Emergency lighting activates
  - Audible alarm (if applicable)
  - Emergency services auto-notified
3. **Information Relay**
  - Store location sent to emergency services
  - Customer count provided
  - Nature of emergency logged
  - Cameras provide live feed
4. **Evacuation Support**
  - Exit routes illuminated
  - Barriers removed
  - Payment processing suspended
  - Safe exit prioritized

### Success Criteria (All Help Flows)

- Issue resolved without human intervention
- Customer able to continue shopping or exit
- Incident logged for analysis
- Customer satisfaction maintained

## Robot Restocking Flow (Operator-Initiated)

### Purpose

Automatically replenish low inventory using robotic systems.

### Prerequisites

- Low stock alerts triggered
- Restock robot available
- Inventory in storage area

### Flow Steps

1. **Low Stock Detection**
  - RFID sensors detect low inventory:
    - Milk: 5 units remaining (threshold: 10)
    - Bread: 3 units remaining (threshold: 10)
  - Operator dashboard shows alerts
  - Priority levels assigned
2. **Restock Review**
  - Operator views inventory dashboard:
    - Critical items highlighted in red
    - Stock levels displayed
    - Sales velocity shown
    - Restock recommendations
3. **Robot Deployment**
  - Operator selects "Deploy Restock Robot"
  - Available robots shown:
    - RS-01: Charging
    - RS-02: Available ✅
    - RS-03: Maintenance
  - Confirm RS-02 deployment
4. **Restocking Execution**
  - Robot receives task list:
    - Priority 1: Milk (20 units)
    - Priority 2: Bread (25 units)
  - Robot navigates to storage
  - Progress tracking:
    - Loading items: 25%
    - Navigating to shelf: 50%
    - Restocking: 75%
    - Complete: 100%
5. **Customer Management**
  - Affected aisle temporarily restricted
  - Customers redirected via displays
  - "Restocking in progress" notices
  - ETA displayed for aisle reopening
6. **Completion**
  - Inventory levels updated in system
  - Aisle reopened to customers
  - Robot returns to charging station
  - Operator notified of completion

### Success Criteria

- Inventory successfully replenished
- Minimal customer disruption
- Accurate stock level updates
- Robot ready for next task

## System Integration Notes

### Key Technologies

- **RFID**: Product and basket tracking
- **Cameras**: Visual verification and security
- **Weight Sensors**: Basket and shelf monitoring
- **Turnstiles**: Controlled entry/exit
- **Robots**: Cleaning, restocking, assistance
- **Mobile App**: Authentication and receipts

### Data Flow

1. All customer actions logged in real-time
2. Ledger service maintains transaction history
3. Inventory updates propagate instantly
4. Analytics dashboard for operators
5. Audit trail for compliance

### Failure Scenarios

- **Power Outage**: Emergency exits unlock, backup power for critical systems
- **Network Failure**: Local processing continues, sync when restored
- **Robot Malfunction**: Backup robots deploy, manual override available
- **Payment Failure**: Multiple payment methods, delayed payment option
- **Authentication Failure**: Alternative methods, operator override capability

## Performance Metrics

### Customer Experience KPIs

- Average shopping time: < 15 minutes
- Authentication time: < 5 seconds
- Checkout processing: < 10 seconds
- Help response time: < 2 minutes

### Operational KPIs

- System uptime: > 99.9%
- Inventory accuracy: > 99.5%
- Robot availability: > 95%
- Transaction success rate: > 99%