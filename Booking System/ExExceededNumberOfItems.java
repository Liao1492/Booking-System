public class ExExceededNumberOfItems extends Exception{

    public ExExceededNumberOfItems(){
        super("Exceeded number of loaned items!");
    }
    
    public ExExceededNumberOfItems(String message) {
        super(message);
    }

}
