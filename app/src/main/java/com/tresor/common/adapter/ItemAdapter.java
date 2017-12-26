package com.tresor.common.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tresor.R;
import com.tresor.home.model.FinancialHistoryModel;

import java.util.List;

/**
 * Created by kris on 10/24/17. Tokopedia
 */

public abstract class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int HEADER_ADAPTER = 0;
    private static final int ITEM_ADAPTER = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEADER_ADAPTER) {
            View adapterView = LayoutInflater.from(parent.getContext())
                    .inflate(getHeaderLayout(), parent, false);
            return getHeaderViewHolder(adapterView);
        } else {
            View adapterView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.financial_list_adapter, parent, false);
            return new FinancialHistoryViewHolder(adapterView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < numberOfHeader()) return HEADER_ADAPTER;
        return ITEM_ADAPTER;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER_ADAPTER:
                setHeaderAdapter(holder);
                break;
            case ITEM_ADAPTER:
                FinancialHistoryViewHolder itemHolder = (FinancialHistoryViewHolder) holder;
                FinancialHistoryModel financialHistoryModel = getHistoryModel()
                        .get(position - numberOfHeader());
                itemHolder.historyAmount.setText(financialHistoryModel.getAmount());
                String appendedHashTag = "";
                for (int i =0; i < financialHistoryModel.getHashtag().size(); i++) {
                    appendedHashTag += financialHistoryModel.getHashtag().get(i);
                }
                itemHolder.historyHashtag.setText(appendedHashTag);
                itemHolder.historyInfo.setText(financialHistoryModel.getInfo());
                itemHolder.historyDate.setText(financialHistoryModel.getDate());
                itemHolder.deleteMenu.setOnClickListener(onDeleteClickedListener(position
                        - numberOfHeader()));
                itemHolder.itemPlaceHolder.setOnClickListener(
                        onMainViewClickedListener(financialHistoryModel)
                );
                setCardTheme(financialHistoryModel.getTheme(), itemHolder);
                break;
        }
    }

    private View.OnClickListener onMainViewClickedListener(final FinancialHistoryModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onClick(model);
            }
        };
    }

    @Override
    public int getItemCount() {
        return getHistoryModel().size() + numberOfHeader();
    }

    private class FinancialHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView historyAmount;
        private TextView historyHashtag;
        private TextView historyInfo;
        private TextView historyDate;
        private CardView itemPlaceHolder;
        private ImageView spendingIcon;
        private View deleteMenu;

        FinancialHistoryViewHolder(View itemView) {
            super(itemView);
            historyAmount = (TextView) itemView.findViewById(R.id.history_amount);
            historyHashtag = (TextView) itemView.findViewById(R.id.history_hastag);
            historyInfo = (TextView) itemView.findViewById(R.id.history_info);
            historyDate = (TextView) itemView.findViewById(R.id.history_date);
            itemPlaceHolder = (CardView) itemView.findViewById(R.id.item_place_holder);
            spendingIcon = (ImageView) itemView.findViewById(R.id.spending_icon);
            deleteMenu = itemView.findViewById(R.id.option_button);
        }
    }

    private View.OnClickListener onDeleteClickedListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHistoryModel().remove(position);
                //TODO change to notify dataset change if keep error
                notifyItemRemoved(position + numberOfHeader());
                notifyItemRangeChanged(position + numberOfHeader(),
                        getHistoryModel().size() + numberOfHeader());
            }
        };
    }

    private void setCardTheme(int theme, FinancialHistoryViewHolder holder) {
        switch (theme) {
            case 0:
                holder.spendingIcon.setImageResource(R.drawable.ic_burger_icon_big);
                break;
            case 1:
                holder.spendingIcon.setImageResource(R.drawable.ic_clothing);
                break;
            case 2:
                holder.spendingIcon.setImageResource(R.drawable.ic_tools);
                break;
            case 3:
                holder.spendingIcon.setImageResource(R.drawable.ic_health);
                break;
            case 4:
                holder.spendingIcon.setImageResource(R.drawable.ic_grocery);
                break;
            case 5:
                holder.spendingIcon.setImageResource(R.drawable.ic_electronics_alternative);
                break;
            case 6:
                holder.spendingIcon.setImageResource(R.drawable.ic_hygine);
                break;
            case 7:
                holder.spendingIcon.setImageResource(R.drawable.ic_transportation);
                break;
            default:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_kitchen_dining_big);
                break;
        }
    }

    protected abstract int numberOfHeader();

    protected abstract int getHeaderLayout();

    protected abstract RecyclerView.ViewHolder getHeaderViewHolder(View itemView);

    protected abstract List<FinancialHistoryModel> getHistoryModel();

    protected abstract void setHeaderAdapter(RecyclerView.ViewHolder holder);

    protected abstract ListItemListener getListener();

    public interface ListItemListener {
        void onClick(FinancialHistoryModel itemModel);
    }

}
