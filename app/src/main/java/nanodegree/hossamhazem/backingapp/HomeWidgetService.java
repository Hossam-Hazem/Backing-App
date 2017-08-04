package nanodegree.hossamhazem.backingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;

public class HomeWidgetService extends RemoteViewsService{

    @Override
    public void onCreate() {
        super.onCreate();

    }



    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    Context mContext;
    public LinkedList<String> mData;
    private int mAppWidgetId;

    public ListRemoteViewsFactory(Context applicationContext, Intent dataIntent){
        this.mContext = applicationContext;

        if(mData == null){
            mData = new LinkedList<>();
        }
        mAppWidgetId = dataIntent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }



    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(RecipeStepActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        mData.addAll(sharedPref.getStringSet("data", null));
    }

    @Override
    public void onDestroy() {

    }



    @Override
    public int getCount() {
        if(mData == null)
            return 0;
        return mData.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.home_backing_app_widget_item);
        remoteViews.setTextViewText(R.id.ingredientText, mData.get(position));
        return remoteViews;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}


