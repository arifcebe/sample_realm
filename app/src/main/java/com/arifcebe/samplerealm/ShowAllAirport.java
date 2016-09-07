package com.arifcebe.samplerealm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.arifcebe.samplerealm.model.AirportList;
import com.arifcebe.samplerealm.model.AllDestination;
import com.arifcebe.samplerealm.model.FavDestination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by arifcebe on 9/6/16.
 */
public class ShowAllAirport extends AppCompatActivity {

    private Realm realm;
    private InputStream getRawAllDest,getRawFavDest;
    private String TAG = "Destination";
    private List<AirportList> airportLists = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        getRawAllDest = getResources().openRawResource(R.raw.all_destination);
        getRawFavDest = getResources().openRawResource(R.raw.fav_destination);



        try {
            String favAirport = convertRawToString(getRawFavDest);
            String allAirport = convertRawToString(getRawAllDest);


                JSONObject joFavAirport = new JSONObject(favAirport);
                JSONObject joAllAirport = new JSONObject(allAirport);

                JSONArray arrFavAirport = joFavAirport.getJSONObject("popular_destinations")
                        .getJSONArray("airport");
                JSONArray arrAllAirport = joAllAirport.getJSONObject("all_airport")
                        .getJSONArray("airport");
                realm.beginTransaction();
                realm.delete(FavDestination.class);
                realm.delete(AllDestination.class);
                realm.createAllFromJson(FavDestination.class,arrFavAirport);
                realm.createAllFromJson(AllDestination.class,arrAllAirport);
                realm.commitTransaction();



        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }


        RealmResults<AllDestination> allDestinationRealmResults =
                realm.where(AllDestination.class).findAll();
        RealmResults<FavDestination> favDestinationRealmResults =
                realm.where(FavDestination.class).findAll();
        airportLists.clear();

        Log.d(TAG,"====== all destination ========");
        for (AllDestination allDest : allDestinationRealmResults){
            Log.d(TAG,"air port name : "+allDest.getAirport_name()+" | "
                +allDest.getLocation_name());
            AirportList al = new AirportList();
            al.setAirport_code(allDest.getAirport_code());
            al.setAirport_name(allDest.getAirport_name());
            airportLists.add(al);
        }
        Log.d(TAG,"====== all destination ========");

        Log.d(TAG,"====== favorite destination ========");
        for (FavDestination allDest : favDestinationRealmResults){
            Log.d(TAG,"arival city : "+allDest.getArrival_city()+" | "
                    +allDest.getProvince_name());

            AirportList al = new AirportList();
            al.setAirport_code(allDest.getArrival_city());
            al.setAirport_name(allDest.getBusiness_name());
            airportLists.add(al);

        }
        Log.d(TAG,"====== favorite destination ========");

        Log.d(TAG,"\n\n====== all airport list ========");
        for (AirportList al : airportLists){
            Log.d(TAG,"arival city : "+al.getAirport_name()+" | "
                    +al.getAirport_code());
        }
        Log.d(TAG,"====== all airport list ========");

    }

    private String convertRawToString(InputStream is) throws IOException {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try{
            Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1){
                writer.write(buffer, 0,n);
            }
        }finally {
            is.close();
        }
        return writer.toString();
    }

    /*InputStream is = getResources().openRawResource(R.raw.json_file);
    Writer writer = new StringWriter();
    char[] buffer = new char[1024];
    try {
        Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        int n;
        while ((n = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, n);
        }
    } finally {
        is.close();
    }

    String jsonString = writer.toString();*/
}
