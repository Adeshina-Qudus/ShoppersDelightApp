package africa.semicolon.shoppersDelight.exception;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String format) {
        super(format);
    }
}
