package eccard.adnd.culinary.ui.recipdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Recip;
import eccard.adnd.culinary.network.share_prefs.AppSharePref;
import eccard.adnd.culinary.widget.RecipeAppWidget;

public class RecipDetailFrg extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecycleView;

    Recip receiptExtra;

    AppSharePref appSharePref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        appSharePref = new AppSharePref(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recyclerview,container,false);


        ButterKnife.bind(this,view);


        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(Recip.class.getSimpleName())) {
            receiptExtra = intent.getParcelableExtra(Recip.class.getSimpleName());
            setUpViews();
        }

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recipe_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if ( itemId == R.id.action_recipe_add_to_widget){
            appSharePref.saveReceipt(receiptExtra);
            Toast.makeText(getActivity(), R.string.receipt_add_to_widget, Toast.LENGTH_SHORT).show();
            RecipeAppWidget.updateWidget(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        RecipDetailsAdapter recipDetailsAdapter = new RecipDetailsAdapter();
        recipDetailsAdapter.setOnStepClickListener((RecipDetailActivity)getActivity());
        mRecycleView.setAdapter(recipDetailsAdapter);

        recipDetailsAdapter.setData(receiptExtra.getIngredients(), receiptExtra.getSteps());
        recipDetailsAdapter.notifyDataSetChanged();

    }
}
