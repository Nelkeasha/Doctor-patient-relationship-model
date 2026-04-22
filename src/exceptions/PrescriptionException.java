package exceptions;

public class PrescriptionException extends RuntimeException {
    public PrescriptionException(String message) {
        super(message);
    }
}