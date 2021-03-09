package businessLayer;

public class PhaseBL {
    public String phaseName;
    public String phaseStartDate;
    public String phaseEndDate;
    public String phaseDescription;

    @Override
    public String toString() {
        return "phases{" +
                "phaseName='" + phaseName + '\'' +
                ", phaseStartDate='" + phaseStartDate + '\'' +
                ", phaseEndDate='" + phaseEndDate + '\'' +
                ", phaseDescription='" + phaseDescription +
                '}';
    }

}
