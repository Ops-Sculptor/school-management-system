[README.md](https://github.com/user-attachments/files/25126278/README.md)
# School Management System

A comprehensive school management system built with Spring Boot that automates and manages academic and administrative operations.

## 📋 Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Default Users](#default-users)

## ✨ Features

### Admin Module
- ✅ User authentication and authorization (JWT-based)
- ✅ Student management (Create, Read, Update, Delete)
- ✅ Teacher management (Create, Read, Update, Delete)
- ✅ Class and subject management
- ✅ Assign teachers to subjects

### Teacher Module
- ✅ View assigned classes and subjects
- ✅ Mark and update student attendance
- ✅ Enter and update examination marks
- ✅ View student lists by class

### Student Module
- ✅ View personal profile
- ✅ View attendance records
- ✅ View examination results
- ✅ View fee status

### Additional Features
- ✅ Role-based access control (Admin, Teacher, Student)
- ✅ Automatic grade calculation
- ✅ Password encryption with BCrypt
- ✅ RESTful API architecture
- ✅ Exception handling

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: MySQL / PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security with JWT
- **Build Tool**: Maven
- **Server**: Embedded Tomcat

## 📁 Project Structure

```
school-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/school/
│   │   │       ├── config/          # Configuration classes
│   │   │       ├── controller/      # REST Controllers
│   │   │       ├── dto/             # Data Transfer Objects
│   │   │       ├── entity/          # JPA Entities
│   │   │       ├── exception/       # Exception handlers
│   │   │       ├── repository/      # Data repositories
│   │   │       ├── security/        # Security configuration
│   │   │       ├── service/         # Business logic
│   │   │       └── SchoolManagementSystemApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 📦 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ or PostgreSQL 12+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone <repository-url>
cd school-management-system
```

### 2. Set Up Database

**For MySQL:**
```sql
CREATE DATABASE school_management;
```

**For PostgreSQL:**
```sql
CREATE DATABASE school_management;
```

### 3. Configure Database Connection

Edit `src/main/resources/application.properties`:

**For MySQL:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/school_management
spring.datasource.username=your_username
spring.datasource.password=your_password
```

**For PostgreSQL:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/school_management
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 4. Build the Project
```bash
mvn clean install
```

## ⚙️ Configuration

### JWT Configuration
Update these values in `application.properties`:
```properties
jwt.secret=your-secret-key-here
jwt.expiration=86400000
```

### Mail Configuration (Optional)
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

## ▶️ Running the Application

### Using Maven
```bash
mvn spring-boot:run
```

### Using JAR
```bash
mvn clean package
java -jar target/school-management-system-1.0.0.jar
```

The application will start on `http://localhost:8080/api`

## 🔌 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/api/auth/login` | User login | Public |
| POST | `/api/auth/logout` | User logout | Authenticated |

### Admin Endpoints

#### Student Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/admin/students` | Create student |
| GET | `/api/admin/students` | Get all students |
| GET | `/api/admin/students/{id}` | Get student by ID |
| GET | `/api/admin/students/class/{classId}` | Get students by class |
| PUT | `/api/admin/students/{id}` | Update student |
| DELETE | `/api/admin/students/{id}` | Delete student |

#### Teacher Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/admin/teachers` | Create teacher |
| GET | `/api/admin/teachers` | Get all teachers |
| GET | `/api/admin/teachers/{id}` | Get teacher by ID |
| PUT | `/api/admin/teachers/{id}` | Update teacher |
| DELETE | `/api/admin/teachers/{id}` | Delete teacher |

### Teacher Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/teacher/attendance` | Mark attendance |
| PUT | `/api/teacher/attendance/{id}` | Update attendance |
| GET | `/api/teacher/attendance/subject/{subjectId}?date=YYYY-MM-DD` | Get attendance by subject and date |
| POST | `/api/teacher/marks` | Enter marks |
| PUT | `/api/teacher/marks/{id}` | Update marks |
| GET | `/api/teacher/marks/examination/{examinationId}` | Get results by examination |

### Student Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/student/profile/{id}` | View profile |
| GET | `/api/student/attendance/{studentId}` | View attendance |
| GET | `/api/student/attendance/{studentId}/range?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` | View attendance by date range |
| GET | `/api/student/marks/{studentId}` | View marks |

## 📝 Sample API Requests

### 1. Login
```json
POST /api/auth/login
{
    "username": "admin",
    "password": "admin123"
}

Response:
{
    "token": "eyJhbGc...",
    "type": "Bearer",
    "id": 1,
    "username": "admin",
    "email": "admin@school.com",
    "role": "ADMIN",
    "message": "Login successful"
}
```

### 2. Create Student
```json
POST /api/admin/students
Authorization: Bearer <token>

{
    "rollNumber": "2024001",
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "2008-05-15",
    "gender": "MALE",
    "address": "123 Main St",
    "city": "New York",
    "state": "NY",
    "pinCode": "10001",
    "bloodGroup": "O+",
    "admissionDate": "2024-01-15",
    "classId": 1,
    "username": "john.doe",
    "password": "password123",
    "email": "john.doe@school.com",
    "phoneNumber": "1234567890"
}
```

### 3. Mark Attendance
```json
POST /api/teacher/attendance
Authorization: Bearer <token>

{
    "studentId": 1,
    "subjectId": 1,
    "teacherId": 1,
    "date": "2024-02-06",
    "status": "PRESENT",
    "remarks": "On time"
}
```

### 4. Enter Marks
```json
POST /api/teacher/marks
Authorization: Bearer <token>

{
    "studentId": 1,
    "examinationId": 1,
    "marksObtained": 85.5,
    "remarks": "Good performance"
}
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Testing with Postman
1. Import the API collection
2. Set environment variables:
   - `base_url`: http://localhost:8080/api
   - `token`: <JWT token from login>

## 👥 Default Users

After first run, you can create an admin user manually or use SQL:

```sql
INSERT INTO users (username, password, email, full_name, phone_number, role, active, created_at) 
VALUES ('admin', '$2a$10$encrypted_password', 'admin@school.com', 'Admin User', '1234567890', 'ADMIN', true, NOW());
```

## 📊 Database Schema

The system uses the following main tables:
- `users` - User authentication
- `students` - Student information
- `teachers` - Teacher information
- `parents` - Parent information
- `classes` - Class management
- `subjects` - Subject management
- `attendance` - Attendance records
- `examinations` - Examination details
- `exam_results` - Student marks
- `fees` - Fee management

## 🔒 Security

- Passwords are encrypted using BCrypt
- JWT tokens for authentication
- Role-based access control
- Session management is stateless

## 🐛 Troubleshooting

### Database Connection Issues
- Verify database credentials
- Ensure database server is running
- Check firewall settings

### JWT Token Issues
- Ensure token is included in Authorization header as `Bearer <token>`
- Check token expiration time

### Port Already in Use
Change the port in `application.properties`:
```properties
server.port=8081
```

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Contributors

- Your Name

## 📞 Support

For issues and questions:
- Create an issue in the repository
- Email: support@school.com

---
**Happy Coding! 🎉**
