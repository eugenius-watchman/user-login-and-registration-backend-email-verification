# User Login and Registration Backend with Email Verification

![Java](https://img.shields.io/badge/Java-17+-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1+-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

A secure backend system for user authentication featuring registration, login, and email verification built with Spring Boot.

## Features

✅ **User Registration**  
✅ **Email Verification**  
✅ **JWT Authentication**  
✅ **Password Reset**  
✅ **Role-based Authorization**  
✅ **API Documentation (Swagger UI)**  

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.1+
- **Database**: PostgreSQL
- **Security**: Spring Security, JWT
- **Email**: Spring Mail (SMTP)
- **API Docs**: OpenAPI 3.0 (Swagger)
- **Build**: Maven

## Prerequisites

- Java 17+
- PostgreSQL 16+
- Maven 3.8+
- SMTP Email Service (Gmail, Mailtrap, etc.)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/eugenius-watchman/user-login-and-registration-backend-email-verification.git


2. application.yml
   spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/auth_db
    username: your_db_user
    password: your_db_password

3. Configure email settings:
   spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your_email@gmail.com
    password: your_app_password

4.Build and run:
  mvn clean install
  mvn spring-boot:run

API Endpoints
Endpoint	Method	Description
/api/auth/register	POST	Register new user
/api/auth/verify	GET	Verify email with token
/api/auth/login	POST	Authenticate user
/api/auth/forgot-password	POST	Initiate password reset
/api/auth/reset-password	POST	Complete password reset

Register a new user:
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePassword123!",
  "firstName": "John",
  "lastName": "Doe"
}

Verify email (click link sent to user's email):
http://localhost:8080/api/auth/verify?token=VERIFICATION_TOKEN

Login:
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePassword123!"
}

Project Structure
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── auth/
│   │               ├── config/        # Security and app configuration
│   │               ├── controller/    # API endpoints
│   │               ├── dto/           # Data transfer objects
│   │               ├── exception/     # Custom exceptions
│   │               ├── model/         # Database entities
│   │               ├── repository/    # Data access layer
│   │               ├── security/      # JWT and security
│   │               ├── service/       # Business logic
│   │               └── util/          # Helper classes
│   └── resources/
│       ├── templates/                 # Email templates
│       └── application.yml            # Configuration


Contributing
Fork the project

Create your feature branch (git checkout -b feature/AmazingFeature)

Commit your changes (git commit -m 'Add some AmazingFeature')

Push to the branch (git push origin feature/AmazingFeature)

Open a Pull Request

License
Distributed under the MIT License. See LICENSE for more information.

Contact
Eugenius Watchman - @eugenius-watchman

Project Link: https://github.com/eugenius-watchman/user-login-and-registration-backend-email-verification




  
  
