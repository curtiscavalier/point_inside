import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class point_inside {

    public static void main(String[] argvs) throws IOException {

        if (argvs.length < 2) {
            System.out.println("please give me 2 variables");
        } else {

            HttpGet getRequest = new HttpGet("http://api.timezonedb.com/v2.1/get-time-zone?key=UBI34OV4J149&format=json&by=position&lat="+argvs[0]+ "&lng="+argvs[1]);

            //Set the API media type in http accept header
            getRequest.addHeader("accept", "application/xml");
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(getRequest);

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }
            HttpEntity httpEntity = response.getEntity();
            String apiOutput = EntityUtils.toString(httpEntity);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(apiOutput);

            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone(node.get("abbreviation").asText()));
            System.out.println(df.format(date));
        }
    }
}
