package com.lesia.android.vkphotos.ui;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.events.OpenSinglePhotoFragmentEvent;
import com.lesia.android.vkphotos.models.Photo;
import com.lesia.android.vkphotos.models.PhotoListResponse;

import java.util.ArrayList;

public class SinglePhotoActivity extends ActionBarActivity {

    PagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    ColorDrawable mBackground;

    private ImageView mImageView;
    private FrameLayout mTopLevelLayout;
    private int mOriginalOrientation;

    private int animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.activity_single_photo);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        mOriginalOrientation = getIntent().getIntExtra(
                OpenSinglePhotoFragmentEvent.ORIENTATION_TAG,
                Configuration.ORIENTATION_LANDSCAPE
        );

        //mBackground = new ColorDrawable(Color.BLACK);
        //mTopLevelLayout.setBackgroundColor(Color.BLACK);

        int position = getIntent().getIntExtra(OpenSinglePhotoFragmentEvent.POSITION_TAG, 0);
        mViewPager.setCurrentItem(position);

        if(savedInstanceState == null) {
            animation = 1;
        } else
            animation = 0;

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
            photos = ((PhotoListResponse) getIntent()
                    .getSerializableExtra(OpenSinglePhotoFragmentEvent.PHOTOS_TAG))
                    .getResponse();
        }



        @Override
        public Fragment getItem(int position) {
            if(getIntent().getIntExtra(OpenSinglePhotoFragmentEvent.POSITION_TAG, 0) != position) {
                animation = PlaceholderFragment.DONT_CREATE_ANIMATION;
            }
            return PlaceholderFragment.newInstance(
                    photos.get(position).getPhotoUrl(),
                    animation,
                    getIntent().getExtras()
                    );
        }

        @Override
        public int getCount() {
            return photos.size();
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_PHOTO_URL = "photo_url";
        private static final String ARG_ANIMATION = "animation";
        private ImageView singlePhotoImageView;
        private Bitmap photo;
        private Uri uri;
        private static final String MESSAGE_TEXT = "Checkout this photo! ";
        int mLeftDelta;
        int mTopDelta;
        float mWidthScale;
        float mHeightScale;
        private FrameLayout mTopLevelLayout;
        private ColorDrawable mBackground;

        private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
        private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();

        public static final int CREATE_ANIMATION = 1;
        public static int DONT_CREATE_ANIMATION = 0;


        public static PlaceholderFragment newInstance(String photo_url,
                                                      int animation,
                                                      Bundle bundle) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PHOTO_URL, photo_url);
            args.putInt(ARG_ANIMATION, animation);
            args.putAll(bundle);
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
            final String photo_url = getArguments().getString(ARG_PHOTO_URL);
            singlePhotoImageView = (ImageView) rootView.findViewById(R.id.singlePhotoImageView);
            singlePhotoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isVisible = ((SinglePhotoActivity) getActivity()).isActionBarVisible();
                    ((SinglePhotoActivity) getActivity()).setActionBarVisibility(!isVisible);
                }
            });
            mBackground = new ColorDrawable(Color.BLACK);
            mTopLevelLayout = (FrameLayout) rootView.findViewById(R.id.topLevelLayout);
            mTopLevelLayout.setBackgroundDrawable(mBackground);
            Glide.with(this).load(photo_url).into(singlePhotoImageView);
            Log.v("ANIMATE", "Animate? " + getArguments().getInt(ARG_ANIMATION));
            if(getArguments().getInt(ARG_ANIMATION) == CREATE_ANIMATION) {
                Log.v("ANIMATE", "animate " + photo_url);
                ViewTreeObserver observer = singlePhotoImageView.getViewTreeObserver();
                observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        singlePhotoImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                        int[] screenLocation = new int[2];
                        singlePhotoImageView.getLocationOnScreen(screenLocation);
                        int thumbLeft = getArguments().getInt(OpenSinglePhotoFragmentEvent.LEFT_LOCATION_TAG);
                        int thumbTop = getArguments().getInt(OpenSinglePhotoFragmentEvent.TOP_LOCATION_TAG);
                        int thumbWidth = getArguments().getInt(OpenSinglePhotoFragmentEvent.WIDTH_TAG);
                        int thumbHeight = getArguments().getInt(OpenSinglePhotoFragmentEvent.HEIGHT_TAG);

                        mLeftDelta = thumbLeft - screenLocation[0];
                        mTopDelta = thumbTop - screenLocation[1];

                        Log.v("ANIMATION",  "getWidth = " + singlePhotoImageView.getWidth() + "\n" +
                                            "getHeight = " + singlePhotoImageView.getHeight()
                        );

                        mWidthScale = ((float) thumbWidth) / ((float) singlePhotoImageView.getWidth());
                        //mHeightScale = ((float) thumbHeight) / ((float) singlePhotoImageView.getHeight());

                        runEnterAnimation();

                        return true;
                    }
                });
            }
            return rootView;
        }

        private void runEnterAnimation() {
            long duration = 500;

            Log.v("ANIMATION",  "setScaleX = " + mWidthScale + '\n' +
                                "setScaleY = " + mHeightScale + '\n' +
                                "setTranslationX = " + mLeftDelta + '\n' +
                                "setTranslationY = " + mTopDelta
            );

            singlePhotoImageView.setPivotX(0f);
            singlePhotoImageView.setPivotY(0f);
            singlePhotoImageView.setScaleX(mWidthScale);
            singlePhotoImageView.setScaleY(mWidthScale);
            singlePhotoImageView.setTranslationX(mLeftDelta);
            singlePhotoImageView.setTranslationY(mTopDelta);

            singlePhotoImageView.animate().setDuration(duration).
                    scaleX(1).scaleY(1).
                    translationX(0).translationY(0).
                    setInterpolator(sDecelerator);

            ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
            bgAnim.setDuration(duration);
            bgAnim.start();

        }

        private Intent createShareIntent()
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            //Bitmap photo = ((BitmapDrawable)singlePhotoImageView.getDrawable()).getBitmap();

            //shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE_TEXT + getArguments().getString(ARG_PHOTO_URL));

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
