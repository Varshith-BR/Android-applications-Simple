package com.example.lab6;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Xml;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    TextView tvXML, tvJSON;
    Button btnParseXML, btnParseJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvXML = findViewById(R.id.tvXML);
        tvJSON = findViewById(R.id.tvJSON);
        btnParseXML = findViewById(R.id.btnParseXML);
        btnParseJSON = findViewById(R.id.btnParseJSON);

        btnParseXML.setOnClickListener(v -> parseXML());
        btnParseJSON.setOnClickListener(v -> parseJSON());
    }

    private void parseXML() {
        try {
            Resources res = getResources();
            InputStream is = res.openRawResource(R.raw.weather); // weather.xml must be in res/raw
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            StringBuilder xmlData = new StringBuilder();
            String tagName = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                } else if (eventType == XmlPullParser.TEXT) {
                    xmlData.append(tagName).append(": ").append(parser.getText()).append("\n");
                }
                eventType = parser.next();
            }
            tvXML.setText(xmlData.toString());
        } catch (Exception e) {
            tvXML.setText("Error parsing XML");
            e.printStackTrace();
        }
    }

    private void parseJSON() {
        try {
            InputStream is = getAssets().open("weather.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);

            StringBuilder jsonData = new StringBuilder();
            jsonData.append("City: ").append(jsonObject.getString("city")).append("\n")
                    .append("Latitude: ").append(jsonObject.getDouble("latitude")).append("\n")
                    .append("Longitude: ").append(jsonObject.getDouble("longitude")).append("\n")
                    .append("Temperature: ").append(jsonObject.getInt("temperature")).append("\n")
                    .append("Humidity: ").append(jsonObject.getInt("humidity")).append("\n");

            tvJSON.setText(jsonData.toString());
        } catch (Exception e) {
            tvJSON.setText("Error parsing JSON");
            e.printStackTrace();
        }
    }
}
