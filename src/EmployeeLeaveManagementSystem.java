import java.util.*;

class EmployeeLeaveManagementSystem {
    private final Map<String, User> users;
    private final List<LeaveRequest> leaveRequests;
    private final List<LeaveRequest> approvedRequests;
    private final List<LeaveRequest> rejectedRequests;

    public EmployeeLeaveManagementSystem() {
        users = new HashMap<>();
        leaveRequests = new ArrayList<>();
        approvedRequests = new ArrayList<>();
        rejectedRequests = new ArrayList<>();
    }

    public void registerUser(String username, String password, String role) {
        if (role.equalsIgnoreCase("employee") || role.equalsIgnoreCase("manager")) {
            User user = new User(username, password, role);
            users.put(username, user);
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Invalid role. Role must be either 'employee' or 'manager'.");
        }
    }

    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public List<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }

    public void approveLeaveRequest(LeaveRequest request) {
        if (request != null) {
            request.setApproved(true);
        }
    }

    public void rejectLeaveRequest(LeaveRequest request) {
        if (request != null) {
            request.setRejected(true);
        }
    }

    // Modify the submitLeaveRequest method to display available leave balance
    public void submitLeaveRequest(User user, String type, Date startDate, Date endDate, String comments) {
        int daysRequested = calculateDays(startDate, endDate);

        System.out.println("Requested Leave: " + daysRequested + " days");

        // Proceed with leave request submission...
        LeaveRequest leaveRequest = new LeaveRequest(type, startDate, endDate, comments, user);
        leaveRequests.add(leaveRequest);
        user.addLeaveRequest(leaveRequest); // Add leave request to user's list
        user.updateLeaveBalance(type, -daysRequested); // Deduct leave balance
    }

    // Add a method to show employee their request list along with status
    public void showEmployeeRequests(User user) {
        System.out.println("Your Leave Requests:");
        List<LeaveRequest> userLeaveRequests = user.getLeaveRequests();
        if (userLeaveRequests.isEmpty()) {
            System.out.println("No leave requests.");
        } else {
            for (LeaveRequest request : userLeaveRequests) {
                String status = request.isApproved() ? "Approved" : (request.isRejected() ? "Rejected" : "In Progress");
                System.out.println("Type: " + request.getType() + ", Start Date: " +
                        request.getStartDate() + ", End Date: " + request.getEndDate() +
                        ", Comments: " + request.getComments() + ", Status: " + status);
            }
        }
    }

    // Calculate number of days between two dates
    public int calculateDays(Date startDate, Date endDate) {
        long differenceInMillis = endDate.getTime() - startDate.getTime();
        return (int) (differenceInMillis / (1000 * 60 * 60 * 24)) + 1;
    }

    // Add a method to show manager the leave requests with employee names
    public void showManagerLeaveRequests() {
        if (leaveRequests.isEmpty()) {
            System.out.println("No leave requests available.");
        } else {
            System.out.println("Leave Requests:");
            for (LeaveRequest request : leaveRequests) {
                System.out.println("Employee: " + request.getEmployee().getUsername() + ", ID: " + request.getId() + ", Type: " + request.getType() + ", Start Date: " +
                        request.getStartDate() + ", End Date: " + request.getEndDate() +
                        ", Comments: " + request.getComments());
            }
        }
    }


    public void showApprovedRequests() {
        if (approvedRequests.isEmpty()) {
            System.out.println("No approved leave requests.");
        } else {
            System.out.println("Approved Leave Requests:");
            for (LeaveRequest request : approvedRequests) {
                System.out.println("Employee: " + request.getEmployee().getUsername() + ", ID: " + request.getId() + ", Type: " + request.getType() + ", Start Date: " +
                        request.getStartDate() + ", End Date: " + request.getEndDate() +
                        ", Comments: " + request.getComments());
            }
        }
    }

    // Method to show rejected leave requests for the manager
    public void showRejectedRequests() {
        if (rejectedRequests.isEmpty()) {
            System.out.println("No rejected leave requests.");
        } else {
            System.out.println("Rejected Leave Requests:");
            for (LeaveRequest request : rejectedRequests) {
                System.out.println("Employee: " + request.getEmployee().getUsername() + ", ID: " + request.getId() + ", Type: " + request.getType() + ", Start Date: " +
                        request.getStartDate() + ", End Date: " + request.getEndDate() +
                        ", Comments: " + request.getComments());
            }
        }
    }

    // Method to filter and move approved and rejected requests from the main list
    public void updateApprovedAndRejectedRequests() {
        for (Iterator<LeaveRequest> iterator = leaveRequests.iterator(); iterator.hasNext(); ) {
            LeaveRequest request = iterator.next();
            if (request.isApproved()) {
                approvedRequests.add(request);
                iterator.remove();
            } else if (request.isRejected()) {
                rejectedRequests.add(request);
                iterator.remove();
            }
        }
    }

    public LeaveRequest findLeaveRequestById(int id) {
        for (LeaveRequest request : leaveRequests) {
            if (request.getId() == id) {
                return request;
            }
        }
        return null;

    }
}