package com.lesia.android.vkphotos.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.models.Photo;
import com.lesia.android.vkphotos.models.PhotoListResponse;

import java.util.ArrayList;

public class SinglePhotoActivity extends ActionBarActivity {

    PagerAdapter mPagerAdapter;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.activity_single_photo);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        int position = getIntent().getIntExtra("POSITION", 0);
        mViewPager.setCurrentItem(position);
    }

    public boolean isActionBarVisible() {
        return getSupportActionBar().isShowing();
    }

    public void setActionBarVisibility(boolean b) {
        if(b) {
            getSupportActionBar().show();
        } else {
            getSupportActionBar().hide();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_photo, menu);
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

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Photo> photos;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            photos = ((PhotoListResponse) getIntent().getSerializableExtra("PHOTOS")).getResponse();
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(photos.get(position).getPhotoUrl());
        }

        @Override
        public int getCount() {
            return photos.size();
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_PHOTO_URL = "photo_url";
        private ImageView singlePhotoImageView;
        private Bitmap photo;
        private Uri uri;
        private static final String MESSAGE_TEXT = "Checkout this photo! ";

        public static PlaceholderFragment newInstance(String photo_url) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PHOTO_URL, photo_url);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_single_photo, container, false);
            String photo_url = getArguments().getString(ARG_PHOTO_URL);
            singlePhotoImageView = (ImageView) rootView.findViewById(R.id.singlePhotoImageView);
            singlePhotoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isVisible = ((SinglePhotoActivity) getActivity()).isActionBarVisible();
                    ((SinglePhotoActivity) getActivity()).setActionBarVisibility(!isVisible);
                }
            });
            Glide.with(this).load(photo_url).into(new BitmapImageViewTarget(singlePhotoImageView) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    super.onResourceReady(resource, glideAnimation);
                    String path = MediaStore.Images.Media.insertImage(
                            getActivity().getContentResolver(),
                            resource,
                            "Cool photo",
                            null
                    );
                    uri = Uri.parse(path);
                }
                }
            );

            return rootView;
        }

        private Intent createShareIntent()
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            //Bitmap photo = ((BitmapDrawable)singlePhotoImageView.getDrawable()).getBitmap();

            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            //shareIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE_TEXT + getArguments().getString(ARG_PHOTO_URL));

            return shareIntent;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_single_photo_fragment, menu);

            MenuItem menuItem = menu.findItem(R.id.action_share);

            ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            if(mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareIntent());
            }
            else {
                Log.d("SHARE", "Share Action Provider is null");
            }
        }
    }

}
