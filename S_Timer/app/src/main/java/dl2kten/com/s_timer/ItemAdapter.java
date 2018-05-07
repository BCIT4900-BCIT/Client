package dl2kten.com.s_timer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private String[] tasks;
    private String[] minutes;
    private String[] seconds;
    private String[] descriptions;

    /**
     * Constructor to initialize variables
     * @param c
     * @param tasks
     * @param mins
     * @param secs
     * @param desc
     */
    public ItemAdapter(Context c, String[] tasks, String[] mins,
                       String[] secs, String[] desc) {
        this.tasks = tasks;
        this.minutes = mins;
        this.seconds = secs;
        this.descriptions = desc;
        //to fill in values for the task activity
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasks.length;
    }

    @Override
    public Object getItem(int position) {
        return tasks[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Inflates the activity_task.xml layout
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.activity_task, null);
        TextView task = (TextView) v.findViewById(R.id.taskTextView);
        TextView numMins = (TextView) v.findViewById(R.id.numMins);
        TextView numSecs = (TextView) v.findViewById(R.id.numSecs);
        TextView desc = (TextView) v.findViewById(R.id.descTextView);

        String taskName = tasks[position];
        String mins = "";
        String secs = "";
        String specificDesc = descriptions[position];

        if(Integer.parseInt(minutes[position]) < 10)
            mins += "0";
        if(Integer.parseInt(seconds[position]) < 10)
            secs += "0";

        mins += minutes[position];
        secs += seconds[position];

        task.setText(taskName);
        numMins.setText(mins);
        numSecs.setText(secs);
        desc.setText(specificDesc);

        return v;
    }
}
