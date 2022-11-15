public class ExItemIsAvailable extends Exception{
    public ExItemIsAvailable(){
        super("Item is available!");
    }

    public ExItemIsAvailable(String message){
        super(message);
    }
}
