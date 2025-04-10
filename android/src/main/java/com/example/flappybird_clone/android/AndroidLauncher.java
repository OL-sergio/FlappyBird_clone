package com.example.flappybird_clone.android;

import android.content.pm.ActivityInfo;
import android.os.Bundle;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.example.flappybird_clone.FlappyBirdActivity;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true; // Recommended, but not required.
        initialize(new FlappyBirdActivity(), configuration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

    }
}
