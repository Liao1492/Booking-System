public class CmdCancelRequest extends RecordedCommand{

    String memberId;
    String itemId;
    int pos;

    @Override
    public void execute(String[] cmdParts) {
        
        try {
            if(cmdParts.length<3){
                throw new ExInsufficientArguments("Insufficient command arguments.");
            }
            memberId=cmdParts[1];
            itemId=cmdParts[2];     
            pos=Club.getInstance().cancelRequest(memberId, itemId);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExInsufficientArguments|ExRequestComandNotFound | ExMemberDoesNotExist | ExItemDoesNotExist e) {
            System.out.println(e.getMessage());
        }
        
            
    }

    @Override
    public void undoMe() {
        Club instance=Club.getInstance();
        try {
            instance.requestItem(memberId, itemId,pos);// Safe Data,no exception will be found!
        } catch (ExMemberDoesNotExist | ExItemDoesNotExist | ExItemRequestQuotaExceeded e) {
            e.printStackTrace();
        } 
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Club instance=Club.getInstance();
        try {
            pos=instance.cancelRequest(memberId, itemId); // Safe data,no exception will arise.
        } catch (ExRequestComandNotFound | ExMemberDoesNotExist | ExItemDoesNotExist e) {
            e.printStackTrace();
        } 
        addUndoCommand(this);
    }

}
