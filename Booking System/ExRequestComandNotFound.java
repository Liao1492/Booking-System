public class ExRequestComandNotFound extends Exception{
    public ExRequestComandNotFound(){
        super("Request not found!");
    }

    public ExRequestComandNotFound(String message){
        super(message);
    }
}
