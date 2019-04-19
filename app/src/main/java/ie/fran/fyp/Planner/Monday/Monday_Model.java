package ie.fran.fyp.Planner.Monday;

public class Monday_Model {



    String titlemon;
    String desc;
    String loc;
    String date;
    String time;

    public Monday_Model(String time,String desc,String loc,String date,String titlemon) {
        this.time = time;
        this.desc = desc;
        this.loc = loc;
        this.date = date;
        this.titlemon = titlemon;
    }
    public Monday_Model() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }


    public String getTitlemon() {
        return titlemon;
    }

    public void setTitlemon(String titlemon) {
        this.titlemon = titlemon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
