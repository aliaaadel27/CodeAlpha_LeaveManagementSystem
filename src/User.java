import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    private final String username;
    private final String password;
    private final String role;
    private final Map<String, Integer> leaveBalances;
    private final List<LeaveRequest> leaveRequests;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.leaveBalances = new HashMap<>();
        this.leaveRequests = new ArrayList<>();
        // Initialize leave balances for different types of leave
        leaveBalances.put("Vacation", 20); // Example: 20 days of vacation leave per year
        leaveBalances.put("Sick", 10); // Example: 10 days of sick leave per year
    }


    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Map<String, Integer> getLeaveBalances() {
        return leaveBalances;
    }

    public List<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }

    public void addLeaveRequest(LeaveRequest request) {
        leaveRequests.add(request);
    }

    public void updateLeaveBalance(String type, int days) {
        int currentBalance = leaveBalances.getOrDefault(type, 0);
        leaveBalances.put(type, currentBalance + days);
    }
}