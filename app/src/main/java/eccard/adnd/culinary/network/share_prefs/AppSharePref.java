package eccard.adnd.culinary.network.share_prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import eccard.adnd.culinary.network.model.Recip;

public class AppSharePref implements SharePrefes {

    Context context;
    final SharedPreferences sharedPreferences;
    private final Gson gson;


    public AppSharePref(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    @Override
    public void saveReceipt(Recip recip) {

        String recipeJson = gson.toJson(recip, Recip.class);

        sharedPreferences.edit().putString(Recip.class.getSimpleName(), recipeJson).apply();

    }

    @Override
    public Recip loadReceipt() {

        try {

            String recipeJson =
                    sharedPreferences.getString(Recip.class.getSimpleName(), "{}");

            return gson.fromJson(recipeJson, Recip.class);

        } catch (Exception exception) {

            return null;
        }

    }
}
