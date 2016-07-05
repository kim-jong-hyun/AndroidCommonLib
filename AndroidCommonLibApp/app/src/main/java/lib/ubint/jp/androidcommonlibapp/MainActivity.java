package lib.ubint.jp.androidcommonlibapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import lib.ubint.jp.androidcommonlibapp.data.RequestData;
import lib.ubint.jp.androidcommonlibapp.data.ResponseData;
import lib.ubint.jp.commonlib.network.core.GsonRequest;
import lib.ubint.jp.commonlib.network.core.UBNRequestQueue;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String url = "http://192.168.11.15/ubint_pos/web/app_dev.php/top/test.json";

        RequestData requestData = new RequestData();
        requestData.email = "yul3@naver.com";// "{ 'email' : 'yul3@naver.com' }"

        GsonRequest<List<ResponseData>> request = new GsonRequest<List<ResponseData>>(url, requestData, new TypeToken<List<ResponseData>>(){}.getType(),
                new Response.Listener<List<ResponseData>>() {
                    @Override
                    public void onResponse(List<ResponseData> response) {
                        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
                        Log.d(TAG, "response size: " + response.size());

                        for (ResponseData data : response) {
                            Log.d(TAG, "id: " + data.id);
                            Log.d(TAG, "name: " + data.name);
                            Log.d(TAG, "email: " + data.email);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
                        Log.e(TAG, "error: " + error);


                    }
                });


        UBNRequestQueue.getInstance(this).addToRequestQueue(request);


    }
}






