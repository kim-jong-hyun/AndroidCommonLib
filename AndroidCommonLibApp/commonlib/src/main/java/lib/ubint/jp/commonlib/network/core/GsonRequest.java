package lib.ubint.jp.commonlib.network.core;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by kimjonghyun on 16/06/23.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson mGson = new Gson();
    private final Object mObject;
    private final Class<T> mClass;
    private final Type mType;

    private final Map<String, String> mHeaders;
    private final Response.Listener<T> mListener;

    private static final String CHARSET_NAME = "utf-8";

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param method
     * @param url       URL of the request to make
     * @param _class    Relevant class object, for Gson's reflection
     * @param headers   Map of request mHeaders
     */
    public GsonRequest(int method, String url, Object object, Class<T> _class, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mObject = object;
        mClass = _class;
        mType = null;
        mHeaders = headers;
        mListener = listener;
    }

    /**
     *
     * @param url
     * @param object
     * @param _class
     * @param listener
     * @param errorListener
     */
    public GsonRequest(String url, Object object, Class<T> _class,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mObject = object;
        mClass = _class;
        mType = null;
        mHeaders = null;
        mListener = listener;
    }

    /**
     *
     * @param url
     * @param object
     * @param type
     * @param listener
     * @param errorListener
     */
    public GsonRequest(String url, Object object, Type type,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mObject = object;
        mClass = null;
        mType = type;
        mHeaders = null;
        mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }
    
    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mObject == null) {
            return super.getBody();
        }

        String jsonString = mGson.toJson(mObject);
        try {
            return jsonString.getBytes(CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonString, CHARSET_NAME);
            return null;
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            if (mClass != null && mClass instanceof Class) {
                return Response.success(
                        mGson.fromJson(json, mClass),
                        HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return (Response<T>) Response.success(
                        mGson.fromJson(json, mType),
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
