package eccard.adnd.culinary.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Recip;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ItemViewHolder>{

    private List<Recip> receipts;

    public void setReceipts(List<Recip> receipts) {
        this.receipts = receipts;
    }

    public interface OnMovieClickListener{
        void onReceiptItemClick(Recip receipt);

    }

    private OnMovieClickListener onMovieClickListener;

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater =  LayoutInflater.from(viewGroup.getContext());

        View view = layoutInflater.inflate(R.layout.receipt_item_view_holder,viewGroup,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        Recip recip = receipts.get(i);
        itemViewHolder.recipt.setText(recip.getName());
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (receipts != null){
            size = receipts.size();
        }
        return size;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView recipt;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            recipt = itemView.findViewById(R.id.receipt_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMovieClickListener.onReceiptItemClick(receipts.get(getAdapterPosition()));
        }
    }
}
