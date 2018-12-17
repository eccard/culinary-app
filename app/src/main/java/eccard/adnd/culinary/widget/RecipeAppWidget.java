package eccard.adnd.culinary.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Recip;
import eccard.adnd.culinary.network.share_prefs.AppSharePref;
import eccard.adnd.culinary.ui.recipdetail.RecipDetailActivity;

public class RecipeAppWidget extends AppWidgetProvider {

    AppSharePref appSharePref;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds);

        appSharePref = new AppSharePref(context);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    private void updateAppWidget(Context context,
                                 AppWidgetManager appWidgetManager,
                                 int appWidgetId){

        RemoteViews views = getIngredientsListRemoteView(context);

        Recip recip = appSharePref.loadReceipt();

        if (recip != null){
            views.setTextViewText(R.id.appwidget_recipe_title, recip.getName());
        }

        appWidgetManager.updateAppWidget(appWidgetId,views);

    }

    public static void updateWidget(Context context){

        Intent intent = new Intent(context, RecipeAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, RecipeAppWidget.class);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        context.sendBroadcast(intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_listView_ingredients);
    }

    private static RemoteViews getIngredientsListRemoteView(Context context){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredients);

        Intent intent = new Intent(context, IngredientsWidgetViewService.class);

        views.setRemoteAdapter(R.id.appwidget_listView_ingredients, intent);
        views.setEmptyView(R.id.appwidget_listView_ingredients, R.id.appwidget_empty_view);

        Intent intentPending = new Intent(context, RecipDetailActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intentPending, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.appwidget_listView_ingredients, pendingIntent);

        return views;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
