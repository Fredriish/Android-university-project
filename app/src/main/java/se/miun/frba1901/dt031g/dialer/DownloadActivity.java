package se.miun.frba1901.dt031g.dialer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DownloadActivity extends AppCompatActivity {
    private WebView mainWebView;
    private Toolbar theToolbar;
    private TextView progressText;
    private TextView progressTitleText;
    private ProgressBar progressBar;
    private ViewGroup progressContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        // konfigurerar webview
        mainWebView = (WebView) findViewById(R.id.voiceDownloadWebView);
        initWebView();

        // Konfigurerar toolbar
        theToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(theToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        progressContainer = (ViewGroup)findViewById(R.id.progressContainer);
        progressText = (TextView)findViewById(R.id.progressText);
        progressTitleText = (TextView) findViewById(R.id.progressTitleText);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }
    private void initWebView(){
        mainWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        Bundle bundle = getIntent().getExtras();
        String siteUrl = bundle.getString("DOWNLOAD_SITE_URL");
        mainWebView.loadUrl(siteUrl);
        mainWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    progressContainer.setVisibility(View.VISIBLE);
                    URL downloadUrl = new URL(url);
                    DownloadVoiceZip asyncDownload = new DownloadVoiceZip();
                    asyncDownload.execute(downloadUrl);

                } catch(java.io.IOException err){
                    Log.e("Webview download exception", err.getMessage());
                }
            }
        });
    }
    private class DownloadVoiceZip extends AsyncTask<URL, Integer, Long>{
        private File voicesDirectory;
        public DownloadVoiceZip() {
            super();
            String voicesPath = getIntent().getExtras().getString("VOICE_STORAGE_PATH");
            voicesDirectory = new File(voicesPath);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Long doInBackground(URL... urls) {
            final int bufferSize = 1024;
            final String cacheZipFilename = "dialer_downloaded_voice.zip";
            FileOutputStream fileOutput = null;
            for(URL downloadUrl : urls){
                try {
                    // Skapar filen som data kommer att skrivas till
                    File voiceZipFile = new File(getCacheDir(), cacheZipFilename);
                    if(voiceZipFile.exists()) // Om filen som vi ska skriva till redan finns så tar vi bort den
                        voiceZipFile.delete();

                    // Öppnar en anslutning till url
                    URLConnection connection = downloadUrl.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();

                    // Skapar FileOutputStream som vi använder för att skriva data från URLen till filen
                    fileOutput = new FileOutputStream(voiceZipFile);
                    byte[] buffer = new byte[bufferSize];
                    int read;
                    while ((read = stream.read(buffer)) != -1) {
                        fileOutput.write(buffer, 0, read);
                    }
                    fileOutput.flush();

                    // Unzip till foldern "voices", därefter ska det finnas en folder där för rösten
                    Util.unzip(voiceZipFile, voicesDirectory);
                    voiceZipFile.delete(); // Tar bort den temporära zip filen i cache
                } catch(java.io.IOException err){
                    Log.e("Webview download exception", err.getMessage());
                } finally{
                    if(fileOutput != null){
                        try {
                            fileOutput.close();
                        } catch(java.io.IOException err){
                            Log.e("Error closing streams on download", err.getMessage());
                        }
                    }
                }
            }
            return null;
        }
    }
}