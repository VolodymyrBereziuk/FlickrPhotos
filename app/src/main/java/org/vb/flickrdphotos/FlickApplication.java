package org.vb.flickrdphotos;

import org.vb.flickrdphotos.models.FlickrClient;

import android.app.Application;
import android.content.Context;

public class FlickApplication extends Application {
	private static Context context;
	
    @Override
    public void onCreate() {
        super.onCreate();
        FlickApplication.context = this;
    }
    
    public static FlickrClient getRestClient() {
    	return (FlickrClient) FlickrClient.getInstance(FlickrClient.class, FlickApplication.context);
    }
}