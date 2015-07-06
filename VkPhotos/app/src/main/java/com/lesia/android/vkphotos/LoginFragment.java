package com.lesia.android.vkphotos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import de.greenrobot.event.EventBus;

public class LoginFragment extends Fragment {

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WebView webView = new WebView(getActivity());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                final String ACCESS_TOKEN = "access_token=";
                final String EXPIRES_IN = "&expires_in=";
                final String USER_ID = "&user_id=";

                if(url.contains(ACCESS_TOKEN)) {
                    int begin_access_token = url.indexOf(ACCESS_TOKEN) + ACCESS_TOKEN.length();
                    int end_access_token = url.indexOf(EXPIRES_IN);
                    int begin_expires_in = end_access_token + EXPIRES_IN.length();
                    int end_expires_in = url.indexOf(USER_ID);
                    int begin_user_id = end_expires_in + USER_ID.length();
                    int end_user_id = url.length() - 1;

                    SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(
                            getString(R.string.access_token_key),
                            url.substring(begin_access_token, end_access_token)
                    );
                    editor.putString(
                            getString(R.string.expires_in_key),
                            url.substring(begin_expires_in, end_expires_in)
                    );
                    editor.putString(
                            getString(R.string.user_id_key),
                            url.substring(begin_user_id, end_user_id)
                    );
                    editor.commit();

                    Log.v("ACCESS_TOKEN", getActivity().getPreferences(Context.MODE_PRIVATE)
                                    .getString(
                                            getString(R.string.access_token_key),
                                            getString(R.string.access_token_def_value))
                    );
                    Log.v("EXPIRES_IN", getActivity().getPreferences(Context.MODE_PRIVATE)
                                    .getString(
                                            getString(R.string.expires_in_key),
                                            getString(R.string.expires_in_def_value))
                    );
                    Log.v("USER_ID", getActivity().getPreferences(Context.MODE_PRIVATE)
                                    .getString(
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
                "scope=" + "friends,photos" + "&" +
                "redirect_uri=" + "https://oauth.vk.com/blank.html" + "&" +
                "display=" + "mobile" + "&" +
                "v=" + "5.34" + "&" +
                "response_type=token");

        return webView;
    }
}
