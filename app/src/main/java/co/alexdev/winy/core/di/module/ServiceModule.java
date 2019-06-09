package co.alexdev.winy.core.di.module;

import javax.inject.Singleton;

import co.alexdev.winy.BuildConfig;
import co.alexdev.winy.core.api.DishResponseService;
import co.alexdev.winy.core.api.WineResponseService;
import co.alexdev.winy.core.di.SingletoneScope;
import co.alexdev.winy.core.util.livedata.LiveDataCallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ServiceModule {

    @Provides
    @SingletoneScope
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @SingletoneScope
    public WineResponseService createWineResponseService(Retrofit retrofit) {
        return retrofit.create(WineResponseService.class);
    }

    @Provides
    @SingletoneScope
    public DishResponseService createDishResponseService(Retrofit retrofit) {
        return retrofit.create(DishResponseService.class);
    }
}
