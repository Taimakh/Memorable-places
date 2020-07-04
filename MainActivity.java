package com.hfad.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   static ArrayList<String> places;
    static ArrayList<LatLng> locations;
   static  ArrayAdapter arrayadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listview=findViewById(R.id.listview);
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.hfad.memorableplaces", Context.MODE_PRIVATE);
        ArrayList <String> latitudes =new ArrayList<String>();
        ArrayList <String> longitudes =new ArrayList<String>();
        places.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();
        try{
            places=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize((new ArrayList<String>()))));
            latitudes=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats",ObjectSerializer.serialize((new ArrayList<String>()))));
            longitudes=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longts",ObjectSerializer.serialize((new ArrayList<String>()))));

        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(places.size()>0&&latitudes.size()>0&&longitudes.size()>0){
            if(places.size()==latitudes.size()&&places.size()==longitudes.size()){
                for(int i=0;i<latitudes.size();i++){
                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i))));
                }
            }
        }else{

            places.add("Add a new place...");
            locations.add(new LatLng(0,0));
        }

        places=new ArrayList<>();
        places.add("Add a new place...");
        locations =new ArrayList<LatLng>();
        locations.add(new LatLng(0,0));

         arrayadapter =new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,places);
        listview.setAdapter(arrayadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent =new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("placeNumber",i);
                startActivity(intent);
            }
        });
    }
}
