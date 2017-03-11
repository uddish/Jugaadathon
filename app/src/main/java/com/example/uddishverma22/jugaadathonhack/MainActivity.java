package com.example.uddishverma22.jugaadathonhack;

import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import fr.arnaudguyon.xmltojsonlib.JsonToXml;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class MainActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener{

    private TextView resultTextView;
    private QRCodeReaderView mydecoderview;
    public static final String TAG = "MainActivity";
    String dataString;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) findViewById(R.id.tv1);
        mydecoderview.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        mydecoderview.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        mydecoderview.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        mydecoderview.setTorchEnabled(true);

        // Use this function to set front camera preview
        mydecoderview.setFrontCamera();

        // Use this function to set back camera preview
        mydecoderview.setBackCamera();
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        resultTextView.setText(text);
        Log.d(TAG, "onQRCodeRead: " + convertXmlToJson(text));
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra("data", dataString);
        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.stopCamera();
    }

    public String convertXmlToJson(String xml) {
        XmlToJson xmlToJson = new XmlToJson.Builder(xml)
                .setAttributeName("/PrintLetterBarcodeData/uid", "UID")
                .build();
        jsonObject = xmlToJson.toJson();
        return xmlToJson.toString();
    }
}
