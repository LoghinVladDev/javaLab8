package ro.uaic.info.exception;

public class ControllerException extends Exception{
    public ControllerException(String message){
        super("Controller exception. Reason : " + message);
    }
}
