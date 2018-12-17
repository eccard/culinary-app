package eccard.adnd.culinary.ui.receipt_steps;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import eccard.adnd.culinary.network.model.Step;

class StepPageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Step> stepArrayList;

    public StepPageAdapter(FragmentManager fm,
                           ArrayList<Step> stepArrayList) {
        super(fm);
        this.stepArrayList = stepArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return StepDetailFrg.newInstance(stepArrayList.get(position));
    }

    @Override
    public int getCount() {
        return stepArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        int id = stepArrayList.get(position).getId();

        return String.valueOf(id);
    }
}
