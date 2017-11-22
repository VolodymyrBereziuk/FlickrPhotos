package org.vb.flickrdphotos.models;

import org.vb.flickrdphotos.entity.ResultData;

import retrofit2.http.GET;
import io.reactivex.Observable;

public interface PhotoService {
    @GET("?method=flickr.people.getPhotos&nojsoncallback=1&api_key=cb548cec8f7d5dd37f863e807939b9d4&user_id=157377817%40N08&format=json")
    Observable<ResultData> getPhotos();
}
