package businessLayer;

import java.util.List;

public class ReleaseBL {
    public String projectPrefix;
    public String releaseName;
    public String releaseStartDate;
    public String releaseEndDate;
    public String releaseDescription;
    public List<PhaseBL> phases;

    @Override
    public String toString() {
        return "ProjectData{" +
                "projectPrefix='" + projectPrefix + '\'' +
                ", releaseName='" + releaseName + '\'' +
                ", releaseStartDate='" + releaseStartDate + '\'' +
                ", releaseEndDate='" + releaseEndDate + '\'' +
                ", releaseDescription='" + releaseDescription + '\'' +
                ", phases='" + phases.toString() +
                '}';
    }


}
