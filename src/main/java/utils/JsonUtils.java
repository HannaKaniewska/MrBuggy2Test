package utils;

import businessLayer.LoginBL;
import businessLayer.ProjectBL;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public abstract class JsonUtils {

    public static Object[][] getLoginData() throws FileNotFoundException {
        //read JSON file with login data
        JsonElement jsonData = new JsonParser().parse(new FileReader("src/test/resources/data/LoginData.json"));
        JsonElement dataSet = jsonData.getAsJsonObject().get("dataSet");
        List<LoginBL> testData = new Gson().fromJson(dataSet, new TypeToken<List<LoginBL>>() {}
            .getType());
        Object[][] loginData = new Object[testData.size()][1];
        int index = 0;
        for (Object[] each : loginData) {
            each[0] = testData.get(index++);
        }
        return loginData;
    }

    public static Object[][] getProjectData() throws FileNotFoundException {
        //read JSON file with project data
        JsonElement jsonData = new JsonParser().parse(new FileReader("src/test/resources/data/ProjectData.json"));
        JsonElement dataSet = jsonData.getAsJsonObject().get("dataSet");
        List<ProjectBL> testData = new Gson().fromJson(dataSet, new TypeToken<List<ProjectBL>>() {}
                .getType());
        Object[][] projectData = new Object[testData.size()][1];
        int index = 0;
        for (Object[] each : projectData) {
            each[0] = testData.get(index++);
        }
        return projectData;
    }
}
