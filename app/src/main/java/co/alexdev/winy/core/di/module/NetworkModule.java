package co.alexdev.winy.core.di.module;

import javax.inject.Singleton;

import co.alexdev.winy.BuildConfig;
import co.alexdev.winy.core.di.SingletoneScope;
import co.alexdev.winy.core.util.Constants;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class NetworkModule {

    @Provides
    @SingletoneScope
    public HttpLoggingInterceptor interceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @SingletoneScope
    public Interceptor requestInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();

            return chain.proceed(new Request.Builder().url(originalRequest.url())
                    .addHeader(Constants.NETWORK.HEADER, BuildConfig.X_RAPID_API_HOST_HEADER)
                    .addHeader(Constants.NETWORK.X_RAPID_API_KEY, BuildConfig.API_KEY)
                    .build());

        };
    }

    @Provides
    @SingletoneScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Interceptor requestInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestInterceptor)
                .build();
    }
}
