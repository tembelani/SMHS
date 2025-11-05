/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mydynamicweb.studenthousingmanagementsystem2025;

/**
 *
 * @author toddt
 */

 
import java.sql.*;
import java.util.Scanner;

public class StudentHousingManagementSystem2025 {
    // Database configuration - UPDATE THESE FOR YOUR SETUP
    private static final String URL = "jdbc:mysql://localhost:3306/shms";
    private static final String USER = "root";  // Change to your MySQL username
    private static final String PASSWORD = "Tembel@ni02310";  // Change to your MySQL password
    
    private Connection connection;
    private Scanner scanner;
    
    public static void main(String[] args) {
        StudentHousingManagementSystem2025 app = new StudentHousingManagementSystem2025();
        app.run();
    }
    
    public StudentHousingManagementSystem2025() {
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("=== Student Housing Management System ===");
            System.out.println("Database connection established successfully!\n");
            
            mainMenu();
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            System.err.println("Please check your database configuration in the code.");
        } finally {
            closeResources();
        }
    }
    
    private void mainMenu() {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. View All Students");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Add New Contract");
            System.out.println("4. View Expiring Contracts (30 days)");
            System.out.println("5. View Students Over Budget");
            System.out.println("6. View All Buildings");
            System.out.println("7. View Waitlisted Students");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        viewAllStudents();
                        break;
                    case 2:
                        viewAvailableRooms();
                        break;
                    case 3:
                        addNewContract();
                        break;
                    case 4:
                        viewExpiringContracts();
                        break;
                    case 5:
                        viewStudentsOverBudget();
                        break;
                    case 6:
                        viewAllBuildings();
                        break;
                    case 7:
                        viewWaitlistedStudents();
                        break;
                    case 8:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
    
    private void viewAllStudents() {
        String sql = "SELECT student_id, first_name, last_name, email, phone, course, max_budget FROM Students ORDER BY last_name, first_name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- All Students ---");
            System.out.printf("%-5s %-15s %-15s %-25s %-15s %-20s %-10s%n", 
                "ID", "First Name", "Last Name", "Email", "Phone", "Course", "Max Budget");
            System.out.println("-".repeat(105));
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.printf("%-5d %-15s %-15s %-25s %-15s %-20s $%-9.2f%n",
                    rs.getInt("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("course"),
                    rs.getDouble("max_budget"));
            }
            System.out.println("\nTotal students: " + count);
            
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
    }
    
    private void viewAvailableRooms() {
        System.out.print("Enter building name to filter (or press enter for all): ");
        String buildingFilter = scanner.nextLine();
        
        String sql = "SELECT b.building_name, r.room_id, r.room_number, r.capacity, r.current_occupancy, r.monthly_rent " +
                    "FROM Rooms r JOIN Buildings b ON r.building_id = b.building_id " +
                    "WHERE r.is_available = TRUE";
        
        if (!buildingFilter.trim().isEmpty()) {
            sql += " AND b.building_name LIKE ?";
        }
        sql += " ORDER BY b.building_name, r.room_number";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (!buildingFilter.trim().isEmpty()) {
                pstmt.setString(1, "%" + buildingFilter + "%");
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n--- Available Rooms ---");
                System.out.printf("%-20s %-5s %-15s %-10s %-15s %-10s%n", 
                    "Building", "Room ID", "Room Number", "Capacity", "Occupancy", "Monthly Rent");
                System.out.println("-".repeat(85));
                
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.printf("%-20s %-5d %-15s %-10d %-15d $%-9.2f%n",
                        rs.getString("building_name"),
                        rs.getInt("room_id"),
                        rs.getString("room_number"),
                        rs.getInt("capacity"),
                        rs.getInt("current_occupancy"),
                        rs.getDouble("monthly_rent"));
                }
                
                if (count == 0) {
                    System.out.println("No available rooms found.");
                } else {
                    System.out.println("\nTotal available rooms: " + count);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving available rooms: " + e.getMessage());
        }
    }
    
    private void addNewContract() {
        System.out.println("\n--- Add New Contract ---");
        
        try {
            // Get student ID
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            
            // Get room ID
            System.out.print("Enter room ID: ");
            int roomId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            // Get dates
            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();
            
            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();
            
            // Get monthly rent
            System.out.print("Enter monthly rent: ");
            double monthlyRent = scanner.nextDouble();
            
            // Check if room is available
            if (!isRoomAvailable(roomId)) {
                System.out.println("Error: Room is not available or doesn't exist.");
                return;
            }
            
            // Check if student exists
            if (!isStudentExists(studentId)) {
                System.out.println("Error: Student doesn't exist.");
                return;
            }
            
            // Insert contract
            String sql = "INSERT INTO Contracts (student_id, room_id, start_date, end_date, monthly_rent) VALUES (?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, studentId);
                pstmt.setInt(2, roomId);
                pstmt.setString(3, startDate);
                pstmt.setString(4, endDate);
                pstmt.setDouble(5, monthlyRent);
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Update room availability
                    updateRoomAvailability(roomId, false);
                    System.out.println("Contract added successfully!");
                } else {
                    System.out.println("Failed to add contract.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error adding contract: " + e.getMessage());
            scanner.nextLine(); // clear any remaining input
        }
    }
    
    private boolean isRoomAvailable(int roomId) {
        String sql = "SELECT is_available FROM Rooms WHERE room_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getBoolean("is_available");
        } catch (SQLException e) {
            return false;
        }
    }
    
    private boolean isStudentExists(int studentId) {
        String sql = "SELECT student_id FROM Students WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
    
    private void updateRoomAvailability(int roomId, boolean available) {
        String sql = "UPDATE Rooms SET is_available = ?, current_occupancy = current_occupancy + 1 WHERE room_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, roomId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating room availability: " + e.getMessage());
        }
    }
    
    private void viewExpiringContracts() {
        String sql = "SELECT c.contract_id, s.first_name, s.last_name, b.building_name, r.room_number, " +
                    "c.end_date, DATEDIFF(c.end_date, CURDATE()) as days_until_expiry " +
                    "FROM Contracts c " +
                    "JOIN Students s ON c.student_id = s.student_id " +
                    "JOIN Rooms r ON c.room_id = r.room_id " +
                    "JOIN Buildings b ON r.building_id = b.building_id " +
                    "WHERE c.end_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) " +
                    "AND c.status = 'active' " +
                    "ORDER BY c.end_date";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- Contracts Expiring in 30 Days ---");
            System.out.printf("%-10s %-15s %-15s %-20s %-15s %-12s %-15s%n", 
                "Contract ID", "First Name", "Last Name", "Building", "Room", "End Date", "Days Left");
            System.out.println("-".repeat(100));
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.printf("%-10d %-15s %-15s %-20s %-15s %-12s %-15d%n",
                    rs.getInt("contract_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("building_name"),
                    rs.getString("room_number"),
                    rs.getDate("end_date"),
                    rs.getInt("days_until_expiry"));
            }
            
            if (count == 0) {
                System.out.println("No contracts expiring in the next 30 days.");
            } else {
                System.out.println("\nTotal expiring contracts: " + count);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving expiring contracts: " + e.getMessage());
        }
    }
    
    private void viewStudentsOverBudget() {
        String sql = "SELECT s.student_id, s.first_name, s.last_name, s.max_budget, " +
                    "c.monthly_rent, (c.monthly_rent - s.max_budget) as over_budget_amount, " +
                    "b.building_name, r.room_number " +
                    "FROM Students s " +
                    "JOIN Contracts c ON s.student_id = c.student_id " +
                    "JOIN Rooms r ON c.room_id = r.room_id " +
                    "JOIN Buildings b ON r.building_id = b.building_id " +
                    "WHERE c.monthly_rent > s.max_budget " +
                    "AND c.status = 'active'";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- Students Over Budget ---");
            System.out.printf("%-5s %-15s %-15s %-10s %-10s %-10s %-20s %-15s%n", 
                "ID", "First Name", "Last Name", "Budget", "Rent", "Overflow", "Building", "Room");
            System.out.println("-".repeat(105));
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.printf("%-5d %-15s %-15s $%-9.2f $%-9.2f $%-9.2f %-20s %-15s%n",
                    rs.getInt("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDouble("max_budget"),
                    rs.getDouble("monthly_rent"),
                    rs.getDouble("over_budget_amount"),
                    rs.getString("building_name"),
                    rs.getString("room_number"));
            }
            
            if (count == 0) {
                System.out.println("No students are currently over their budget.");
            } else {
                System.out.println("\nTotal students over budget: " + count);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students over budget: " + e.getMessage());
        }
    }
    
    private void viewAllBuildings() {
        String sql = "SELECT building_id, building_name, address, manager_name, manager_contact FROM Buildings ORDER BY building_name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- All Buildings ---");
            System.out.printf("%-5s %-20s %-30s %-20s %-25s%n", 
                "ID", "Building Name", "Address", "Manager", "Contact");
            System.out.println("-".repeat(105));
            
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-30s %-20s %-25s%n",
                    rs.getInt("building_id"),
                    rs.getString("building_name"),
                    rs.getString("address"),
                    rs.getString("manager_name"),
                    rs.getString("manager_contact"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving buildings: " + e.getMessage());
        }
    }
    
    private void viewWaitlistedStudents() {
        String sql = "SELECT s.student_id, s.first_name, s.last_name, s.email, s.phone, s.course, s.max_budget " +
                    "FROM Students s " +
                    "WHERE s.student_id NOT IN (" +
                    "    SELECT DISTINCT student_id " +
                    "    FROM Contracts " +
                    "    WHERE status = 'active'" +
                    ") " +
                    "ORDER BY s.created_at";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- Waitlisted Students (No Active Contract) ---");
            System.out.printf("%-5s %-15s %-15s %-25s %-15s %-20s %-10s%n", 
                "ID", "First Name", "Last Name", "Email", "Phone", "Course", "Max Budget");
            System.out.println("-".repeat(105));
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.printf("%-5d %-15s %-15s %-25s %-15s %-20s $%-9.2f%n",
                    rs.getInt("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("course"),
                    rs.getDouble("max_budget"));
            }
            
            if (count == 0) {
                System.out.println("No waitlisted students found.");
            } else {
                System.out.println("\nTotal waitlisted students: " + count);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving waitlisted students: " + e.getMessage());
        }
    }
    
    private void closeResources() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
            if (scanner != null) {
                scanner.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
