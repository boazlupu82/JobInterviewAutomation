import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestClass {

    private static JSONArray usersArrayJson;

    @BeforeClass
    public static void setup() {
        usersArrayJson = Utils.SendHttpRequestAndGetResponse();
    }

    @Test()
    public void verifyNumberOfUsersISMoreThan40() {
        Assert.assertTrue(Utils.isIsGETResponseOK());
        Assert.assertTrue(usersArrayJson.length() > 40);

    }

    @Test()
    public void fieldsValidation() throws IOException {
        for (int i = 0; i < usersArrayJson.length(); i++) {
            JSONObject jsonObject = usersArrayJson.getJSONObject(i);

            String id = jsonObject.get("id").toString();
            String dayOfBirth = jsonObject.get("dayOfBirth").toString();
            String email = jsonObject.get("email").toString();

            Utils.regexValidation(Utils.Validation_type.ID, id);
            Utils.regexValidation(Utils.Validation_type.DATE, dayOfBirth);
            Utils.regexValidation(Utils.Validation_type.EMAIL, email);
        }
        System.out.println("# of users : " + usersArrayJson.length());
    }

    @Test()
    public void addNewUser() throws IOException {
        List<Child> childrens = new ArrayList<Child>();
        Child child = new Child("child1", 10);
        childrens.add(child);
        User user = new User("test1", "2020-06-21", "test1@gmail.com", childrens);
        Gson gson = new Gson();
        String userJsonStr = gson.toJson(user);
        int statusCode = Utils.postUser(userJsonStr);
        Assert.assertEquals(statusCode, 200);
    }

    @AfterClass()
    public static void getClassInfo() throws ClassNotFoundException {
        String[] classNames = {"TestClass"};
        String classInfo = TestResults.getInstance(classNames).getClassesInfo();
        System.out.println(classInfo);
    }
}