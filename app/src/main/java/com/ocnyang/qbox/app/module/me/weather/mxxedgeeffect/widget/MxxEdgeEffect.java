package com.ocnyang.qbox.app.module.me.weather.mxxedgeeffect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;


public class MxxEdgeEffect {
   // private static final String TAG = "MxxEdgeEffect";

    // Time it will take the effect to fully recede in ms  后退动画的时间（从松手到恢复原状）
    private static final int RECEDE_TIME = 200;// 1000;

    // Time it will take before a pulled glow begins receding in ms
    private static final int PULL_TIME = Integer.MAX_VALUE;// 167;

    // Time it will take in ms for a pulled glow to decay to partial strength before release
    private static final int PULL_DECAY_TIME = 200;// 1000;

//    private static final float MAX_ALPHA = 1.f;
//    private static final float HELD_EDGE_SCALE_Y = 0.5f;

//    private static final float MAX_GLOW_HEIGHT = 40f;// 4.f;

    private static final float PULL_GLOW_BEGIN = 0f;// 1.f;
//    private static final float PULL_EDGE_BEGIN = 0.6f;

    // Minimum velocity that will be absorbed
    private static final int MIN_VELOCITY = 100;
    // Maximum velocity, clamps at this value
    private static final int MAX_VELOCITY = 10000;

    private static final float EPSILON = 0.001f;

//    private final Drawable mEdge;
//    private final Drawable mGlow;
//    private int mWidth;
//    private int mHeight;
//    private int mX;
//    private int mY;
//    private static final int MIN_WIDTH = 300;
//    private final int mMinWidth;

//    private float mEdgeAlpha;
//    private float mEdgeScaleY;
//    private float mGlowAlpha;
    private float mGlowScaleY;

//    private float mEdgeAlphaStart;
//    private float mEdgeAlphaFinish;
//    private float mEdgeScaleYStart;
//    private float mEdgeScaleYFinish;
//    private float mGlowAlphaStart;
//    private float mGlowAlphaFinish;
    private float mGlowScaleYStart;
    private float mGlowScaleYFinish;

    private long mStartTime;
    private float mDuration;

    private final Interpolator mInterpolator;

    private static final int STATE_IDLE = 0;
    private static final int STATE_PULL = 1;
    private static final int STATE_ABSORB = 2;
    private static final int STATE_RECEDE = 3;
    private static final int STATE_PULL_DECAY = 4;

    // How much dragging should effect the height of the edge image.
    // Number determined by user testing.
//    private static final int PULL_DISTANCE_EDGE_FACTOR = 7;

    // How much dragging should effect the height of the glow image.
    // Number determined by user testing.
    //相对于pull的缩小
    private static final float PULL_DISTANCE_GLOW_FACTOR = 0.4f;
//    private static final float PULL_DISTANCE_ALPHA_GLOW_FACTOR = 1.1f;
//
//    private static final int VELOCITY_EDGE_FACTOR = 8;
//    private static final int VELOCITY_GLOW_FACTOR = 12;

    private int mState = STATE_IDLE;

    private float mPullDistance;
    
//    private final Rect mBounds = new Rect();

//    private final int mEdgeHeight;
//    private final int mGlowHeight;
//    private final int mGlowWidth;
//    private final int mMaxEffectHeight;

    /**
     * Construct a new EdgeEffect with a theme appropriate for the provided context.
     * @param context Context used to provide theming and resource information for the EdgeEffect
     */
    public MxxEdgeEffect(Context context) {
//        final Resources res = context.getResources();
//        final float density = res.getDisplayMetrics().density;
//        mEdgeHeight = (int) (10 * density + 0.5f);// mEdge.getIntrinsicHeight();
//        mGlowHeight = (int) (114 * density + 0.5f); //gf mGlow.getIntrinsicHeight();
//        mGlowWidth = (int) (522 * density + 0.5f);//mGlow.getIntrinsicWidth();

//        mMaxEffectHeight = (int) ( mGlowHeight * MAX_GLOW_HEIGHT);
//        mMinWidth = (int) (res.getDisplayMetrics().density * MIN_WIDTH + 0.5f);
        mInterpolator = new DecelerateInterpolator();
    }
    
    public boolean isFinished() {
        return mState == STATE_IDLE;
    }

    /**
     * Immediately finish the current animation.
     * After this call {@link #isFinished()} will return true.
     */
    public void finish() {
        mState = STATE_IDLE;
    }

