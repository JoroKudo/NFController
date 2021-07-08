package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ch.bbcag.NFController.Database.RequestHandler;


public class TaskAdder extends AppCompatActivity implements View.OnClickListener {

    EditText etProcName;
    private final RequestHandler r = new RequestHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addtask);
        FloatingActionButton tvaddtask = findViewById(R.id.TV_ListEntry);
        etProcName = findViewById(R.id.procedure_name);
        LinearLayout rlwritetask = findViewById(R.id.rlWriteTask);
        rlwritetask.setOnClickListener(this);

        tvaddtask.setOnClickListener(this);

        Util.fragmentLauncher(new TaskAdderList(), R.id.list_here, this);


    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.TV_ListEntry:
                intent = new Intent(this, TaskSelector.class);
                startActivity(intent);
                break;


            case R.id.rlWriteTask:


                r.addProcedure(etProcName.getText().toString());

                intent = new Intent(this, TaskWriter.class);
                startActivity(intent);


        }


    }
}
