import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

public class Utils {

    private static boolean isGETResponseOK = false;
    private static JSONArray jsonArray = null;
    private static Properties prop = new Properties();

    public static JSONArray SendHttpRequestAndGetResponse() {
        try {

            InputStream in = Utils.class.getResourceAsStream("config.properties");
            prop.load(in);

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(prop.getProperty("getUsersEndpoint"));

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JSONObject usersJsonObject = new JSONObject(jsonResponse);
            jsonArray = usersJsonObject.getJSONArray("users");
            if (response.getStatusLine().getStatusCode() == 200)
                isGETResponseOK = true;

        } catch (Exception e) {
            System.out.println("An error occurred while sending http request");
        }

        return jsonArray;
    }

    /**
     *
     * @param userJsonStr
     * @return
     * @throws IOException
     */
    public static int postUser(String userJsonStr) throws IOException {

        int statusCode = 0;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost(prop.getProperty("postUsersEndpoint"));
            StringEntity params = new StringEntity(userJsonStr);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception ex) {
            System.out.println("An error occurred while trying to post new user");
        } finally {
            httpClient.close();
        }
        return statusCode;
    }

    /**
     * @param type
     * @param value
     * @return
     */
    public static boolean regexValidation(Validation_type type, String value) {

        String regex = null;
        switch (type) {
            case ID:
                regex = "\\d+";
                break;
            case DATE:
                regex = "([12]\\d{3}-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[12]\\d|3[01]))";
                break;
            case EMAIL:
                regex = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
                break;
        }

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }

    public static boolean isIsGETResponseOK() {
        return isGETResponseOK;
    }

    enum Validation_type {
        ID,
        DATE,
        EMAIL
    }
}
