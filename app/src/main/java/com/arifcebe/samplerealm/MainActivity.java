package com.arifcebe.samplerealm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.arifcebe.samplerealm.model.Migration;
import com.arifcebe.samplerealm.model.People;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main";
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);
        Log.d(TAG, "key secure " + key);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .encryptionKey(loadkey(this))
                .schemaVersion(1)
                .name("sample.realm")
                .migration(new Migration())
                .build();*/

        // Start with a clean slate every time
        //Realm.deleteRealm(realmConfiguration);

        // Open the Realm with encryption enabled
        //realm = Realm.getInstance(realmConfiguration);
        realm = Realm.getDefaultInstance();

        RealmResults<People> listPerson = realm.where(People.class).findAll();
        Log.i(TAG, String.format("get person name: " + listPerson.size()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //insertToRealm();
                startActivity(new Intent(MainActivity.this,ShowAllAirport.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertToRealm() {


        // Everything continues to work as normal except for that the file is encrypted on disk
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                People person = realm.createObject(People.class);
                for (int i = 0; i < 5; i++) {
                    person.setName("DOna doni");
                    person.setAge(14);
                }
            }
        });

        People person = realm.where(People.class).findFirst();
        Log.i(TAG, String.format("Person name: %s", person.getName()));

        RealmResults<People> listPerson = realm.where(People.class).findAll();
        Log.i(TAG, String.format("get person name: " + listPerson.size()));


        realm.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public byte[] loadkey(Context context) {


        byte[] content = new byte[64];
        try {
            /*if (ks == null) {
                createNewKeys(context);
            }*/

            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);

            content= ks.getCertificate("cebe").getEncoded();
            Log.e(TAG, "key original :" + Arrays.toString(content));
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = Arrays.copyOfRange(content, 0, 64);
        return content;
    }
}
