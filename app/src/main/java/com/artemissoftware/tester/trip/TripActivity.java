package com.artemissoftware.tester.trip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.artemissoftware.tester.R;
import com.artemissoftware.tester.databinding.ActivityTripBinding;

public class TripActivity extends AppCompatActivity {

    //data binding
    ActivityTripBinding mBinding;


    /**
     * Função responsável por fazer a criação da Activity
     *
     * */
    public TripViewModel tripViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //setContentView(R.layout.activity_trip);


        tripViewModel = ViewModelProviders.of(this).get(TripViewModel.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_trip);
        mBinding.setViewModel(tripViewModel);
  /*
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = mainViewModel
        }
*/
        getError();

    }


    private void getError() {

        tripViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String value) {
                if(value != null) {
                    Toast.makeText(getApplicationContext(), value.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
