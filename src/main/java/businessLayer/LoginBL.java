package businessLayer;

import java.util.List;

public class LoginBL {
    public String testCase;
    public String email;
    public String password;
    public List<String> errorMessageList;

    @Override
    public String toString() {
        return "TestData{" +
                "testCase='" + testCase + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", errorMessageList='" + errorMessageList +
                '}';
    }
}
