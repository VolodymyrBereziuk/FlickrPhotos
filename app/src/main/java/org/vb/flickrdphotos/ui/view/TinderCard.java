package org.vb.flickrdphotos.ui.view;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;

import com.mindorks.placeholderview.SwipeDirection;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeTouch;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;
import com.mindorks.placeholderview.annotations.swipe.SwipingDirection;

import org.vb.flickrdphotos.entity.Photo;


@Layout(R.layout.tinder_card_view)
public class TinderCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @SwipeView
    private FrameLayout mSwipeView;

    private Photo photo;
    private Context mContext;
    private Point mCardViewHolderSize;
    private Callback mCallback;
    private SwipeDirection lastSwipeDirection;

    public TinderCard(Context context, Photo photo, Point cardViewHolderSize, Callback callback) {
        this.mContext = context;
        this.photo = photo;
        this.mCardViewHolderSize = cardViewHolderSize;
        this.mCallback = callback;
    }

    @Resolve
    private void onResolved() {
        Glide.with(mContext).load(photo.getUrl(mContext)).into(profileImageView);
    }

    @Click(R.id.profileImageView)
    private void onClick() {
        Log.d("EVENT", "profileImageView click");
    }

    @SwipeOutDirectional
    private void onSwipeOutDirectional(SwipeDirection direction) {
        Log.e("DEBUG", "SwipeOutDirectional " + direction.name());
        mCallback.onSwipe(lastSwipeDirection);
    }

    @SwipeInDirectional
    private void onSwipeInDirectional(SwipeDirection direction) {
        Log.e("DEBUG", "SwipeInDirectional " + direction.name());
        mCallback.onSwipe(lastSwipeDirection);
    }


    @SwipingDirection
    private void onSwipingDirection(SwipeDirection direction) {
        Log.d("DEBUG", "SwipingDirection " + direction.name());
        if (direction != SwipeDirection.BOTTOM && direction != SwipeDirection.TOP) {
            if (direction == SwipeDirection.LEFT_BOTTOM || direction == SwipeDirection.LEFT || direction == SwipeDirection.LEFT_TOP) {
                lastSwipeDirection = SwipeDirection.LEFT;
            }
            if (direction == SwipeDirection.RIGHT_BOTTOM || direction == SwipeDirection.RIGHT || direction == SwipeDirection.RIGHT_TOP) {
                lastSwipeDirection = SwipeDirection.RIGHT;
            }
        }
    }


    @SwipeTouch
    private void onSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent) {
    }

    public interface Callback {
        void onSwipe(SwipeDirection direction);
    }
}
