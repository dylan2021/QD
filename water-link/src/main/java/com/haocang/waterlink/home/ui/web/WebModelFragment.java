package com.haocang.waterlink.home.ui.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.waterlink.R;

@Route(path = LibConfig.AROUTE_MODEL_MSG)
public class WebModelFragment extends Fragment {

    private WebView webView;
    private TextView titletv;
    private ProgressBarDialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.from(getActivity()).inflate(R.layout.fragment_web_model, null);
        initView(view);

        return view;
    }

    private void initView(View view) {
        dialog = new ProgressBarDialog(getActivity());
        dialog.show();
        titletv = view.findViewById(R.id.home_title_tv);
        titletv.setText("模型信息");
        view.findViewById(R.id.sccan_iv).setVisibility(View.GONE);
        view.findViewById(R.id.back_v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        webView = view.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAppCacheEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if (dialog!=null&&dialog.isShowing()){
                    dialog.cancel();
                }
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl("http://www.qdhsdd.com:18106/#/screenbim");
    }
}
