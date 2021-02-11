package course.spring.restmvc.exception;

import java.util.List;

public class InvalidEntityException extends RuntimeException {
    private List<String> violations;

    public InvalidEntityException() {
    }

    public InvalidEntityException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }

    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityException(Throwable cause) {
        super(cause);
    }
}
