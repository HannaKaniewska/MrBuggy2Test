package tests.testData.userData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

public abstract class UserDataProvider {

    private static final String fileName = "src/test/java/tests/testData/userData/LoginData.json";

    public static Object[][] getLoginData() throws FileNotFoundException {
        //read JSON file with login data
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<UserData>>(){}.getType();
        Collection<UserData> userData = gson.fromJson(new FileReader(fileName),collectionType);

        Object[][] dataArray = new Object[userData.size()][1];
        Iterator<UserData> iterator = userData.iterator();
        for (Object[] each : dataArray) {
            if (iterator.hasNext()) {
                each[0] = iterator.next();
            }
        }
        return dataArray;
    }

}
