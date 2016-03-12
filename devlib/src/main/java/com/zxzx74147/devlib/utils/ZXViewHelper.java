package com.zxzx74147.devlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zxzx74147.devlib.ZXApplicationDelegate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhengxin on 15/9/16.
 */
public class ZXViewHelper {

    static int displayMetricsWidthPixels;
    static int displayMetricsHeightPixels;
    private static float displayMetricsDensity;


    public interface IViewProcess {
        public void processView(View view);
    }


    public interface DoubleClickListener {
        public void onDoubleClick(View view);
    }


    public static void dfsViewGroup(View view, IViewProcess process) {
        if (process == null || view == null) {
            return;
        }
        process.processView(view);
        if (view instanceof ViewGroup) {
            int childNum = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childNum; i++) {
                dfsViewGroup(((ViewGroup) view).getChildAt(i), process);
            }
        }
    }

    public static boolean fitRect(View view, Rect rect) {

        if (!(view.getParent() instanceof View)) {
            return false;
        }
        View parent = (View) view.getParent();
        boolean hasFit = false;
        int fitLeft = parent.getPaddingLeft();
        int fitRight = parent.getWidth() - parent.getPaddingRight();
        int fitTop = parent.getPaddingTop();
        int fitBottom = parent.getHeight() - parent.getPaddingBottom();
        if (rect.left < fitLeft) {
            rect.right -= rect.left - fitLeft;
            rect.left = fitLeft;
            hasFit = true;
        }
        if (rect.right > fitRight) {
            rect.left -= rect.right - fitRight;
            rect.right = fitRight;
            hasFit = true;
        }
        if (rect.top < fitTop) {
            rect.bottom -= rect.top - fitTop;
            rect.top = fitTop;
            hasFit = true;
        }
        if (rect.bottom > fitBottom) {
            rect.top -= rect.bottom - fitBottom;
            rect.bottom = fitBottom;
            hasFit = true;
        }
        return hasFit;
    }

    public static View getRootView(View view, int id) {
        while (view.getParent() != null) {
            if (view.getId() == id) {
                break;
            }
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                view = (ViewGroup) parent;
            } else {
                break;
            }
        }
        return view;
    }

    public static View getRootView(View view) {
        while (view.getParent() != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                view = (ViewGroup) parent;
            } else {
                break;
            }
        }
        return view;
    }

    public static void setBackground(View view, int background) {
        if (view == null) {
            return;
        }
        int left = view.getPaddingLeft();
        int right = view.getPaddingRight();
        int top = view.getPaddingTop();
        int bottom = view.getPaddingBottom();
        view.setBackgroundResource(background);
        view.setPadding(left, top, right, bottom);
    }

    public static void stopScroll(AbsListView view) {
        try {
            Field field = android.widget.AbsListView.class.getDeclaredField("mFlingRunnable");
            field.setAccessible(true);
            Object flingRunnable = field.get(view);
            if (flingRunnable != null) {
                Method method = Class.forName("android.widget.AbsListView$FlingRunnable").getDeclaredMethod("endFling");
                method.setAccessible(true);
                method.invoke(flingRunnable);
            }
        } catch (Exception e) {
        }
    }

    public static void setDoubleClickListener(View view, DoubleClickListener listener) {
        OnDoubleClickTouchListener mTouchListener = new OnDoubleClickTouchListener(listener);
        view.setOnTouchListener(mTouchListener);
    }

    public Bitmap getCache(View view) {
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();
        return bm;
    }

    public static void removeViewFromParent(View view) {
        if (view.getParent() == null) {
            return;
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        parent.removeView(view);
    }

    private static class OnDoubleClickTouchListener implements View.OnTouchListener {
        private long lastClickTime = 0;
        private static final long MAX_INV = 700;
        private DoubleClickListener mListener = null;
        private PointF mStartPoint = new PointF();

        public OnDoubleClickTouchListener(DoubleClickListener listener) {
            mListener = listener;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()) {
                mStartPoint.x = event.getRawX();
                mStartPoint.y = event.getRawY();

            } else if (MotionEvent.ACTION_UP == event.getAction()) {
                float len = Math.abs(mStartPoint.x - event.getRawX()) + Math.abs(mStartPoint.y - event.getRawY());
                if (len < 20) {
                    long now = System.currentTimeMillis();
                    if (now - lastClickTime < MAX_INV) {
                        if (mListener != null) {
                            mListener.onDoubleClick(v);
                        }
                        lastClickTime = 0;
                    } else {
                        lastClickTime = now;
                    }
                }
            }
            return true;
        }

    }


    public static void showViewAnim(View view, int id) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }
        Animation anim = AnimationUtils.loadAnimation(view.getContext(), id);
        view.setVisibility(View.VISIBLE);
        view.clearAnimation();
        view.startAnimation(anim);
    }

    public static void scrollToBottom(final ListView listview) {
        if (listview == null || listview.getAdapter() == null) {
            return;
        }
        BaseAdapter adapter = (BaseAdapter) listview.getAdapter();
        adapter.notifyDataSetChanged();
        final int count = listview.getAdapter().getCount();
        listview.setSelection(count);
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                int count = listview.getAdapter().getCount();
                listview.setSelection(count + 1);
            }
        });

    }

    public static void scrollToBottomSmooth(final ListView listview) {
        if (listview == null || listview.getAdapter() == null) {
            return;
        }
        final int count = listview.getAdapter().getCount();
        int last = listview.getLastVisiblePosition();
        if (count - last > 2 && count - 2 >= 0) {
            listview.setSelection(count - 2);
            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    int count = listview.getAdapter().getCount();
                    listview.smoothScrollToPosition(count);
                }
            });
        } else {
            listview.smoothScrollToPosition(count);
        }

    }


    public static void hideViewAnim(final View view, int id) {

        if (view == null) {
            return;
        }
        if (view.getVisibility() == View.GONE) {
            return;
        }
        Animation old = view.getAnimation();
        if (old != null && old.hasStarted() && old.hasEnded() == false) {
            return;
        }
        Animation anim = AnimationUtils.loadAnimation(view.getContext(), id);
        view.setVisibility(View.VISIBLE);
        view.clearAnimation();
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                });
            }
        });
        view.startAnimation(anim);
    }

    public static void locateCursur(final EditText text) {
        if (text == null) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Editable b = text.getText();
                text.setSelection(b.length());
            }
        }, 100);
    }


    public static View inflateLayout(Context context, int id, ViewGroup view) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View result = inflate.inflate(id, view);
        return result;
    }

    public static void setEnable(View view, final boolean enable) {
        dfsViewGroup(view, new IViewProcess() {
            @Override
            public void processView(View view) {
                view.setEnabled(enable);
            }
        });
    }

    public static void setSelected(View view, final boolean selection) {
        dfsViewGroup(view, new IViewProcess() {
            @Override
            public void processView(View view) {
                view.setSelected(selection);
            }
        });
    }


    public static int mesureGridView(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            //利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = Integer.valueOf(column.get(gridView).toString());
            //利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }

        int totalHeight = 0;
        int totalWidth = 0;
        for (int i = 0; i < rows; i++) { //只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        for (int i = 0; i < columns; i++) { //只计算每项宽度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalWidth += listItem.getMeasuredWidth(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        totalHeight = totalHeight + horizontalBorderHeight * (rows - 1) + gridView.getPaddingTop() + gridView.getPaddingBottom();//最后加上分割线总高度
        totalWidth = totalWidth + horizontalBorderHeight * (columns - 1) + gridView.getPaddingRight() + gridView.getPaddingLeft();
        params.width = totalWidth;
        params.height = totalHeight;
        gridView.setLayoutParams(params);

        return totalHeight;
//        return params.height;
    }


    public static int measureListView(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        //listview 没有经过测算时候，需先测算
        if (listView.getMeasuredWidth() == 0) {
            listView.measure(View.MeasureSpec.makeMeasureSpec(displayMetricsWidthPixels, View.MeasureSpec.EXACTLY), 0);
        }
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(View.MeasureSpec.makeMeasureSpec(listView.getMeasuredWidth(), View.MeasureSpec.EXACTLY), 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        totalHeight += (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        totalHeight += listView.getPaddingTop() + listView.getPaddingBottom();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (params != null) {
            params.height = totalHeight;
            // listView.getDividerHeight()获取子项间分隔符占用的高度
            // params.height最后得到整个ListView完整显示需要的高度
            listView.setLayoutParams(params);
        }
        return totalHeight;
    }

    public static void startFramAnim(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).start();
        }
    }

    public static void stopFramAnim(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).stop();
        }
    }

    public static int getAnimationTime(AnimationDrawable animation) {
        if (animation == null) {
            return 0;
        }
        int time = 0;
        for (int i = 0; i < animation.getNumberOfFrames(); i++) {
            time += animation.getDuration(i);
        }
        return time;
    }

    public static Bitmap getViewBitmap(View view) {
        Bitmap bm = null;
        int width = view.getWidth();
        int height = view.getHeight();
        bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        view.draw(canvas);
        return bm;
    }

    public static BitmapDrawable getViewDrawble(View view) {
        Bitmap bm = null;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        view.layout(0, 0, width, height);//very important
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        BitmapDrawable drawable = new BitmapDrawable(view.getDrawingCache());
        drawable.setBounds(0, 0, width, height);
        return drawable;

//        //方法二
//        bm = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bm);
//
//        view.draw(canvas);
//        BitmapDrawable drawable = new BitmapDrawable(view.getResources(), bm);
//        drawable.setBounds(0, 0, width, height);
//        return drawable;
    }


    static {
        DisplayMetrics displayMetrics = null;
        displayMetrics = ZXApplicationDelegate.getApplication().getResources().getDisplayMetrics();
        displayMetricsDensity = displayMetrics.density;
        displayMetricsWidthPixels = displayMetrics.widthPixels;
        displayMetricsHeightPixels = displayMetrics.heightPixels;

    }

    public static int getScreenW()

    {
        return displayMetricsWidthPixels;
    }

    public static int getScreenH()

    {
        return displayMetricsHeightPixels;
    }

    public static int dip2px(Context context, float dipValue) {


        return (int) (dipValue * displayMetricsDensity + 0.5f);
    }


    public static void showSoftKey(final EditText editText) {
        editText.requestFocus();

        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager mgr = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);

    }

}
