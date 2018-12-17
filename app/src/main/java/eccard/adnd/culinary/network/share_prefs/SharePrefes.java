package eccard.adnd.culinary.network.share_prefs;

import eccard.adnd.culinary.network.model.Recipt;

public interface SharePrefes {

    void saveReceipt(Recipt recipt);

    Recipt loadReceipt();
}
