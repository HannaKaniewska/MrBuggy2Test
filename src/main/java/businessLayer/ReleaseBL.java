package businessLayer;

import java.util.List;

public class ReleaseBL {
    public String projectName;
    public String releaseName;
    public String releaseStartDate;
    public String releaseEndDate;
    public String releaseDescription;
    public List<PhaseBL> phases;

    @Override
    public String toString() {
        return "ProjectData{" +
                "projectName='" + projectName + '\'' +
                ", releaseName='" + releaseName + '\'' +
                ", releaseStartDate='" + releaseStartDate + '\'' +
                ", releaseEndDate='" + releaseEndDate + '\'' +
                ", releaseDescription='" + releaseDescription + '\'' +
                ", phases='" + phases.toString() +
                '}';
    }


}
