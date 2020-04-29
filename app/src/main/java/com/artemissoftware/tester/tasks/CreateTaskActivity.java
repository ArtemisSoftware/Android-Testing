package com.artemissoftware.tester.tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.artemissoftware.tester.R;

public class CreateTaskActivity extends AppCompatActivity {

    private Button createTaskButton;
    private EditText task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        createTaskButton = (Button)findViewById(R.id.create_task_button);
        task = (EditText) findViewById(R.id.create_task_title);

        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask(task.getText().toString());
            }
        });
    }

    private void createTask(String title) {
        Intent intent=new Intent();
        intent.putExtra("TASK", "☐ " + title);
        setResult(2,intent);
        finish();
    }
}
