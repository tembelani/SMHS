-- schema creation
CREATE DATABASE SHMS;
USE SHMS;

-- Building Table
CREATE TABLE Buildings (
    building_id INT AUTO_INCREMENT PRIMARY KEY,
    building_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    manager_name VARCHAR(100),
    manager_contact VARCHAR(100)
);

-- Students table
CREATE TABLE Students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    course VARCHAR(100),
    max_budget DECIMAL(8,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Rooms table
CREATE TABLE Rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    building_id INT NOT NULL,
    room_number VARCHAR(20) NOT NULL,
    capacity INT NOT NULL CHECK (capacity >= 1),
    current_occupancy INT DEFAULT 0,
    monthly_rent DECIMAL(8,2) NOT NULL CHECK (monthly_rent > 0),
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (building_id) REFERENCES Buildings(building_id) ON DELETE CASCADE,
    UNIQUE KEY unique_room_building (building_id, room_number)
);

-- Contracts table
CREATE TABLE Contracts (
    contract_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    room_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    monthly_rent DECIMAL(8,2) NOT NULL,
    status ENUM('active', 'expired', 'terminated') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES Rooms(room_id) ON DELETE CASCADE,
    CHECK (end_date > start_date)
);




