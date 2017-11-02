package androidchallenge.yinkaige.com.andelaandroidapplicationchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        Intent i = new Intent(LaunchScreen.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
