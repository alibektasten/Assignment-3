package org.example;

import org.example.DatabaseManager;

import java.sql.*;
import java.util.Scanner;

public class ComputerClubManagementSystem {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        Scanner scanner = new Scanner(System.in);

        if (DatabaseManager.authenticateAdmin(scanner)) {
            try  {
                System.out.println("Connected to the database");
                while (true) {
                    System.out.println("\nComputer Club Management System");
                    System.out.println("1. View Members");
                    System.out.println("2. Add Member");
                    System.out.println("3. Update Member");
                    System.out.println("4. Delete Member");
                    System.out.println("5. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            dbManager.viewMembers();
                            break;
                        case 2:
                            dbManager.addMember(scanner);
                            break;
                        case 3:
                            dbManager.updateMember(scanner);
                            break;
                        case 4:
                            dbManager.deleteMember(scanner);
                            break;
                        case 5:
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Connection failed. Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid admin username or password. Please try again!");
        }


    }
}