package net.ntechniks.thebesttoast.service;


import java.io.ByteArrayInputStream;
import java.io.InputStream;




import net.ntechniks.thebesttoast.model.ContentModel;
import net.ntechniks.thebesttoast.service.WikiXmlParser.WikiData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import java.io.FileOutputStream;
//import net.ntechniks.wiki.WikiXmlParser.WikiData;


public class WikiStarter {

	private Wiki_v29 wiki; // create wiki object
	private String pageTitle;
	private String domain;
	private String localDomain;
	private String subUrl;
	private String url;
	private String subDirectory;
	private String protocol;
	
	private int parsedOnTry;
	
	
	
	public static class WikiLanguage {
		
		public static final String english = "en";
		public static final String german = "de";
		public static final String Italian = "it";
		public static final String bulgarian = "bg";
		
	}//End of class
	
	
	
	
	private String setWikiLanguage(String wikiLanguage){
		
		String result;
		
		if(wikiLanguage == null){
			result = "en";
		}else{
			result = wikiLanguage;
		}		
		
		return result;
	}//End of method
	
	
	/**
	 * @return WikiData
	 * @throws Exception (the super class for Exceptions)
	 */
	
	public WikiData getRandomPageText(String wikiLanguage) {
		
		
		this.domain = ".wikipedia.org";
		this.localDomain = this.setWikiLanguage(wikiLanguage);			// set language
		this.subDirectory = "wiki";										// set sub wiki directory
		this.protocol = "https://";										// set protocol type
		this.subUrl = this.localDomain + this.domain;					// wiki start url
		
		
		this.wiki = new Wiki_v29(subUrl);								// set Wiki url
		this.wiki.setThrottle(5000); 									// set config

		
		WikiData wikiData;												// create new WikiData instance
		
		
		this.parsedOnTry = 0;	
		
		
		while(true){
			
			this.parsedOnTry++;	
			
			try{
				
				
				this.pageTitle = this.wiki.random();							// get Random page
				String content = this.wiki.export(this.pageTitle);				// export a page to xml
				
			
				this.url = this.protocol + this.subUrl + "/" + this.subDirectory + "/" + this.pageTitle;	// constructs the whole URL to a page article
				
				
				//System.out.println("\n\n\n");
				//System.out.println("Page url="+this.url);
				
				
				WikiXmlParser wikiXml = new WikiXmlParser();					// create new instance
				//wikiData = wikiXml.parse(content, this.wiki);					// parses Xml content and return a class with Parsed Data
				wikiData = null;
				
				//System.out.println("\n\n\n");
				//System.out.println("parsed on Try="+this.parsedOnTry);
				
				break;
			}catch(Exception e){
				// ignore ( if there's an exception it will just start the cycle again to get a page that actually can be parsed without an error )
				Log.e(null, e.getMessage());
				//log.
			}//End of try	
		}//End of while
		
		
		return wikiData; // return data
	}//End of method
	

	public ContentModel getRandomPageTextX(String wikiLanguage) {
		
		
		this.domain = ".wikipedia.org";
		this.localDomain = this.setWikiLanguage(wikiLanguage);					// set language
		this.subDirectory = "wiki";												// set sub wiki directory
		this.protocol = "https://";												// set protocol type
		this.subUrl = this.localDomain + this.domain;							// wiki start url
		
								
		this.wiki = new Wiki_v29(subUrl);										// set Wiki url				
		this.wiki.setThrottle(5000); 											// set config

		
		WikiData wikiData = null;												// create new WikiData instance
		//ContentModel contentModel = new ContentModel();

		this.parsedOnTry = 0;	
		
		
		
		while(true){
			
			this.parsedOnTry++;	
			
			try{
				
				
				this.pageTitle = this.wiki.random();							// get Random page			
				//this.pageTitle = "Javanshir_clan";
				//this.pageTitle = "Filmworks_XX:_Sholem_Aleichem";
				//this.pageTitle = "Konstantinos_Sapountzakis";
				//this.pageTitle = "People";
				
				String content = this.wiki.export(this.pageTitle);				// export a page to xml
				
				Log.w(null, "content= "+content);
			
				//String ImagesOnPage[] = this.wiki.getImagesOnPage(this.pageTitle);
				//Log.w(null, "Image length= "+Image.length);
				
				//if( Image.length > 2 ){
				//	for(int i=0; i<Image.length; i++){
				//		Log.w(null, "Image Name = "+Image[i]);
				//	}
				//}
				
				
				this.url = this.protocol + this.subUrl + "/" + this.subDirectory + "/" + this.pageTitle;	// constructs the whole URL to a page article
						
				
				WikiXmlParser wikiXml = new WikiXmlParser();												// create new instance
				wikiData = wikiXml.parse(content, this.wiki, this.pageTitle);									// parses Xml content and return a class with Parsed Data
				
		
				break;
			}catch(Exception e){
				// ignore ( if there's an exception it will just start the cycle again to get a page that actually can be parsed without an error )
				//e.printStackTrace();	
			}//End of try	
		}//End of while
		

		ContentModel contentModel = this.convertWikiDataToContentModel(wikiData);		// converts WikiData type to ContentModel type	
		
		return contentModel; // return data
	}//End of method
	
	
	private ContentModel convertWikiDataToContentModel(WikiData wikiData){
		
		ContentModel contentModel = new ContentModel();							// create new ContentModel instance
		
		contentModel.setArticalUrl(this.url);									// set URL
		contentModel.setTitle(this.pageTitle);									// set Page Title
		
		if( wikiData.getImagesFiles().isEmpty() == false ){						// check for available image elements
			//byte[] bitmapdata = wikiData.getImagesFiles().get(0);				// get Image byte array
			//Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
			//contentModel.setImage(bitmap);									// set Image 
		
			byte[] image = wikiData.getImagesFiles().get(0);
			InputStream is = new ByteArrayInputStream(image);
			contentModel.setImage(BitmapFactory.decodeStream(is));
		
			Log.w(null, "Bitmapa go ima");
		}
		
		Log.w(null, "Image tags size= " +wikiData.getImagesTags().size());
		
		
		if( wikiData.getImagesFiles() == null ){
			Log.w(null, "Bitmapa = null");
		}
		
		
		String blockContent = wikiData.getBlocksContent().get(0);				// get Description text
		contentModel.setDescription(blockContent);								// set Description text
		
		
		
		Log.w(null, "Test R ?");
		
		
		return contentModel;													// return instance
	}//End of method
	
	
	
	
}//End of class









