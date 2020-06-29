import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestResults {

    private static TestResults single_instance = null;
    private String classesInfo;

    /**
     *
     * @param classNames
     * @throws ClassNotFoundException
     */
    private TestResults(String[] classNames) throws ClassNotFoundException {
        JSONObject jsonObjectRoot = new JSONObject();
        JSONArray jsonArrRoot = new JSONArray();
        jsonObjectRoot.put("testClasses", jsonArrRoot);

        //iterate over all classes
        for (String className : classNames) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArr = new JSONArray();
            Class c = Class.forName(className);
            Method methods[] = c.getDeclaredMethods();
            int testsCounter = 0;
            for (Method method : methods)
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(org.junit.Test.class)) {
                        jsonArr.put(method.getName());
                        testsCounter++;
                    }
                }
            jsonObject.put("testClassName", c.getName());
            jsonObject.put("totalTestMethods", testsCounter);
            jsonObject.put("methodNames", jsonArr);
            jsonArrRoot.put(jsonObject);
        }
        classesInfo = jsonObjectRoot.toString();

        //write test results to txt file
        try {
            FileWriter myWriter = new FileWriter(System.getProperty("java.io.tmpdir") + "//" + "testResults.txt");
            myWriter.write(classesInfo);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while trying to write to a file.");
        }
    }

    /**
     *
     * @param classNames
     * @return
     * @throws ClassNotFoundException
     */
    public static TestResults getInstance(String[] classNames) throws ClassNotFoundException {
        if (single_instance == null)
            single_instance = new TestResults(classNames);

        return single_instance;
    }

    public String getClassesInfo() {
        return classesInfo;
    }
}
