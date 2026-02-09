# School Management System

A comprehensive web-based School Management System built with **Spring Boot**, designed for educational institutions to manage students, teachers, classes, attendance, examinations, and fees efficiently.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [System Requirements](#system-requirements)
- [Installation & Setup](#installation--setup)
- [Database Configuration](#database-configuration)
- [Running the Application](#running-the-application)
- [Default Login Credentials](#default-login-credentials)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

### Admin Module
- ✅ Manage Students (Add, Update, View, Delete)
- ✅ Manage Teachers (Add, Update, View, Delete)
- ✅ Manage Classes & Sections
- ✅ Manage Subjects
- ✅ Assign Teachers to Subjects
- ✅ Manage Fee Structure
- ✅ View Reports and Statistics

### Teacher Module
- ✅ View Assigned Classes and Subjects
- ✅ Mark Student Attendance
- ✅ Enter and Update Student Marks
- ✅ View Class Performance

### Student Module
- ✅ View Personal Profile
- ✅ View Attendance Records
- ✅ View Examination Results
- ✅ View Fee Payment Status

## 🛠️ Technology Stack

- **Backend Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **Database:** MySQL (Primary), PostgreSQL (Alternative), H2 (Testing)
- **ORM:** Spring Data JPA / Hibernate
- **Security:** Spring Security (Role-based authentication)
- **Frontend:** Thymeleaf, HTML5, CSS3
- **Build Tool:** Maven
- **Server:** Embedded Tomcat

## 💻 System Requirements

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher (or PostgreSQL 12+)
- Any modern web browser (Chrome, Firefox, Edge)
- 4GB RAM minimum
- 500MB free disk space

## 📦 Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd school-management-system
```

### 2. Configure Database

#### Using MySQL (Recommended)

1. Install MySQL and create a database:

```sql
CREATE DATABASE school_db;
```

2. Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/school_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

#### Using PostgreSQL (Alternative)

1. Create database:

```sql
CREATE DATABASE school_db;
```

2. Update `application.properties`:

```properties
# Comment MySQL configuration and uncomment PostgreSQL:
spring.datasource.url=jdbc:postgresql://localhost:5432/school_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/school-management-system-1.0.0.jar
```

The application will start on `http://localhost:8080`

## 🔐 Default Login Credentials

The system comes with pre-configured demo accounts:

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Teacher | teacher1 | teacher123 |
| Student | student1 | student123 |

**⚠️ Important:** Change these default passwords in production!

## 📁 Project Structure

```
school-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/com/school/
│   │   │   ├── config/           # Configuration classes
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── DataInitializer.java
│   │   │   │
│   │   │   ├── entity/           # JPA Entities
│   │   │   │   ├── User.java
│   │   │   │   ├── Student.java
│   │   │   │   ├── Teacher.java
│   │   │   │   ├── SchoolClass.java
│   │   │   │   ├── Subject.java
│   │   │   │   ├── Attendance.java
│   │   │   │   ├── Exam.java
│   │   │   │   ├── Mark.java
│   │   │   │   └── Fee.java
│   │   │   │
│   │   │   ├── repository/       # Data Access Layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── TeacherRepository.java
│   │   │   │   ├── SchoolClassRepository.java
│   │   │   │   ├── SubjectRepository.java
│   │   │   │   ├── AttendanceRepository.java
│   │   │   │   ├── ExamRepository.java
│   │   │   │   ├── MarkRepository.java
│   │   │   │   └── FeeRepository.java
│   │   │   │
│   │   │   ├── service/          # Business Logic Layer
│   │   │   │   ├── UserService.java
│   │   │   │   ├── StudentService.java
│   │   │   │   ├── TeacherService.java
│   │   │   │   ├── SchoolClassService.java
│   │   │   │   ├── SubjectService.java
│   │   │   │   ├── AttendanceService.java
│   │   │   │   ├── ExamService.java
│   │   │   │   ├── MarkService.java
│   │   │   │   ├── FeeService.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   │
│   │   │   ├── controller/       # REST Controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── TeacherController.java
│   │   │   │   └── StudentController.java
│   │   │   │
│   │   │   └── SchoolManagementSystemApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/        # Thymeleaf HTML templates
│   │           ├── login.html
│   │           ├── admin/
│   │           │   └── dashboard.html
│   │           ├── teacher/
│   │           │   └── dashboard.html
│   │           └── student/
│   │               └── dashboard.html
│   │
│   └── test/                     # Unit and Integration Tests
│
├── pom.xml                       # Maven configuration
└── README.md                     # This file
```

## 🌐 Application URLs

After starting the application, access these URLs:

- **Home/Login:** http://localhost:8080
- **Admin Dashboard:** http://localhost:8080/admin/dashboard
- **Teacher Dashboard:** http://localhost:8080/teacher/dashboard
- **Student Dashboard:** http://localhost:8080/student/dashboard

## 🔄 Key Functionalities

### 1. Authentication & Authorization
- Secure login with Spring Security
- Password encryption using BCrypt
- Role-based access control (RBAC)
- Session management

### 2. Student Management
- Complete student lifecycle management
- Automatic user account creation
- Class assignment
- Profile management

### 3. Teacher Management
- Teacher registration with employee ID
- Subject assignment
- Multiple subjects per teacher support

### 4. Attendance Tracking
- Daily attendance marking
- Attendance percentage calculation
- Historical attendance reports
- Filter by date range

### 5. Examination & Marks
- Exam scheduling by class
- Marks entry by subject
- Automatic grade calculation
- Performance percentage

### 6. Fee Management
- Fee structure definition
- Payment tracking
- Payment status (Pending/Paid/Partial/Overdue)
- Balance calculation

## 🎯 Future Enhancements

- [ ] Online fee payment gateway integration
- [ ] Email/SMS notifications
- [ ] Parent portal
- [ ] Timetable management
- [ ] Library management
- [ ] Report card generation (PDF)
- [ ] Mobile application
- [ ] Dashboard analytics & charts
- [ ] Bulk data import/export

## 🧪 Testing

Run tests using Maven:

```bash
mvn test
```

## 🐛 Troubleshooting

### Database Connection Issues

1. Verify MySQL is running:
```bash
mysql -u root -p
```

2. Check database exists:
```sql
SHOW DATABASES;
```

3. Verify credentials in `application.properties`

### Port Already in Use

Change the port in `application.properties`:
```properties
server.port=8081
```

### Build Errors

Clean and rebuild:
```bash
mvn clean install -U
```

## 📝 License

This project is created for educational purposes.

## 👨‍💻 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📧 Contact

For questions or support, please contact the development team.

---

**Note:** This is a beginner-friendly academic project built with Spring Boot for learning purposes.
