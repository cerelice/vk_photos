package com.lesia.android.vkphotos.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.events.AuthEvent;
import com.lesia.android.vkphotos.events.OpenAlbumsFragmentEvent;
import com.lesia.android.vkphotos.events.OpenPhotosFromAlbumEvent;
import com.lesia.android.vkphotos.events.OpenSinglePhotoFragmentEvent;

import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity {
    final String LOG_TAG = "MAIN_ACTIVITY";
    boolean isTabet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mActionBarToolbar = (Toolbar)findViewById(R.id.actionbar_friend);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        String pref_access_token = getSharedPreferences(
                    getString(R.string.shared_pref_file_name),
                    Context.MODE_PRIVATE
                )
                .getString(
                    getString(R.string.access_token_key),
                    getString(R.string.access_token_def_value)
                );
        String access_token_def_value = getString(R.string.access_token_def_value);
        if(!pref_access_token.equals(access_token_def_value)) {
            getSupportActionBar().setTitle("Friends");
            FrameLayout tabletFragment = (FrameLayout)findViewById(R.id.fragment_tablet_container);
            if(tabletFragment == null) {
                isTabet = false;
                Log.v(LOG_TAG, "Already have access_token, send to friend list");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new FriendListFragment())
                        .commit();
            } else {
                isTabet = true;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new FriendListFragment())
                        .commit();
            }
        } else {
            getSupportActionBar().setTitle("Login");
            FrameLayout tabletFragment = (FrameLayout)findViewById(R.id.fragment_tablet_container);
            isTabet = tabletFragment == null;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new LoginFragment())
                        .commit();
        }
        Intent intent = getIntent();
        if(intent != null && Intent.ACTION_SEARCH.equals(intent.getAction())) {
            getSupportActionBar().setTitle("Search");
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

        if (id == R.id.action_quit) {
            SharedPreferences sharedPreferences = getSharedPreferences(
                    getString(R.string.shared_pref_file_name),
                    Context.MODE_PRIVATE
            );
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(
                    getString(R.string.access_token_key),
                    getString(R.string.access_token_def_value)
            );
            editor.putString(
                    getString(R.string.expires_in_key),
                    getString(R.string.access_token_def_value)
            );
            editor.putString(
                    getString(R.string.user_id_key),
                    getString(R.string.access_token_def_value)
            );
            editor.apply();
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getAccessToken() {
        return getSharedPreferences(
                getString(R.string.shared_pref_file_name),
                Context.MODE_PRIVATE)
                .getString(
                        getString(R.string.access_token_key),
                        getString(R.string.access_token_def_value)
                );
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
        getSupportActionBar().setTitle("Albums");
        Bundle bundle = new Bundle();
        bundle.putString("OWNER_ID", event.getOwnerId());
        AlbumsFragment fragment = new AlbumsFragment();
        fragment.setArguments(bundle);
        if(isTabet) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_tablet_container, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    public void onEvent(OpenPhotosFromAlbumEvent event) {
        Log.v(LOG_TAG, "Send to photos from album onclick");
        getSupportActionBar().setTitle("Photos");
        Bundle bundle = new Bundle();
        bundle.putString("OWNER_ID", event.getOwnerId());
        bundle.putString("ALBUM_ID", event.getAlbumId());
        AlbumPhotoFragment fragment = new AlbumPhotoFragment();
        fragment.setArguments(bundle);

        if(isTabet) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_tablet_container, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    public void onEvent(OpenSinglePhotoFragmentEvent event) {
        Intent intent = new Intent(this, SinglePhotoActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(OpenSinglePhotoFragmentEvent.PHOTOS_TAG, event.getPhotos());
        bundle.putInt(OpenSinglePhotoFragmentEvent.POSITION_TAG, event.getPosition());
        bundle.putInt(OpenSinglePhotoFragmentEvent.ORIENTATION_TAG, event.getOrientation());
        bundle.putInt(OpenSinglePhotoFragmentEvent.LEFT_LOCATION_TAG, event.getLeftLocation());
        bundle.putInt(OpenSinglePhotoFragmentEvent.TOP_LOCATION_TAG, event.getTopLocation());
        bundle.putInt(OpenSinglePhotoFragmentEvent.WIDTH_TAG, event.getWidth());
        bundle.putInt(OpenSinglePhotoFragmentEvent.HEIGHT_TAG, event.getHeight());

        intent.putExtras(bundle);

        startActivity(intent);

        overridePendingTransition(0, 0);
    }

    public void setUpNavigation(boolean b)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(b);
    }


}
