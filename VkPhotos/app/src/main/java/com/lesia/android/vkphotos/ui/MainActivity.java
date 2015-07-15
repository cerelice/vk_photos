package com.lesia.android.vkphotos.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.events.AuthEvent;
import com.lesia.android.vkphotos.events.OpenAlbumsFragmentEvent;
import com.lesia.android.vkphotos.events.OpenPhotosFromAlbumEvent;
import com.lesia.android.vkphotos.events.OpenSinglePhotoFragmentEvent;
import com.lesia.android.vkphotos.models.PhotoListResponse;

import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity {
    final String LOG_TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeButtonEnabled(true);

        String pref_access_token = getPreferences(Context.MODE_PRIVATE).getString(
                getString(R.string.access_token_key),
                getString(R.string.access_token_def_value)
        );
        String access_token_def_value = getString(R.string.access_token_def_value);
        if(!pref_access_token.equals(access_token_def_value)) {
            Log.v(LOG_TAG, "Already have access_token, send to friend list");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new FriendListFragment())
                    .commit();
        } else {
            Log.v(LOG_TAG, "Don't have access_token, send to login fragment");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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
        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEvent(AuthEvent event) {
        Log.v(LOG_TAG, "Send to friend list after authorization");
        FriendListFragment fragment = new FriendListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void onEvent(OpenAlbumsFragmentEvent event) {
        Log.v(LOG_TAG, "Send to albums list onclick");

        Bundle bundle = new Bundle();
        bundle.putString("OWNER_ID", event.getOwnerId());
        AlbumsFragment fragment = new AlbumsFragment();
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void onEvent(OpenPhotosFromAlbumEvent event) {
        Log.v(LOG_TAG, "Send to photos from album onclick");

        Bundle bundle = new Bundle();
        bundle.putString("OWNER_ID", event.getOwnerId());
        bundle.putString("ALBUM_ID", event.getAlbumId());
        AlbumPhotoFragment fragment = new AlbumPhotoFragment();
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void onEvent(OpenSinglePhotoFragmentEvent event) {
        Intent intent = new Intent(this, SinglePhotoActivity.class);
        Bundle bundle = new Bundle();
        PhotoListResponse photos = event.getPhotos();
        bundle.putSerializable("PHOTOS", photos);
        bundle.putInt("POSITION", event.getPosition());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void setUpNavigation(boolean b)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(b);
    }
}
