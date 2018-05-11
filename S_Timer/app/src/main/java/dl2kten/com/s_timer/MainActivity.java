package dl2kten.com.s_timer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    private Task[] tasks;
    private SharedPreferences prefs;
    private static final String MyPREFERENCES = "MyPrefs";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_TASK = "key_task";
    private static final String KEY_DESC = "key_desc";
    private static final String KEY_MINUTES = "key_minutes";
    private static final String KEY_SECONDS = "key_seconds";
    private static final String KEY_DONE = "key_done";
    private static final String KEY_RUNNING = "key_running";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.myListView);
        prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        getData();
        showTasks();

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Check to see if a task if already running
                SharedPreferences.Editor editor = prefs.edit();
                String running = prefs.getString(KEY_RUNNING, "Huh?");

                //Check to see if task already ran
                Boolean ran = alreadyRan(position);

                if(!ran) {
                    if(running.equals("Huh?") || running.equals("false")
                            || running.equals(Integer.toString(position))) {
                        //Get list of done tasks and add to it1
                        colorCode(parent, position);
                        String done = prefs.getString(KEY_DONE, "Huh?");

                        if(!done.equals("Huh?")) {
                            done += ",";
                            done += Integer.toString(position);
                        } else {
                            done = Integer.toString(position);
                        }

                        editor.putString(KEY_DONE, done);
                        editor.putString(KEY_RUNNING, Integer.toString(position));
                        editor.commit();

                        Intent countDown = new Intent(getApplicationContext(), CountDOWN.class);
                        countDown.putExtra("dl2kten.com.s_timer.task", tasks[position]);
                        startActivity(countDown);
                    }
                }

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
    /**
     *
     */
    private void getData() {
        String email = prefs.getString(KEY_EMAIL, "Huh?");
        String password = prefs.getString(KEY_PASSWORD, "Huh?");
        String output = email + "\n" + password;

        String[] allTasks = prefs.getString(KEY_TASK, "Huh?").split(",");
        String[] allMinutes = prefs.getString(KEY_MINUTES, "Huh?").split(",");
        String[] allSeconds = prefs.getString(KEY_SECONDS, "Huh?").split(",");
        String[] allDescriptions = prefs.getString(KEY_DESC, "Huh?").split(",");
        String done = prefs.getString(KEY_DONE, "Huh?");

        tasks = new Task[allTasks.length];

        //take out spaces at beginning if it exists and initialize tasks
        for(int i = 0; i < tasks.length; i++) {
            allTasks[i] = allTasks[i].trim();
            allMinutes[i] = allMinutes[i].trim();
            allSeconds[i] = allSeconds[i].trim();
            allDescriptions[i] = allDescriptions[i].trim();

            tasks[i] = new Task(allTasks[i], allMinutes[i], allSeconds[i],
                    allDescriptions[i]);

        }

        if(!done.equals("Huh?")) {
            String[] tasksFinished = done.split(",");
            int[] doneNumber = new int[tasksFinished.length];

            for(int i = 0; i < tasksFinished.length; i++) {
                tasksFinished[i] = tasksFinished[i].trim();
                doneNumber[i] = Integer.parseInt(tasksFinished[i]);
            }

            for(int i = 0; i < doneNumber.length; i++) {
                tasks[doneNumber[i]].setInitiated(true);
            }
        }

        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
    }

    /**
     *
     */
    private void showTasks()
    {
        ArrayList<Task> shownTasks = new ArrayList<Task>();
        int count = 0;

        for(int i = 0; i < tasks.length; i++) {
            if(tasks[i].getInitiated()) {
                shownTasks.add(tasks[i]);
            } else {
                if(count < 3) {
                    shownTasks.add(tasks[i]);
                    count++;
                }
            }
        }

        String[] tempTasks = new String[shownTasks.size()];
        String[] tempMins = new String[shownTasks.size()];
        String[] tempSecs = new String[shownTasks.size()];
        String[] tempDesc = new String[shownTasks.size()];
        Boolean[] tempInitiated = new Boolean[shownTasks.size()];

        for(int i = 0; i < tempTasks.length; i++) {
            tempTasks[i] = tasks[i].getTask();
            tempMins[i] = tasks[i].getMinutes();
            tempSecs[i] = tasks[i].getSeconds();
            tempDesc[i] = tasks[i].getDesc();
            tempInitiated[i] = tasks[i].getInitiated();
        }

        ItemAdapter itemAdapter = new ItemAdapter(this, tempTasks, tempMins, tempSecs,
                tempDesc, tempInitiated);
        myListView.setAdapter(itemAdapter);
    }

    /**
     *
     * @param position
     * @return
     */
    private Boolean alreadyRan(int position) {
        String ran = prefs.getString(KEY_DONE, "Huh?");
        String done = prefs.getString(KEY_RUNNING, "Huh?");

        if(ran.equals("Huh?"))
            return false;

        String[] ranTasks = ran.split(",");

        //if not completed
        if(done.equals("false")) {
            for(int i = 0; i < ranTasks.length; i++) {
                if(Integer.parseInt(ranTasks[i]) == position) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     * @param parent
     */
    private void colorCode(AdapterView<?> parent, int position) {
        for(int i = 0; i < parent.getChildCount(); i++) {
            parent.getChildAt(i).setBackgroundColor(Color.RED);
        }

        parent.getChildAt(position).setBackgroundColor(Color.GREEN);
    }

}
