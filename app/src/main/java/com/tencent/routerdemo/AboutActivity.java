package com.tencent.routerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.tencent.annotation.Router;

@Router(value = "about")
public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "AboutActivity", 0).show();

    }
}
