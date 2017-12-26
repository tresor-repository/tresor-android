package com.tresor.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tresor.R;
import com.tresor.common.adapter.ItemAdapter;
import com.tresor.home.model.FinancialHistoryModel;

import java.util.List;

/**
 * Created by kris on 11/8/17. Tokopedia
 */

public class TodayPageAdapter extends ItemAdapter{

    private List<FinancialHistoryModel> financialHistoryModels;
    private TodayAdapterListener listener;

    public TodayPageAdapter(
            List<FinancialHistoryModel> financialHistoryModels,
            TodayAdapterListener listener) {
        this.financialHistoryModels = financialHistoryModels;
        this.listener = listener;
    }

    @Override
    protected int numberOfHeader() {
        return 1;
    }

    @Override
    protected int getHeaderLayout() {
        return R.layout.today_header_list_adapter;
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderViewHolder(View itemView) {
        return new TodayHeaderHolder(itemView);
    }

    @Override
    protected List<FinancialHistoryModel> getHistoryModel() {
        return financialHistoryModels;
    }

    @Override
    protected void setHeaderAdapter(RecyclerView.ViewHolder holder) {
        TodayHeaderHolder todayHeaderHolder = (TodayHeaderHolder) holder;
        todayHeaderHolder.totalExpense.setText(totalAllocatedMoney());
        todayHeaderHolder.addSpendingButton.setOnClickListener(onAddButtonClickedListener());
    }

    @Override
    protected ListItemListener getListener() {
        return listener;
    }

    private class TodayHeaderHolder extends RecyclerView.ViewHolder {
        private TextView totalExpense;
        private LinearLayout addSpendingButton;

        TodayHeaderHolder(View itemView) {
            super(itemView);
            totalExpense = (TextView) itemView.findViewById(R.id.total_expense);
            addSpendingButton = (LinearLayout) itemView.findViewById(R.id.add_spending_button);
        }
    }

    private String totalAllocatedMoney() {
        double total = 0;
        for(int i =0; i < financialHistoryModels.size(); i++) {
            total +=financialHistoryModels.get(i).getAmountUnformatted();
        }
        return String.valueOf(total);
    }

    public void updateData(List<FinancialHistoryModel> financialHistoryModelList) {
        this.financialHistoryModels = financialHistoryModelList;
    }

    private View.OnClickListener onAddButtonClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHeaderClickedListener();
            }
        };
    }

    public interface TodayAdapterListener extends ListItemListener{
        void onHeaderClickedListener();
    }
}
