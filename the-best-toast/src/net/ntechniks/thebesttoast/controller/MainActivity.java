package net.ntechniks.thebesttoast.controller;

import net.ntechniks.thebesttoast.R;
import net.ntechniks.thebesttoast.model.ContentModel;
import net.ntechniks.thebesttoast.service.WikiStarter;
import net.ntechniks.thebesttoast.service.WikiStarter.WikiLanguage;
import net.ntechniks.thebesttoast.service.interfaces.IShowModelContent;
import net.ntechniks.thebesttoast.view.ContentFragment;
import net.ntechniks.thebesttoast.view.ContentFragment.OnGoButtonListener;
import net.ntechniks.thebesttoast.view.MainScreen;
import net.ntechniks.thebesttoast.view.ResultScreen;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements IShowModelContent,
		OnGoButtonListener {

	private ShareActionProvider sap;
	private ContentModel contentModel;
	private ContentFragment fragment;
	private String packageName;
	private String appUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_action_menu, menu);
		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.main_action_share);

		// Fetch and store ShareActionProvider
		this.sap = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

		// Call to update the share intent
		Intent shareIntent = getShareIntent();
		setShareIntent(shareIntent);

		// Return true to display menu
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action buttons
		int itemId = item.getItemId();

		switch (itemId) {
		case R.id.main_action_more_apps:
			openMoreApps();
			return true;
		case R.id.main_action_about:
			openAbout();
			return true;
		case R.id.main_action_exit:
			exit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void init() {
		ContentFragment fragment = new MainScreen();

		replaceFragment(fragment);
	}

	/**
	 * @param fragment
	 */
	private void replaceFragment(ContentFragment newFragment) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = this.getFragmentManager();
		Bundle args = new Bundle();
		int position = 0;

		// Putting unic ID of the fragment in the Bundle and creating it.
		args.putInt(ContentFragment.TAG, position);

		if (newFragment != null) {
			newFragment.setArguments(args);
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, newFragment).commit();
			this.fragment = newFragment;
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

	/**
	 * @param shareIntent
	 */
	private void setShareIntent(Intent shareIntent) {
		if (this.sap != null) {
			this.sap.setShareIntent(shareIntent);
		}
	}

	private void openMoreApps() {

	}

	private void openAbout() {
		Intent in = new Intent(this, AboutActivity.class);
		this.startActivity(in);
	}

	private void exit() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(intent);
	}

	public void goButtonClicked() {
		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
			WikiStarter service = new WikiStarter();

			@Override
			protected Void doInBackground(Void... params) {
				contentModel = service.getRandomPageTextX(WikiLanguage.english);
				return null;
			}
		};

		ContentFragment newFragment = new ResultScreen();
		replaceFragment(newFragment);
	}

	public void goShowMoreInfo() {

	}

	@Override
	public void showModelContent() {
		((ResultScreen) fragment).updateContent();
	}
}