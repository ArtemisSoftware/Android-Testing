package com.artemissoftware.tester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.artemissoftware.tester.coffeecompanion.beveragelist.BeverageListActivity;
import com.artemissoftware.tester.notekeeper.NoteListActivity;
import com.artemissoftware.tester.tasklist.LoginActivity;
import com.artemissoftware.tester.trip.TripActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Descomentar a linha correspondente para o projeto poder executar os testes

        Intent intent = new Intent(this, TripActivity.class);
        //Intent intent = new Intent(this, NoteListActivity.class);
        //Intent intent = new Intent(this, BeverageListActivity.class);
        //Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
