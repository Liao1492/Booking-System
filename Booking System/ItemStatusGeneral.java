public abstract class ItemStatusGeneral implements ItemStatus{
    public abstract String getStatus();
    public abstract boolean getAvaibility();
    public Member getMember(){
        return null;
    }

    public Day getDay(){
        return null;
    }
}
