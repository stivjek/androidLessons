package ru.startandroid.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherController {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Context myContext;
    final String LOG_TAG = "myLogs";

    public WeatherController(Context context) {
        myContext = context;
        mDBHelper = new DatabaseHelper(myContext);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    public String getDb()
    {
        String str = "";
        Cursor cursor = mDb.rawQuery("select * from wheather inner join location on location_id=location._id inner join current on current_id = current._id", null);
        cursor.moveToFirst();
        str += "Город: "+cursor.getString(cursor.getColumnIndex("name"))+"\n"+
                "Время запроса: "+cursor.getString(cursor.getColumnIndex("localtime"))+"\n"+
                "Последнее время обновления: "+cursor.getString(cursor.getColumnIndex("last_updated"))+"\n"+
                "Температура: "+cursor.getString(cursor.getColumnIndex("temp_c")) + "\n\n";
        while (cursor.moveToNext())
        {
            str += "Город: "+cursor.getString(cursor.getColumnIndex("name"))+"\n"+
                    "Время запроса: "+cursor.getString(cursor.getColumnIndex("localtime"))+"\n"+
                    "Последнее время обновления: "+cursor.getString(cursor.getColumnIndex("last_updated"))+"\n"+
                    "Температура: "+cursor.getString(cursor.getColumnIndex("temp_c")) + "\n\n";
        }
        return str;
    }

    private long instertLocation(Location location)
    {
        ContentValues locationValue = new ContentValues();
        locationValue.put("name", location.name);
        locationValue.put("region", location.region);
        locationValue.put("country", location.country);
        locationValue.put("lat", location.lat);
        locationValue.put("lon", location.lon);
        locationValue.put("tz_id", location.tz_id);
        locationValue.put("localtime_epoch", location.localtime_epoch);
        locationValue.put("localtime", location.localtime);
        long locationKey = mDb.insert("Location", null, locationValue);
        return locationKey;
    }

    private long insertCondition(Condition condition)
    {
        ContentValues conditionValue = new ContentValues();
        conditionValue.put("text", condition.text);
        conditionValue.put("icon", condition.icon);
        conditionValue.put("code", condition.code);
        long conditionKey = mDb.insert("Condition", null, conditionValue);
        return conditionKey;
    }

    private long insertCurrent(Current current, long conditionKey)
    {
        ContentValues currentValue = new ContentValues();
        currentValue.put("last_updated_epoch", current.last_updated_epoch);
        currentValue.put("last_updated", current.last_updated);
        currentValue.put("temp_c", current.temp_c);
        currentValue.put("temp_f", current.temp_f);
        currentValue.put("is_day", current.is_day);
        currentValue.put("condition_id", conditionKey);
        currentValue.put("wind_mph", current.wind_mph);
        currentValue.put("wind_kph", current.wind_kph);
        currentValue.put("wind_degree", current.wind_degree);
        currentValue.put("wind_dir", current.wind_dir);
        currentValue.put("pressure_mb", current.pressure_mb);
        currentValue.put("pressure_in", current.pressure_in);
        currentValue.put("precip_mm", current.precip_mm);
        currentValue.put("precip_in", current.precip_in);
        currentValue.put("humidity", current.humidity);
        currentValue.put("cloud", current.cloud);
        currentValue.put("feelslike_c", current.feelslike_c);
        currentValue.put("feelslike_f", current.feelslike_f);
        currentValue.put("vis_km", current.vis_km);
        currentValue.put("vis_miles", current.vis_miles);
        long currentKey = mDb.insert("Current", null, currentValue);
        return currentKey;
    }

    public long insesrtWheather(Wheather wheather, long locationKey, long currentKey)
    {
        ContentValues wheatherValue = new ContentValues();
        wheatherValue.put("location_id", locationKey);
        wheatherValue.put("current_id", currentKey);
        long wheatherKey = mDb.insert("Wheather", null, wheatherValue);
        return wheatherKey;
    }

    public void getWeather()
    {
        RequestQueue queue = Volley.newRequestQueue(myContext);
        String url ="http://api.apixu.com/v1/current.json?key=f159424fa0394e9989473704181710&q=Omsk";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        try {
                            Location location = new Location(response.getJSONObject(
                                    "location").getString("name"),
                                    response.getJSONObject("location").getString("region"),
                                    response.getJSONObject("location").getString("country"),
                                    response.getJSONObject("location").getDouble("lat"),
                                    response.getJSONObject("location").getDouble("lon"),
                                    response.getJSONObject("location").getString("tz_id"),
                                    response.getJSONObject("location").getString("localtime_epoch"),
                                    response.getJSONObject("location").getString("localtime"));
                            long locationKey = instertLocation(location);

                            Condition condition = new Condition(
                                    response.getJSONObject("current").getJSONObject("condition").getString("text"),
                                    response.getJSONObject("current").getJSONObject("condition").getString("icon"),
                                    response.getJSONObject("current").getJSONObject("condition").getInt("code"));
                            long conditionKey = insertCondition(condition);
                            Current current = new Current(
                                    response.getJSONObject("current").getString("last_updated_epoch"),
                                    response.getJSONObject("current").getString("last_updated"),
                                    response.getJSONObject("current").getDouble("temp_c"),
                                    response.getJSONObject("current").getDouble("temp_f"),
                                    Boolean.valueOf(response.getJSONObject("current").getString("is_day")),
                                    condition,
                                    response.getJSONObject("current").getDouble("wind_mph"),
                                    response.getJSONObject("current").getDouble("wind_kph"),
                                    response.getJSONObject("current").getInt("wind_degree"),
                                    response.getJSONObject("current").getString("wind_dir"),
                                    response.getJSONObject("current").getDouble("pressure_mb"),
                                    response.getJSONObject("current").getDouble("pressure_in"),
                                    response.getJSONObject("current").getDouble("precip_mm"),
                                    response.getJSONObject("current").getDouble("precip_in"),
                                    response.getJSONObject("current").getInt("humidity"),
                                    response.getJSONObject("current").getInt("cloud"),
                                    response.getJSONObject("current").getDouble("feelslike_c"),
                                    response.getJSONObject("current").getDouble("feelslike_f"),
                                    response.getJSONObject("current").getDouble("vis_km"),
                                    response.getJSONObject("current").getDouble("vis_miles"));
                            long currentKey = insertCurrent(current, conditionKey);

                            Wheather wheather = new Wheather(location, current);
                            long wheatherKey = insesrtWheather(wheather, locationKey, currentKey);
                        } catch (JSONException e) {
                            Log.d(LOG_TAG, e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d(LOG_TAG, error.getLocalizedMessage());
            }
        });
        queue.add(jsObjRequest);
    }
}
