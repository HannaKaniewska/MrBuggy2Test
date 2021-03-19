package businessLayer;

import java.util.List;

public class TestCaseTaskBL {
    public String projectPrefix;
    public String taskName;
    public String taskDescription;
    public String taskPresuppositions;
    public String taskResult;
    public List<TaskForwardToExecuteBL> taskForwardToExecuteData;

    @Override
    public String toString() {
        return "TestCaseTask{" +
                "projectPrefix='" + projectPrefix + '\'' +
                "taskName='" + taskName + '\'' +
                "taskDescription='" + taskDescription + '\'' +
                "taskPresuppositions='" + taskPresuppositions + '\'' +
                ", taskResult='" + taskResult + '\'' +
                ", taskForwardToExecuteData='" + taskForwardToExecuteData +
                '}';
    }

}
