package org.vb.flickrdphotos.models;

import org.vb.flickrdphotos.FlickApplication;
import org.vb.flickrdphotos.entity.Photo;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class PhotosManager {

    private Listener listener;
    private List<Photo> photos = new ArrayList<>();

    public PhotosManager(Listener listener) {
        this.listener = listener;
    }


    public void loadPhotos() {
        FlickApplication.getRestClient().getInterestingnessList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject json) {
                Observable.just(json)
                        .map(new Function<JSONObject, List<Photo>>() {
                            @Override
                            public List<Photo> apply(JSONObject jsonObject) throws Exception {
                                List<Photo> list = new ArrayList<>();
                                JSONArray photos = json.getJSONObject("photos").getJSONArray("photo");
                                for (int x = 0; x < photos.length(); x++) {
                                    list.add(new Photo(photos.getJSONObject(x)));
                                }
                                return list;
                            }
                        })
                        .flatMap(new Function<List<Photo>, ObservableSource<Photo>>() {
                            @Override
                            public ObservableSource<Photo> apply(List<Photo> list) throws Exception {
                                return Observable.fromIterable(list);
                            }
                        }).subscribe(new Observer<Photo>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Photo photo) {
                        photos.add(photo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onLoadingFailed();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (listener != null) {
                            listener.onLoadingFinished(photos);
                        }
                    }
                });
            }
        });
    }

    public interface Listener {
        void onLoadingFinished(List<Photo> list);

        void onLoadingFailed();

    }
}
