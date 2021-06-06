package tests.testData.userData;

import java.util.List;

public class UserData {
    private String testCase;
    private String email;
    private String password;
    private List<String> errorMessageList;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }

    public void setErrorMessageList(List<String> errorMessageList) {
        this.errorMessageList = errorMessageList;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "testCase='" + testCase + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", errorMessageList='" + errorMessageList +
                '}';
    }

}
