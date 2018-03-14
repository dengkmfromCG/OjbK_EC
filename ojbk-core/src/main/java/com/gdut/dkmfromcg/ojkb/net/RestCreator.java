package com.gdut.dkmfromcg.ojkb.net;

import com.gdut.dkmfromcg.ojkb.app.ConfigType;
import com.gdut.dkmfromcg.ojkb.app.DKM;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by dkmFromCG on 2018/3/12.
 * function:
 */

public final class RestCreator {

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    private static final class ParamsHolder{
        private static final WeakHashMap<String,Object> PARAMS=new WeakHashMap<>();
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE
                =RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL= (DKM.getConfiguration(ConfigType.API_HOST));
        private static final Retrofit RETROFIT_CLIENT=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    /**
     * 构建OkHttp
     */
    private static final class OkHttpHolder{
        private static final int TIME_OUT=60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = DKM.getConfiguration(ConfigType.INTERCEPTOR);

        private static final OkHttpClient OK_HTTP_CLIENT=addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
    }

}
