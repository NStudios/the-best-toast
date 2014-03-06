/**
 * 
 */
package net.ntechniks.thebesttoast.model;

import android.graphics.Bitmap;

/**
 * @author Nikola Georgiev
 *
 */
public class ContentModel {
	
	private String title;
	private Bitmap image;
	private String description;
	private String articalUrl;
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the image
	 */
	public Bitmap getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Bitmap image) {
		this.image = image;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the articalUrl
	 */
	public String getArticalUrl() {
		return articalUrl;
	}
	/**
	 * @param articalUrl the articalUrl to set
	 */
	public void setArticalUrl(String articalUrl) {
		this.articalUrl = articalUrl;
	}
}
