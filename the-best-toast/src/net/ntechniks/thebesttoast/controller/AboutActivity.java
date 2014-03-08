package net.ntechniks.thebesttoast.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import net.ntechniks.thebesttoast.R;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckedTextView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends ActionBarActivity {

	private ShareActionProvider sap;
	private String appUri;
	private String packageName;
	private ActionBar sab;
	private Uri storeUri;
	private String devName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu resource file.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.about_action_menu, menu);

		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.about_action_share);

		// Fetch and store ShareActionProvider
		this.sap = (ShareActionProvider) MenuItemCompat
				.getActionProvider(item);

		// Call to update the share intent
		Intent shareIntent = getShareIntent();
		setShareIntent(shareIntent);

		// Return true to display menu
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.about_action_exit:
			exit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // Google Analytics.
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Google Analitycs.
	}

	private void init() {
		Resources resources = this.getResources();
		this.sab = this.getSupportActionBar();
		TextView versionHistory = (TextView) this
				.findViewById(R.id.about_whats_new_text);
		WebView webView = (WebView) this.findViewById(R.id.webView);

		this.devName = getString(R.string.developer);
		String path = "search?q=pub:";
		this.storeUri = Uri.parse("market://" + path + devName);

		if (webView != null) {
			String storeUrl = "http://play.google.com/store/" + path + devName;
			webView.setWebViewClient(new WebViewClient() {
		        @Override
		        public void onReceivedError(WebView view, int errorCode,
		                String description, String failingUrl) {
		            // Handle the error
		        }

		        @Override
		        public boolean shouldOverrideUrlLoading(WebView view, String url) {
		            view.loadUrl(url);
		            return true;
		        }
		    });
			webView.setKeepScreenOn(false);
			webView.loadUrl(storeUrl);
		}
		InputStream is = null;
		int bufferSize = 256;

		// This enables the app icon as the Up button.
		this.sab.setDisplayHomeAsUpEnabled(true);

		// Set background color of the ActionBar
		int actionBarBGColorID = R.color.blue;
		int color = resources.getColor(actionBarBGColorID);
		this.sab.setBackgroundDrawable(new ColorDrawable(color));

		try {
			is = resources.openRawResource(R.raw.version_history);
			String history = slurp(is, bufferSize);
			versionHistory.setText(history);
		} catch (IOException e) {
			CharSequence noInfo = getString(R.string.no_info);
			versionHistory.setText(noInfo);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Toast.makeText(this, R.string.no_info, Toast.LENGTH_SHORT)
						.show();
			}
		}

		addListeners();
		
		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) this.findViewById(R.id.aboutAdView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	private void addListeners() {
		CheckedTextView devButton = (CheckedTextView) findViewById(R.id.developer_link);
		RatingBar rateButton = (RatingBar) findViewById(R.id.ratingBar);
		CheckedTextView contactMeButton = (CheckedTextView) findViewById(R.id.contactMe);

		devButton.setClickable(true);
		devButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				visitGoogleMarketplace();
			}
		});

		rateButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent me) {
				if (v != null) {
					goRateUs();
				}
				return true;
			}
		});

		contactMeButton.setClickable(true);
		contactMeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				contactMe();
			}
		});
	}

	private void exit() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void contactMe() {
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto", "nikigeorgiev2000@gmail.com", null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
		startActivity(Intent.createChooser(emailIntent, ""));
	}

	private void goRateUs() {
		String string = "market://details?id=" + packageName;
		Uri uri = Uri.parse(string);
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

		try {
			this.startActivity(goToMarket);
		} catch (ActivityNotFoundException e) {
			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUri)));
		}
	}

	private void visitGoogleMarketplace() {
		Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, storeUri);

		try {
			startActivity(myAppLinkToMarket);
		} catch (ActivityNotFoundException e) {
			// the device hasn't installed Google Play
			Uri url = Uri
					.parse("http://play.google.com/store/apps/developer?id="
							+ devName);
			CharSequence noGooglePlayInstalled = getString(R.string.no_google_play);

			// Popup a Toast to inform the user.
			Toast.makeText(this, noGooglePlayInstalled, Toast.LENGTH_LONG)
					.show();

			// Open the marketplace internal.
			startActivity(new Intent(Intent.ACTION_VIEW, url));
		}
	}

	/**
	 * @param shareIntent
	 */
	private void setShareIntent(Intent shareIntent) {
		if (sap != null) {
			sap.setShareIntent(shareIntent);
		}
	}

	/**
	 * Create a share intent for the selected data
	 * 
	 * @return Intent - The url of the app in GooglePlay Store.
	 */
	private Intent getShareIntent() {
		this.packageName = this.getPackageName();
		this.appUri = "http://play.google.com/store/apps/details?id="
				+ packageName;
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");

		if (this.sap != null) {
			shareIntent.putExtra(Intent.EXTRA_TEXT, this.appUri);
		}

		return shareIntent;
	}

	public static String slurp(final InputStream is, final int bufferSize)
			throws IOException {
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();

		try {
			final Reader in = new InputStreamReader(is, "UTF-8");

			try {
				int rsz = 0;

				while (rsz >= 0) {
					rsz = in.read(buffer, 0, buffer.length);

					if (rsz < 0) {
						break;
					}

					out.append(buffer, 0, rsz);
				}
			} finally {
				in.close();
			}
		} catch (UnsupportedEncodingException ex) {
			// TODO Error message here.
		}

		return out.toString();
	}
}
