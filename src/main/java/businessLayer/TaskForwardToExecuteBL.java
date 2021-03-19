package businessLayer;

public class TaskForwardToExecuteBL {

    public String releaseName;
    public String phaseName;
    public String environmentName;
    public String priority;
    public String dueDate;
    public String assigneeName;
    public String assignToMe;
    public String comment;

    @Override
    public String toString() {
        return "TaskForwardToExecuteBL{" +
                "releaseName='" + releaseName + '\'' +
                "phaseName='" + phaseName + '\'' +
                "environmentName='" + environmentName + '\'' +
                "priority='" + priority + '\'' +
                "dueDate='" + dueDate + '\'' +
                "assigneeName='" + assigneeName + '\'' +
                "assignToMe='" + assignToMe + '\'' +
                ", comment='" + comment +
                '}';
    }

}
