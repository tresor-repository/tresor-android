package com.tresor.home.bottomsheet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tresor.R;
import com.tresor.common.model.viewmodel.SpendingModel;
import com.tresor.home.inteface.IconSelectetionListener;
import com.tresor.home.model.IconModel;

import java.util.List;

/**
 * Created by kris on 7/6/17. Tokopedia
 */

/*
public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ImageHolder>{

    private List<IconModel> iconModelList;

    private IconSelectetionListener listener;

    private SpendingModel model;

    public IconAdapter(List<IconModel> iconModelList, IconSelectetionListener listener) {
        this.iconModelList = iconModelList;
        this.listener = listener;
    }

    public IconAdapter(List<IconModel> iconModelList, SpendingModel model) {
        this.iconModelList = iconModelList;
        this.model = model;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.icon_list_layout, parent, false);
        return new ImageHolder(adapterView);
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, int position) {
        setIconImage(holder.iconImage, iconModelList.get(position).getIconImageId());
        switchIconBackgroundColor(holder, position);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change background of selected item
                refreshIcons(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    private void refreshIcons(int position) {
        for (int i = 0; i < iconModelList.size(); i++) {
            iconModelList.get(i).setChoosen(false);
        }
        iconModelList.get(position).setChoosen(true);
    }

    private void switchIconBackgroundColor(ImageHolder holder, int position) {
        if(!iconModelList.get(position).isChosen())
            holder.parentLayout.setBackgroundColor(holder.parentLayout.getContext()
                    .getResources().getColor(android.R.color.transparent));
        else holder.parentLayout.setBackgroundColor(holder.parentLayout.getContext()
                .getResources().getColor(R.color.brightBlue));
    }

    @Override
    public int getItemCount() {
        return iconModelList.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView iconImage;
        private RelativeLayout parentLayout;
        ImageHolder(View itemView) {
            super(itemView);
            iconImage = (ImageView) itemView.findViewById(R.id.icon_image);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
        }
    }

    public int getChoosenIcon() {
        for (int i = 0; i < iconModelList.size(); i++) {
            if (iconModelList.get(i).isChosen()) return i;
        }
        return 0;
    }

    private void setIconImage(ImageView image, int imageId) {
        //TODO make function to pair data id with drawable id CODE RED
        switch (imageId) {
            case 0:
                image.setImageResource((R.mipmap.ic_cat_everything_else_big));
                break;
            case 1:
                image.setImageResource((R.mipmap.ic_cat_automotive_big));
                break;
            case 2:
                image.setImageResource((R.mipmap.ic_cat_clothing_big));
                break;
            case 3:
                image.setImageResource((R.mipmap.ic_cat_health_big));
                break;
            case 4:
                image.setImageResource((R.mipmap.ic_cat_kitchen_dining_big));
                break;
            case 5:
                image.setImageResource((R.mipmap.ic_cat_clothing_big));
                break;
            case 6:
                image.setImageResource((R.mipmap.ic_cat_health_big));
                break;
            case 7:
                image.setImageResource((R.mipmap.ic_cat_kitchen_dining_big));
                break;
            default:
                image.setImageResource((R.mipmap.ic_cat_everything_else_big));
                break;
        }
    }
}
*/
