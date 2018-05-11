package dl2kten.com.s_timer;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable{

    private String task;
    private String minutes;
    private String seconds;
    private String desc;
    private Boolean initiated;

    public Task(String task, String minutes, String seconds,
                String desc) {
        this.task = task;
        this.minutes = minutes;
        this.seconds = seconds;
        this.desc = desc;
        initiated = false;
    }

    public Task(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);

        task = data[0];
        minutes = data[1];
        seconds = data[2];
        desc = data[3];
        initiated = Boolean.valueOf(data[4]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{task, minutes, seconds, desc, String.valueOf(initiated)});
    }

    public static final Creator<Task> CREATOR = new Parcelable.Creator<Task>() {

        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }

    };

    public Boolean getInitiated() {
        return initiated;
    }

    public void setInitiated(Boolean initiated) {
        this.initiated = initiated;
    }

    public String getTask() {
        return task;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getSeconds() {
        return seconds;
    }

    public String getDesc() {
        return desc;
    }
}
