package com.tencent.routerdispatch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class JumpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String uriString = getIntent().getDataString();
        Uri uri = getIntent().getData();
        Intent intent = new Intent(this, Router.getActivity(uriString));
        startActivity(intent);
        finish();
    }
}
