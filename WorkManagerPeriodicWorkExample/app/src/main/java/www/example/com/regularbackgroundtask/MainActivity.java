package www.example.com.regularbackgroundtask;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                .Builder(MyWorker.class, 1, TimeUnit.HOURS)
                .addTag("notification work").build();

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        SharedPreferences sp = getSharedPreferences(this.getString(R.string.app_name),0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("current time",currentTime);
        editor.apply();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork("notification work", ExistingPeriodicWorkPolicy.REPLACE , periodicWorkRequest);
    }
}
