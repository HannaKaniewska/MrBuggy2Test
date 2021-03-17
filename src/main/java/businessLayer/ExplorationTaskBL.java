package businessLayer;

public class ExplorationTaskBL {

    public String projectPrefix;
    public String taskName;
    public String taskDuration;
    public String testCard;

    @Override
    public String toString() {
        return "ExplorationTask{" +
                "projectPrefix='" + projectPrefix + '\'' +
                "taskName='" + taskName + '\'' +
                "taskDuration='" + taskDuration + '\'' +
                ", testCard='" + testCard +
                '}';
    }

}
