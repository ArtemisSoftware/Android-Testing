package com.artemissoftware.tester.coffeecompanion.beveragelist;

import android.content.Context;

import androidx.annotation.NonNull;

import com.artemissoftware.tester.coffeecompanion.common.Beverage;


interface BeverageListView {

    void startCoffeeShopListActivity();

    void startBeverageActivity(@NonNull Beverage beverage);

    void displayBeverages(@NonNull BeverageListModel beverageListModel);

    @NonNull
    Context getContext();

}
