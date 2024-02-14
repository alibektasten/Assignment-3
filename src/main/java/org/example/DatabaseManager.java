package org.example;

import java.sql.*;
import java.util.Scanner;

public class DatabaseManager {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "1234";
    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "12345";



    public static boolean authenticateAdmin(Scanner scanner) {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }

    public static void viewMembers() throws SQLException {
        Statement stmt = null;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM members";
            ResultSet rs = stmt.executeQuery(sql);


            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("--------------------");
            }
            rs.close();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static void addMember(Scanner scanner) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        System.out.print("Enter member phone: ");
        String phone = scanner.nextLine();

        PreparedStatement pstmt = null;
        try {

            String sql = "INSERT INTO members (name, email, phone) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.executeUpdate();
            System.out.println("Member added successfully.");
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public static void updateMember(Scanner scanner) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.print("Enter member ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Choose field to update:");
        System.out.println("1. Name");
        System.out.println("2. Email");
        System.out.println("3. Phone");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String fieldName = "";
        String newValue = "";

        switch (choice) {
            case 1:
                fieldName = "name";
                System.out.print("Enter new name: ");
                newValue = scanner.nextLine();
                break;
            case 2:
                fieldName = "email";
                System.out.print("Enter new email: ");
                newValue = scanner.nextLine();
                break;

            case 3:
                fieldName = "phone";
                System.out.print("Enter new phone: ");
                newValue = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid choice. No changes will be made.");
                return;
        }

        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE members SET " + fieldName + "=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newValue);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Member updated successfully.");
            } else {
                System.out.println("No member found with ID " + id);
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public static void deleteMember(Scanner scanner) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.print("Enter member ID to delete: ");
        int id = scanner.nextInt();

        PreparedStatement pstmt = null;
        try {
            String sql = "DELETE FROM members WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Member deleted successfully.");
            } else {
                System.out.println("No member found with ID " + id);
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}






