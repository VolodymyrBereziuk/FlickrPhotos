package org.vb.flickrdphotos.activity;

import org.vb.flickrdphotos.entity.Photo;
import com.codepath.apps.restclienttemplate.R;
import org.vb.flickrdphotos.models.CountManager;
import org.vb.flickrdphotos.models.PhotosManager;
import org.vb.flickrdphotos.ui.view.TinderCard;
import org.vb.flickrdphotos.util.Utils;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipeDirection;
import com.mindorks.placeholderview.SwipeDirectionalView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import java.util.List;

public class PhotosActivity extends AppCompatActivity implements TinderCard.Callback {

    private static final String TAG = PhotosActivity.class.getSimpleName();
    private static final int ANIMATION_DURATION = 300;

    private SwipeDirectionalView mSwipeView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView dislikesView;
    private TextView likesView;
    private Point cardViewHolderSize;

    private PhotosManager photosManager;
    private CountManager countManager;
    private boolean isToUndo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initManagers();
    }

    private void initView() {
        setContentView(R.layout.activity_photos);
        int bottomMargin = Utils.dpToPx(320);
        Point windowSize = Utils.getDisplaySize(getWindowManager());
        mSwipeView = (SwipeDirectionalView) findViewById(R.id.swipeView);
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setIsUndoEnabled(true)
                .setSwipeVerticalThreshold(Utils.dpToPx(50))
                .setSwipeHorizontalThreshold(Utils.dpToPx(50))
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x - Utils.dpToPx(32))
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setSwipeAnimTime(ANIMATION_DURATION)
                        .setRelativeScale(0.01f));
        cardViewHolderSize = new Point(windowSize.x, windowSize.y - bottomMargin);
        mSwipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                if (isToUndo) {
                    isToUndo = false;
                    mSwipeView.undoLastSwipe();
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photosManager.loadPhotos();
            }
        });

        dislikesView = (TextView) findViewById(R.id.txt_dislikes);
        likesView = (TextView) findViewById(R.id.txt_likes);

    }

    private void initManagers() {
        countManager = new CountManager();
        photosManager = new PhotosManager(new PhotosManager.Listener() {
            @Override
            public void onLoadingFinished(List<Photo> list) {
                mSwipeView.removeAllViews();
                swipeRefreshLayout.setRefreshing(false);
                for (Photo photo : list) {
                    mSwipeView.addView(new TinderCard(PhotosActivity.this, photo, cardViewHolderSize, PhotosActivity.this));
                }
            }

            @Override
            public void onLoadingFailed() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        photosManager.loadPhotos();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onSwipe(SwipeDirection direction) {
        if (direction.getDirection() == SwipeDirection.LEFT.getDirection()) {
            dislikesView.setText("Dislikes " + countManager.getDislikes());
        } else if (direction.getDirection() == SwipeDirection.RIGHT.getDirection()) {
            likesView.setText("Likes " + countManager.getLikes());
        } else {
            isToUndo = true;
        }
    }

}
