import java.util.Date;

class LeaveRequest {
    private static int nextId = 1; // Static counter for generating unique IDs
    private final int id; // Unique ID for the request
    private final String type;
    private final Date startDate;
    private final Date endDate;
    private final String comments;
    private boolean approved;
    private boolean rejected;
    private final User employee; // Reference to the employee who submitted the request

    public LeaveRequest(String type, Date startDate, Date endDate, String comments, User employee) {
        this.id = nextId++; // Assign a unique ID and increment the counter
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comments = comments;
        this.approved = false;
        this.rejected = false;
        this.employee = employee;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public boolean isApproved() {
        return approved;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public String getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getComments() {
        return comments;
    }

    public User getEmployee() {
        return employee;
    }
}