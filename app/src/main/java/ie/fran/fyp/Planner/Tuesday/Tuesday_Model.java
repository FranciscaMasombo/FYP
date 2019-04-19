package ie.fran.fyp.Planner.Tuesday;

class Tuesday_Model {


    private String Ttitlemon;
    private String Tdesc;
    private String Tloc;
    private String Tdate;
    private String Ttime;

    public Tuesday_Model(String Ttime, String Tdesc, String Tloc, String Tdate, String Ttitlemon) {
        this.Ttime = Ttime;
        this.Tdesc = Tdesc;
        this.Tloc = Tloc;
        this.Tdate = Tdate;
        this.Ttitlemon = Ttitlemon;
    }
    public Tuesday_Model() {
    }

    public String getTtime() {
        return Ttime;
    }

    public void setTtime(String ttime) {
        this.Ttime = ttime;
    }

    public String getTdate() {
        return Tdate;
    }

    public void setTdate(String tdate) {
        this.Tdate = tdate;
    }


    public String getTloc() {
        return Tloc;
    }

    public void setTloc(String tloc) {
        this.Tloc = tloc;
    }


    public String getTtitlemon() {
        return Ttitlemon;
    }

    public void setTtitlemon(String ttitlemon) {
        this.Ttitlemon = ttitlemon;
    }

    public String getTdesc() {
        return Tdesc;
    }

    public void setTdesc(String tdesc) {
        this.Tdesc = tdesc;
    }
}
