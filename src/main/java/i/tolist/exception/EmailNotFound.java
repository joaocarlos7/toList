package i.tolist.exception;


public class EmailNotFound extends RuntimeException {
    public EmailNotFound(String email) {
        super("Incorrect credentials" + email);
    }
}
