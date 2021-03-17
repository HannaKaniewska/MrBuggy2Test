package businessLayer;

public class OtherTaskBL {
    public String projectPrefix;
    public String taskName;
    public String taskDescription;

    @Override
    public String toString() {
        return "OtherTask{" +
                "projectPrefix='" + projectPrefix + '\'' +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription +
                '}';
    }

}
