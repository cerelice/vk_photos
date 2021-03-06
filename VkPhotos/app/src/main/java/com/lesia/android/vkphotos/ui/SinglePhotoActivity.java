package com.lesia.android.vkphotos.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.elements.TouchImageView;
import com.lesia.android.vkphotos.events.AddLikeEvent;
import com.lesia.android.vkphotos.events.DeleteLikeEvent;
import com.lesia.android.vkphotos.events.LikeCountChangedEvent;
import com.lesia.android.vkphotos.events.OpenCommentsFragmentEvent;
import com.lesia.android.vkphotos.events.OpenSinglePhotoFragmentEvent;
import com.lesia.android.vkphotos.models.Photo;
import com.lesia.android.vkphotos.models.PhotoListResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

public class SinglePhotoActivity extends ActionBarActivity {

    PagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    ColorDrawable mBackground;

    private ImageView mImageView;
    private FrameLayout mTopLevelLayout;
    private int mOriginalOrientation;

    private int animation;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_single_photo);
        //getSupportActionBar().setElevation(0);
        Toolbar mActionBarToolbar = (Toolbar)findViewById(R.id.actionbar_photo);
        setSupportActionBar(mActionBarToolbar);
        //supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getSupportActionBar() == null) {
            Log.v("ACTIONBAR", "getSupportActionBar IS NULL");
        }

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        mOriginalOrientation = getIntent().getIntExtra(
                OpenSinglePhotoFragmentEvent.ORIENTATION_TAG,
                Configuration.ORIENTATION_LANDSCAPE
        );

        int position = getIntent().getIntExtra(OpenSinglePhotoFragmentEvent.POSITION_TAG, 0);
        mViewPager.setCurrentItem(position);

        if(savedInstanceState == null) {
            animation = 1;
        } else
            animation = 0;

    }

    public boolean isActionBarVisible() {
        return getSupportActionBar().isShowing();
        //return getActionBar().isShowing();
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

        return super.onOptionsItemSelected(item);
    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Photo> photos;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            photos = ((PhotoListResponse) getIntent()
                    .getSerializableExtra(OpenSinglePhotoFragmentEvent.PHOTOS_TAG))
                    .getResponse();

            String access_token = getPreferences(Context.MODE_PRIVATE).getString(
                    getString(R.string.access_token_key),
                    getString(R.string.access_token_def_value)
            );
            Log.v("ACTIVITY access_token", access_token);
        }



        @Override
        public Fragment getItem(int position) {
            if(getIntent().getIntExtra(OpenSinglePhotoFragmentEvent.POSITION_TAG, 0) != position) {
                animation = PlaceholderFragment.DONT_CREATE_ANIMATION;
            }
            return PlaceholderFragment.newInstance(
                    photos.get(position),
                    photos.get(position).getPhotoUrl(),
                    animation,
                    photos.get(position).getLikes().getCount(),
                    photos.get(position).getLikes().getUserLikes(),
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
        private static final String ARG_PHOTO = "photo";
        private static final String ARG_ANIMATION = "animation";
        private static final String ARG_LIKES = "likes";
        private static final String ARG_IS_LIKED = "is_liked";
        ShareActionProvider mShareActionProvider;
        private TouchImageView singlePhotoImageView;
        private Photo photo;
        private Uri uri;
        private static final String MESSAGE_TEXT = "Checkout this photo! ";
        int mLeftDelta;
        int mTopDelta;
        float mWidthScale;
        float mHeightScale;
        private RelativeLayout mTopLevelLayout;
        private ColorDrawable mBackground;
        private FrameLayout likeButton;
        String access_token;
        private ImageView likeButtonImageView;
        private TextView likeButtonTextView;

        private int mLikesCount;

        private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
        private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();

        public static final int CREATE_ANIMATION = 1;
        public static int DONT_CREATE_ANIMATION = 0;

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            EventBus.getDefault().register(this);
        }

        @Override
        public void onDetach() {
            EventBus.getDefault().unregister(this);
            super.onDetach();
        }

        public static PlaceholderFragment newInstance(Photo photo,
                                                      String photo_url,
                                                      int animation,
                                                      int likes,
                                                      int isLiked,
                                                      Bundle bundle) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PHOTO_URL, photo_url);
            args.putInt(ARG_ANIMATION, animation);
            args.putInt(ARG_LIKES, likes);
            args.putInt(ARG_IS_LIKED, isLiked);
            args.putSerializable(ARG_PHOTO, photo);
            args.putAll(bundle);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
            setHasOptionsMenu(true);
        }

        private String getAccessToken() {
            return getActivity().getSharedPreferences(
                        getString(R.string.shared_pref_file_name),
                        Context.MODE_PRIVATE)
                    .getString(
                        getString(R.string.access_token_key),
                        getString(R.string.access_token_def_value)
                    );
        }

        private void like() {

        }

        private void dislike() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_single_photo, container, false);
            photo = (Photo) getArguments().getSerializable(ARG_PHOTO);
            mLikesCount = photo.getLikes().getCount();
            access_token = getAccessToken();
            final String photo_url = photo.getPhotoUrl();

            likeButton = (FrameLayout) rootView.findViewById(R.id.likeButton);
            likeButtonImageView = (ImageView)rootView.findViewById(R.id.likeButtonImageView);
            likeButtonTextView = (TextView)rootView.findViewById(R.id.likeButtonTextView);
            //if(getArguments().getInt(ARG_IS_LIKED) == 1) {
            if(photo.getLikes().getUserLikes() == 1) {
                likeButtonTextView.setText("" + mLikesCount);
                likeButtonImageView.setImageDrawable(getResources().getDrawable(R.mipmap.heart));
                likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new DeleteLikeEvent(
                                photo.getOwnerID(),
                                photo.getId(),
                                access_token
                        ));
                    }
                });
            } else {
                likeButtonTextView.setText("" + mLikesCount);
                likeButtonImageView.setImageDrawable(getResources().getDrawable(R.mipmap.heartoutline));
                likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new AddLikeEvent(
                                photo.getOwnerID(),
                                photo.getId(),
                                access_token
                        ));
                    }
                });
            }
            singlePhotoImageView = (TouchImageView) rootView.findViewById(R.id.singlePhotoImageView);
            singlePhotoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isVisible = ((SinglePhotoActivity) getActivity()).isActionBarVisible();
                    ((SinglePhotoActivity) getActivity()).setActionBarVisibility(!isVisible);
                }
            });
            mBackground = new ColorDrawable(Color.BLACK);
            mTopLevelLayout = (RelativeLayout) rootView.findViewById(R.id.topLevelLayout);
            mTopLevelLayout.setBackgroundDrawable(mBackground);
            Log.v("ANIMATE", "Animate? " + getArguments().getInt(ARG_ANIMATION));
            if(getArguments().getInt(ARG_ANIMATION) == CREATE_ANIMATION) {
                Drawable dw = EventBus.getDefault().getStickyEvent(GlideBitmapDrawable.class);
                if(dw == null) {
                    Log.v("DRAWABLE", "NULL DRAWABLE AFTER STICKY EVENT");
                } else {
                    Log.v("DRAWABLE", "DRAWABLE AFTER STICKY EVENT IS OK");
                }
                singlePhotoImageView.setImageDrawable(dw);
                EventBus.getDefault().removeStickyEvent(GlideBitmapDrawable.class);
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
                                            "getHeight = " + singlePhotoImageView.getHeight() + "\n"
                        );
                        mWidthScale = ((float) thumbWidth) / ((float) singlePhotoImageView.getWidth());
                        //mHeightScale = ((float) thumbHeight) / ((float) singlePhotoImageView.getHeight());
                        runEnterAnimation();
                        return true;
                    }
                });
            } else {
                Glide.with(this).load(photo_url).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        singlePhotoImageView.setImageBitmap(resource);
                    }
                });
            }
            setHasOptionsMenu(true);
            return rootView;
        }

        private void setLiked() {

        }

        private void setNewLikes(int likesCount, int action) {
            Photo.Likes likes = new Photo.Likes();
            likes.setCount(mLikesCount);
            likes.setUserLikes(action);
            photo.setLikes(likes);
        }

        public void onEvent(LikeCountChangedEvent event) {
            mLikesCount = event.getLikesCount();
            Log.v("LikeCountChangedEvent", "likes: " + event.toString());
            if(event.getAction() == LikeCountChangedEvent.ADD_LIKE) {
                likeButtonTextView.setText("" + mLikesCount);
                likeButtonImageView.setImageDrawable(getResources().getDrawable(R.mipmap.heart));

                setNewLikes(mLikesCount, event.getAction());

                likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new DeleteLikeEvent(
                                photo.getOwnerID(),
                                photo.getId(),
                                access_token
                        ));
                    }
                });
            }
            if(event.getAction() == LikeCountChangedEvent.DELETE_LIKE) {
                likeButtonTextView.setText("" + mLikesCount);
                likeButtonImageView.setImageDrawable(getResources().getDrawable(R.mipmap.heartoutline));

                setNewLikes(mLikesCount, event.getAction());

                likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new AddLikeEvent(
                                photo.getOwnerID(),
                                photo.getId(),
                                access_token
                        ));
                    }
                });
            }
        }

        private void runEnterAnimation() {
            long duration = 500;

            Log.v("ANIMATION", "setScaleX = " + mWidthScale + '\n' +
                            "setScaleY = " + mHeightScale + '\n' +
                            "setTranslationX = " + mLeftDelta + '\n' +
                            "setTranslationY = " + mTopDelta
            );

            singlePhotoImageView.setPivotX(0.5f);
            singlePhotoImageView.setPivotY(0.5f);
            singlePhotoImageView.setScaleX(mWidthScale);
            singlePhotoImageView.setScaleY(mWidthScale);
            singlePhotoImageView.setTranslationX(mLeftDelta);
            singlePhotoImageView.setTranslationY(mTopDelta);

            singlePhotoImageView.animate().setDuration(duration).
                    scaleX(1).scaleY(1).
                    translationX(0).translationY(0).
                    setInterpolator(sDecelerator).
                    setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Glide.with(getActivity())
                                    .load(photo.getPhotoUrl()).dontAnimate()
                                    .into(new GlideDrawableImageViewTarget(singlePhotoImageView) {
                                        @Override
                                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                            super.onResourceReady(resource, animation);
                                            if(mShareActionProvider != null) {
                                                mShareActionProvider.setShareIntent(createShareIntent());
                                            }
                                            else {
                                                Log.d("SHARE", "Share Action Provider is null");
                                            }
                                        }
                                    });
                        }
                    });

            ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
            bgAnim.setDuration(duration);
            bgAnim.start();

        }

        private Intent createShareIntent()
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/jpeg");
            Bitmap photo;
            Drawable dw = (Drawable) singlePhotoImageView.getDrawable();
            if(dw instanceof GlideBitmapDrawable) {
                photo = ((GlideBitmapDrawable)dw).getBitmap();
            } else
                if(dw instanceof BitmapDrawable) {
                    photo = ((BitmapDrawable)dw).getBitmap();
                }
                else {
                    return shareIntent;
                }

            String path = MediaStore.Images.Media.insertImage(
                    getActivity().getContentResolver(),
                    photo,
                    MESSAGE_TEXT + getArguments().getString(ARG_PHOTO_URL),
                    null
            );
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
            return shareIntent;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_single_photo_fragment, menu);

            MenuItem menuItem = menu.findItem(R.id.action_share);
            mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(menuItem);
            if(mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareIntent());
            }
            else {
                Log.d("SHARE", "Share Action Provider is null");
            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_download) {
                Bitmap photo = null;
                Drawable dw = (Drawable) singlePhotoImageView.getDrawable();
                if(dw instanceof GlideBitmapDrawable) {
                    photo = ((GlideBitmapDrawable)dw).getBitmap();
                    Log.v("PHOTO", "GlideBitmapDrawable");
                    Log.v("PHOTO", photo.toString());
                } else
                if(dw instanceof BitmapDrawable) {
                    photo = ((BitmapDrawable)dw).getBitmap();
                    Log.v("PHOTO", "BitmapDrawable");
                }
                else {
                    photo = null;
                    Log.v("PHOTO", "There is no photo");
                    return false;
                }
                FileOutputStream out = null;
                String path = Environment.getExternalStorageDirectory().toString();
                final String file_name = "vkphotos" + System.currentTimeMillis() + ".jpg";
                final File photo_file = new File(path, file_name);
                //ProgressDialog progressDialog = new ProgressDialog(getActivity());
                try {
                    out = new FileOutputStream(photo_file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 85, out);

                } catch(Exception e) {
                    Log.e("FILE_OUTPUT", "Error: " + e.getMessage());
                } finally {
                    try {
                        if(out != null) {
                            //progressDialog.dismiss();
                            AlertDialog.Builder alertDialogBuilder = null;
                            alertDialogBuilder = new AlertDialog.Builder(getActivity())
                            .setTitle("Image was downloaded")
                            .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.parse("file://" + photo_file.getAbsolutePath()), "image/*");
                                    startActivity(intent);
                                }
                            })
                                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //alertDialogBuilder.cancel();
                                        }
                                    })
                            .setCancelable(true);
                            alertDialogBuilder.show();
                            out.close();
                        }
                        out.flush();
                    } catch (IOException e) {
                        Log.e("FILE_OUTPUT", "Error: " + e.getMessage());
                    }
                }
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }
}
