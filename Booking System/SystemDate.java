public class SystemDate extends Day{

    private static SystemDate instance=null;

    private SystemDate(String sDays){
        super(sDays);
    }

    public static SystemDate getInstance(){
        return instance;
    }
    public static void createTheInstance(String sDays){
        if(instance==null){
            instance =new SystemDate(sDays);
        } else{
            System.out.println("Cannot create one more system date instance.");
        }
    }
}
