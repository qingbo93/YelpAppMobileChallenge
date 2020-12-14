package com.example.mobilecodingchallenge.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilecodingchallenge.MobileCodingChallengeApplication;
import com.example.mobilecodingchallenge.R;
import com.example.mobilecodingchallenge.databinding.ActivityMainBinding;
import com.example.mobilecodingchallenge.utils.CommonUtils;
import com.example.mobilecodingchallenge.viewmodel.factory.ApiViewModelFactory;

import javax.inject.Inject;

import com.example.mobilecodingchallenge.utils.AppConstants;

public class MainActivity extends AppCompatActivity
{
    @Inject
    ApiViewModelFactory apiViewModelFactory;

    @Inject
    SharedPreferences sharedPreferences;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ((MobileCodingChallengeApplication) this.getApplication()).getMobileCodingChallengeComponent().inject(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sharedPreferences.edit().putString(AppConstants.API_KEY, "Bearer " + getString(R.string.api_key)).apply();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void onRadioButtonClicked(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_key_term:
                if (checked)
                    break;
            case R.id.radio_location:
                if (checked)
                    break;
        }
    }

    public void onSearchButtonClicked(View view)
    {
        if(binding.searchView.getQuery().length() == 0 || binding.searchView.getQuery().equals(""))
        {
            Toast.makeText(this, "Search bar cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else if (checkInternet())
        {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra(AppConstants.KEY_OR_LOCATION_TERM, binding.radioKeyTerm.isChecked());
            intent.putExtra(AppConstants.SEARCH_TERM, binding.searchView.getQuery().toString());
            startActivity(intent);
        }

    }

    public boolean checkInternet()
    {
        if(CommonUtils.isNetworkAvailable(this))
        {
            return true;
        }
        Toast.makeText(this, "Internet currently unavailable. Please try again later.", Toast.LENGTH_SHORT).show();
        return false;
    }
}
