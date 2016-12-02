package leapingwolf.firebasecloudmessaging;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NotificationDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String user = getIntent().getExtras().getString("user");
        Gson gson = new GsonBuilder().create();

        User userModel =  gson.fromJson(user, User.class);

        TextView txtName = (TextView) findViewById(R.id.name);
        txtName.setText(userModel.getName());
        TextView txtOccupation = (TextView) findViewById(R.id.occupation);
        txtOccupation.setText(userModel.getOccupation());
        setSupportActionBar(toolbar);
    }

}
