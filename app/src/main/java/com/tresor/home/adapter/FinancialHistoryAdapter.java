package com.tresor.home.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tresor.R;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.home.model.SpendingDataModel;

import java.util.List;

/**
 * Created by kris on 5/28/17. Tokopedia
 */

public class FinancialHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private SpendingDataModel spendingDataModel;
    private List<FinancialHistoryModel> financialHistoryModelList;
    private ListItemListener listener;

    public static final int NUMBER_OF_HEADER_ADAPTER = 1;

    private static final int HEADER_ADAPTER = 0;
    private static final int ITEM_ADAPTER = 1;

    public FinancialHistoryAdapter(Context context,
                                   SpendingDataModel spendingDataModel,
                                   ListItemListener listener) {
        this.context = context;
        this.spendingDataModel = spendingDataModel;
        this.financialHistoryModelList = spendingDataModel.getFinancialHistoryModelList();
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        //TODO set 2 item ivew type one for total
        if(position == 0) return HEADER_ADAPTER;
        return ITEM_ADAPTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEADER_ADAPTER) {
            View adapterView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_header_adapter, parent, false);
            return new FinancialHistoryHeaderHolder(adapterView);
        } else {
            View adapterView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.financial_list_adapter, parent, false);
            return new FinancialHistoryViewHolder(adapterView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER_ADAPTER:
                FinancialHistoryHeaderHolder headerHolder = (FinancialHistoryHeaderHolder) holder;
                headerHolder.totalExpense.setText(totalAllocatedMoney());
                //TODO SIMPLIFY!!!!
                break;
            case ITEM_ADAPTER:
                FinancialHistoryViewHolder itemHolder = (FinancialHistoryViewHolder) holder;
                FinancialHistoryModel financialHistoryModel = financialHistoryModelList
                        .get(position - NUMBER_OF_HEADER_ADAPTER);
                itemHolder.historyAmount.setText(financialHistoryModel.getAmount());
                String appendedHashTag = "";
                for (int i =0; i < financialHistoryModel.getHashtag().size(); i++) {
                    appendedHashTag += financialHistoryModel.getHashtag().get(i);
                }
                itemHolder.historyHashtag.setText(appendedHashTag);
                itemHolder.historyInfo.setText(financialHistoryModel.getInfo());
                itemHolder.historyDate.setText(financialHistoryModel.getDate());
                itemHolder.optionMenu.setOnClickListener(onOptionClickedListener(position
                        - NUMBER_OF_HEADER_ADAPTER));
                itemHolder.itemPlaceHolder.setOnClickListener(
                        onMainViewClickedListener(financialHistoryModel)
                );
                setCardTheme(financialHistoryModel.getTheme(), itemHolder);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return financialHistoryModelList.size() + NUMBER_OF_HEADER_ADAPTER;
    }

    private class FinancialHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView historyAmount;
        private TextView historyHashtag;
        private TextView historyInfo;
        private TextView historyDate;
        private CardView itemPlaceHolder;
        private ImageView spendingIcon;
        private View optionMenu;

        FinancialHistoryViewHolder(View itemView) {
            super(itemView);
            historyAmount = (TextView) itemView.findViewById(R.id.history_amount);
            historyHashtag = (TextView) itemView.findViewById(R.id.history_hastag);
            historyInfo = (TextView) itemView.findViewById(R.id.history_info);
            historyDate = (TextView) itemView.findViewById(R.id.history_date);
            itemPlaceHolder = (CardView) itemView.findViewById(R.id.item_place_holder);
            spendingIcon = (ImageView) itemView.findViewById(R.id.spending_icon);
            optionMenu = itemView.findViewById(R.id.option_button);
        }
    }

    private class FinancialHistoryHeaderHolder extends RecyclerView.ViewHolder {
        private TextView totalExpense;


        FinancialHistoryHeaderHolder(View itemView) {
            super(itemView);
            totalExpense = (TextView) itemView.findViewById(R.id.total_expense);
        }
    }

    private void setCardTheme(int theme, FinancialHistoryViewHolder holder) {
        switch (theme) {
            case 0:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_everything_else_big);
                break;
            case 1:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_automotive_big);
                break;
            case 2:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_clothing_big);
                break;
            case 3:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_health_big);
                break;
            case 4:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_kitchen_dining_big);
                break;
            case 5:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_clothing_big);
                break;
            case 6:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_health_big);
                break;
            case 7:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_health_big);
                break;
            default:
                holder.spendingIcon.setImageResource(R.mipmap.ic_cat_kitchen_dining_big);
                break;
        }
    }

    public void updateData(List<FinancialHistoryModel> financialHistoryModelList) {
        this.financialHistoryModelList = financialHistoryModelList;
    }

    private String totalAllocatedMoney() {
        double total = 0;
        for(int i =0; i < financialHistoryModelList.size(); i++) {
            total +=financialHistoryModelList.get(i).getAmountUnformatted();
        }
        return String.valueOf(total);
    }

    private View.OnClickListener onOptionClickedListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(context, v);
                menu.getMenuInflater().inflate(R.menu.history_setting_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.delete_list) {
                            financialHistoryModelList.remove(position);
                            //TODO change to notify dataset change if keep error
                            notifyItemRemoved(position + NUMBER_OF_HEADER_ADAPTER);
                            notifyItemRangeChanged(position + NUMBER_OF_HEADER_ADAPTER,
                                    financialHistoryModelList.size() + NUMBER_OF_HEADER_ADAPTER);
                            return true;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        };
    }

    private View.OnClickListener onMainViewClickedListener(final FinancialHistoryModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(model);
            }
        };
    }

    public interface ListItemListener {
        void onClick(FinancialHistoryModel itemModel);
    }

}
