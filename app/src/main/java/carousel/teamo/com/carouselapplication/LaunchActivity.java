package carousel.teamo.com.carouselapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import carousel.teamo.com.carouselapplication.draweractivity.AddScoreActivity;

public class LaunchActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "carousel.teamo.com.carouselapplication";
    public static final String INTRO_COMPLETED = "APP_INTRO_COMPLETED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_launch);
        // check for login first
        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity(new Intent(this, AddScoreActivity.class));

        } else {
            // else check if intro was done by user or not.
            sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//            startActivity(new Intent(this, LoginActivity.class));
            if (sharedPreferences.contains(INTRO_COMPLETED)) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, IntroductionActivity.class));
            }
        }
        finish();
    }
}
