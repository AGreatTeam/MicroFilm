package com.zsc.game.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.zsc.game.R;
import com.zsc.game.ui.adapter.SwipeDeckAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 苏照亮 2017/12/17.
 */
public class SwipeCardView extends LinearLayout {

    private List<View> viewList = new ArrayList<>(); // 存放的是每一层的view，从顶到底
    private List<View> releasedViewList = new ArrayList<>(); // 手指松开后存放的view列表

    private int initCenterViewX = 0, initCenterViewY = 0; // 最初时，中间View的x位置,y位置
    private int mWidth = 0; // swipeCardsView的宽度
    private int mHeight = 0; // swipeCardsView的高度
    private int mCardWidth = 0; // 每一个子View对应的宽度

    private static final int MAX_SLIDE_DISTANCE_LINKAGE = 400; // 水平距离+垂直距离

    private int yOffsetStep = 0; // view叠加垂直偏移量的步长
    private float scaleOffsetStep = 0f; // view叠加缩放的步长
    private int alphaOffsetStep = 0; //view叠加透明度的步长

    private static final int X_VEL_THRESHOLD = 900;
    private static final int X_DISTANCE_THRESHOLD = 300;

    private CardsSlideListener mCardsSlideListener; // 回调接口
    private List<?> dataList; // 存储的数据链表
    private int showingIndex = 0; // 当前正在显示的小项
    private OnClickListener btnListener;

    private BaseCardAdapter mAdapter;
    private Scroller mScroller;
    private int mTouchSlop;
    private int mLastY = -1; // save event y
    private int mLastX = -1; // save event x
    private int mInitialMotionY;
    private int mInitialMotionX;
    private final int SCROLL_DURATION = 300; // scroll back duration

    public SwipeCardView(Context context) {
        this(context, null);
    }

