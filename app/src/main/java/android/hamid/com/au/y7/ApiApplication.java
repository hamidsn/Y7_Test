package android.hamid.com.au.y7;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hamid on 13/11/2015.
 * Goal: Having a single context for the whole app
 */
public class ApiApplication extends Application {

    private static Context myContext;

    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;
    }

    public static Context getAppContext() {
        return myContext;
    }
}
