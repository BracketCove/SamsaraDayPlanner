package com.wiseassblog.samsaradayplanner;

import android.app.Application;

public class SamsaraApp extends Application {

    private StorageServiceLocator locator;

    @Override
    public void onCreate() {
        super.onCreate();

        locator = new StorageServiceLocator(this);
    }

    public StorageServiceLocator getServiceLocator() {
        return locator;
    }
}
