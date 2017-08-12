package nanodegree.hossamhazem.backingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class HomeBackingAppWidget extends AppWidgetProvider {

    static final String UPDATE_ACTION = "updateWidget";

    String recipeName = "";

     void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                          int appWidgetId) {

         // Set up the intent that starts the StackViewService, which will
         // provide the views for this collection.
         Intent intent = new Intent(context, HomeWidgetService.class);
         // Add the app widget ID to the intent extras.
         intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
         intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
         // Instantiate the RemoteViews object for the app widget layout.
         RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.home_backing_app_widget);
         // Set up the RemoteViews object to use a RemoteViews adapter.
         // This adapter connects
         // to a RemoteViewsService  through the specified intent.
         // This is how you populate the data.
         rv.setRemoteAdapter(appWidgetId, R.id.widgetIngredientsListView, intent);

         // The empty view is displayed when the collection has no items.
         // It should be in the same layout used to instantiate the RemoteViews
         // object above.
         rv.setEmptyView(R.id.widgetIngredientsListView, R.id.empty_view);

         rv.setTextViewText(R.id.widgetHeadline, recipeName);

         appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
        /// / There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName thisWidget = new ComponentName(context, HomeBackingAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        SharedPreferences sharedPref = context.getSharedPreferences(RecipeStepActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        recipeName = sharedPref.getString("recipeName", "error");

        if(intent.getAction().equals(UPDATE_ACTION)){

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetIngredientsListView);
        }

        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

