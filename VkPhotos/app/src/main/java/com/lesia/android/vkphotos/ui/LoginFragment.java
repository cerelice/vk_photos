package com.lesia.android.vkphotos.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lesia.android.vkphotos.events.AuthEvent;
import com.lesia.android.vkphotos.R;

import de.greenrobot.event.EventBus;

public class LoginFragment extends Fragment {

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WebView webView = new WebView(getActivity());
        webView.clearCache(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                final String ACCESS_TOKEN = "access_token";
                final String EXPIRES_IN = "expires_in";
                final String USER_ID = "user_id";

                if (url.contains(ACCESS_TOKEN)) {
                    url = url.replace('#', '?');
                    Uri uri = Uri.parse(url);
                    Log.v("URI", url);
                    Log.v("URI", uri.getQueryParameterNames().toString());
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                            getString(R.string.shared_pref_file_name),
                            Context.MODE_PRIVATE
                    );
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(
                            getString(R.string.access_token_key),
                            uri.getQueryParameter(ACCESS_TOKEN)
                    );
                    editor.putString(
                            getString(R.string.expires_in_key),
                            uri.getQueryParameter(EXPIRES_IN)
                    );
                    editor.putString(
                            getString(R.string.user_id_key),
                            uri.getQueryParameter(USER_ID)
                    );
                    editor.apply();

                    Log.v("ACCESS_TOKEN", sharedPreferences.getString(
                                    getString(R.string.access_token_key),
                                    getString(R.string.access_token_def_value))
                    );
                    Log.v("EXPIRES_IN", sharedPreferences.getString(
                                    getString(R.string.expires_in_key),
                                    getString(R.string.expires_in_def_value))
                    );
                    Log.v("USER_ID", sharedPreferences.getString(
                                    getString(R.string.user_id_key),
                                    getString(R.string.user_id_def_value))
                    );
                    EventBus.getDefault().post(new AuthEvent());
                    return true;
                }
                return false;
            }
        });

        webView.loadUrl("http://oauth.vk.com/authorize?" +
                "client_id=" + "4980534" + "&" +
                "scope=" + "friends,photos,offline,wall" + "&" +
                "redirect_uri=" + "https://oauth.vk.com/blank.html" + "&" +
                "display=" + "mobile" + "&" +
                "v=" + "5.34" + "&" +
                "response_type=token");

        return webView;
    }
}
