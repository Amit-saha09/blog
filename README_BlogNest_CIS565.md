# 📘 BlogNest Application - CIS 565

A full-stack blogging platform built using **Spring Boot**, with support for **unit testing**, **UI testing using Selenium**, and **load testing**.

---

## 📝 Project Overview

This project is a Spring Boot-based blogging application for CIS 565. It includes backend logic, frontend integration via Thymeleaf, and robust testing layers. The configuration is driven by two YAML files:
- `application.yml`: Common settings.
- `application-dev.yml`: Environment-specific settings for development.

---

## 🛠 Prerequisites

Before running the app, ensure the following tools are installed:

### ✅ Java 11 or above (Java 22 recommended)
1. Download JDK from [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) or OpenJDK.
2. Set `JAVA_HOME` and add `%JAVA_HOME%\bin` to your system's `PATH`.

### ✅ Apache Maven
1. Download from [Apache Maven](https://maven.apache.org/download.cgi).
2. Set `MAVEN_HOME` and add `%MAVEN_HOME%\bin` to your `PATH`.

### ✅ Verify Setup:
```bash
java -version
mvn -version
```

---

## ⚙ Configuration

### `application.yml`
```yaml
spring:
  profiles:
    active: dev
```

### `application-dev.yml`
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

---

## 🚀 Running the Application

### 🔹 Option 1: Using IntelliJ IDEA
1. Open the project → IntelliJ auto-detects as Maven project.
2. In Maven sidebar → Run `clean` then `install`.
3. Right-click `BlogApplication.java` → `Run`.

### 🔹 Option 2: Using Command Line
```bash
cd path\to\project
mvn spring-boot:run
```

Visit: [http://localhost:8201/api/home](http://localhost:8201/api/home)

---

## 🧪 Testing Suite

### ✅ 1. Unit Testing
**Tool**: JUnit/TestNG  
**Location**: `src/test/unit/`  
**Run with:**
```bash
mvn test
```
Covers:
- Service logic
- Exception handling
- API endpoints

---

### ✅ 2. Selenium UI Testing
**Tool**: Selenium WebDriver + TestNG  
**Location**: `src/test/selenium/`  
**Run from CLI or IDE**:
```bash
mvn test -Dtest=HomePageTest
```
Covers:
- Home Page, Login, Signup, About Us
- Blog post CRUD
- Admin Panel, Profile, FAQ, Contact Us

> **Tip**: Ensure your ChromeDriver path is configured properly in each test file.

---

### ✅ 3. Load Testing
**Tool**: Apache JMeter  
**Location**: `src/test/load/`  
Steps:
1. Open `.jmx` file in JMeter.
2. Configure target:
   - Host: `localhost`
   - Port: `8201`
3. Run & analyze performance metrics via:
   - Summary Report
   - View Results Tree

---

## 📂 Project Structure

```
src/
 ├── main/
 │   ├── java/com/example/blog/
 │   │   └── BlogApplication.java
 │   └── resources/
 │       ├── application.yml
 │       ├── application-dev.yml
 │       ├── static/    # CSS, JS, Images
 │       └── templates/ # HTML files
 └── test/
     ├── unit/      # JUnit test cases
     ├── selenium/  # Selenium tests
     └── load/      # JMeter scripts
```

---

## 👨‍🎓 Contributors – CIS 565

| STUDENT               | EMAIL-ID               |
|------------------------|------------------------|
| **AMIT SAHA**          | amitsaha@umich.edu     |
| **FARHAN TANVIR**      | farhanta@umich.edu     |
| **SHOMITRO KUMAR GHOSH** | shomitro@umich.edu   |
| **VEDANT PATEL**       | pvedant@umich.edu      |

---

## ✅ Conclusion

This `README` provides a comprehensive guide for setting up and running BlogNest, including all the testing layers essential for software quality assurance. Be sure to update your configuration files with the correct DB credentials and test the full workflow end-to-end.