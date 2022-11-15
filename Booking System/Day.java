public class Day implements Cloneable{
    private int day;
    private int month;
    private int year;
    private static final String MonthNames="JanFebMarAprMayJunJulAugSepOctNovDec";

    public void setDay(String sDays){
        String[] sDaysPart = sDays.split("-");
        this.year = Integer.parseInt(sDaysPart[2]);
        this.month=MonthNames.indexOf(sDaysPart[1])/3+1;
        this.day=Integer.parseInt(sDaysPart[0]);
    }

    public Day(String sDays){
        setDay(sDays);
    }

    static public boolean isLeapYear(int y) {
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}

    static public boolean valid(int y, int m, int d) {
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

    private int getDayNumber(){
        switch(this.month){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return 31; 
			case 4: case 6: case 9: case 11:
					 return 30; 
			case 2:
					 if (isLeapYear(this.year))
						 return 29; 
					 else
						 return 28; 
		}
        return 0;
    }

    public void addDays(int n){
       int d=getDayNumber();
       if(d-(this.day+n)<0){
           d=-(d-(this.day+n));
           this.day=d;
           if(valid(this.year, this.month+1,this.day)){
               month=month+1;
           } else {
               month=1;
               year=year+1;
           }
       } else {
           day=day+n;
       }
    }

    public int compareDays(Day d){
        if(this.year>d.year){
            return 0;
        } else if(this.month>d.month){
            return 0;
        }else if(this.day>d.day){
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String toString(){
        return day+"-"+MonthNames.substring((month-1)*3,month*3)+"-"+year;
    }

    @Override
    public Day clone(){
        Day copy=null;
        try {
            copy=(Day)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;

    }
}