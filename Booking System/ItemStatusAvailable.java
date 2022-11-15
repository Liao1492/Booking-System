public class ItemStatusAvailable extends ItemStatusGeneral{
    private String availability;
    public ItemStatusAvailable(){
        availability="Available";
    }
    @Override
    public boolean getAvaibility() {
        return true;
    }
    @Override
    public String getStatus() {
        return availability;
    }
}
