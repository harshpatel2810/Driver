package com.classicphoto.rpmfordriver.utils;

import android.content.Context;
import android.util.Log;

import com.classicphoto.rpmfordriver.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by pc on 17-04-2017.
 */

public class Soap {

    private static final String BASE_URL = AppConfig.URL+"api/";

    private static String getAbsiluteURL(String postFixUrl) {
        return BASE_URL + postFixUrl;
    }

    private static String getConnectionErrorResponse(Context context)
            throws JSONException {
        JSONObject objRes = new JSONObject();
        objRes.put("status", "401");
        objRes.put("message", context.getString(R.string.err_network));
        return objRes.toString();
    }

    private static String postSoapResponse(Context context, String postFixUrl, RequestBody formBody)
            throws IOException, JSONException {
        if (Utils.connectionAvailable(context)) {
            String url = getAbsiluteURL(postFixUrl);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            Log.e("res", res);
            return res;
        } else {
            return getConnectionErrorResponse(context);
        }
    }

    public static ParsedResponse selectinsertcategory(Context context, String branchcode, String lrno, String image)
            throws IOException, JSONException {
        ParsedResponse p = new ParsedResponse();
        if (Utils.connectionAvailable(context)) {
            RequestBody formBody = new FormBody.Builder()
                    .add("BranchCode", branchcode)
                    .add("LrNo", lrno)
                    .add("ImgFileBase64", image)
                    .build();
            String res = postSoapResponse(context, "ApprovedByAuditor.ashx", formBody);
            p.error = false;
            p.o = res;
        } else {
            p.error = true;
            p.o = context.getString(R.string.err_network);
        }
        return p;
    }

}
