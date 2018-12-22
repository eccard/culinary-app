package eccard.adnd.culinary.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Recip;
import eccard.adnd.culinary.ui.recipdetail.RecipDetailActivity;

public class MainActivity extends AppCompatActivity implements ReceiptAdapter.OnMovieClickListener, GetReceiptsFragment.GetMoviesCallbacks {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_recipes)
    RecyclerView mRecycleView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.pb_main)
    ProgressBar mPB;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_generic_error)
    TextView mTvGenericError;

    @SuppressWarnings("WeakerAccess")
    @OnClick(R.id.btn_retry)
    public void onRetry(View view) {
        getMoviePage();
    }

    @BindView(R.id.btn_retry)
    Button mBtnRetry;

    private ReceiptAdapter receiptAdapter;
    private GetReceiptsFragment mGetReceiptsFrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);

        setUpViews();


        FragmentManager fm = getSupportFragmentManager();
        mGetReceiptsFrg = (GetReceiptsFragment) fm.findFragmentByTag(GetReceiptsFragment.TAG);

        if (mGetReceiptsFrg == null) {
            mGetReceiptsFrg = new GetReceiptsFragment();
            fm.beginTransaction().add(mGetReceiptsFrg, GetReceiptsFragment.TAG).commit();

            getMoviePage();
        } else {
            onReceiptResult(mGetReceiptsFrg.getRetainRecip());
        }
    }

    private void getMoviePage() {
        showLoading();
        mGetReceiptsFrg.getData();
    }

    private void setUpViews() {

        GridLayoutManager layoutManager;

        layoutManager = new GridLayoutManager(this, calculateBestSpanCount());

        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        receiptAdapter = new ReceiptAdapter();
        receiptAdapter.setOnMovieClickListener(this);
        mRecycleView.setAdapter(receiptAdapter);

    }

    private int calculateBestSpanCount() {

        float posterWidth = getResources().getDimension(R.dimen.img_view_recycler_view_holder_width);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    private void showReceipts() {
        mRecycleView.setVisibility(View.VISIBLE);
        mPB.setVisibility(View.INVISIBLE);
        mTvGenericError.setVisibility(View.INVISIBLE);
        mBtnRetry.setVisibility(View.INVISIBLE);
    }

    private void showLoading() {
        mRecycleView.setVisibility(View.INVISIBLE);
        mPB.setVisibility(View.VISIBLE);
        mTvGenericError.setVisibility(View.INVISIBLE);
        mBtnRetry.setVisibility(View.INVISIBLE);
    }

    private void showLoadingError() {
        mRecycleView.setVisibility(View.INVISIBLE);
        mPB.setVisibility(View.INVISIBLE);
        mTvGenericError.setVisibility(View.VISIBLE);
        mBtnRetry.setVisibility(View.VISIBLE);
    }


    @Override
    public void onReceiptItemClick(Recip receipt) {
        Intent i = RecipDetailActivity.newIntent(this,receipt);
        startActivity(i);
    }

    @Override
    public void onReceiptResult(List<Recip> recip) {
        showReceipts();
        receiptAdapter.setReceipts(recip);
        receiptAdapter.notifyDataSetChanged();
    }

    @Override
    public void onReceiptError(Throwable throwable) {
        showLoadingError();
    }

}
