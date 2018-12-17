package eccard.adnd.culinary.network.api;

import java.util.List;

import eccard.adnd.culinary.network.model.Recip;
import io.reactivex.Single;

interface ApiHelper {

    Single<List<Recip>> doGetRecipeApiCall();

}
