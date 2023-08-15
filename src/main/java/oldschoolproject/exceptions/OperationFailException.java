package oldschoolproject.exceptions;

public class OperationFailException extends Exception {

    public OperationFailException(String msg) {
        super("§cError: " + msg);
    }
}
