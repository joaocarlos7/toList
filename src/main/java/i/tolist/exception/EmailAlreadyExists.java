package i.tolist.exception;

public class EmailAlreadyExists extends RuntimeException {
    public EmailAlreadyExists(String message) {
        super("email already exists: " + message);
    }
}
