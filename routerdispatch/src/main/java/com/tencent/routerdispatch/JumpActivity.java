package com.tencent.routerdispatch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class JumpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        UriPath uriPath = UriPath.createUriPath(uri);
        Intent intent = new Intent(this,Router.getActivity(uriPath.getHost() + "/" + uriPath.getPath()));
        for (Params params : uriPath.getParamsList()) {
            intent.putExtra(params.key, params.value);
        }
        startActivity(intent);
        finish();
    }


    public static void doJumpAction() {

    }
}
