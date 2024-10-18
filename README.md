CWB Banking Application
Overview
This is a simple banking application built using Spring Boot. It provides basic banking functionalities such as user registration, account management, deposits, withdrawals, and fund transfers.
Features

User registration and authentication
Account dashboard
Deposit and withdrawal operations
Fund transfers between accounts
Transaction history
Basic information pages (About Us, Loans)

Technology Stack

Java 17
Spring Boot
Spring Security
Spring Data JPA
Thymeleaf (assumed for view templating)
Maven (assumed for dependency management)
H2 Database (assumed, as no specific database is mentioned in the provided code)

Project Structure
Copycom.bank.cwb
├── config
│   └── SecurityConfig.java
├── controller
│   └── AccountController.java
├── model
│   ├── Account.java
│   ├── Transaction.java
│   └── User.java
├── repository
│   ├── AccountRepository.java
│   ├── TransactionRepository.java
│   └── UserRepository.java
└── service
    └── AccountService.java
Setup and Running

Ensure you have Java 17 and Maven installed on your system.
Clone the repository:
Copygit clone [repository-url]

Navigate to the project directory:
Copycd cwb-banking-application

Build the project:
Copymvn clean install

Run the application:
Copymvn spring-boot:run

Access the application at http://localhost:8080

Usage

Register a new user account at /register
Log in with your credentials at /login
Use the dashboard to manage your account, make deposits, withdrawals, and transfers
View your transaction history at /transactions

Security
This application uses Spring Security for authentication and authorization. Passwords are encrypted using BCrypt before being stored in the database.
Contributing
Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests.
License
This project is licensed under the MIT License - see the LICENSE.md file for details.
Acknowledgments

Spring Boot team for the excellent framework
All contributors who participate in this project
