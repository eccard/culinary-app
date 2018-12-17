package eccard.adnd.culinary.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class IngredientsWidgetViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientRemoteViewServiceFactory(this.getApplicationContext());
    }


}
