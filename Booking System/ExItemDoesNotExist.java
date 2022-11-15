public class ExItemDoesNotExist extends Exception{
    public ExItemDoesNotExist(){
        super("Item does not exist!");
    }
    public ExItemDoesNotExist(String message){
        super(message);
    }
}
