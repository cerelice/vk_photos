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

                if(url.contains(ACCESS_TOKEN)) {
                    int begin = url.indexOf(ACCESS_TOKEN) + ACCESS_TOKEN.length();
                    int end = url.indexOf(EXPIRES_IN);

                    SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("ACCESS_TOKEN", url.substring(begin, end));
                    editor.commit();

                    Log.v("ACCESS_TOKEN", getActivity().getPreferences(Context.MODE_PRIVATE)
                            .getString("ACCESS_TOKEN", "0"));

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
