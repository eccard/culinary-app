package eccard.adnd.culinary.network.api;

import java.util.List;

import eccard.adnd.culinary.network.model.Recipt;
import io.reactivex.Single;

interface ApiHelper {

    Single<List<Recipt>> doGetRecipeApiCall();

}
