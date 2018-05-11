package dl2kten.com.s_timer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.net.URL;

public class Login extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private ProgressBar progressBar;
    private static final String MyPREFERENCES = "MyPrefs";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_TASK = "key_task";
    private static final String KEY_DESC = "key_desc";
    private static final String KEY_MINUTES = "key_minutes";
    private static final String KEY_SECONDS = "key_seconds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    /**
     *  Checks whether fields are empty and stores value in sharedPreferences
     */
    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please Enter Email");
            emailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Please Enter Password");
            passwordEditText.requestFocus();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(MyPREFERENCES);
        editor.clear();
        editor.commit();

        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_TASK, "Brush Teeth, Wash Face, Eat Breakfast, Get Dressed," +
                "Pack Backpack, Check Homework");
        editor.putString(KEY_DESC, "Brush Teeth with tooth brush..., Throughly wash face, Eat Breakfast," +
                "Get dressed for school, Put everything needed inside backpack, Check Homework");
        editor.putString(KEY_MINUTES, "3, 2, 10, 5, 0, 1");
        editor.putString(KEY_SECONDS, "15, 30, 0, 45, 45, 0");
        editor.commit();

        new PullData().execute(1);
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     *
     */
    private class PullData extends AsyncTask<Integer, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(Integer... integers) {


            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Done";
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Login.this, "Progress Dialog",
                    "Depreciated?");
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            cancel(true);
        }
    }
}
