package eccard.adnd.culinary.ui.recip_steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Step;

public class StepDetailsActivity extends AppCompatActivity {

    private static final String TAG = StepDetailsActivity.class.getSimpleName();

    ArrayList<Step> stepListExtra;
    int stepIdExtra;

    static public Intent newIntent(Context context,
                                   ArrayList<Step> stepList,
                                   int stepiD){

        Intent stepDetailIntent = new Intent(context, StepDetailsActivity.class);
        stepDetailIntent.putParcelableArrayListExtra(Step.class.getSimpleName(),stepList);
        stepDetailIntent.putExtra(Intent.EXTRA_UID, stepiD);

        return stepDetailIntent;
    }

    private void setExtras(){
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            stepListExtra = extras.getParcelableArrayList(Step.class.getSimpleName());
            stepIdExtra = extras.getInt(Intent.EXTRA_UID);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recip_detail);

        ViewPager mViewPager = findViewById(R.id.pager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);


        setExtras();

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.setTitle(R.string.recipe_steps);
        }

        StepPageAdapter stepPageAdapter = new StepPageAdapter(getSupportFragmentManager(),
                stepListExtra);

        mViewPager.setAdapter(stepPageAdapter);

        if (stepIdExtra > 0 ){

            TabLayout.Tab tab = tabLayout.getTabAt(stepIdExtra);
            if (tab != null){
                tab.select();
            }

        }


    }




}
