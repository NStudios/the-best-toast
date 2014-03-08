/**
 * 
 */
package net.ntechniks.thebesttoast.view;

import net.ntechniks.thebesttoast.R;
import net.ntechniks.thebesttoast.model.ContentModel;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Nikola Georgiev
 * 
 */
public class ResultScreen extends ContentFragment {

	public static final String TAG = "ResultScreen";
	
	private TextView textViewTitle;
	private TextView textViewDescription;
	private ImageView imageView;

	private ContentModel contentModel;

	public ResultScreen() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		fragmentLayout = R.layout.result_screen;
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
		Button goButon = (Button) rootView.findViewById(R.id.moreInfoButtom);
		
		this.textViewTitle = (TextView) rootView.findViewById(R.id.infoTitle);
		this.textViewDescription = (TextView) rootView.findViewById(R.id.infoDescription);
		this.imageView = (ImageView) rootView.findViewById(R.id.imageView);
		
		goButon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goInfoScreen();
			}
		});
	}

	private void goInfoScreen() {
		activity.goShowMoreInfo();
	}
	
	public void updateContent() {
		// Init resources
		String title = this.contentModel.getTitle();
		String description = this.contentModel.getDescription();
		Bitmap bm = this.contentModel.getImage();
		
		// Setting values
		this.textViewTitle.setText(title);
		this.textViewDescription.setText(description);
		this.imageView.setImageBitmap(bm);
	}
}
