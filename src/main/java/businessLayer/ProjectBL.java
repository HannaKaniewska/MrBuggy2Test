package businessLayer;

import java.util.List;

public class ProjectBL {

    public String projectName;
    public String projectPrefix;
    public List<ProjectUserBL> projectUsers;

    @Override
    public String toString() {
        return "ProjectData{" +
                "projectName='" + projectName + '\'' +
                ", projectPrefix='" + projectPrefix + '\'' +
                ", projectUsers='" + projectUsers.toString() +
                '}';
    }

}
