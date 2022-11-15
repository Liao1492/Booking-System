public class CmdStartNewDay extends RecordedCommand{

    public String sDay1,sDay2;
    @Override
    public void execute(String[] cmdParts) {
        try {
        SystemDate sd=SystemDate.getInstance();
        if(cmdParts.length<2){
                throw new ExInsufficientArguments("Insufficient command arguments.");
            }
        sDay1=sd.toString();
        sDay2=cmdParts[1];
        sd.setDay(sDay2);
        addUndoCommand(this);
        Club.getInstance().checkDueDate();
        clearRedoList();

        System.out.println("Done.");
        }catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        SystemDate d= SystemDate.getInstance();
        d.setDay(sDay1);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        SystemDate d= SystemDate.getInstance();
        d.setDay(sDay2);
        addUndoCommand(this);
        
    }
    
}