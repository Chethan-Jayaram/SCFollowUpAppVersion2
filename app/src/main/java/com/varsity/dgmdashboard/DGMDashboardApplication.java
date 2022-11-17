package com.varsity.dgmdashboard;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import com.varsity.dgmdashboard.data.PreferencesManager;
import com.varsity.dgmdashboard.utils.AppConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DGMDashboardApplication extends Application {

    private static DGMDashboardApplication mInstance;
    private static Retrofit retrofit = null;
    private static PreferencesManager preferencesManager;
    final String TAG = getClass().getSimpleName();

    public static synchronized DGMDashboardApplication getInstance() {
        return mInstance;
    }

    public static Retrofit getRetrofitClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    String accessToken = "Basic c2Nmb2xsb3d1cDpzZWNyZXQ=";
                    if (getPreferencesManager().getBooleanValue(AppConstant.IS_LOGIN)) {
                        accessToken = getPreferencesManager().getStringValue(AppConstant.TOKEN);
                    }
                    Request request = original.newBuilder()
                            .header("Authorization", accessToken)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
            okhttp3.OkHttpClient client = httpClient.addInterceptor(interceptor).build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.BASE_URL)
                    .build();
        }
        return retrofit;
    }

    public static PreferencesManager getPreferencesManager() {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager(getInstance().getApplicationContext());
        }
        return preferencesManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
