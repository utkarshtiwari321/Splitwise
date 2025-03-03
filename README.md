# Splitwise Clone - Expense Sharing Application

## Overview

This repository is the basic implementation of a Splitwise-like expense-sharing application. There are many additional features that can be added to enhance functionality. It is built using **Java and Spring Boot**, allowing users to **split expenses**, **track balances**, and **settle payments** seamlessly. The application uses **H2 as a file system database** for storing data.

## Note
H2 Database is used because of time constraints. It is also using sql dialects. 
If in case we need to use Postgre SQL or Mysql , we need to pass Database URL , dialect , username and password
## Features

- **User Management**: Create and manage users.
- **Expense Tracking**: Add expenses and define how they are split among users.
- **Balance Calculation**: View individual user balances.
- **Settlement Handling**: Allow users to settle pending amounts.

## Technologies Used

- **Java 17**
- **Spring Boot** (Spring MVC, Spring Data JPA, Spring Security)
- **H2 Database** (for development and testing)
- **Maven** (for dependency management and build)
- **Docker** (for containerization)

## API Endpoints

### **User APIs**

| Method | Endpoint          | Description            |
| ------ | ----------------- | ---------------------- |
| POST   | `/api/users`      | Create a new user      |
| GET    | `/api/users/{id}` | Get user details by ID |
| GET    | `/api/users`      | Retrieve all users     |

### **Expense APIs**

| Method | Endpoint                          | Description            |
| ------ | --------------------------------- | ---------------------- |
| POST   | `/api/expenses`                   | Create a new expense   |
| GET    | `/api/expenses/balances/{userid}` | Get balance for a user |

### **Settlement APIs**

| Method | Endpoint           | Description                       |
| ------ | ------------------ | --------------------------------- |
| POST   | `/api/settlements` | Create a settlement between users |

## How to Run the Application

### **1. Clone the Repository**

```sh
git clone https://github.com/your-username/splitwise-app.git
cd splitwise-app
