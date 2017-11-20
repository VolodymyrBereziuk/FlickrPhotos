package org.vb.flickrdphotos;

import org.vb.flickrdphotos.models.FlickrClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.Context;

public class FlickApplication extends Application {
	private static Context context;
	
    @Override
    public void onCreate() {
        super.onCreate();
        FlickApplication.context = this;

        // Create global configuration and initialize ImageLoader with this configuration
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
        		cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .defaultDisplayImageOptions(defaultOptions)
            .build();
        ImageLoader.getInstance().init(config);
    }
    
    public static FlickrClient getRestClient() {
    	return (FlickrClient) FlickrClient.getInstance(FlickrClient.class, FlickApplication.context);
    }
}