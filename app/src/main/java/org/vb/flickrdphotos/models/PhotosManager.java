package org.vb.flickrdphotos.models;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscription;
import org.vb.flickrdphotos.entity.Photo;
import org.vb.flickrdphotos.entity.ResultData;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PhotosManager {

    private static final String BASE_URL = "https://api.flickr.com/services/rest/";
    private Listener listener;
    private List<Photo> resultList = new ArrayList<>();

    public PhotosManager(Listener listener) {
        this.listener = listener;
    }

    public void loadPhotos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PhotoService photoService = retrofit.create(PhotoService.class);
        Observable<ResultData> photos = photoService.getPhotos();
        photos
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResultData, List<Photo>>() {
                    @Override
                    public List<Photo> apply(ResultData resultData) throws Exception {
                        return resultData.getPhotos().getList();
                    }
                }).subscribe(new Observer<List<Photo>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Photo> list) {
                resultList = list;
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
                    listener.onLoadingFinished(resultList);
                }
            }
        });

    }

    public interface Listener {
        void onLoadingFinished(List<Photo> list);

        void onLoadingFailed();

    }
}
