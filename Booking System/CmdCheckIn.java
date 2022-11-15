public class CmdCheckIn extends RecordedCommand{

    String memberId;
    String itemId;
    Member onHold;
    @Override
    public void execute(String[] cmdParts) {
        
        try {
            if(cmdParts.length<3){
                throw new ExInsufficientArguments("Insufficient command arguments.");
            }
            memberId=cmdParts[1];
            itemId=cmdParts[2];
            onHold=Club.getInstance().checkInItem(memberId, itemId);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExInsufficientArguments|ExMemberDoesNotExist | ExItemDoesNotExist |ExItemNotBorrowedByThisUser e) {
            System.out.println(e.getMessage());
        }
        
    }

    @Override
    public void undoMe() {
        Club instance = Club.getInstance();
        try {
            Item i= instance.searchItem(itemId);
            i.makeItemAvailable();
            instance.checkOutItem(memberId, itemId); // Safe data, Won't catch any exception
            if(onHold!=null){
                System.out.println(String.format("Sorry. %s %s please ignore the pick up notice for %s %s.", onHold.getId(),onHold.getName(),i.getId(),i.getName()));
                instance.requestItem(onHold.getId(),itemId,0);
            }

        } catch (ExItemDoesNotExist | ExMemberDoesNotExist | ExItemNotAvailable | ExExceededNumberOfItems | ExItemRequestQuotaExceeded e) {
            e.printStackTrace();
        }
        addRedoCommand(this);
        
    }

    @Override
    public void redoMe() {
        Club instance=Club.getInstance();
        try { 
            onHold=instance.checkInItem(memberId, itemId); // Safe data, Won't catch any exception
        } catch (ExMemberDoesNotExist | ExItemDoesNotExist |ExItemNotBorrowedByThisUser e) {
            e.printStackTrace();
        }
        addUndoCommand(this);
    }

}
