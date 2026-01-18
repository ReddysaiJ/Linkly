# 🔗 Linkly – URL Shortening Application

**Author:** Reddysai Jonnadula  
**Tech Stack:** Java, Spring Boot, PostgreSQL, Thymeleaf, Spring Security, Flyway, Docker

---

## 📌 Project Overview

Linkly is a secure and scalable **URL Shortening Application** built using Spring Boot.  
It allows users to generate short URLs, manage their links, track click counts, and set expiration rules, while administrators can monitor and manage all URLs in the system.

The application ensures **secure authentication**, **role-based access control**, **data consistency**, and **efficient link management** using modern backend practices.

---

## 🎯 Scope

### Purpose

To simplify long URLs into short, manageable links with secure access, tracking, and administrative control.

### Included

* User registration and login
* Role-based access (ADMIN / USER)
* Short URL generation
* Public and private URLs
* URL expiration support
* Click count tracking
* User dashboard for managing URLs
* Admin dashboard for system-wide URL management
* Pagination for large URL datasets
* Flyway migration scripts for database version control
* Dockerized application and database setup

### Excluded

* Custom domain support
* QR code generation
* Advanced analytics (geo, device tracking)
* Mobile application

---

## ✨ Features

### 👤 User Features

* Register and log in securely
* Create short URLs
* Set URL expiration
* View personal URLs
* Track click counts
* Delete URLs in bulk
* Paginated dashboard view

### 🛠 Admin Features

* View all URLs across users
* Monitor system-wide usage
* Administrative access to all links

### 🔐 Security

* Spring Security
* BCrypt password hashing
* Role-based authorization (USER / ADMIN)

---

## 🧰 Technology Stack

| Layer               | Technology              |
|--------------------|-------------------------|
| Backend            | Java, Spring Boot       |
| ORM                | Hibernate (JPA)         |
| Database           | PostgreSQL              |
| Database Migration | Flyway                  |
| Security           | Spring Security, BCrypt |
| Frontend           | Thymeleaf, HTML, CSS    |
| Build Tool         | Maven                   |
| Containerization   | Docker, Docker Compose  |

---

## ⚙️ Installation & Setup

### Prerequisites

* Java 21 or higher
* PostgreSQL 15+
* Maven
* Docker & Docker Compose (for containerized setup)

---

## ▶️ How to Run the Application

### 1️⃣ Run Locally

```bash
git clone https://github.com/ReddysaiJ/Linkly.git
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=yourpassword
```

```bash
mvn clean package
mvn spring-boot:run
```

Open:
```
http://localhost:8080
```

---

### 2️⃣ Run Using Docker

```bash
docker-compose up --build
```

Endpoints:
```
Application : http://localhost:8080
PostgreSQL  : localhost:15432
```

---

## 🗄 Database & Migrations

* PostgreSQL database
* Flyway for schema versioning
* Scripts location:

```
src/main/resources/db/migration
```

---

## 🚀 Future Enhancements

* Custom domain support
* QR code generation
* Advanced analytics dashboard
* CI/CD pipeline

---

## 📌 Author

**Reddysai Jonnadula**  
📧 [reddysai2107@gmail.com](mailto:reddysai2107@gmail.com)
