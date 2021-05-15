package net.smartgekko.mytimeline.ui.main;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.smartgekko.mytimeline.Event;
import net.smartgekko.mytimeline.EventList;
import net.smartgekko.mytimeline.LockableNestedScrollView;
import net.smartgekko.mytimeline.R;
import net.smartgekko.mytimeline.SimpleEvent;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainFragment extends Fragment implements View.OnTouchListener {

    private MainViewModel mViewModel;
    private ArrayList<Event> eventList;
    private LinearLayout eventRootLayout;
    private ConstraintLayout mainLayout;
    private LockableNestedScrollView rootScroll;
    private int rootLayoutWidth;
    int xDelta;
    int yDelta;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.main_fragment, container, false);
        mainLayout=view.findViewById(R.id.main);
        eventRootLayout=view.findViewById(R.id.eventsRootLayout);
        rootScroll=(LockableNestedScrollView)view.findViewById(R.id.rootScroll);
        eventList=new EventList().getEventList();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        rootLayoutWidth = displayMetrics.widthPixels;


        for(Event item:eventList){
            int startTimePoint=item.getStartTime();
            int duration=item.getDuration();
            int endTimePoint=startTimePoint+duration;


            LinearLayout ll = new LinearLayout( eventRootLayout.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.topMargin=startTimePoint;
            layoutParams.width=(rootLayoutWidth-100)/eventList.size();
            layoutParams.height= duration;
            ll.setPadding(1,0,1,0);
            GradientDrawable border = new GradientDrawable();
            border.setColor(0xFFFFFFFF); //white background
            border.setStroke(1, 0xFF000000);
            ll.setBackground(border);
            ll.setLayoutParams(layoutParams);
            ll.setOnTouchListener(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            eventRootLayout.addView(ll);
        }


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        LinearLayout ll = (LinearLayout)view;
        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();


        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll.getLayoutParams();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                rootScroll.setScrollingEnabled(false);

                GradientDrawable border = new GradientDrawable();
                border.setColor(0xFFFFFF00); //white background
                border.setStroke(1, 0xFF000000);
                ll.setBackground(border);
                yDelta = y - layoutParams.topMargin;
                break;

            case MotionEvent.ACTION_MOVE: // движение
                int mainHeight= rootScroll.getHeight();
                int screenHeight=getScreenHeight();
                int eventLayoutHeght=eventRootLayout.getHeight();
                int eventTopMargin=layoutParams.topMargin;
                int fullScreenHeight=getScreenHeight()+(eventRootLayout.getHeight()-getScreenHeight());
                int topUpButtonLimit=screenHeight-mainHeight+eventTopMargin+200;
                int test2=eventRootLayout.getHeight();

                if(y - yDelta + view.getHeight() <= eventRootLayout.getHeight()&&y - yDelta >= 0&&y<topUpButtonLimit){
                    layoutParams.topMargin = y - yDelta;
                    ll.setLayoutParams(layoutParams);

                    ll.removeAllViews();
                    TextView startTime =new TextView(ll.getContext());
                    startTime.setText(String.valueOf(eventTopMargin)+"-"+String.valueOf(eventTopMargin+eventLayoutHeght));
                    ll.addView(startTime);
                }
                break;
            case MotionEvent.ACTION_UP: // отпускание
                border = new GradientDrawable();
                border.setColor(0xFFFFFFFF); //white background
                border.setStroke(1, 0xFF000000);
                ll.setBackground(border);
                rootScroll.setScrollingEnabled(true);
                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        eventRootLayout.invalidate();
        return true;
    }
    public static float convertDpToPixel(int dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int convertPixelsToDp(float px, Context context) {
        return (int)(px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}