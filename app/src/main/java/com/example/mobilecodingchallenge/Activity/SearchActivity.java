package com.example.mobilecodingchallenge.Activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.mobilecodingchallenge.AdapterInterface.OnItemClickListener;
import com.example.mobilecodingchallenge.MobileCodingChallengeApplication;
import com.example.mobilecodingchallenge.adapter.BusinessesAdapter;
import com.example.mobilecodingchallenge.databinding.ActivitySearchBinding;
import com.example.mobilecodingchallenge.model.BusinessListItem;
import com.example.mobilecodingchallenge.model.Businesses;
import com.example.mobilecodingchallenge.viewmodel.ApiViewModel;
import com.example.mobilecodingchallenge.viewmodel.factory.ApiViewModelFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.example.mobilecodingchallenge.utils.AppConstants;

public class SearchActivity extends AppCompatActivity  implements View.OnClickListener, OnItemClickListener {

    @Inject
    ApiViewModelFactory apiViewModelFactory;

    @Inject
    SharedPreferences sharedPreferences;

    private ApiViewModel apiViewModel;

    private ActivitySearchBinding binding;

    private BusinessesAdapter businessesAdapter;

    private boolean isKeyTerm = true;

    List<Businesses> businessesList = new ArrayList<>();

    List<BusinessListItem> businessListItems;

    private Observer<List<Businesses>> businessesListResponseObserver = new Observer<List<Businesses>>() {
        @Override
        public void onChanged(List<Businesses> business) {
            businessesList = business;
            businessListItems = new ArrayList<>();
            if(isKeyTerm)
            {
                for(Businesses bus : business)
                {
                    businessListItems.add(new BusinessListItem(AppConstants.TYPE_ITEM, bus, ""));
                }
            }
            else
            {
                HashMap<String, Integer> categoryCount = new HashMap<>();
                for(Businesses bus : business)
                {
                    categoryCount.put(bus.getCategories().get(0).getTitle(), categoryCount.getOrDefault(bus.getCategories().get(0).getTitle(), 0) + 1);
                }

                Iterator hmIterator = categoryCount.entrySet().iterator();
                while (hmIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry)hmIterator.next();
                    businessListItems.add(new BusinessListItem(AppConstants.TYPE_HEADER, null, mapElement.getKey() + "(" + mapElement.getValue() + ")"));
                    for(Businesses bus : business)
                    {
                        if(bus.getCategories().get(0).getTitle().equals(mapElement.getKey()))
                        {
                            businessListItems.add(new BusinessListItem(AppConstants.TYPE_ITEM, bus, ""));
                        }
                    }
                }
            }
            businessesAdapter.updateBusinessesList(businessListItems);
        }
    };
    private Observer<Businesses> businessesResponseObserver = new Observer<Businesses>() {
        @Override
        public void onChanged(Businesses businesses) {
            Glide.with(SearchActivity.this)
                    .load(businesses.getPhotos().get(0))
                    .into(binding.storeReviewPicture1);
            Glide.with(SearchActivity.this)
                    .load(businesses.getPhotos().get(1))
                    .into(binding.storeReviewPicture2);
            Glide.with(SearchActivity.this)
                    .load(businesses.getPhotos().get(2))
                    .into(binding.storeReviewPicture3);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MobileCodingChallengeApplication) this.getApplication()).getMobileCodingChallengeComponent().inject(this);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        isKeyTerm = getIntent().getExtras().getBoolean(AppConstants.KEY_OR_LOCATION_TERM);

        this.setUpViews();
        this.setUpViewModel();
        this.setUpRecyclerView();
        this.searchYelp(getIntent().getExtras().getBoolean(AppConstants.KEY_OR_LOCATION_TERM), getIntent().getExtras().getString(AppConstants.SEARCH_TERM));
    }

    private void setUpViews() {
        binding.navToolbar.setNavigationOnClickListener(this);
        binding.sortButtonA.setVisibility(isKeyTerm ? View.VISIBLE : View.GONE);
        binding.sortButtonB.setVisibility(isKeyTerm ? View.VISIBLE : View.GONE);
    }

    private void searchYelp(boolean keyTerm, String searchTerm) {
        String apiKey = sharedPreferences.getString(AppConstants.API_KEY, "");

        apiViewModel.getBusinessesListResponse().observe(this, this.businessesListResponseObserver);
        apiViewModel.getBusinessByIdResponse().observe(this, this.businessesResponseObserver);

        if (keyTerm)
        {
            apiViewModel.getBusinessesByKeyTermList(apiKey, searchTerm);
        }
        else
        {
            apiViewModel.getBusinessesByLocationList(apiKey, searchTerm);
        }
    }

    private void setUpRecyclerView() {
        this.businessesAdapter = new BusinessesAdapter(new ArrayList<BusinessListItem>(), this, isKeyTerm);
        if(isKeyTerm)
        {
            binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
        else
        {
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
        binding.recyclerView.setAdapter(this.businessesAdapter);
    }

    private void setUpViewModel()
    {
        this.apiViewModel = new ViewModelProvider(this, this.apiViewModelFactory).get(ApiViewModel.class);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(binding.moreInfoLayout)) {
            binding.moreInfoLayout.setVisibility(View.INVISIBLE);
        }
        else {
            finish();
        }
    }

    @Override
    public void onThumbnailClicked(Businesses businesses) {
        apiViewModel.getBusinessesById(sharedPreferences.getString(AppConstants.API_KEY, ""), businesses.getId());

        Glide.with(this)
                .load(businesses.getImage_url())
                .into(binding.storePicture);

        binding.storeName.setText(String.format("Store Name: %s", businesses.getName()));
        binding.storeRating.setText(String.format("Store Rating: %s", String.valueOf(businesses.getRating())));
        binding.storeAddress.setText(String.format("Address: %s", businesses.getLocation().getAddress1()));
        binding.storeIsOpen.setText(businesses.isIs_closed() ? "Currently : Open" : "Currently: Closed");
        binding.moreInfoLayout.setVisibility(View.VISIBLE);
    }

    public void onSortButtonClicked(View view) {
        Collections.sort(businessListItems, new Comparator<BusinessListItem>() {
            @Override
            public int compare(BusinessListItem lhs, BusinessListItem rhs) {
                if(view.equals(binding.sortButtonA)) {
                    return lhs.getBusinesses().getName().compareTo(rhs.getBusinesses().getName());
                }
                else {
                    return rhs.getBusinesses().getName().compareTo(lhs.getBusinesses().getName());
                }
            }
        });
        businessesAdapter.updateBusinessesList(businessListItems);
    }
}
