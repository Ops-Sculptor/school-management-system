# Quick Start Guide - School Management System

## 🚀 Get Started in 5 Minutes!

This guide will help you get the School Management System up and running quickly.

## Prerequisites Check

Before starting, make sure you have:

- ✅ Java 17 installed (`java -version`)
- ✅ Maven installed (`mvn -version`)
- ✅ MySQL installed and running

## Step-by-Step Setup

### Step 1: Install Java (if not installed)

**Windows:**
1. Download JDK 17 from [Oracle](https://www.oracle.com/java/technologies/downloads/)
2. Run the installer
3. Set JAVA_HOME environment variable

**Linux:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**Mac:**
```bash
brew install openjdk@17
```

### Step 2: Install MySQL (if not installed)

**Windows:**
1. Download MySQL from [mysql.com](https://dev.mysql.com/downloads/installer/)
2. Run the installer
3. Remember the root password you set!

**Linux:**
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

**Mac:**
```bash
brew install mysql
brew services start mysql
```

### Step 3: Create Database

Open MySQL command line:

```bash
mysql -u root -p
```

Enter your password, then run:

```sql
CREATE DATABASE school_db;
EXIT;
```

### Step 4: Configure the Application

1. Navigate to project folder:
```bash
cd school-management-system
```

2. Edit `src/main/resources/application.properties`:

Update these lines with your MySQL credentials:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 5: Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

Wait for the message: **"Started SchoolManagementSystemApplication"**

### Step 6: Access the Application

Open your browser and go to:
```
http://localhost:8080
```

## 🔐 Login Credentials

Use these credentials to login:

**Admin Account:**
- Username: `admin`
- Password: `admin123`

**Teacher Account:**
- Username: `teacher1`
- Password: `teacher123`

**Student Account:**
- Username: `student1`
- Password: `student123`

## 🎯 What to Do First?

### As Admin:
1. Login with admin credentials
2. Go to "Manage Students" to add new students
3. Go to "Manage Teachers" to add new teachers
4. Go to "Manage Classes" to create classes
5. Go to "Manage Subjects" to add subjects and assign teachers

### As Teacher:
1. Login with teacher credentials
2. View assigned subjects
3. Mark attendance for students
4. Enter marks for examinations

### As Student:
1. Login with student credentials
2. View your profile
3. Check attendance records
4. View examination marks
5. Check fee payment status

## 🔧 Common Issues & Solutions

### Issue: "Port 8080 already in use"

**Solution:** Change the port in `application.properties`:
```properties
server.port=8081
```

Then access at: `http://localhost:8081`

### Issue: "Access denied for user 'root'"

**Solution:** 
1. Check your MySQL password is correct in `application.properties`
2. Try resetting MySQL password:
```bash
mysql -u root -p
ALTER USER 'root'@'localhost' IDENTIFIED BY 'newpassword';
```

### Issue: "Table doesn't exist"

**Solution:** The application creates tables automatically. Make sure:
1. Database `school_db` exists
2. Application started successfully
3. Check `spring.jpa.hibernate.ddl-auto=update` in properties

### Issue: Build fails

**Solution:**
```bash
# Clean and rebuild
mvn clean install -U

# Skip tests if needed
mvn clean install -DskipTests
```

## 📱 Next Steps

1. **Explore the System:**
   - Login with different roles
   - Try adding students, teachers, classes
   - Mark attendance
   - Enter marks

2. **Customize:**
   - Modify the templates in `src/main/resources/templates/`
   - Add new features
   - Change styling

3. **Learn More:**
   - Read the full README.md
   - Explore the code structure
   - Check out Spring Boot documentation

## 💡 Tips for Beginners

1. **Don't change code while the app is running** - Stop and restart
2. **Save before running** - Always save your changes
3. **Check console for errors** - Error messages are helpful
4. **Use H2 for quick testing** - Switch to H2 in-memory database for faster testing

## 📚 Learning Resources

- [Spring Boot Official Docs](https://spring.io/projects/spring-boot)
- [Thymeleaf Documentation](https://www.thymeleaf.org/)
- [Spring Security Guide](https://spring.io/guides/gs/securing-web/)

## ✅ Success Checklist

- [ ] Java 17 installed
- [ ] Maven installed
- [ ] MySQL installed and running
- [ ] Database `school_db` created
- [ ] `application.properties` configured
- [ ] Project builds successfully
- [ ] Application starts without errors
- [ ] Can login at http://localhost:8080
- [ ] Admin dashboard loads

## 🎉 You're All Set!

Congratulations! Your School Management System is now running. Start exploring and learning!

---

**Need Help?** Check the troubleshooting section in README.md or review the error messages in the console.
