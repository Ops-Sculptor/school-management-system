# School Management System - API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
All endpoints except `/auth/login` require JWT token authentication.

Include the token in the request header:
```
Authorization: Bearer <your-jwt-token>
```

---

## 1. Authentication APIs

### 1.1 Login
**Endpoint:** `POST /auth/login`

**Request Body:**
```json
{
    "username": "admin",
    "password": "admin123"
}
```

**Response:** (200 OK)
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "type": "Bearer",
    "id": 1,
    "username": "admin",
    "email": "admin@school.com",
    "role": "ADMIN",
    "message": "Login successful"
}
```

**Error Response:** (401 Unauthorized)
```json
{
    "status": 401,
    "message": "Invalid username or password",
    "timestamp": "2024-02-06T10:30:00"
}
```

---

## 2. Admin APIs

### 2.1 Student Management

#### Create Student
**Endpoint:** `POST /admin/students`

**Headers:**
```
Authorization: Bearer <token>
```

**Request Body:**
```json
{
    "rollNumber": "2024001",
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "2008-05-15",
    "gender": "MALE",
    "address": "123 Main Street",
    "city": "New York",
    "state": "NY",
    "pinCode": "10001",
    "bloodGroup": "O+",
    "admissionDate": "2024-01-15",
    "classId": 1,
    "username": "john.doe",
    "password": "password123",
    "email": "john.doe@student.school.com",
    "phoneNumber": "1234567890"
}
```

**Response:** (201 Created)
```json
{
    "id": 1,
    "rollNumber": "2024001",
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "2008-05-15",
    "gender": "MALE",
    "address": "123 Main Street",
    "city": "New York",
    "state": "NY",
    "pinCode": "10001",
    "bloodGroup": "O+",
    "admissionDate": "2024-01-15",
    "classId": 1,
    "className": "Class 6 - A",
    "username": "john.doe",
    "email": "john.doe@student.school.com",
    "phoneNumber": "1234567890",
    "active": true
}
```

#### Get All Students
**Endpoint:** `GET /admin/students`

**Response:** (200 OK)
```json
[
    {
        "id": 1,
        "rollNumber": "2024001",
        "firstName": "John",
        "lastName": "Doe",
        ...
    },
    {
        "id": 2,
        "rollNumber": "2024002",
        "firstName": "Jane",
        "lastName": "Smith",
        ...
    }
]
```

#### Get Student by ID
**Endpoint:** `GET /admin/students/{id}`

**Response:** (200 OK)
```json
{
    "id": 1,
    "rollNumber": "2024001",
    "firstName": "John",
    "lastName": "Doe",
    ...
}
```

#### Get Students by Class
**Endpoint:** `GET /admin/students/class/{classId}`

**Response:** (200 OK)
```json
[
    {
        "id": 1,
        "rollNumber": "2024001",
        "className": "Class 6 - A",
        ...
    }
]
```

#### Update Student
**Endpoint:** `PUT /admin/students/{id}`

**Request Body:** (Same as Create Student, excluding username and password)

#### Delete Student
**Endpoint:** `DELETE /admin/students/{id}`

**Response:** (200 OK)
```json
"Student deleted successfully"
```

### 2.2 Teacher Management

#### Create Teacher
**Endpoint:** `POST /admin/teachers`

**Request Body:**
```json
{
    "employeeId": "EMP001",
    "firstName": "Sarah",
    "lastName": "Johnson",
    "dateOfBirth": "1985-03-15",
    "gender": "FEMALE",
    "qualification": "M.A. English",
    "specialization": "English Literature",
    "address": "456 Oak Street",
    "city": "New York",
    "state": "NY",
    "pinCode": "10002",
    "joiningDate": "2020-01-15",
    "salary": 50000.00,
    "username": "sarah.johnson",
    "password": "password123",
    "email": "sarah.johnson@school.com",
    "phoneNumber": "9876543210"
}
```

**Response:** (201 Created)
```json
{
    "id": 1,
    "employeeId": "EMP001",
    "firstName": "Sarah",
    "lastName": "Johnson",
    ...
}
```

#### Get All Teachers
**Endpoint:** `GET /admin/teachers`

#### Get Teacher by ID
**Endpoint:** `GET /admin/teachers/{id}`

#### Update Teacher
**Endpoint:** `PUT /admin/teachers/{id}`

#### Delete Teacher
**Endpoint:** `DELETE /admin/teachers/{id}`

---

## 3. Teacher APIs

### 3.1 Attendance Management

#### Mark Attendance
**Endpoint:** `POST /teacher/attendance`

**Request Body:**
```json
{
    "studentId": 1,
    "subjectId": 1,
    "teacherId": 1,
    "date": "2024-02-06",
    "status": "PRESENT",
    "remarks": "On time"
}
```

**Status Options:** `PRESENT`, `ABSENT`, `LATE`, `HALF_DAY`

**Response:** (201 Created)
```json
{
    "id": 1,
    "studentId": 1,
    "studentName": "John Doe",
    "rollNumber": "2024001",
    "subjectId": 1,
    "subjectName": "English",
    "teacherId": 1,
    "date": "2024-02-06",
    "status": "PRESENT",
    "remarks": "On time"
}
```

#### Update Attendance
**Endpoint:** `PUT /teacher/attendance/{id}`

**Request Body:**
```json
{
    "status": "ABSENT",
    "remarks": "Medical leave"
}
```

#### Get Attendance by Subject and Date
**Endpoint:** `GET /teacher/attendance/subject/{subjectId}?date=2024-02-06`

**Response:** (200 OK)
```json
[
    {
        "id": 1,
        "studentName": "John Doe",
        "rollNumber": "2024001",
        "status": "PRESENT",
        ...
    }
]
```

### 3.2 Marks Management

#### Enter Marks
**Endpoint:** `POST /teacher/marks`

**Request Body:**
```json
{
    "studentId": 1,
    "examinationId": 1,
    "marksObtained": 85.5,
    "remarks": "Good performance"
}
```

**Response:** (201 Created)
```json
{
    "id": 1,
    "studentId": 1,
    "studentName": "John Doe",
    "rollNumber": "2024001",
    "examinationId": 1,
    "examName": "Mid Term - English",
    "subjectName": "English",
    "maxMarks": 100,
    "marksObtained": 85.5,
    "grade": "A",
    "status": "PASS",
    "remarks": "Good performance",
    "enteredByTeacher": "Sarah Johnson"
}
```

#### Update Marks
**Endpoint:** `PUT /teacher/marks/{id}`

**Request Body:**
```json
{
    "marksObtained": 90.0,
    "remarks": "Excellent work"
}
```

#### Get Results by Examination
**Endpoint:** `GET /teacher/marks/examination/{examinationId}`

**Response:** (200 OK)
```json
[
    {
        "studentName": "John Doe",
        "marksObtained": 85.5,
        "grade": "A",
        "status": "PASS"
    }
]
```

---

## 4. Student APIs

### 4.1 View Profile
**Endpoint:** `GET /student/profile/{id}`

**Response:** (200 OK)
```json
{
    "id": 1,
    "rollNumber": "2024001",
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "2008-05-15",
    "className": "Class 6 - A",
    ...
}
```

### 4.2 View Attendance
**Endpoint:** `GET /student/attendance/{studentId}`

**Response:** (200 OK)
```json
[
    {
        "date": "2024-02-06",
        "subjectName": "English",
        "status": "PRESENT"
    },
    {
        "date": "2024-02-06",
        "subjectName": "Mathematics",
        "status": "PRESENT"
    }
]
```

### 4.3 View Attendance by Date Range
**Endpoint:** `GET /student/attendance/{studentId}/range?startDate=2024-02-01&endDate=2024-02-28`

### 4.4 View Marks
**Endpoint:** `GET /student/marks/{studentId}`

**Response:** (200 OK)
```json
[
    {
        "examName": "Mid Term - English",
        "subjectName": "English",
        "maxMarks": 100,
        "marksObtained": 85.5,
        "grade": "A",
        "status": "PASS"
    }
]
```

---

## Error Responses

### Validation Error (400 Bad Request)
```json
{
    "firstName": "First name is required",
    "email": "Email should be valid"
}
```

### Unauthorized (401)
```json
{
    "status": 401,
    "message": "Invalid username or password",
    "timestamp": "2024-02-06T10:30:00"
}
```

### Forbidden (403)
```json
{
    "status": 403,
    "message": "Access denied",
    "timestamp": "2024-02-06T10:30:00"
}
```

### Not Found (404)
```json
{
    "status": 404,
    "message": "Student not found",
    "timestamp": "2024-02-06T10:30:00"
}
```

### Server Error (500)
```json
{
    "status": 500,
    "message": "An error occurred: Database connection failed",
    "timestamp": "2024-02-06T10:30:00"
}
```

---

## Grading System

| Percentage | Grade |
|------------|-------|
| 90-100%    | A+    |
| 80-89%     | A     |
| 70-79%     | B+    |
| 60-69%     | B     |
| 50-59%     | C     |
| 40-49%     | D     |
| Below 40%  | F     |

---

## Testing with cURL

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Create Student
```bash
curl -X POST http://localhost:8080/api/admin/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "rollNumber": "2024001",
    "firstName": "John",
    "lastName": "Doe",
    ...
  }'
```

### Get All Students
```bash
curl -X GET http://localhost:8080/api/admin/students \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## Notes

1. All date fields use ISO 8601 format: `YYYY-MM-DD`
2. JWT tokens expire after 24 hours (configurable)
3. Passwords are encrypted using BCrypt
4. All responses include appropriate HTTP status codes
5. CORS is enabled for all origins (configure for production)

---

**Last Updated:** February 6, 2024
