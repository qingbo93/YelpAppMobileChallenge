package com.example.mobilecodingchallenge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilecodingchallenge.AdapterInterface.OnItemClickListener;
import com.example.mobilecodingchallenge.databinding.HeaderLayoutBinding;
import com.example.mobilecodingchallenge.databinding.ImageLayoutBinding;
import com.example.mobilecodingchallenge.model.BusinessListItem;
import java.util.List;

public class BusinessesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    List<BusinessListItem> businessesList;
    OnItemClickListener listener;
    boolean requireHeader;

    public BusinessesAdapter(List<BusinessListItem> businessesList, OnItemClickListener listener, boolean requireHeader)
    {
        this.requireHeader = requireHeader;
        this.businessesList = businessesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM)
        {
            return new BusinessItemViewHolder(ImageLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        else if (viewType == TYPE_HEADER)
        {
            return new HeaderViewHolder(HeaderLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.binding.itemHeader.setText(businessesList.get(position).getText());
        }
        else if (holder instanceof BusinessItemViewHolder){
            BusinessItemViewHolder businessItemViewHolder = (BusinessItemViewHolder) holder;
            businessItemViewHolder.binding.restaurantsName.setText(businessesList.get(position).getBusinesses().getName());

            Glide.with(businessItemViewHolder.binding.restaurantsImage.getContext())
                    .load(businessesList.get(position).getBusinesses().getImage_url())
                    .into(businessItemViewHolder.binding.restaurantsImage);

            businessItemViewHolder.binding.restaurantsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onThumbnailClicked(businessesList.get(position).getBusinesses());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return businessesList.size();
    }

    @Override
    public int getItemViewType(int position) {
       return businessesList.get(position).getItemType();
    }

    public void updateBusinessesList(List<BusinessListItem> businessesList)
    {
        this.businessesList = businessesList;
        notifyDataSetChanged();
    }

    public static class BusinessItemViewHolder extends RecyclerView.ViewHolder {
        ImageLayoutBinding binding;
        public BusinessItemViewHolder(ImageLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderLayoutBinding binding;
        public HeaderViewHolder(HeaderLayoutBinding headerView) {
            super(headerView.getRoot());
            binding = headerView;
        }
    }
}
