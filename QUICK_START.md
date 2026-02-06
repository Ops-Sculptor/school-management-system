# Quick Start Guide - School Management System

## 🚀 Get Started in 5 Minutes

### Step 1: Prerequisites Check
Make sure you have installed:
- ✅ Java 17 or higher
- ✅ Maven 3.6+
- ✅ MySQL 8.0+ or PostgreSQL 12+

Verify installations:
```bash
java -version
mvn -version
mysql --version  # or psql --version
```

### Step 2: Database Setup

#### For MySQL:
```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE school_management;
exit;
```

#### For PostgreSQL:
```bash
# Login to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE school_management;
\q
```

### Step 3: Configure Application

Edit `src/main/resources/application.properties`:

**For MySQL:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/school_management
spring.datasource.username=root
spring.datasource.password=your_password
```

**For PostgreSQL:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/school_management
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Step 4: Build and Run

```bash
# Navigate to project directory
cd school-management-system

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

Or run the JAR file:
```bash
mvn clean package
java -jar target/school-management-system-1.0.0.jar
```

### Step 5: Verify Installation

Open your browser or use cURL:
```bash
curl http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

If you see a JSON response with a token, you're all set! 🎉

---

## 📝 Initial Setup

### Load Sample Data (Optional)

After the application starts, run the SQL script to load sample data:

```bash
mysql -u root -p school_management < src/main/resources/data.sql
```

Or for PostgreSQL:
```bash
psql -U postgres -d school_management -f src/main/resources/data.sql
```

### Default Login Credentials

After loading sample data:

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Teacher | sarah.johnson | admin123 |
| Teacher | john.smith | admin123 |
| Student | emma.williams | admin123 |
| Parent | robert.williams | admin123 |

---

## 🧪 Test the API

### 1. Login as Admin
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

**Save the token from the response!**

### 2. Get All Students
```bash
curl -X GET http://localhost:8080/api/admin/students \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### 3. Create a New Student
```bash
curl -X POST http://localhost:8080/api/admin/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "rollNumber": "2024010",
    "firstName": "Alice",
    "lastName": "Brown",
    "dateOfBirth": "2009-03-20",
    "gender": "FEMALE",
    "address": "789 Park Avenue",
    "city": "New York",
    "state": "NY",
    "pinCode": "10010",
    "bloodGroup": "AB+",
    "admissionDate": "2024-02-01",
    "classId": 1,
    "username": "alice.brown",
    "password": "password123",
    "email": "alice.brown@student.school.com",
    "phoneNumber": "5551234567"
  }'
```

---

## 🛠️ Common Issues and Solutions

### Issue 1: Port 8080 already in use
**Solution:** Change the port in `application.properties`:
```properties
server.port=8081
```

### Issue 2: Database connection refused
**Solution:** 
- Verify database is running: `systemctl status mysql` or `systemctl status postgresql`
- Check credentials in `application.properties`
- Ensure database exists

### Issue 3: JWT secret key error
**Solution:** The default secret key is already configured. If you want to change it:
```properties
jwt.secret=your-new-secret-key-minimum-256-bits
```

### Issue 4: BCrypt password error
**Solution:** Use BCrypt to generate password hash:
```java
String hashedPassword = new BCryptPasswordEncoder().encode("password123");
```

---

## 📚 Next Steps

1. **Explore the API**: Check `API_DOCUMENTATION.md` for all endpoints
2. **Customize**: Modify entities and DTOs as per your requirements
3. **Add Features**: Extend functionality (see Future Enhancements in README)
4. **Frontend**: Create a UI using React, Angular, or Vue.js
5. **Deploy**: Deploy to cloud platforms (AWS, Heroku, etc.)

---

## 🔗 Useful Links

- **Full Documentation**: `README.md`
- **API Reference**: `API_DOCUMENTATION.md`
- **Sample Data**: `src/main/resources/data.sql`
- **Configuration**: `src/main/resources/application.properties`

---

## 💡 Pro Tips

1. **Use Postman**: Import API collection for easier testing
2. **Enable Logging**: Set logging level to DEBUG for troubleshooting
3. **Database GUI**: Use MySQL Workbench or pgAdmin for database management
4. **Hot Reload**: Use Spring DevTools for automatic restarts during development
5. **Environment Profiles**: Create separate properties files for dev, test, and prod

---

## 🆘 Need Help?

- Check error logs: `logs/spring.log`
- Review exception stack traces in console
- Verify all dependencies are downloaded: `mvn dependency:tree`
- Ensure Java version compatibility: `java -version`

---

**Happy Coding! 🎓**

For issues, create a ticket or contact the development team.
