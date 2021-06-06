package tests.testData;

import businessLayer.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public abstract class UserDataProvider {

    public static Object[][] getLoginData() throws FileNotFoundException {
        //TODO: uprościć czytanie danych z pliku
        //read JSON file with login data
        JsonElement jsonData = new JsonParser().parse(new FileReader("src/test/java/tests/testData/LoginData.json"));
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

}
