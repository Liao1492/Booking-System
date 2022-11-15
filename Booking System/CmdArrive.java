public class CmdArrive extends RecordedCommand{

    Item i;
    @Override
    public void execute(String[] cmdParts) {
        try {
            if(cmdParts.length<3){
                throw new ExInsufficientArguments("Insufficient command arguments.");
            }
            i=new Item(cmdParts[1],cmdParts[2]);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } 
        catch (ExInsufficientArguments|ExItemIdAlreadyExist e) {
            System.out.println(e.getMessage());       
        }
    }

    @Override
    public void undoMe() {
        Club.getInstance().removeItem(i);
        addRedoCommand(this);
        
    }

    @Override
    public void redoMe() {
        Club.getInstance().addItem(i);
        addUndoCommand(this);
    }

}
