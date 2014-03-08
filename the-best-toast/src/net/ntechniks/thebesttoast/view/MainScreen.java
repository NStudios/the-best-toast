/**
 * 
 */
package net.ntechniks.thebesttoast.view;

import net.ntechniks.thebesttoast.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Nikola Georgiev
 * 
 */
public class MainScreen extends ContentFragment {

	public static final String TAG = "MainScreen";

	public MainScreen() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		fragmentLayout = R.layout.main_screen;
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initViewItems(savedInstanceState);
	}

	/**
	 * @param savedInstanceState
	 */
	private void initViewItems(Bundle savedInstanceState) {
		Button goButon = (Button) rootView.findViewById(R.id.goButton);

		goButon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goNextScreen();
			}
		});
	}

	private void goNextScreen() {
		activity.goButtonClicked();
	}
}
