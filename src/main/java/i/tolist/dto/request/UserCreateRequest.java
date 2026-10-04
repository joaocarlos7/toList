package i.tolist.dto.request;

import java.util.UUID;

public record UserCreateRequest (
        UUID id,
        String name,
        String email,
        String password,
        boolean active
) {

}
