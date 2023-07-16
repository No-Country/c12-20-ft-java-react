package c1220ftjavareact.gym.training.model;

public class AuxTimes {
    private String[] session;

    public AuxTimes(String timeStart, String timeEnd) {
        this.session = new String[2];
        session[0] = timeStart;
        session[1] = timeEnd;
    }

    public String[] getSession() {
        return session;
    }

    @Override
    public String toString() {
        return "[" + session[0] + "," +  session[1] + "]";
    }
}
