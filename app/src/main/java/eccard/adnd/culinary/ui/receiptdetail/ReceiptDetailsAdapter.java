package eccard.adnd.culinary.ui.receiptdetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Ingredient;
import eccard.adnd.culinary.network.model.Step;

public class ReceiptDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ingredient> ingredients;
    private List<Step> steps;

    private final int VIEW_HOLDER_TYPE_INGREDIENT  = 1;
    private final int VIEW_HOLDER_TYPE_STEP_HEADER = 1<<1;
    private final int VIEW_HOLDER_TYPE_STEP        = 1<<2;

    void setData(List<Ingredient> ingredients,
                 List<Step> steps){
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public interface OnStepClickListener {
        void onStepClicked(Step step);
    }

    private OnStepClickListener onStepClickListener;

    public void setOnStepClickListener(OnStepClickListener onStepClickListener) {
        this.onStepClickListener = onStepClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        RecyclerView.ViewHolder viewHolder = null;
        View view;

        switch (viewType){

            case VIEW_HOLDER_TYPE_INGREDIENT:

                view = inflater.inflate(R.layout.adapter_receipt_detail_ingredient,viewGroup,false);
                viewHolder = new IngredientViewHolder(view);

                break;

            case VIEW_HOLDER_TYPE_STEP:

                view = inflater.inflate(R.layout.adapter_receipt_detail_step,viewGroup,false);
                viewHolder = new StepViewHolder(view);

                break;

            default:
                view = inflater.inflate(R.layout.adapter_receipt_detail_step_header,viewGroup,false);
                viewHolder = new StepHaederHolder(view);

                break;



        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);


        switch (viewType){

            case VIEW_HOLDER_TYPE_INGREDIENT:

                IngredientViewHolder ingredientViewHolder = (IngredientViewHolder) viewHolder;

                Ingredient ingredient = ingredients.get(position);

                ingredientViewHolder.ingName.setText(ingredient.getIngredient());

                String quantityAndMeasure = String.format(viewHolder.itemView.getContext().getString(R.string.ingredient_quantity_and_measure),
                        ingredient.getQuantity(), ingredient.getMeasure());

                ingredientViewHolder.ingQnt.setText(quantityAndMeasure);

                break;

            case VIEW_HOLDER_TYPE_STEP:

                StepViewHolder stepViewHolder = (StepViewHolder) viewHolder;

                int stepPosition = getStepPosition(position);
                Step step = steps.get(stepPosition);

                String stepText = stepPosition + 1 + " - " +step.getShortDescription();
                stepViewHolder.tvStepName.setText(stepText);

                break;

            default:


                break;



        }

    }

    @Override
    public int getItemCount() {
        int totalItems = 0;

        if(ingredients != null) totalItems += ingredients.size()-1;

        if(steps != null) totalItems += steps.size();

        // + 1 header
        return totalItems + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if ( position < ingredients.size()) {
            return VIEW_HOLDER_TYPE_INGREDIENT;
        } else if (position == ingredients.size()){
            return VIEW_HOLDER_TYPE_STEP_HEADER;
        } else {
            return VIEW_HOLDER_TYPE_STEP;
        }

    }

    class IngredientViewHolder extends RecyclerView.ViewHolder{
        TextView ingName;
        TextView ingQnt;
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingName = itemView.findViewById(R.id.tv_ingredient_name);
            ingQnt = itemView.findViewById(R.id.tv_ingredient_qnt);
        }
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvStepName;
        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStepName = itemView.findViewById(R.id.tv_step_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Step step = steps.get(getStepPosition(getAdapterPosition()));
            onStepClickListener.onStepClicked(step);
        }
    }

    class StepHaederHolder extends RecyclerView.ViewHolder{

        public StepHaederHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected int getStepPosition(int adapterPosition){
        return adapterPosition - ingredients.size() -1;
    }
}
