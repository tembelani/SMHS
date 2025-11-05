-- Insert sample buildings
INSERT INTO Buildings (building_name, address, manager_name, manager_contact) VALUES
('Campus Towers', '123 University Ave', 'John Smith', 'john@campustowers.com'),
('Student Village', '456 College Road', 'Sarah Johnson', 'sarah@studentvillage.com'),
('Academic Residences', '789 Campus Drive', 'Mike Davis', 'mike@academicres.com'),
('University Heights', '321 Scholar Lane', 'Emily Wilson', 'emily@uheights.com'),
('Campus Lodge', '654 Education Blvd', 'David Brown', 'david@campuslodge.com');

-- Insert sample students
INSERT INTO Students (first_name, last_name, email, phone, course, max_budget) VALUES
('Alice', 'Johnson', 'alice.johnson@student.edu', '555-0101', 'Computer Science', 1200.00),
('Bob', 'Smith', 'bob.smith@student.edu', '555-0102', 'Engineering', 1000.00),
('Carol', 'Davis', 'carol.davis@student.edu', '555-0103', 'Business', 1500.00),
('David', 'Wilson', 'david.wilson@student.edu', '555-0104', 'Medicine', 1300.00),
('Eva', 'Brown', 'eva.brown@student.edu', '555-0105', 'Law', 1400.00),
('Frank', 'Miller', 'frank.miller@student.edu', '555-0106', 'Arts', 900.00),
('Grace', 'Taylor', 'grace.taylor@student.edu', '555-0107', 'Science', 1100.00),
('Henry', 'Anderson', 'henry.anderson@student.edu', '555-0108', 'Engineering', 1200.00),
('Ivy', 'Thomas', 'ivy.thomas@student.edu', '555-0109', 'Computer Science', 1000.00),
('Jack', 'Jackson', 'jack.jackson@student.edu', '555-0110', 'Business', 1300.00),
('Karen', 'White', 'karen.white@student.edu', '555-0111', 'Medicine', 1500.00),
('Leo', 'Harris', 'leo.harris@student.edu', '555-0112', 'Law', 1400.00),
('Mia', 'Martin', 'mia.martin@student.edu', '555-0113', 'Arts', 950.00),
('Nathan', 'Thompson', 'nathan.thompson@student.edu', '555-0114', 'Science', 1050.00),
('Henry', 'Garcia', 'olivia.garcia@student.edu', '555-0115', 'Engineering', 1250.00);

-- Insert sample rooms
INSERT INTO Rooms (building_id, room_number, capacity, monthly_rent, is_available) VALUES
(1, '101', 2, 800.00, TRUE),
(1, '102', 2, 850.00, TRUE),
(1, '103', 1, 950.00, FALSE),
(1, '201', 2, 820.00, TRUE),
(1, '202', 3, 750.00, TRUE),
(2, 'A101', 2, 900.00, FALSE),
(2, 'A102', 2, 920.00, TRUE),
(2, 'A201', 1, 1100.00, TRUE),
(2, 'A202', 2, 880.00, FALSE),
(3, '101', 2, 780.00, TRUE),
(3, '102', 2, 790.00, TRUE),
(3, '103', 3, 700.00, TRUE),
(4, '101', 1, 1200.00, TRUE),
(4, '102', 2, 950.00, TRUE),
(5, '101', 2, 850.00, FALSE);

-- Insert sample contracts
INSERT INTO Contracts (student_id, room_id, start_date, end_date, monthly_rent) VALUES
(1, 3, '2024-01-15', '2024-12-15', 950.00),
(2, 6, '2024-02-01', '2024-12-31', 900.00),
(3, 9, '2024-01-20', '2024-12-20', 880.00),
(4, 15, '2024-03-01', '2024-12-31', 850.00);

-- Update room occupancy
UPDATE Rooms SET current_occupancy = 1, is_available = FALSE WHERE room_id IN (3, 6, 9, 15);