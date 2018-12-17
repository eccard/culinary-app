package eccard.adnd.culinary.network.share_prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import eccard.adnd.culinary.network.model.Recipt;

public class AppSharePref implements SharePrefes {

    Context context;
    SharedPreferences sharedPreferences;
    private final Gson gson;


    public AppSharePref(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    @Override
    public void saveReceipt(Recipt recipt) {

        String recipeJson = gson.toJson(recipt, Recipt.class);

        sharedPreferences.edit().putString(Recipt.class.getSimpleName(), recipeJson).apply();

    }

    @Override
    public Recipt loadReceipt() {

        try {

            String recipeJson =
                    sharedPreferences.getString(Recipt.class.getSimpleName(), "{}");

            return gson.fromJson(recipeJson, Recipt.class);

        } catch (Exception exception) {

            return null;
        }

    }
}
