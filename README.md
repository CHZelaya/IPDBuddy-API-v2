# IPD Buddy API

[![Java](https://img.shields.io/badge/Backend-Java%2021-blue?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791?logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Containerized-Docker-blue?logo=docker)](https://www.docker.com/)
[![Status](https://img.shields.io/badge/Status-Work%20in%20Progress-yellow)]()
[![License](https://img.shields.io/badge/License-MIT-green)](./LICENSE)

---

## Project Overview

**IPD Buddy** is a personal project built to help a contractor friend reduce the amount of time doing administrative tasks after his work day, track material usage and get paid accurately for piecework in the Insulation, Poly, and Drywall trade. The system is designed to:
- Let contractors log materials used on jobs via a simple web form
- Automatically calculate their total pay
- Generate and email two PDF reports (one for the employer and one for personal record-keeping)
- Include personal financial breakdowns like taxes and savings recommendations based on financial literacy research

This repo contains the backend API built with Java Spring Boot.

---

##  Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **Jakarta JPA**
- **PostgreSQL**
- **Docker (TBD implementation)** 
- **RESTful APIs**
- **PDF generation (TBD implementation)**
- **Deployment Target: AWS**

---

##  Current Features

- Contractor authentication (in progress)
- Material tracking and cost calculation
- Scaffolding setup/cleanup tracking
- Basic service & controller structure (WIP)
- Dummy data for testing

---

## Testing

All endpoints will be tested using Postman.

---

## Future Improvements

- Customizable job templates (e.g., pre-loaded templates based on home builder)
- Financial insights dashboard

---

## Author

Built with 💻 & ☕ by a developer who just wants to help a dear friend of mine, spend more time living and less time working.
Reach out if you're curious about the code or want to chat side projects!

---

## 📄 License

This project is licensed under the [MIT License](./LICENSE).
