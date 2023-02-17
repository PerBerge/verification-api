package payroll;

public enum ServiceStatus {

    SUCCESS(0, "Success");

    private final int id;
    private final String message;

    //Enum constructor
    ServiceStatus(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}