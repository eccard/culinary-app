package eccard.adnd.culinary.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Ingredient;
import eccard.adnd.culinary.network.model.Recipt;
import eccard.adnd.culinary.network.share_prefs.AppSharePref;

public class IngredientRemoteViewServiceFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    AppSharePref appSharePref;


    private Recipt recipt;
    private List<Ingredient> ingredients;

    public IngredientRemoteViewServiceFactory(Context context) {
        this.context = context;
        appSharePref = new AppSharePref(context);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        recipt = appSharePref.loadReceipt();
        ingredients = recipt.getIngredients();


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION || ingredients == null ||
                ingredients.isEmpty()) {
            return null;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wigdet_recipe_ingredient_item);

        Ingredient ingredient = ingredients.get(position);

        String title = ingredient.getIngredient();

        String quantityAndMeasure = String.format(context.getString(R.string.ingredient_quantity_and_measure),
                ingredient.getQuantity(), ingredient.getMeasure());

        views.setTextViewText(R.id.textView_ingredient_title, title);
        views.setTextViewText(R.id.textView_ingredient_quantity_and_measure, quantityAndMeasure);

        Intent fillIntent = new Intent();
        fillIntent.putExtra(Recipt.class.getSimpleName(), recipt);
        views.setOnClickFillInIntent(R.id.textView_ingredient_title, fillIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
