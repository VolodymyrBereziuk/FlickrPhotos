package org.vb.flickrdphotos.models;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

import android.content.Context;

public class FlickrClient extends OAuthBaseClient {

    public static final Class<? extends Api> REST_API_CLASS = FlickrApi.class;
    public static final String REST_URL = "https://www.flickr.com/services";
    public static final String REST_CONSUMER_KEY = "57ac210e2e82195e071f9a761d763ca0";
    public static final String REST_CONSUMER_SECRET = "7d359e4f4149545b";
    public static final String REST_CALLBACK_URL = "oauth://cprest";

    public FlickrClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        setBaseUrl("https://api.flickr.com/services/rest");
    }

    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?&format=json&nojsoncallback=1&method=flickr.people.getPhotos&user_id=157377817%40N08");
        client.get(apiUrl, null, handler);
    }
}
