-- School Management System - Initial Setup Script
-- Run this script after starting the application for the first time

-- ====================================
-- 1. Create Admin User
-- ====================================
-- Password: admin123 (BCrypt encoded)
INSERT INTO users (username, password, email, full_name, phone_number, role, active, created_at) 
VALUES ('admin', '$2a$10$QhXKSiW8k.VhCFHJZ5D0G.VeKbV/RLjvE9n.xE4QvGj6K5U5ynEze', 
        'admin@school.com', 'System Administrator', '9999999999', 'ADMIN', true, NOW());

-- ====================================
-- 2. Create Sample Classes
-- ====================================
INSERT INTO classes (class_name, section, capacity, active, created_at) VALUES
('Class 1', 'A', 40, true, NOW()),
('Class 2', 'A', 40, true, NOW()),
('Class 3', 'A', 40, true, NOW()),
('Class 4', 'A', 40, true, NOW()),
('Class 5', 'A', 40, true, NOW()),
('Class 6', 'A', 35, true, NOW()),
('Class 7', 'A', 35, true, NOW()),
('Class 8', 'A', 35, true, NOW()),
('Class 9', 'A', 30, true, NOW()),
('Class 10', 'A', 30, true, NOW());

-- ====================================
-- 3. Create Sample Subjects
-- ====================================
-- For Class 1
INSERT INTO subjects (subject_name, subject_code, description, class_id, active, created_at) VALUES
('English', 'ENG-1-A', 'English Language', 1, true, NOW()),
('Mathematics', 'MATH-1-A', 'Mathematics', 1, true, NOW()),
('Science', 'SCI-1-A', 'General Science', 1, true, NOW());

-- For Class 6
INSERT INTO subjects (subject_name, subject_code, description, class_id, active, created_at) VALUES
('English', 'ENG-6-A', 'English Language and Literature', 6, true, NOW()),
('Mathematics', 'MATH-6-A', 'Mathematics', 6, true, NOW()),
('Science', 'SCI-6-A', 'Physics, Chemistry, Biology', 6, true, NOW()),
('Social Studies', 'SS-6-A', 'History and Geography', 6, true, NOW()),
('Computer Science', 'CS-6-A', 'Basic Computing', 6, true, NOW());

-- For Class 10
INSERT INTO subjects (subject_name, subject_code, description, class_id, active, created_at) VALUES
('English', 'ENG-10-A', 'English Language and Literature', 10, true, NOW()),
('Mathematics', 'MATH-10-A', 'Advanced Mathematics', 10, true, NOW()),
('Physics', 'PHY-10-A', 'Physics', 10, true, NOW()),
('Chemistry', 'CHEM-10-A', 'Chemistry', 10, true, NOW()),
('Biology', 'BIO-10-A', 'Biology', 10, true, NOW()),
('Computer Science', 'CS-10-A', 'Computer Applications', 10, true, NOW());

-- ====================================
-- 4. Create Sample Teachers
-- ====================================
-- Teacher 1 - English Teacher
INSERT INTO users (username, password, email, full_name, phone_number, role, active, created_at) 
VALUES ('sarah.johnson', '$2a$10$QhXKSiW8k.VhCFHJZ5D0G.VeKbV/RLjvE9n.xE4QvGj6K5U5ynEze', 
        'sarah.johnson@school.com', 'Sarah Johnson', '9876543210', 'TEACHER', true, NOW());

INSERT INTO teachers (user_id, employee_id, first_name, last_name, date_of_birth, gender, 
                     qualification, specialization, address, city, state, pin_code, 
                     joining_date, salary, active, created_at)
VALUES ((SELECT id FROM users WHERE username = 'sarah.johnson'), 
        'EMP001', 'Sarah', 'Johnson', '1985-03-15', 'FEMALE',
        'M.A. English', 'English Literature', '456 Oak Street', 'New York', 'NY', '10002',
        '2020-01-15', 50000.00, true, NOW());

