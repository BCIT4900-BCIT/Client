package dl2kten.com.s_timer;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    private String[] tasks;
    private String[] minutes;
    private String[] seconds;
    private String[] descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.myListView);

        tasks = res.getStringArray(R.array.tasks);
        minutes = res.getStringArray(R.array.minutes);
        seconds = res.getStringArray(R.array.seconds);
        descriptions = res.getStringArray(R.array.description);

        ItemAdapter itemAdapter = new ItemAdapter(this, tasks, minutes, seconds,
                descriptions);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent countDown = new Intent(getApplicationContext(), CountDOWN.class);
                countDown.putExtra("dl2kten.com.s_timer.task", position);
                startActivity(countDown);
            }
        });

    }
}
