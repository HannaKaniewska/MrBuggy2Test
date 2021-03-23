package businessLayer;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TaskForwardToExecuteBL {

    public String releaseName;
    public String phaseName;
    public String environmentName;
    public String priority;
    private String dueDate;
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

    public String getDueDate() {
        if (dueDate.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH, 1);
            return(formatter.format(now.getTime()));
        } else {
            return dueDate;
        }
    }

}
