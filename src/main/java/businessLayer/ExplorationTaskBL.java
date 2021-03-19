package businessLayer;

import java.util.List;

public class ExplorationTaskBL {

    public String projectPrefix;
    public String taskName;
    public String taskDuration;
    public String testCard;
    public List<TaskForwardToExecuteBL> taskForwardToExecuteData;

    @Override
    public String toString() {
        return "ExplorationTask{" +
                "projectPrefix='" + projectPrefix + '\'' +
                "taskName='" + taskName + '\'' +
                "taskDuration='" + taskDuration + '\'' +
                ", testCard='" + testCard + '\'' +
                ", taskForwardToExecuteData='" + taskForwardToExecuteData +
                '}';
    }

}
