package eccard.adnd.culinary.network.api;

import java.util.List;

import eccard.adnd.culinary.network.model.Recip;
import io.reactivex.Single;
import retrofit2.http.GET;

interface  MoviesApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Single<List<Recip>> doGetPopularMovies();

}

