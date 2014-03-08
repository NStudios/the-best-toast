/**
 * 
 */
package net.ntechniks.thebesttoast.view;

import net.ntechniks.thebesttoast.R;
import net.ntechniks.thebesttoast.controller.MainActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Nikola Georgiev
 * 
 *         Fragment that appears in the "content_frame".
 */
public abstract class ContentFragment extends Fragment implements IContent {

	public static final String TAG = "screen";

	protected MainActivity activity;
	protected View rootView;

	protected int fragmentLayout;
	protected int contentId;

	public ContentFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof OnGoButtonListener) {
			this.activity = (MainActivity) activity;
		} else {
			throw new IllegalArgumentException(
					"Activity must implement OnGoButtonListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Init objects
		this.rootView = inflater.inflate(fragmentLayout, container, false);

		return rootView;
	}

	public interface OnGoButtonListener {
		void goButtonClicked();
	}
}