public class ExItemIdAlreadyExist extends Exception{
    public ExItemIdAlreadyExist(){
        super("Item Id already exist!");
    }

    public ExItemIdAlreadyExist(String message){
        super(message);
    }

}
