public class ExInsufficientArguments extends Exception{
    public ExInsufficientArguments(){
        super("Not enough arguments!");
    }

    public ExInsufficientArguments(String message){
        super(message);
    }
}