    public SwipeCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipCardsView);
        yOffsetStep = (int) a.getDimension(R.styleable.SwipCardsView_yOffsetStep, yOffsetStep);
        alphaOffsetStep = a.getInt(R.styleable.SwipCardsView_alphaOffsetStep, alphaOffsetStep);
        scaleOffsetStep = a.getFloat(R.styleable.SwipCardsView_scaleOffsetStep, scaleOffsetStep);

        a.recycle();

        btnListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击的是卡片
                if (null != mCardsSlideListener) {
                    mCardsSlideListener.onItemClick(view, showingIndex);
                }
            }
        };
        mScroller = new Scroller(getContext(), sInterpolator);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mMaxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mMinVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();

    }

    /**
     * Interpolator defining the animation curve for mScroller
     */
    private static final Interpolator sInterpolator = new Interpolator() {

        private float mTension = 1.6f;

        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * ((mTension + 1) * t + mTension) + 1.0f;
        }
    };

    private int getCardLayoutId(int layoutid) {
        String resourceTypeName = getContext().getResources().getResourceTypeName(layoutid);
        if (!resourceTypeName.contains("layout")) {
            String errorMsg = getContext().getResources().getResourceName(layoutid) + " is a illegal layoutid , please check your layout id first !";
            throw new RuntimeException(errorMsg);
        }
        return layoutid;
    }

    private void bindCardData(int position, View cardview) {
        if (mAdapter != null) {
            mAdapter.onBindData(position, cardview, dataList.get(position));
        }
        cardview.setVisibility(View.VISIBLE);
    }

    public void notifyDatasetChanged(BaseCardAdapter adapter) {
        if (dataList != null) {
            if (canResetView()) {
                removeAllViews();
                showingIndex = 0;
            }
        }
        setAdapter(adapter);
    }

    public void setAdapter(BaseCardAdapter adapter) {
        if (adapter == null) {
            throw new RuntimeException("adapter==null");
        }
        mAdapter = adapter;
        dataList = mAdapter.getData();
        if (dataList == null) {
            throw new RuntimeException("mAdapter.getData() return null");
        }
        viewList.clear();
        int cardVisibleCount = mAdapter.getVisibleCardCount();
        cardVisibleCount = Math.min(cardVisibleCount, dataList.size());
        for (int i = 0; i < cardVisibleCount; i++) {
            View childView = LayoutInflater.from(getContext()).inflate(getCardLayoutId(mAdapter.getCardLayoutId()), this, false);
            if (childView == null) {
                return;
            }
            bindCardData(i, childView);
            viewList.add(childView);
            childView.setOnClickListener(btnListener);
            addView(childView, 0);
        }
        if (null != mCardsSlideListener) {
            mCardsSlideListener.onShow(0);
        }
    }

    private boolean hasTouchTopView;
    private VelocityTracker mVelocityTracker;
    private float mMaxVelocity;
    private float mMinVelocity;
    private boolean isIntercepted = false;
    private boolean isTouching = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //拦截resideLayout的事件并消费
        getParent().requestDisallowInterceptTouchEvent(true);
        final int action = ev.getActionMasked();
        acquireVelocityTracker(ev);
        int deltaY = 0;
        int deltaX = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mScroller.abortAnimation();
                resetViewGroup();
                isTouching = true;
                hasTouchTopView = false;
                mLastY = (int) ev.getRawY();
                mLastX = (int) ev.getRawX();
                mInitialMotionY = mLastY;
                mInitialMotionX = mLastX;
                break;
            case MotionEvent.ACTION_MOVE:
                mLastMoveEvent = ev;
                int currentY = (int) ev.getRawY();
                int currentX = (int) ev.getRawX();
                deltaY = currentY - mLastY;
                deltaX = currentX - mLastX;
                mLastY = currentY;
                mLastX = currentX;
                if (!isIntercepted) {
                    int distanceX = Math.abs(currentX - mInitialMotionX);
                    int distanceY = Math.abs(currentY - mInitialMotionY);
                    if (distanceX * distanceX + distanceY + distanceY >= mTouchSlop * mTouchSlop) {
                        isIntercepted = true;
                    } else {
                        return super.dispatchTouchEvent(ev);
                    }
                }

                if (isIntercepted && (hasTouchTopView || isTouchTopView(ev))) {
                    hasTouchTopView = true;
                    moveTopView(deltaX, deltaY);
                    sendCancelEvent();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                hasTouchTopView = false;
                isTouching = false;
                isIntercepted = false;
                mHasSendCancelEvent = false;
                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                final float velocityX = mVelocityTracker.getXVelocity();
                final float velocityY = mVelocityTracker.getYVelocity();
                final float xvel = clampMag(velocityX, mMinVelocity, mMaxVelocity);
                final float yvel = clampMag(velocityY, mMinVelocity, mMaxVelocity);

                releaseTopView(xvel, yvel);
                releaseVelocityTracker();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private MotionEvent mLastMoveEvent;
    private boolean mHasSendCancelEvent = false;

    private void sendCancelEvent() {
        if (!mHasSendCancelEvent) {
            mHasSendCancelEvent = true;
            MotionEvent last = mLastMoveEvent;
            MotionEvent e = MotionEvent.obtain(
                    last.getDownTime(),
                    last.getEventTime()
                            + ViewConfiguration.getLongPressTimeout(),
                    MotionEvent.ACTION_CANCEL, last.getX(), last.getY(),
                    last.getMetaState());
            dispatchTouchEventSupper(e);
        }
    }

    public boolean dispatchTouchEventSupper(MotionEvent e) {
        return super.dispatchTouchEvent(e);
    }

    private void releaseTopView(float xvel, float yvel) {
        View TopView = getTopView();
        if (TopView != null) {
            onTopViewReleased(TopView, xvel, yvel);
        }
    }

    /**
     * 是否摸到了某个view
     *
     * @param ev
     * @return
     */
    private boolean isTouchTopView(MotionEvent ev) {
        View topView = getTopView();
        if (topView != null) {
            Rect bounds = new Rect();
            topView.getGlobalVisibleRect(bounds);
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            if (bounds.contains(x, y)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void moveTopView(int deltaX, int deltaY) {
        View topView = getTopView();
        if (topView != null) {
            topView.offsetLeftAndRight(deltaX);
            topView.offsetTopAndBottom(deltaY);
            processLinkageView(topView);
        }
    }

    private View getTopView() {
        if (viewList.size() > 0) {
            return viewList.get(0);
        }
        return null;
    }


    public void startScrollTopView(int finalLeft, int finalTop, int duration, SlideType flyType) {
        View topView = getTopView();
        if (topView == null) {
            return;
        }
        if (finalLeft != initCenterViewX) {
            releasedViewList.add(topView);
        }
        final int startLeft = topView.getLeft();
        final int startTop = topView.getTop();
        final int dx = finalLeft - startLeft;
        final int dy = finalTop - startTop;
        if (dx != 0 || dy != 0) {
            mScroller.startScroll(topView.getLeft(), topView.getTop(), dx, dy, duration);
            ViewCompat.postInvalidateOnAnimation(this);
        }
        if (flyType != SlideType.NONE && mCardsSlideListener != null) {
            mCardsSlideListener.onCardVanish(showingIndex, flyType);
        }
    }

    /**
     * @param event 向VelocityTracker添加MotionEvent
     * @see VelocityTracker#obtain()
     * @see VelocityTracker#addMovement(MotionEvent)
     */
    private void acquireVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 释放VelocityTracker
     *
     * @see VelocityTracker#clear()
     * @see VelocityTracker#recycle()
     */
    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * Clamp the magnitude of value for absMin and absMax.
     * If the value is below the minimum, it will be clamped to zero.
     * If the value is above the maximum, it will be clamped to the maximum.
     *
     * @param value  Value to clamp
     * @param absMin Absolute value of the minimum significant value to return
     * @param absMax Absolute value of the maximum value to return
     * @return The clamped value with the same sign as <code>value</code>
     */
    private float clampMag(float value, float absMin, float absMax) {
        final float absValue = Math.abs(value);
        if (absValue < absMin) return 0;
        if (absValue > absMax) return value > 0 ? absMax : -absMax;
        return value;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0), resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (hasTouchTopView) {
            return;
        }
        int size = viewList.size();
        if (size == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            View child = viewList.get(i);
            layoutChild(child, i);
        }
        // 初始化一些中间参数
        initCenterViewX = viewList.get(0).getLeft();
        initCenterViewY = viewList.get(0).getTop();
        mCardWidth = viewList.get(0).getMeasuredWidth();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void layoutChild(View child, int index) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int width = child.getMeasuredWidth();
        int height = child.getMeasuredHeight();

        int gravity = lp.gravity;
        if (gravity == -1) {
            gravity = Gravity.TOP | Gravity.START;
        }

        int layoutDirection = getLayoutDirection();
        final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
        final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

        int childLeft;
        int childTop;
        switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.CENTER_HORIZONTAL:
                childLeft = (getWidth() + getPaddingLeft() - getPaddingRight() - width) / 2 +
                        lp.leftMargin - lp.rightMargin;
                break;
            case Gravity.END:
                childLeft = getWidth() + getPaddingRight() - width - lp.rightMargin;
                break;
            case Gravity.START:
            default:
                childLeft = getPaddingLeft() + lp.leftMargin;
                break;
        }
        switch (verticalGravity) {
            case Gravity.CENTER_VERTICAL:
                childTop = (getHeight() + getPaddingTop() - getPaddingBottom() - height) / 2 +
                        lp.topMargin - lp.bottomMargin;
                break;
            case Gravity.BOTTOM:
                childTop = getHeight() - getPaddingBottom() - height - lp.bottomMargin;
                break;
            case Gravity.TOP:
            default:
                childTop = getPaddingTop() + lp.topMargin;
                break;
        }
        child.layout(childLeft, childTop, childLeft + width, childTop + height);
        int offset = yOffsetStep * index;
        float scale = 1 - scaleOffsetStep * index;
        float alpha = 1.0f * (100 - alphaOffsetStep * index) / 100;
        child.offsetTopAndBottom(offset);
        child.setScaleX(scale);
        child.setScaleY(scale);
        child.setAlpha(alpha);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            View topView = getTopView();
            if (topView == null) {
                return;
            }
            final int x = mScroller.getCurrX();
            final int y = mScroller.getCurrY();
            final int dx = x - topView.getLeft();
            final int dy = y - topView.getTop();
            if (x != mScroller.getFinalX() || y != mScroller.getFinalY()) {
                moveTopView(dx, dy);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            onAnimalStop();
        }
    }

    private void onAnimalStop() {
        if (canResetView()) {
            resetViewGroup();
        }
    }

    private boolean canResetView() {
        return !mScroller.computeScrollOffset() && !isTouching;
    }

    /**
     * 对View重新排序
     */
    private void resetViewGroup() {
        if (releasedViewList.size() == 0) {
            if (viewList.size() != 0) {
                View topView = getTopView();
                if (topView != null) {
                    if (topView.getLeft() != initCenterViewX || topView.getTop() != initCenterViewY) {
                        topView.offsetLeftAndRight(initCenterViewX - topView.getLeft());
                        topView.offsetTopAndBottom(initCenterViewY - topView.getTop());
                    }
                }
            }
            return;
        }
        View changedView = releasedViewList.get(0);
        if (changedView.getLeft() == initCenterViewX) {
            releasedViewList.remove(0);
            return;
        }
        int viewSize = viewList.size();
        removeViewInLayout(changedView);
        addViewInLayout(changedView, 0, changedView.getLayoutParams(), true);
        requestLayout();
//            removeView(changedView);
//            addView(changedView,0);

        int newIndex = showingIndex + viewSize;
        if (newIndex < dataList.size()) {
            bindCardData(newIndex, changedView);
        } else {
            changedView.setVisibility(View.GONE);
        }

        viewList.remove(changedView);
        viewList.add(changedView);
        releasedViewList.remove(0);

        if (showingIndex + 1 < dataList.size()) {
            showingIndex++;
        }
        if (null != mCardsSlideListener) {
            mCardsSlideListener.onShow(showingIndex);
        }
    }

    /**
     * 顶层卡片View位置改变，底层的位置需要调整
     *
     * @param changedView 顶层的卡片view
     */
    private void processLinkageView(View changedView) {
        int changeViewLeft = changedView.getLeft();
        int changeViewTop = changedView.getTop();
        int distance = Math.abs(changeViewTop - initCenterViewY) + Math.abs(changeViewLeft - initCenterViewX);
        float rate = distance / (float) MAX_SLIDE_DISTANCE_LINKAGE;

        for (int i = 1; i < viewList.size(); i++) {
            float rate3 = rate - 0.2f * i;
            if (rate3 > 1) {
                rate3 = 1;
            } else if (rate3 < 0) {
                rate3 = 0;
            }
            ajustLinkageViewItem(changedView, rate3, i);
        }
    }

    // 由index对应view变成index-1对应的view
    private void ajustLinkageViewItem(View changedView, float rate, int index) {
        int changeIndex = viewList.indexOf(changedView);

        int initPosY = yOffsetStep * index;
        float initScale = 1 - scaleOffsetStep * index;
        float initAlpha = 1.0f * (100 - alphaOffsetStep * index) / 100;

        int nextPosY = yOffsetStep * (index - 1);
        float nextScale = 1 - scaleOffsetStep * (index - 1);
        float nextAlpha = 1.0f * (100 - alphaOffsetStep * (index - 1)) / 100;

        int offset = (int) (initPosY + (nextPosY - initPosY) * rate);
        float scale = initScale + (nextScale - initScale) * rate;
        float alpha = initAlpha + (nextAlpha - initAlpha) * rate;

        View ajustView = viewList.get(changeIndex + index);
        ajustView.offsetTopAndBottom(offset - ajustView.getTop() + initCenterViewY);
        ajustView.setScaleX(scale);
        ajustView.setScaleY(scale);
        ajustView.setAlpha(alpha);
    }

    /**
     * 松手时处理滑动到边缘的动画
     *
     * @param xvel X方向上的滑动速度
     */
    private void onTopViewReleased(View changedView, float xvel, float yvel) {
        int finalX = initCenterViewX;
        int finalY = initCenterViewY;
        SlideType flyType = SlideType.NONE;

        int dx = changedView.getLeft() - initCenterViewX;
        int dy = changedView.getTop() - initCenterViewY;
        if (dx == 0) {
            // 由于dx作为分母，此处保护处理
            dx = 1;
        }
        if (dx > X_DISTANCE_THRESHOLD || (xvel > X_VEL_THRESHOLD && dx > 0)) {//向右边滑出
            finalX = mWidth;
            finalY = dy * (mCardWidth + initCenterViewX) / dx + initCenterViewY;
            flyType = SlideType.RIGHT;
        } else if (dx < -X_DISTANCE_THRESHOLD || (xvel < -X_VEL_THRESHOLD && dx < 0)) {//向左边滑出
            finalX = -mCardWidth;
            finalY = dy * (mCardWidth + initCenterViewX) / (-dx) + dy + initCenterViewY;
            flyType = SlideType.LEFT;
        }

        if (finalY > mHeight) {
            finalY = mHeight;
        } else if (finalY < -mHeight / 2) {
            finalY = -mHeight / 2;
        }
        startScrollTopView(finalX, finalY, SCROLL_DURATION, flyType);
    }

    /**
     * use this method to Slide the card out of the screen
     *
     * @param type
     */
    public void slideCardOut(SlideType type) {
        mScroller.abortAnimation();
        resetViewGroup();
        View topview = getTopView();
        if (topview == null) {
            return;
        }
        if (releasedViewList.contains(topview) || type == SlideType.NONE) {
            return;
        }
        int finalX = 0;
        switch (type) {
            case LEFT:
                finalX = -mCardWidth;
                break;
            case RIGHT:
                finalX = mWidth;
                break;
        }
        if (finalX != 0) {
            startScrollTopView(finalX, initCenterViewY + mHeight, SCROLL_DURATION, type);
        }
    }

    /**
     * 这是View的方法，该方法不支持android低版本（2.2、2.3）的操作系统，所以手动复制过来以免强制退出
     */
    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result | (childMeasuredState & MEASURED_STATE_MASK);
    }

    /**
     * 设置卡片操作回调
     *
     * @param cardsSlideListener 回调接口
     */
    public void setCardsSlideListener(CardsSlideListener cardsSlideListener) {
        this.mCardsSlideListener = cardsSlideListener;
    }

    /**
     * 卡片回调接口
     */
    public interface CardsSlideListener {
        /**
         * 新卡片显示回调
         *
         * @param index 最顶层显示的卡片的index
         */
        void onShow(int index);

        /**
         * 卡片飞向两侧回调
         *
         * @param index 飞向两侧的卡片数据index
         * @param type  飞向哪一侧{@link SlideType#LEFT}或{@link SlideType#RIGHT}
         */
        void onCardVanish(int index, SlideType type);

        /**
         * 卡片点击事件
         *
         * @param cardImageView 卡片上的图片view
         * @param index         点击到的index
         */
        void onItemClick(View cardImageView, int index);
    }

    /**
     * <p>
     * {@link #LEFT} 从屏幕左边滑出
     * </p>
     * {@link #RIGHT} 从屏幕右边滑出
     */
    public enum SlideType {
        LEFT, RIGHT, NONE
    }
}
