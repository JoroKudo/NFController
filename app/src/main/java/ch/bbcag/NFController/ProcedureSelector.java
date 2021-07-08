package ch.bbcag.NFController;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import ch.bbcag.NFController.Database.RequestHandler;

public class ProcedureSelector extends AppCompatActivity {
    private final RequestHandler r = new RequestHandler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        ListView listView = findViewById(R.id.mobile_list);
        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, R.layout.task_list_item, Const.procedures);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, viewv, position, id) -> {
            Const.taskcontainer = r.readProcedures(Const.procedures.get(position));
            Intent intent = new Intent(this, TaskAdder.class);
            startActivity(intent);
            finish();
        });
    }
}