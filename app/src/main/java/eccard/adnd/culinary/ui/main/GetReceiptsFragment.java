package eccard.adnd.culinary.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

import eccard.adnd.culinary.network.api.AppApiHelper;
import eccard.adnd.culinary.network.model.Recipt;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class GetReceiptsFragment extends Fragment {

    public static final String TAG = GetReceiptsFragment.class.getSimpleName();

    private List<Recipt> retainRecipt = null;

    public List<Recipt> getRetainRecipt() {
        return retainRecipt;
    }

    public void setRetainRecipt(List<Recipt> retainRecipt) {
        this.retainRecipt = retainRecipt;
    }

    private CompositeDisposable compositeDisposable;

    interface GetMoviesCallbacks {
        void onReceiptResult(List<Recipt> recipt);
        void onReceiptError(Throwable throwable);
    }

    private GetMoviesCallbacks mCallbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        removeObservers();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = null;
    }

    public void getData() {

        // TODO get next pages
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(AppApiHelper.getInstance()
                .doGetRecipeApiCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Recipt>>() {
                    @Override
                    public void accept(List<Recipt> recipt) throws Exception {

                        setRetainRecipt(recipt);

                        if (mCallbacks != null) {
                            mCallbacks.onReceiptResult(recipt);
                        } else {
                            Log.e(TAG, "mCallbacks == nul");
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        setRetainRecipt(null);

                        Log.e(TAG, throwable.toString());
                        if (mCallbacks != null) {
                            mCallbacks.onReceiptError(throwable);
                        } else {
                            Log.e(TAG, "mCallbacks == nul");
                        }
                    }
                })
        );

    }
}
