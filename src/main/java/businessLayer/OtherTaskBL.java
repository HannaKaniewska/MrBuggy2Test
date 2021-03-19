package businessLayer;

import java.util.List;

public class OtherTaskBL {
    public String projectPrefix;
    public String taskName;
    public String taskDescription;
    public List<TaskForwardToExecuteBL> taskForwardToExecuteData;

    @Override
    public String toString() {
        return "OtherTask{" +
                "projectPrefix='" + projectPrefix + '\'' +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskForwardToExecuteData='" + taskForwardToExecuteData +
                '}';
    }

}