-- Teacher 2 - Mathematics Teacher
INSERT INTO users (username, password, email, full_name, phone_number, role, active, created_at) 
VALUES ('john.smith', '$2a$10$QhXKSiW8k.VhCFHJZ5D0G.VeKbV/RLjvE9n.xE4QvGj6K5U5ynEze', 
        'john.smith@school.com', 'John Smith', '9876543211', 'TEACHER', true, NOW());

INSERT INTO teachers (user_id, employee_id, first_name, last_name, date_of_birth, gender, 
                     qualification, specialization, address, city, state, pin_code, 
                     joining_date, salary, active, created_at)
VALUES ((SELECT id FROM users WHERE username = 'john.smith'), 
        'EMP002', 'John', 'Smith', '1980-07-22', 'MALE',
        'M.Sc. Mathematics', 'Applied Mathematics', '789 Pine Avenue', 'New York', 'NY', '10003',
        '2019-08-01', 55000.00, true, NOW());

-- Teacher 3 - Science Teacher
INSERT INTO users (username, password, email, full_name, phone_number, role, active, created_at) 
VALUES ('emily.davis', '$2a$10$QhXKSiW8k.VhCFHJZ5D0G.VeKbV/RLjvE9n.xE4QvGj6K5U5ynEze', 
        'emily.davis@school.com', 'Emily Davis', '9876543212', 'TEACHER', true, NOW());

INSERT INTO teachers (user_id, employee_id, first_name, last_name, date_of_birth, gender, 
                     qualification, specialization, address, city, state, pin_code, 
                     joining_date, salary, active, created_at)
VALUES ((SELECT id FROM users WHERE username = 'emily.davis'), 
        'EMP003', 'Emily', 'Davis', '1988-11-30', 'FEMALE',
        'M.Sc. Physics', 'Physics and Chemistry', '321 Elm Road', 'New York', 'NY', '10004',
        '2021-03-10', 52000.00, true, NOW());

-- ====================================
-- 5. Assign Subjects to Teachers
-- ====================================
INSERT INTO teacher_subjects (teacher_id, subject_id) VALUES
-- Sarah Johnson - English
((SELECT id FROM teachers WHERE employee_id = 'EMP001'), 
 (SELECT id FROM subjects WHERE subject_code = 'ENG-6-A')),
((SELECT id FROM teachers WHERE employee_id = 'EMP001'), 
 (SELECT id FROM subjects WHERE subject_code = 'ENG-10-A')),

-- John Smith - Mathematics
((SELECT id FROM teachers WHERE employee_id = 'EMP002'), 
 (SELECT id FROM subjects WHERE subject_code = 'MATH-6-A')),
((SELECT id FROM teachers WHERE employee_id = 'EMP002'), 
 (SELECT id FROM subjects WHERE subject_code = 'MATH-10-A')),

-- Emily Davis - Science
((SELECT id FROM teachers WHERE employee_id = 'EMP003'), 
 (SELECT id FROM subjects WHERE subject_code = 'SCI-6-A')),
((SELECT id FROM teachers WHERE employee_id = 'EMP003'), 
 (SELECT id FROM subjects WHERE subject_code = 'PHY-10-A'));

-- ====================================
-- 6. Create Sample Parent
-- ====================================
INSERT INTO users (username, password, email, full_name, phone_number, role, active, created_at) 
VALUES ('robert.williams', '$2a$10$QhXKSiW8k.VhCFHJZ5D0G.VeKbV/RLjvE9n.xE4QvGj6K5U5ynEze', 
        'robert.williams@email.com', 'Robert Williams', '9876543213', 'PARENT', true, NOW());

INSERT INTO parents (user_id, father_name, mother_name, father_occupation, mother_occupation,
                    father_phone, mother_phone, address, city, state, pin_code, active, created_at)
VALUES ((SELECT id FROM users WHERE username = 'robert.williams'),
        'Robert Williams', 'Mary Williams', 'Engineer', 'Teacher',
        '9876543213', '9876543214', '555 Maple Drive', 'New York', 'NY', '10005',
        true, NOW());

