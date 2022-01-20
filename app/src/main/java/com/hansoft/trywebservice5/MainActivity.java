package com.hansoft.trywebservice5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button querybutton,insertbutton,updatebutton,deletebutton,signInsertButton,signUpdateButton,
    atlInsertButton,atlUpdateButton,failInsertButton,failUpdateButton,pickupInsertButton,pickupUpdateButton,
            instructionButton,feedbackButton;
    private static final String nameSpace = "https://www.hansoft.com.au/querydata";
    private static final String url = "https://www.hansoft.com.au/querydata.asmx";

    private static final String querymethod = "QueryDeliveryData";
    private static final String insertmethod = "InsertOnboardTime";
    private static final String updatemethod = "UpdateDeliveryData";
    private static final String deletemethod = "DeleteDeliveryData";
    private static final String insertsignmethod = "InsertDeliverySignData";
    private static final String updatesignmethod = "UpdateDeliverySignData";
    private static final String insertatlmethod = "InsertDeliveryAtlData";
    private static final String updateatlmethod = "UpdateDeliveryAtlData";
    private static final String insertfailmethod = "InsertDeliveryFailData";
    private static final String updatefailmethod = "UpdateDeliveryFailData";
    private static final String querypickupmethod = "QueryPickupData";
    private static final String insertpickupmethod = "InsertPickupData";
    private static final String updatepickupmethod = "UpdatePickupData";

    private static final String querysoapAction = "https://www.hansoft.com.au/querydata/QueryDeliveryData";
    private static final String insertsoapAction = "https://www.hansoft.com.au/querydata/InsertOnboardTime";
    private static final String updatesoapAction = "https://www.hansoft.com.au/querydata/UpdateDeliveryData";
    private static final String deletesoapAction = "https://www.hansoft.com.au/querydata/DeleteDeliveryData";
    private static final String insertsignsoapAction = "https://www.hansoft.com.au/querydata/InsertDeliverySignData";
    private static final String updatesignsoapAction = "https://www.hansoft.com.au/querydata/UpdateDeliverySignData";
    private static final String insertatlsoapAction = "https://www.hansoft.com.au/querydata/InsertDeliveryAtlData";
    private static final String updateatlsoapAction = "https://www.hansoft.com.au/querydata/UpdateDeliveryAtlData";
    private static final String insertfailsoapAction = "https://www.hansoft.com.au/querydata/InsertDeliveryFailData";
    private static final String updatefailsoapAction = "https://www.hansoft.com.au/querydata/UpdateDeliveryFailData";
    private static final String querypickupsoapAction = "https://www.hansoft.com.au/querydata/QueryPickupData";
    private static final String insertpickupsoapAction = "https://www.hansoft.com.au/querydata/InsertPickupData";
    private static final String updatepickupsoapAction = "https://www.hansoft.com.au/querydata/UpdatePickupData";
    private static final String updatefeedbackmethod = "UpdateFeedbackData";
    private static final String updatefeedbacksoapAction = "https://www.hansoft.com.au/querydata/UpdateFeedbackData";

    private String result;
    private TextView txt_result;
    private EditText connoteEditText,runEditText,driverEditText,quantityEditText;
    private static final int REQUEST_PERMISSION = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults[0] == PackageManager.PERMISSION_DENIED) || (grantResults[1] == PackageManager.PERMISSION_DENIED)) {

                Toast.makeText(this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void bindViews() {


        querybutton = (Button) findViewById(R.id.querybutton);
        insertbutton = (Button) findViewById(R.id.insertbutton);
        updatebutton = (Button) findViewById(R.id.updatebutton);
        deletebutton = (Button) findViewById(R.id.deletebutton);
        signInsertButton = (Button) findViewById(R.id.signInsertButton);
        signUpdateButton = (Button) findViewById(R.id.signUpdateButton);
        atlInsertButton = (Button) findViewById(R.id.atlInsertButton);
        atlUpdateButton = (Button) findViewById(R.id.atlUpdateButton);
        failInsertButton = (Button) findViewById(R.id.failInsertButton);
        failUpdateButton = (Button) findViewById(R.id.failUpdateButton);
        pickupInsertButton = (Button) findViewById(R.id.pickupInsertButton);
        pickupUpdateButton = (Button) findViewById(R.id.pickupUpdateButton);
        instructionButton = (Button) findViewById(R.id.instructionButton);
        feedbackButton = (Button) findViewById(R.id.feedbackButton);

        txt_result = (TextView) findViewById(R.id.resulttextview);
        connoteEditText = (EditText) findViewById(R.id.connoteEditText);
        runEditText = (EditText) findViewById(R.id.runEditText);
        driverEditText = (EditText) findViewById(R.id.driverEditText);
        quantityEditText = (EditText) findViewById(R.id.quantityEditText);
        querybutton.setOnClickListener(this);
        insertbutton.setOnClickListener(this);
        updatebutton.setOnClickListener(this);
        deletebutton.setOnClickListener(this);
        signInsertButton.setOnClickListener(this);
        signUpdateButton.setOnClickListener(this);
        atlInsertButton.setOnClickListener(this);
        atlUpdateButton.setOnClickListener(this);
        failInsertButton.setOnClickListener(this);
        failUpdateButton.setOnClickListener(this);
        pickupInsertButton.setOnClickListener(this);
        pickupUpdateButton.setOnClickListener(this);
        instructionButton.setOnClickListener(this);
        feedbackButton.setOnClickListener(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    Toast.makeText(MainActivity.this, "get query web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    Toast.makeText(MainActivity.this, "get insert web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0x003:
                    Toast.makeText(MainActivity.this, "get update web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0x004:
                    Toast.makeText(MainActivity.this, "get delete web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0x005:
                    Toast.makeText(MainActivity.this, "get upload web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0x006:
                    Toast.makeText(MainActivity.this, "get query instruction web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0x007:
                    Toast.makeText(MainActivity.this, "get query instruction web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.querybutton:
                new Thread() {
                    @Override
                    public void run() {
                        getQueryWebservice();
                    }
                }.start();
                break;
            case R.id.insertbutton:
                new Thread() {
                    @Override
                    public void run() {

                            if (getQueryWebservice() == 0) {
                                getInsertOnboardTimeWebservice();
                            }

                    }
                }.start();
                break;
            case R.id.updatebutton:
                new Thread() {
                    @Override
                    public void run() {
                        if  (getQueryWebservice() > 0) {
                            getUpdateDeliveryDataWebservice();
                        }
                    }
                }.start();
                break;
            case R.id.deletebutton:
                new Thread() {
                    @Override
                    public void run() {
                        getDeleteWebservice();
                    }
                }.start();
                break;
            case R.id.signInsertButton:
                new Thread() {
                    @Override
                    public void run() {
                        if (getQueryWebservice() == 0) {
                            getInsertSignDataWebservice();
                        }

                    }
                }.start();
                break;
            case R.id.signUpdateButton:
                new Thread() {
                    @Override
                    public void run() {
                        if (getQueryWebservice() > 0) {
                            getUpdateSignDataWebservice();
                        }

                    }
                }.start();
                break;
            case R.id.atlInsertButton:
                new Thread() {
                    @Override
                    public void run() {
                        if (getQueryWebservice() == 0) {
                            getInsertAtlDataWebservice();
                        }

                    }
                }.start();
                break;
            case R.id.atlUpdateButton:
                new Thread() {
                    @Override
                    public void run() {
                        if (getQueryWebservice() > 0) {
                            getUpdateAtlDataWebservice();
                        }

                    }
                }.start();
                break;
            case R.id.failInsertButton:
                new Thread() {
                    @Override
                    public void run() {
                        if (getQueryWebservice() == 0) {
                            getInsertFailDataWebservice();
                        }

                    }
                }.start();
                break;
            case R.id.failUpdateButton:
                new Thread() {
                    @Override
                    public void run() {
                        if (getQueryWebservice() > 0) {
                            getUpdateFailDataWebservice();
                        }

                    }
                }.start();
                break;
            case R.id.pickupInsertButton:
                new Thread() {
                    @Override
                    public void run() {
                        if (getQueryPickupWebservice() == 0) {
                            getInsertPickupDataWebservice();
                        }

                    }
                }.start();
                break;
            case R.id.pickupUpdateButton:
                new Thread() {
                    @Override
                    public void run() {
                        if (getQueryPickupWebservice() > 0) {
                            getUpdatePickupDataWebservice();
                        }

                    }
                }.start();
                break;
            case R.id.instructionButton:
                new Thread() {
                    @Override
                    public void run() {
                        getQueryInstructionWebservice();
                    }
                }.start();
                break;
            case R.id.feedbackButton:
                new Thread() {
                    @Override
                    public void run() {

                        getUpdateFeedbackDataWebService();

                    }
                }.start();
                break;
            default:
                break;
        }
    }

    public int getQueryPickupWebservice()
    {
        result = "";
        int count = 0;
        SoapObject soapObject = new SoapObject(nameSpace, querypickupmethod);
        soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
        soapObject.addProperty("count", count);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getquerywebservice: prepare start query web service");
        try {

            httpTransportSE.call(querypickupsoapAction, envelope);
            Log.d("aaa", "getquerywebservice: invoke query Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getquerywebservice: invoke query Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getquerywebservice: receive query web service response");
        result = object.getProperty(0).toString();
        if (result.indexOf("successfully") > 0)
        {
            count = Integer.parseInt(object.getProperty(1).toString());
            handler.sendEmptyMessage(0x001);
        }

        txt_result.setText(result);
        Log.d("aaa", "getquerywebservice: finish query web service, count = " + count);
        return count;
    }


    public int getQueryWebservice() {
        result = "";
        int count = 0;
        SoapObject soapObject = new SoapObject(nameSpace, querymethod);
        soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
        soapObject.addProperty("count", count);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getquerywebservice: prepare start query web service");
        try {

            httpTransportSE.call(querysoapAction, envelope);
            Log.d("aaa", "getquerywebservice: invoke query Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getquerywebservice: invoke query Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getquerywebservice: receive query web service response");
        result = object.getProperty(0).toString();
        if (result.indexOf("successfully") > 0)
        {
            count = Integer.parseInt(object.getProperty(1).toString());
            handler.sendEmptyMessage(0x001);
        }

        txt_result.setText(result);
        Log.d("aaa", "getquerywebservice: finish query web service, count = " + count);
        return count;
    }


    public void getInsertOnboardTimeWebservice() {
        result = "";

        try {
        SoapObject soapObject = new SoapObject(nameSpace, insertmethod);
        soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String today = simpleDateFormat.format(new Date().getTime());


        soapObject.addProperty("OnboardDateTime", today);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getinsertwebservice: prepare start insert web service");

        httpTransportSE.call(insertsoapAction, envelope);
        Log.d("aaa", "getinsertwebservice: invoke insert Web Service successfully");


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getinsertwebservice: receive insert web service response");
        result = object.getProperty(0).toString();
        if (result.indexOf("successfully") > 0)
        {
            handler.sendEmptyMessage(0x002);
        }
        txt_result.setText(result);
        Log.d("aaa", "getinsertwebservice: finish insert web service");
        } catch (Exception e) {

            Log.d("aaa", "getinsertwebservice: invoke insert Web Service failed " + e.getLocalizedMessage());
        }

    }

    public void getUpdateDeliveryDataWebservice() {
        result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String today = simpleDateFormat.format(new Date().getTime());

        SoapObject soapObject = new SoapObject(nameSpace, updatemethod);
        soapObject.addProperty("RunNumber", runEditText.getText().toString());
        soapObject.addProperty("DriverName", driverEditText.getText().toString());
        soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
        soapObject.addProperty("Quantity", quantityEditText.getText().toString());
        soapObject.addProperty("OnboardDateTime", today);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getupdatewebservice: prepare start update web service");
        try {

            httpTransportSE.call(updatesoapAction, envelope);
            Log.d("aaa", "getupdatewebservice: invoke update Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getupdatewebservice: invoke update Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getupdatewebservice: receive update web service response");

        result = object.getProperty(0).toString();
        if (result.indexOf("successfully") > 0)
        {
            handler.sendEmptyMessage(0x003);
        }
        txt_result.setText(result);
        Log.d("aaa", "getupdatewebservice: finish update web service");
    }


    public void getDeleteWebservice() {
        result = "";
        SoapObject soapObject = new SoapObject(nameSpace, deletemethod);
        soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getdeletewebservice: prepare start delete web service");
        try {

            httpTransportSE.call(deletesoapAction, envelope);
            Log.d("aaa", "getdeletewebservice: invoke delete Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getdeletewebservice: invoke delete Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;
        result = object.getProperty(0).toString();
        if (result.indexOf("successfully") > 0)
        {
            handler.sendEmptyMessage(0x004);
        }
        txt_result.setText(result);



        Log.d("aaa", "getdeletewebservice: finish delete web service");
    }


    public void getInsertSignDataWebservice() {
        result = "";

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date().getTime());

            SoapObject soapObject = new SoapObject(nameSpace, insertsignmethod);
            soapObject.addProperty("RunNumber", runEditText.getText().toString());
            soapObject.addProperty("DriverName", driverEditText.getText().toString());
            soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
            soapObject.addProperty("Quantity", quantityEditText.getText().toString());
            soapObject.addProperty("CustomerName", "tom");
            soapObject.addProperty("CustomerSignature", "uploadimage/911.png");
            soapObject.addProperty("DeliveryDateTime", today);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.bodyOut = soapObject;

            envelope.dotNet = true;

            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(url);

            Log.d("aaa", "getinsertsignwebservice: prepare start insert sign web service");

            httpTransportSE.call(insertsignsoapAction, envelope);
            Log.d("aaa", "getinsertsignwebservice: invoke insert sign Web Service successfully");


            SoapObject object = (SoapObject) envelope.bodyIn;

            Log.d("aaa", "getinsertsignwebservice: receive insert sign web service response");
            result = object.getProperty(0).toString();
            if (result.indexOf("successfully") > 0)
            {
                handler.sendEmptyMessage(0x002);
            }
            txt_result.setText(result);
            Log.d("aaa", "getinsertsignwebservice: finish insert sign web service");
        } catch (Exception e) {

            Log.d("aaa", "getinsertsignwebservice: invoke insert sign Web Service failed " + e.getLocalizedMessage());
        }

    }

    public void getUpdateSignDataWebservice()
    {
        result = "";

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date().getTime());

            SoapObject soapObject = new SoapObject(nameSpace, updatesignmethod);
            soapObject.addProperty("RunNumber", runEditText.getText().toString());
            soapObject.addProperty("DriverName", driverEditText.getText().toString());
            soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
            soapObject.addProperty("Quantity", quantityEditText.getText().toString());
            soapObject.addProperty("CustomerName", "mary");
            soapObject.addProperty("CustomerSignature", "uploadimage/654311.png");
            soapObject.addProperty("DeliveryDateTime", today);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.bodyOut = soapObject;

            envelope.dotNet = true;

            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(url);

            Log.d("aaa", "getupdatesignwebservice: prepare start update sign web service");

            httpTransportSE.call(updatesignsoapAction, envelope);
            Log.d("aaa", "getupdatesignwebservice: invoke update sign Web Service successfully");


            SoapObject object = (SoapObject) envelope.bodyIn;

            Log.d("aaa", "getupdatesignwebservice: receive update sign web service response");
            result = object.getProperty(0).toString();
            if (result.indexOf("successfully") > 0)
            {
                handler.sendEmptyMessage(0x003);
            }
            txt_result.setText(result);
            Log.d("aaa", "getupdatesignwebservice: finish update sign web service");
        } catch (Exception e) {

            Log.d("aaa", "getupdatesignwebservice: invoke update sign Web Service failed " + e.getLocalizedMessage());
        }

    }


    public void getInsertAtlDataWebservice() {
        result = "";

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date().getTime());

            SoapObject soapObject = new SoapObject(nameSpace, insertatlmethod);
            soapObject.addProperty("RunNumber", runEditText.getText().toString());
            soapObject.addProperty("DriverName", driverEditText.getText().toString());
            soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
            soapObject.addProperty("Quantity", quantityEditText.getText().toString());
            soapObject.addProperty("ATLPlace", "leave at front door");
            soapObject.addProperty("CustomerSignature", "uploadimage/]C100093312591062668795ATL.png");
            soapObject.addProperty("DeliveryDateTime", today);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.bodyOut = soapObject;

            envelope.dotNet = true;

            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(url);

            Log.d("aaa", "getinsertatlwebservice: prepare start insert atl web service");

            httpTransportSE.call(insertatlsoapAction, envelope);
            Log.d("aaa", "getinsertatlwebservice: invoke insert atl Web Service successfully");


            SoapObject object = (SoapObject) envelope.bodyIn;

            Log.d("aaa", "getinsertatlwebservice: receive insert atl web service response");
            result = object.getProperty(0).toString();
            if (result.indexOf("successfully") > 0)
            {
                handler.sendEmptyMessage(0x002);
            }
            txt_result.setText(result);
            Log.d("aaa", "getinsertatlwebservice: finish insert atl web service");
        } catch (Exception e) {

            Log.d("aaa", "getinsertatlwebservice: invoke insert atl Web Service failed " + e.getLocalizedMessage());
        }

    }

    public void getUpdateAtlDataWebservice() {
        result = "";

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date().getTime());

            SoapObject soapObject = new SoapObject(nameSpace, updateatlmethod);
            soapObject.addProperty("RunNumber", runEditText.getText().toString());
            soapObject.addProperty("DriverName", driverEditText.getText().toString());
            soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
            soapObject.addProperty("Quantity", quantityEditText.getText().toString());
            soapObject.addProperty("ATLPlace", "leave at back door");
            soapObject.addProperty("CustomerSignature", "uploadimage/]C100093312591062668795ATL.png");
            soapObject.addProperty("DeliveryDateTime", today);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.bodyOut = soapObject;

            envelope.dotNet = true;

            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(url);

            Log.d("aaa", "getupdateatlwebservice: prepare start update atl web service");

            httpTransportSE.call(updateatlsoapAction, envelope);
            Log.d("aaa", "getupdateatlwebservice: invoke update atl Web Service successfully");


            SoapObject object = (SoapObject) envelope.bodyIn;

            Log.d("aaa", "getupdateatlwebservice: receive update atl web service response");
            result = object.getProperty(0).toString();
            if (result.indexOf("successfully") > 0)
            {
                handler.sendEmptyMessage(0x003);
            }
            txt_result.setText(result);
            Log.d("aaa", "getupdateatlwebservice: finish update atl web service");
        } catch (Exception e) {

            Log.d("aaa", "getupdateatlwebservice: invoke update atl Web Service failed " + e.getLocalizedMessage());
        }

    }


    public void getInsertPickupDataWebservice()
    {
        result = "";

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date().getTime());

            SoapObject soapObject = new SoapObject(nameSpace, insertpickupmethod);
            soapObject.addProperty("RunNumber", runEditText.getText().toString());
            soapObject.addProperty("DriverName", driverEditText.getText().toString());
            soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
            soapObject.addProperty("Quantity", quantityEditText.getText().toString());
            soapObject.addProperty("CustomerName", "bob");
            soapObject.addProperty("CustomerSignature", "uploadimage/goodmanPickupSignature.png");
            soapObject.addProperty("ATLPlace", "leave at front door");
            soapObject.addProperty("FailedReason", "pickup not ready");
            soapObject.addProperty("PickupDateTime", today);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.bodyOut = soapObject;

            envelope.dotNet = true;

            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(url);

            Log.d("aaa", "getinsertpickupwebservice: prepare start insert pickup web service");

            httpTransportSE.call(insertpickupsoapAction, envelope);
            Log.d("aaa", "getinsertpickupwebservice: invoke insert pickup Web Service successfully");


            SoapObject object = (SoapObject) envelope.bodyIn;

            Log.d("aaa", "getinsertpickupwebservice: receive insert pickup web service response");
            result = object.getProperty(0).toString();
            if (result.indexOf("successfully") > 0)
            {
                handler.sendEmptyMessage(0x002);
            }
            txt_result.setText(result);
            Log.d("aaa", "getinsertpickupwebservice: finish insert pickup web service");
        } catch (Exception e) {

            Log.d("aaa", "getinsertpickupwebservice: invoke insert pickup Web Service failed " + e.getLocalizedMessage());
        }

    }

    public void getInsertFailDataWebservice()
    {
        result = "";

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date().getTime());

            SoapObject soapObject = new SoapObject(nameSpace, insertfailmethod);
            soapObject.addProperty("RunNumber", runEditText.getText().toString());
            soapObject.addProperty("DriverName", driverEditText.getText().toString());
            soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
            soapObject.addProperty("Quantity", quantityEditText.getText().toString());
            soapObject.addProperty("FailedReason", "not home");
            soapObject.addProperty("DeliveryDateTime", today);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.bodyOut = soapObject;

            envelope.dotNet = true;

            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(url);

            Log.d("aaa", "getinsertfailwebservice: prepare start insert fail web service");

            httpTransportSE.call(insertfailsoapAction, envelope);
            Log.d("aaa", "getinsertfailwebservice: invoke insert fail Web Service successfully");


            SoapObject object = (SoapObject) envelope.bodyIn;

            Log.d("aaa", "getinsertfailwebservice: receive insert fail web service response");
            result = object.getProperty(0).toString();
            if (result.indexOf("successfully") > 0)
            {
                handler.sendEmptyMessage(0x002);
            }
            txt_result.setText(result);
            Log.d("aaa", "getinsertfailwebservice: finish insert fail web service");
        } catch (Exception e) {

            Log.d("aaa", "getinsertfailwebservice: invoke insert fail Web Service failed " + e.getLocalizedMessage());
        }

    }

    public void getUpdatePickupDataWebservice()
    {
        result = "";

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date().getTime());

            SoapObject soapObject = new SoapObject(nameSpace, updatepickupmethod);
            soapObject.addProperty("RunNumber", runEditText.getText().toString());
            soapObject.addProperty("DriverName", driverEditText.getText().toString());
            soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
            soapObject.addProperty("Quantity", quantityEditText.getText().toString());
            soapObject.addProperty("CustomerName", "bob");
            soapObject.addProperty("CustomerSignature", "uploadimage/goodmanPickupSignature.png");
            soapObject.addProperty("ATLPlace", "leave at front door");
            soapObject.addProperty("FailedReason", "pickup not ready");
            soapObject.addProperty("PickupDateTime", today);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.bodyOut = soapObject;

            envelope.dotNet = true;

            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(url);

            Log.d("aaa", "getinsertpickupwebservice: prepare start insert pickup web service");

            httpTransportSE.call(updatepickupsoapAction, envelope);
            Log.d("aaa", "getinsertpickupwebservice: invoke insert pickup Web Service successfully");


            SoapObject object = (SoapObject) envelope.bodyIn;

            Log.d("aaa", "getinsertpickupwebservice: receive insert pickup web service response");
            result = object.getProperty(0).toString();
            if (result.indexOf("successfully") > 0)
            {
                handler.sendEmptyMessage(0x002);
            }
            txt_result.setText(result);
            Log.d("aaa", "getinsertpickupwebservice: finish insert pickup web service");
        } catch (Exception e) {

            Log.d("aaa", "getinsertpickupwebservice: invoke insert pickup Web Service failed " + e.getLocalizedMessage());
        }

    }

    public void getUpdateFailDataWebservice()
    {
        result = "";

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date().getTime());

            SoapObject soapObject = new SoapObject(nameSpace, updatefailmethod);
            soapObject.addProperty("RunNumber", runEditText.getText().toString());
            soapObject.addProperty("DriverName", driverEditText.getText().toString());
            soapObject.addProperty("ConnoteNumber", connoteEditText.getText().toString());
            soapObject.addProperty("Quantity", quantityEditText.getText().toString());
            soapObject.addProperty("FailedReason", "wrong address");
            soapObject.addProperty("DeliveryDateTime", today);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.bodyOut = soapObject;

            envelope.dotNet = true;

            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(url);

            Log.d("aaa", "getupdatefailwebservice: prepare start update fail web service");

            httpTransportSE.call(updatefailsoapAction, envelope);
            Log.d("aaa", "getupdatefailwebservice: invoke update fail Web Service successfully");


            SoapObject object = (SoapObject) envelope.bodyIn;

            Log.d("aaa", "getupdatefailwebservice: receive update fail web service response");
            result = object.getProperty(0).toString();
            if (result.indexOf("successfully") > 0)
            {
                handler.sendEmptyMessage(0x003);
            }
            txt_result.setText(result);
            Log.d("aaa", "getupdatefailwebservice: finish update fail web service");
        } catch (Exception e) {

            Log.d("aaa", "getupdatefailwebservice: invoke update fail Web Service failed " + e.getLocalizedMessage());
        }

    }

    public void getQueryInstructionWebservice() {
        result = "";
        String barcode = "20080213";
        String instruction = "";
        String instructiondate = "";


        SoapObject soapObject = new SoapObject(nameSpace, querymethod);
        soapObject.addProperty("ConnoteNumber", barcode);
        soapObject.addProperty("Instruction", instruction);
        soapObject.addProperty("InstructionDate", instructiondate);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getqueryinstructionwebservice: prepare start query web service");
        try {

            httpTransportSE.call(querysoapAction, envelope);
            Log.d("aaa", "getqueryinstructionwebservice: invoke query Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getqueryinstructionwebservice: invoke query Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getqueryinstructionwebservice: receive query web service response");
        result = object.getProperty(0).toString();
        if (result.indexOf("successfully") > 0)
        {
            handler.sendEmptyMessage(0x006);
        }

    }

    public void getUpdateFeedbackDataWebService()
    {
        String feedbackresult = "";
        String feedback = "ok";
        String barcode = "20080213";
        SoapObject soapObject = new SoapObject(nameSpace, updatefeedbackmethod);
        soapObject.addProperty("ConnoteNumber", barcode);
        soapObject.addProperty("Feedback", feedback);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getupdatefeedbackwebservice: prepare start update web service");
        try {

            httpTransportSE.call(updatefeedbacksoapAction, envelope);
            Log.d("aaa", "getupdatefeedbackwebservice: invoke update Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getupdatefeedbackwebservice: invoke update Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getupdatefeedbackwebservice: receive update web service response");
        feedbackresult = object.getProperty(0).toString();
        if (feedbackresult.indexOf("successfully") > 0)
        {
            //  Toast.makeText(DeliveryActivity.this, "Submit feedback successfully", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessage(0x007);
        }



    }


}