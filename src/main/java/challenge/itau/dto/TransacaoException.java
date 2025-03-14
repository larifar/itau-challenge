package challenge.itau.dto;

public class TransacaoException extends RuntimeException{
    private String message;
    private Throwable cause;

    public TransacaoException(String message) {
        super(message);
        this.message = message;
    }

    public TransacaoException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public TransacaoException() {
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
