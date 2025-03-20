# Bank App (Spring Boot)

A simple **Bank Application** built with **Spring Boot**, featuring authentication, account management, and transaction operations.

---

## 🚀 Features

✅ **User Authentication and Authorization** (Register, Login, JWT Access & Refresh Tokens)  
✅ **Account Management** (Create Account, View User Accounts)  
✅ **Transaction Features**: (Deposit, Withdraw and Transfer)

---

## 🛠 Technologies Used

- **Java 21**  
- **Spring Boot 3**  
- **Spring Security** (JWT based Authentication with Access & Refresh Tokens)
- **PostgreSQL** (via Docker Compose)
- **JPA (Hibernate)** for Database Interaction
- **Lombok** (to reduce boilerplate code)
- **Spring-Dotenv** in order to get environment variables from `.env` file

---

## ⚡ Getting Started

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/ismayilibrahimov/bankapp.git
cd bankapp
```


### 2️⃣ Configure Environment Variables
Create a `.env` file in the root directory:
```bash
DATABASE_URL=
DATABASE_USERNAME=
DATABASE_PASSWORD=

JWT_SECRET_KEY=
ACCESS_TOKEN_EXPIRATION=
REFRESH_TOKEN_EXPIRATION=
```

### 3️⃣ Run PostgreSQL with Docker Compose
```bash
docker compose up -d
```

### 4️⃣ Run the Application
```bash
./mvnw spring-boot:run
```
---

## API Endpoints
This section provides details on how to interact with the Bank App using its REST API. The endpoints are categorized into authentication, account management, and transaction operations.


### 🔑 Authentication API

Register User:

POST `/api/v1/auth/register`
```json
{
  "firstname": "John",
  "lastname": "Doe",
  "username": "johndoe",
  "password": "securepassword"
}
```

Login:

POST `/api/v1/auth/login`
```json
{
  "username": "johndoe",
  "password": "securepassword"
}
```

Refresh Token:

use `Authorization` header with `Bearer <refresh-token>`

POST `/api/v1/auth/refresh-token`


### 💳 Account API

Create Account:

use `Authorization` header with `Bearer <access-token>`

POST `/api/v1/accounts/create` (Authenticated)

Get User Accounts:

use `Authorization` header with `Bearer <access-token>`

GET `/api/v1/accounts` (Authenticated)


### 💰 Transaction API

Deposit Money:

use `Authorization` header with `Bearer <access-token>`

POST `/api/v1/transactions/deposit` (Authenticated)
```json
{
  "accountNumber": "123456789",
  "amount": 100.00
}
```

Withdraw Money:

use `Authorization` header with `Bearer <access-token>`

POST `/api/v1/transactions/withdraw` (Authenticated)

```json
{
  "accountNumber": "123456789",
  "amount": 50.00
}

```

Transfer Money:

use `Authorization` header with `Bearer <access-token>`

POST `/api/v1/transactions/transfer` (Authenticated)
```json
{
  "fromAccountNumber": "123456789",
  "toAccountNumber": "987654321",
  "amount": 25.00
}
```

---

## How JWT Authentication Works

1️⃣ User Logs In → Receives `accessToken` & `refreshToken`

2️⃣ Use `accessToken` in `Authorization: Bearer <accessToken>` header for API calls

3️⃣ If `accessToken` expires, use `refreshToken` to generate a new one




