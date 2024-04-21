import java.util.*;
import java.text.*;

public class LeaveManagementApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeLeaveManagementSystem system = new EmployeeLeaveManagementSystem();
        boolean authenticated = false;
        User authenticatedUser = null;

        while (true) {
            if (!authenticated) {
                System.out.println("__________________________________________________");
                System.out.println("Employee Leave Management System");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter username: ");
                        String regUsername = scanner.next();
                        System.out.print("Enter password: ");
                        String regPassword = scanner.next();
                        System.out.print("Enter role (employee/manager): ");
                        String regRole = scanner.next();
                        system.registerUser(regUsername, regPassword, regRole);
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        String username = scanner.next();
                        System.out.print("Enter password: ");
                        String password = scanner.next();
                        authenticatedUser = system.authenticateUser(username, password);
                        if (authenticatedUser != null) {
                            System.out.println("Authentication successful.");
                            authenticated = true;
                        } else {
                            System.out.println("Invalid credentials.");
                        }
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                if (authenticatedUser.getRole().equals("employee")) {
                    // Employee menu
                    System.out.println("__________________________________________________");
                    System.out.println("Employee Menu");
                    System.out.println("1. Submit Leave Request");
                    System.out.println("2. Retrieve My Requests");
                    System.out.println("3. Logout");
                    System.out.print("Enter your choice: ");
                    int empChoice = scanner.nextInt();
                    switch (empChoice) {
                        case 1:
                            // Display available leave types and balances
                            System.out.println("Available Leave Types and Balances:");
                            Map<String, Integer> leaveBalances = authenticatedUser.getLeaveBalances();
                            for (Map.Entry<String, Integer> entry : leaveBalances.entrySet()) {
                                System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " days");
                            }
                            System.out.print("Enter type of leave: ");
                            String type = scanner.next();
                            // Validate entered leave type
                            if (!leaveBalances.containsKey(type)) {
                                System.out.println("Invalid leave type. Please select from the available leave types.");
                                break; // Return to the main menu
                            }

                            int availableDays = leaveBalances.get(type);
                            if (availableDays == 0) {
                                System.out.println("You have no available leave balance for " + type + " leave.");
                                break; // Return to the main menu
                            }

                            System.out.println("You have " + availableDays + " days available for " + type + " leave.");

                            // Enter start date
                            System.out.println("Enter start date (YYYY-MM-DD): ");
                            String startDateStr = scanner.next();
                            // Enter end date
                            System.out.println("Enter end date (YYYY-MM-DD): ");
                            String endDateStr = scanner.next();
                            scanner.nextLine(); // Consume the newline character
                            // Enter comments
                            System.out.print("Enter comments: ");
                            String comments = scanner.nextLine();
                            // Parse dates
                            Date startDate = parseDate(startDateStr);
                            Date endDate = parseDate(endDateStr);
                            if (startDate != null && endDate != null) {
                                int requestedDays = system.calculateDays(startDate, endDate);
                                if (requestedDays <= availableDays) {
                                    system.submitLeaveRequest(authenticatedUser, type, startDate, endDate, comments);
                                    System.out.println("Leave request submitted successfully.");
                                } else {
                                    System.out.println("Insufficient leave balance for " + type + " leave.");
                                }
                            } else {
                                System.out.println("Invalid date format.");
                            }
                            break;

                        case 2:
                            system.showEmployeeRequests(authenticatedUser);
                            break;
                        case 3:
                            System.out.println("Logged out.");
                            authenticated = false;
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                } else if (authenticatedUser.getRole().equals("manager")) {
                    // Manager menu
                    System.out.println("__________________________________________________");
                    System.out.println("Manager Menu");
                    System.out.println("1. View Leave Requests");
                    System.out.println("2. View Approved Leave Requests");
                    System.out.println("3. View Rejected Leave Requests");
                    System.out.println("4. Approve Leave Request");
                    System.out.println("5. Reject Leave Request");
                    System.out.println("6. Logout");
                    System.out.print("Enter your choice: ");
                    int mgrChoice = scanner.nextInt();
                    switch (mgrChoice) {
                        case 1:
                            // View Leave Requests
                            system.showManagerLeaveRequests();
                            break;
                        case 2:
                            // View Approved Leave Requests
                            system.showApprovedRequests();
                            break;
                        case 3:
                            // View Rejected Leave Requests
                            system.showRejectedRequests();
                            break;
                        case 4:
                            // Approve Leave Request
                            if (!system.getLeaveRequests().isEmpty()) {
                                System.out.print("Enter the ID of the leave request to approve: ");
                                int approveId = scanner.nextInt();
                                LeaveRequest approveRequest = system.findLeaveRequestById(approveId);
                                if (approveRequest != null) {
                                    system.approveLeaveRequest(approveRequest);
                                    System.out.println("Leave request approved successfully.");
                                    // Update approved and rejected requests after approval
                                    system.updateApprovedAndRejectedRequests();
                                } else {
                                    System.out.println("Leave request with ID " + approveId + " not found.");
                                }
                            } else {
                                System.out.println("No leave requests available to approve.");
                            }
                            break;
                        case 5:
                            // Reject Leave Request
                            if (!system.getLeaveRequests().isEmpty()) {
                                System.out.print("Enter the ID of the leave request to reject: ");
                                int rejectId = scanner.nextInt();
                                LeaveRequest rejectRequest = system.findLeaveRequestById(rejectId);
                                if (rejectRequest != null) {
                                    system.rejectLeaveRequest(rejectRequest);
                                    System.out.println("Leave request rejected successfully.");
                                    // Update approved and rejected requests after rejection
                                    system.updateApprovedAndRejectedRequests();
                                } else {
                                    System.out.println("Leave request with ID " + rejectId + " not found.");
                                }
                            } else {
                                System.out.println("No leave requests available to reject.");
                            }
                            break;


                        case 6:
                            System.out.println("Logged out.");
                            authenticated = false;
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                } else {
                    System.out.println("Invalid role. Please contact your administrator.");
                    authenticated = false;
                }
            }
        }
    }

    private static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