    /**
     * A view should call this when content is pulled away from an edge by the user.
     * This will update the state of the current visual effect and its associated animation.
     * The host view should always {@link android.view.View#invalidate()} after this
     * and draw the results accordingly.
     *
     * @param deltaDistance Change in distance since the last call. Values may be 0 (no change) to
     *                      1.f (full length of the view) or negative values to express change
     *                      back toward the edge reached to initiate the effect.
     */
    public boolean onPull(float deltaDistance) {
        final long now = AnimationUtils.currentAnimationTimeMillis();
        if (mState == STATE_PULL_DECAY && now - mStartTime < mDuration) {
            return true;
        }
        if (mState != STATE_PULL) {
            mGlowScaleY = PULL_GLOW_BEGIN;
        }
        mState = STATE_PULL;

        mStartTime = now;
        mDuration = PULL_TIME;

        mPullDistance += deltaDistance;

        float glowChange = Math.abs(deltaDistance);
        if (deltaDistance > 0 && mPullDistance < 0) {
            glowChange = -glowChange;
        }
        if (mPullDistance == 0) {
            mGlowScaleY = 0;
        }

        // Do not allow glow to get larger than MAX_GLOW_HEIGHT.
        mGlowScaleYFinish = mGlowScaleY = mGlowScaleYStart =  Math.max( 0, mGlowScaleY + glowChange * PULL_DISTANCE_GLOW_FACTOR);

//        mEdgeAlphaFinish = mEdgeAlpha;
//        mEdgeScaleYFinish = mEdgeScaleY;
//        mGlowAlphaFinish = mGlowAlpha;
        
        return true;
    }

    /**
     * Call when the object is released after being pulled.
     * This will begin the "decay" phase of the effect. After calling this method
     * the host view should {@link android.view.View#invalidate()} and thereby
     * draw the results accordingly.
     */
    public boolean onRelease() {
        mPullDistance = 0;

        if (mState != STATE_PULL && mState != STATE_PULL_DECAY) {
            return isFinished();
        }

        mState = STATE_RECEDE;
        
        mGlowScaleYStart = mGlowScaleY;

        mGlowScaleYFinish = 0.f;

        mStartTime = AnimationUtils.currentAnimationTimeMillis();
        mDuration = RECEDE_TIME;
        
        return isFinished();
    }

   
    public void onAbsorb(int velocity) {
        mState = STATE_ABSORB;
        velocity = Math.min(Math.max(MIN_VELOCITY, Math.abs(velocity)), MAX_VELOCITY);

        mStartTime = AnimationUtils.currentAnimationTimeMillis();
        mDuration = 0.15f + (velocity * 0.02f);

        // The edge should always be at least partially visible, regardless
        // of velocity.
//        mEdgeAlphaStart = 0.f;
//        mEdgeScaleY = mEdgeScaleYStart = 0.f;
        // The glow depends more on the velocity, and therefore starts out
        // nearly invisible.
//        mGlowAlphaStart = 0.3f;
        mGlowScaleYStart = 0.f;

        // Factor the velocity by 8. Testing on device shows this works best to
        // reflect the strength of the user's scrolling.
//        mEdgeAlphaFinish = Math.max(0, Math.min(velocity * VELOCITY_EDGE_FACTOR, 1));
        // Edge should never get larger than the size of its asset.
//        mEdgeScaleYFinish = Math.max(
//                HELD_EDGE_SCALE_Y, Math.min(velocity * VELOCITY_EDGE_FACTOR, 1.f));

        // Growth for the size of the glow should be quadratic to properly
        // respond
        // to a user's scrolling speed. The faster the scrolling speed, the more
        // intense the effect should be for both the size and the saturation.
        mGlowScaleYFinish = Math.min(0.025f + (velocity * (velocity / 100) * 0.00015f), 1.75f);
        // Alpha should change for the glow as well as size.
//        mGlowAlphaFinish = Math.max(
//                mGlowAlphaStart, Math.min(velocity * VELOCITY_GLOW_FACTOR * .00001f, MAX_ALPHA));
    }


    
    public boolean draw(Canvas canvas) {
        update();
        if (mState == STATE_RECEDE && mGlowScaleY == 0 ) {
            mState = STATE_IDLE;
        }
        return mState != STATE_IDLE;
    }
    
    public float getTranslateDiatance(){
    	return mGlowScaleY;
//    	return (int) Math.min(
//                mGlowHeight * mGlowScaleY * mGlowHeight / mGlowWidth * 0.6f,
//                mGlowHeight * MAX_GLOW_HEIGHT);
    }

    private void update() {
        final long time = AnimationUtils.currentAnimationTimeMillis();
        final float t = Math.min((time - mStartTime) / mDuration, 1.f);
        final float interp = mInterpolator.getInterpolation(t);
        mGlowScaleY = mGlowScaleYStart + (mGlowScaleYFinish - mGlowScaleYStart) * interp;

        if (t >= 1.f - EPSILON) {
            switch (mState) {
                case STATE_ABSORB:
                    mState = STATE_RECEDE;
                    mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    mDuration = RECEDE_TIME;

                    mGlowScaleYStart = mGlowScaleY;

                    // After absorb, the glow and edge should fade to nothing.
                    mGlowScaleYFinish = 0.f;
                    break;
                case STATE_PULL:
                    mState = STATE_PULL_DECAY;
                    mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    mDuration = PULL_DECAY_TIME;

                    mGlowScaleYStart = mGlowScaleY;

                    // After pull, the glow and edge should fade to nothing.
                    mGlowScaleYFinish = 0.f;
                    break;
                case STATE_PULL_DECAY:
                    mState = STATE_RECEDE;
                    break;
                case STATE_RECEDE:
                    mState = STATE_IDLE;
                    break;
            }
        }
    }
}
