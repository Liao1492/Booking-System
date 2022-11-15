public class ExItemNotBorrowedByThisUser extends Exception{

    public ExItemNotBorrowedByThisUser(){
        super("Item not borrowed by this user!");
    }

    public ExItemNotBorrowedByThisUser(String message){
        super(message);
    }

}
