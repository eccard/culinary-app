package eccard.adnd.culinary.network.api;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import eccard.adnd.culinary.BuildConfig;
import eccard.adnd.culinary.network.model.Recip;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApiHelper implements ApiHelper{


    private static MoviesApi moviesApi;


    private static AppApiHelper instance;


    private AppApiHelper(){}

    public static AppApiHelper getInstance() {

        if ( instance == null){


            HttpLoggingInterceptor loggingInterceptor =
                    new HttpLoggingInterceptor();

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


            OkHttpClient okhttp = new OkHttpClient.Builder()
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .addInterceptor(loggingInterceptor)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okhttp)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();


            moviesApi = retrofit.create(MoviesApi.class);

            instance = new AppApiHelper();
        }

        return instance;
    }

    @Override
    public Single<List<Recip>> doGetRecipeApiCall() {
        return moviesApi.doGetPopularMovies();
    }
}
