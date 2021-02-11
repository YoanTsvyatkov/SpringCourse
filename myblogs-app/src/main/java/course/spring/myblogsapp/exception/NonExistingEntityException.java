package course.spring.myblogsapp.exception;

public class NonExistingEntityException extends RuntimeException {
    public NonExistingEntityException() {
        super();
    }

    public NonExistingEntityException(String message) {
        super(message);
    }

    public NonExistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
