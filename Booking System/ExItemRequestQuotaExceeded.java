public class ExItemRequestQuotaExceeded extends Exception{
    public ExItemRequestQuotaExceeded(){
        super("Quota exceeded!");
    }

    public ExItemRequestQuotaExceeded(String message){
        super(message);
    }
}
