package eccard.adnd.culinary.ui.recipdetail;

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
import eccard.adnd.culinary.network.model.Recip;
import eccard.adnd.culinary.network.model.Step;
import eccard.adnd.culinary.ui.recip_steps.StepDetailFrg;
import eccard.adnd.culinary.ui.recip_steps.StepDetailsActivity;

public class RecipDetailActivity extends AppCompatActivity implements RecipDetailsAdapter.OnStepClickListener{

    private boolean isTablet;

    static public Intent newIntent(Context context, Recip recip){
        Intent i = new Intent(context, RecipDetailActivity.class);
        i.putExtra(Recip.class.getSimpleName(), recip);
        return i;
    }


    Recip receiptExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_receipt_details);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.hasExtra(Recip.class.getSimpleName())) {
            receiptExtra = intent.getParcelableExtra(Recip.class.getSimpleName());
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

        Fragment fragment = new RecipDetailFrg();
        fragTransaction.add(R.id.recipe_container, fragment , RecipDetailFrg.class.getSimpleName()) ;


        if (isTablet){

            Step step =  receiptExtra.getSteps().get(0);
            Fragment stepDetailFrg = StepDetailFrg.newInstance(step);

            fragTransaction.replace(R.id.container_step_detail, stepDetailFrg , StepDetailFrg.class.getSimpleName()) ;

        }

        fragTransaction.commit();

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
