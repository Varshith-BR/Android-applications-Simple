package com.example.lab6;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    TextView xmlData, jsonData;
    Button btnParserXML, btnParserJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xmlData = findViewById(R.id.xmlData);
        jsonData = findViewById(R.id.jsonData);
        btnParserXML = findViewById(R.id.btnParserXML);
        btnParserJSON = findViewById(R.id.btnParserJSON);

        btnParserXML.setOnClickListener(v -> parseXML());
        btnParserJSON.setOnClickListener(v -> parseJSON());
    }

    @SuppressLint("SetTextI18n")
    private void parseXML() {
        try (InputStream is = getResources().openRawResource(R.raw.city_data_xml)) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(is);
            StringBuilder result = new StringBuilder();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    result.append(node.getNodeName())
                            .append(": ")
                            .append(node.getTextContent().trim())
                            .append("\n");
                }
            }
            xmlData.setText(result.toString());
        } catch (Exception e) {
            xmlData.setText("Error parsing XML");
        }
    }

    @SuppressLint("SetTextI18n")
    private void parseJSON() {
        try (InputStream is = getResources().openRawResource(R.raw.city_data_json)) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            JSONObject obj = new JSONObject(new String(buffer, StandardCharsets.UTF_8));
            String op = "name: " + obj.getString("name") + "\n" +
                    "latitude: " + obj.getDouble("latitude") + "\n" +
                    "longitude: " + obj.getDouble("longitude") + "\n" +
                    "temperature: " + obj.getDouble("temperature") + "\n" +
                    "humidity: " + obj.getInt("humidity") + "\n";
            jsonData.setText(op);
        } catch (Exception e) {
            jsonData.setText("Error parsing JSON");
        }
    }
}