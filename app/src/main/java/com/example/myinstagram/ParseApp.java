package com.example.myinstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        // creates configuration needed to initialize parse
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this )
                .applicationId("code2league")
                .clientKey("genetics")
                .server("http://genenartey3-myinstagram.herokuapp.com/parse")
                .build();

        // initializes
        Parse.initialize(configuration);

    }
}
