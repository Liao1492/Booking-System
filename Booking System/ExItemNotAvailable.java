public class ExItemNotAvailable extends Exception{
    public ExItemNotAvailable(){
        super("Item already borrowed or on hold");
    }

    public ExItemNotAvailable(String message){
        super(message);
    }
}
