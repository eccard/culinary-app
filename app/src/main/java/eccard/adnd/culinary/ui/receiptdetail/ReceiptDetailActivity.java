package eccard.adnd.culinary.ui.receiptdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Recipt;
import eccard.adnd.culinary.network.model.Step;
import eccard.adnd.culinary.ui.receipt_steps.StepDetailFrg;
import eccard.adnd.culinary.ui.receipt_steps.StepDetailsActivity;

public class ReceiptDetailActivity extends AppCompatActivity implements ReceiptDetailsAdapter.OnStepClickListener{

    private boolean isTablet;

    static public Intent newIntent(Context context, Recipt recipt){
        Intent i = new Intent(context, ReceiptDetailActivity.class);
        i.putExtra(Recipt.class.getSimpleName(),recipt);
        return i;
    }


    Recipt receiptExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_receipt_details);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.hasExtra(Recipt.class.getSimpleName())) {
            receiptExtra = intent.getParcelableExtra(Recipt.class.getSimpleName());
            setUpViews();
        }

    }


    private void setUpViews() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(receiptExtra.getName());
        }



        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();

        Fragment fragment = new ReceiptDetailFrg();
        fragTransaction.add(R.id.recipe_container, fragment , ReceiptDetailFrg.class.getSimpleName()) ;
        fragTransaction.commit();

        if (isTablet){

            Step step =  receiptExtra.getSteps().get(0);
            Fragment stepDetailFrg = StepDetailFrg.newInstance(step);

            fragTransaction.replace(R.id.container_step_detail, stepDetailFrg , StepDetailFrg.class.getSimpleName()) ;
            fragTransaction.commit();

        }



    }

    @Override
    public void onStepClicked(Step step) {

        if (isTablet) {

            FragmentManager fragMan = getSupportFragmentManager();
            FragmentTransaction fragTransaction = fragMan.beginTransaction();

            Fragment stepDetailFrg = StepDetailFrg.newInstance(step);

            fragTransaction.replace(R.id.container_step_detail, stepDetailFrg , StepDetailFrg.class.getSimpleName()) ;

            fragTransaction.commit();

        } else {
            Intent stepDetailIntent = StepDetailsActivity.newIntent(this,
                    receiptExtra.getSteps(), step.getId());

            startActivity(stepDetailIntent);

        }

    }
}
