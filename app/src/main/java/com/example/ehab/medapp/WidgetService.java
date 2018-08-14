package com.example.ehab.medapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.ehab.medapp.models.DayPart;
import com.example.ehab.medapp.models.Drug;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalTime;

import java.util.ArrayList;

import static com.example.ehab.medapp.Utils.getDayPart;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetItemFactory(getApplicationContext(),intent);
    }

    class WidgetItemFactory implements RemoteViewsFactory {

        private Context context;
        private int appWidgetId;
        private ArrayList<Drug> values=new ArrayList<>();
        private FirebaseDatabase database;
        private FirebaseUser firebaseUser;
        private DatabaseReference mDatabase;
        private FirebaseAuth mAuth;
        WidgetItemFactory(Context context,Intent intent)
        {
            this.context=context;
            this.appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {
            //connect to data source

            LocalTime  time = new LocalTime();
          String dayPart=getDayPart(time);


            Log.e("day part is: ",dayPart);
            database = FirebaseDatabase.getInstance();
            mDatabase = database.getReference();
            mAuth = FirebaseAuth.getInstance();
            firebaseUser = mAuth.getCurrentUser();
            if(firebaseUser!=null) {
                Log.e("User id is ",firebaseUser.getUid());
                Query myTopPostsQuery = mDatabase.child("usersDrugs").child(firebaseUser.getUid()).child(dayPart);
                myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            Drug drug = dataSnapshot1.getValue(Drug.class);


                            values.add(drug);
                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(
                                    new ComponentName(getApplicationContext(), AppWidget.class)), R.id.widget_list_view);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Database Error ", databaseError.getMessage() );
                    }
                });
            }
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            Log.e("Size",values.size()+"");
            return values.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            Log.e("View At ",values.get(i).getName());
            views.setTextViewText(R.id.widget_item_name, values.get(i).getName());
            views.setTextViewText(R.id.widget_item_time, values.get(i).getTime());

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