-- ====================================
-- 7. Create Sample Students
-- ====================================
-- Student 1
INSERT INTO users (username, password, email, full_name, phone_number, role, active, created_at) 
VALUES ('emma.williams', '$2a$10$QhXKSiW8k.VhCFHJZ5D0G.VeKbV/RLjvE9n.xE4QvGj6K5U5ynEze', 
        'emma.williams@student.school.com', 'Emma Williams', '9876543220', 'STUDENT', true, NOW());

INSERT INTO students (user_id, roll_number, first_name, last_name, date_of_birth, gender,
                     address, city, state, pin_code, blood_group, admission_date,
                     class_id, parent_id, active, created_at)
VALUES ((SELECT id FROM users WHERE username = 'emma.williams'),
        '2024001', 'Emma', 'Williams', '2008-04-12', 'FEMALE',
        '555 Maple Drive', 'New York', 'NY', '10005', 'A+', '2024-01-10',
        6, (SELECT id FROM parents WHERE father_name = 'Robert Williams'),
        true, NOW());

-- Student 2
INSERT INTO users (username, password, email, full_name, phone_number, role, active, created_at) 
VALUES ('james.williams', '$2a$10$QhXKSiW8k.VhCFHJZ5D0G.VeKbV/RLjvE9n.xE4QvGj6K5U5ynEze', 
        'james.williams@student.school.com', 'James Williams', '9876543221', 'STUDENT', true, NOW());

INSERT INTO students (user_id, roll_number, first_name, last_name, date_of_birth, gender,
                     address, city, state, pin_code, blood_group, admission_date,
                     class_id, parent_id, active, created_at)
VALUES ((SELECT id FROM users WHERE username = 'james.williams'),
        '2024002', 'James', 'Williams', '2006-09-25', 'MALE',
        '555 Maple Drive', 'New York', 'NY', '10005', 'B+', '2024-01-10',
        10, (SELECT id FROM parents WHERE father_name = 'Robert Williams'),
        true, NOW());

-- ====================================
-- 8. Create Sample Examinations
-- ====================================
INSERT INTO examinations (exam_name, exam_type, class_id, subject_id, exam_date, 
                         max_marks, passing_marks, duration, active, created_at) VALUES
('Mid Term - English', 'MID_TERM', 6, 
 (SELECT id FROM subjects WHERE subject_code = 'ENG-6-A'), 
 '2024-03-15', 100, 40, 180, true, NOW()),

('Mid Term - Mathematics', 'MID_TERM', 6, 
 (SELECT id FROM subjects WHERE subject_code = 'MATH-6-A'), 
 '2024-03-17', 100, 40, 180, true, NOW()),

('Final Term - English', 'FINAL_TERM', 10, 
 (SELECT id FROM subjects WHERE subject_code = 'ENG-10-A'), 
 '2024-06-10', 100, 35, 180, true, NOW());

-- ====================================
-- 9. Create Sample Fee Structure
-- ====================================
INSERT INTO fees (student_id, fee_type, total_amount, paid_amount, balance, 
                 due_date, payment_status, active, created_at) VALUES
((SELECT id FROM students WHERE roll_number = '2024001'), 
 'TUITION', 10000.00, 5000.00, 5000.00, '2024-03-31', 'PARTIAL', true, NOW()),

((SELECT id FROM students WHERE roll_number = '2024002'), 
 'TUITION', 12000.00, 12000.00, 0.00, '2024-03-31', 'PAID', true, NOW());

-- ====================================
-- Default Login Credentials
-- ====================================
-- Admin:
--   Username: admin
--   Password: admin123
--
-- Teachers:
--   Username: sarah.johnson, john.smith, emily.davis
--   Password: admin123
--
-- Students:
--   Username: emma.williams, james.williams
--   Password: admin123
--
-- Parent:
--   Username: robert.williams
--   Password: admin123
-- ====================================

COMMIT;
