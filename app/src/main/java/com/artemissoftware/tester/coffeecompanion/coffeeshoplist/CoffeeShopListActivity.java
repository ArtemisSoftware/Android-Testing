package com.artemissoftware.tester.coffeecompanion.coffeeshoplist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artemissoftware.tester.R;
import com.artemissoftware.tester.coffeecompanion.coffeeshopdetail.CoffeeShopDetailActivity;
import com.artemissoftware.tester.coffeecompanion.coffeeshoplist.idlingresource.CoffeeShopsIdlingResource;

import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static com.artemissoftware.tester.coffeecompanion.coffeeshopdetail.CoffeeShopDetailActivity.COFFEE_SHOP_BUNDLE_KEY;

public class CoffeeShopListActivity extends AppCompatActivity implements CoffeeShopListView {

    @NonNull
    public static final String COFFEE_SHOPS_BUNDLE_KEY = "COFFEE_SHOPS_BUNDLE_KEY";

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private CoffeeShopListViewPresenter presenter;

    @Nullable
    private RecyclerView coffeeShopRecyclerView;

    @Nullable
    private ProgressBar progressBar;

    @Nullable
    private TextView emptyTextView;

    @Nullable
    private MenuItem shareItem;

    @NonNull
    private String[] locationPermissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_shops);
        initializeViews();

        presenter = new CoffeeShopListViewPresenter(this);
        presenter.onCreate(/*savedInstanceState*/getIntent().getExtras(), locationPermissionHasBeenGranted());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coffee_shop_menu, menu);
        shareItem = menu.findItem(R.id.action_share);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                presenter.onShareClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(COFFEE_SHOPS_BUNDLE_KEY, presenter.getCoffeeShopListModel());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void displayCoffeeShops(@NonNull CoffeeShopListModel model) {
        hideProgressBar();
        hideEmptyTextView();

        List<CoffeeShop> coffeeShops = model.getCoffeeShops();
        CoffeeShopAdapter coffeeShopAdapter = new CoffeeShopAdapter(presenter, coffeeShops);
        if (null != coffeeShopRecyclerView) {
            coffeeShopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            coffeeShopRecyclerView.setAdapter(coffeeShopAdapter);
        }
    }

    @Override
    public void showShareButton() {
        if (shareItem != null) {
            shareItem.setVisible(true);
        }
    }

    @Override
    public void hideShareButton() {
        if (shareItem != null) {
            shareItem.setVisible(false);
        }
    }

    @Override
    public void openCoffeeShopDetailsActivity(@NonNull CoffeeShop coffeeShop) {
        Intent coffeeShopDetailIntent = new Intent(this, CoffeeShopDetailActivity.class);
        coffeeShopDetailIntent.putExtra(COFFEE_SHOP_BUNDLE_KEY, coffeeShop);
        startActivity(coffeeShopDetailIntent);
    }

    @Override
    public void sendShareIntent(@NonNull CoffeeShop coffeeShop) {
        String message = getString(
                R.string.coffee_shop_share_message,
                coffeeShop.getHumanReadableDistance(),
                coffeeShop.getName()
        );

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void displayPermissionRequest() {
        requestPermission(LOCATION_PERMISSION_REQUEST_CODE, locationPermissions);
    }

    @Override
    public void showPermissionError() {
        showMessage(R.string.error_cannot_get_device_location);
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void requestPermission(int requestCode, @NonNull String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE || permissions.length == 0) {
            return;
        }

        boolean canAccessLocation = canAccessLocation(permissions, grantResults);
        if (canAccessLocation) {
            presenter.onPermissionGranted();
        } else {
            presenter.onRequestPermissionFailed();
        }
    }

    @NonNull
    @VisibleForTesting
    public CoffeeShopsIdlingResource getCoffeeShopsIdlingResource() {
        return presenter.getCoffeeShopsIdlingResource();
    }

    @Override
    public void showMessage(@StringRes int message) {
        hideRecyclerView();
        hideProgressBar();
        showEmptyTextView(message);
    }

    private void initializeViews() {
        coffeeShopRecyclerView = (RecyclerView) findViewById(R.id.coffee_shops_recycler);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        emptyTextView = (TextView) findViewById(R.id.empty_text);
    }

    private boolean canAccessLocation(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length != 0) {
            String permission = permissions[0];
            if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults.length != 0) {
                    int grantResult = grantResults[0];
                    return grantResult == PackageManager.PERMISSION_GRANTED;
                }
            }
        }
        return false;
    }

    private boolean locationPermissionHasBeenGranted() {
        int permission = ContextCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
        );

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void hideProgressBar() {
        if (null != progressBar) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void hideRecyclerView() {
        if (null != coffeeShopRecyclerView) {
            coffeeShopRecyclerView.setVisibility(View.GONE);
        }
    }

    private void hideEmptyTextView() {
        if (null != emptyTextView) {
            emptyTextView.setVisibility(View.GONE);
        }
    }

    private void showEmptyTextView(@StringRes int message) {
        if (null != emptyTextView) {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(getString(message));
        }
    }

}
