package eccard.adnd.culinary.network.share_prefs;

import eccard.adnd.culinary.network.model.Recip;

public interface SharePrefes {

    void saveReceipt(Recip recip);

    Recip loadReceipt();
}
