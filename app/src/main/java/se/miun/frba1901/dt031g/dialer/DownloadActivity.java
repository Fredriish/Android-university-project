package se.miun.frba1901.dt031g.dialer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class DownloadActivity extends AppCompatActivity {
    private WebView mainWebView;
    private Toolbar theToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        // konfigurerar webview
        mainWebView = (WebView) findViewById(R.id.voiceDownloadWebView);
        mainWebView.setWebViewClient(new WebViewClient());
        String siteUrl = "https://dt031g.programvaruteknik.nu/dialer/voices";
        mainWebView.loadUrl(siteUrl);

        // Konfigurerar toolbar
        theToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(theToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}