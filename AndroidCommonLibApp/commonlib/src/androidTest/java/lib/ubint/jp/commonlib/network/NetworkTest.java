package lib.ubint.jp.commonlib.network;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import lib.ubint.jp.commonlib.network.core.GsonRequest;
import lib.ubint.jp.commonlib.network.core.UBNRequestQueue;
import lib.ubint.jp.commonlib.network.data.RequestData;
import lib.ubint.jp.commonlib.network.data.ResponseData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kimjonghyun on 16/06/28.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class NetworkTest {

    private static final String TAG = NetworkTest.class.getSimpleName();

    @Test
    public void testApi() {

        final String url = "http://192.168.11.15/ubint_pos/web/app_dev.php/top/test.json";
//        final String requestJson = "{ 'email' : 'yul@naver.com' }";

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {

                RequestData requestData = new RequestData();
                requestData.email = "yul@naver.com";

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

                UBNRequestQueue.getInstance(InstrumentationRegistry.getContext()).addToRequestQueue(request);

            }
        });

    }
}



