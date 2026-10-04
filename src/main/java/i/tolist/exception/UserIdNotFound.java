package i.tolist.exception;

import java.util.UUID;

public class UserIdNotFound extends RuntimeException {
    public UserIdNotFound(UUID userId) {
        super("Incorrect credentials" + userId);
    }
}
