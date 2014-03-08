package net.ntechniks.thebesttoast.service;


import java.io.IOException;
import java.util.ArrayList;
import android.util.Log;


/**
 * @author scx (Hristo Prodanov)
 * 
 */
public class WikiXmlParser {

	
	public boolean debug = false;			// debug flag that allows to System.out errors
	
	
	public class WikiData{
		
		private String textTagElement;
		private String textTag;	
		//private String parsedTextTag;
		
			
		private ArrayList<String> imagesTags = new ArrayList<String>();
		private ArrayList<String> imagesNames = new ArrayList<String>();
		private ArrayList<byte[]> imagesFiles = new ArrayList<byte[]>();
		
		
		private ArrayList<String> blocksTitle = new ArrayList<String>();
		private ArrayList<String> blocksTitleParsed = new ArrayList<String>();
		private ArrayList<String> blocksContent = new ArrayList<String>();
		
		
		
		public String getTextTagElement(){
			return this.textTagElement;
		}
		
		public String getTextTag(){
			return this.textTag;
		}
			
		public ArrayList<String> getImagesTags(){
			return this.imagesTags;
		}
		
		public ArrayList<String> getImagesNames(){
			return this.imagesNames;
		}
			
		public ArrayList<byte[]> getImagesFiles(){
			return this.imagesFiles;
		}	
		
		public ArrayList<String> getBlocksTitle(){
			return this.blocksTitle;
		}
		
		public ArrayList<String> getBlocksTitleParsed(){
			return this.blocksTitleParsed;
		}
			
		public ArrayList<String> getBlocksContent(){
			return this.blocksContent;
		}
			
		
	}//End of class
	
	
	//under constructing ***
	
	// check for {{Authority control|VIAF=214946442}} Tags
	// no not check for integrity
	public static void parseExtensionTag(String content){
		
		/*
		String parserBeginET = "{{";
		String parserEndET = "}}";				
		int iBeginExt;
		int iEndExt;
		
		String parserTR = "|";
		int iBeginRedir;
		int iEndRedir;
		
		int iTemp1; 
		int iTemp2;
		
		boolean notFound = false;
		
		
		iBeginExt = content.indexOf(parserBeginET);
		iEndExt = content.indexOf(parserEndET);
		
		
		iBeginRedir = content.indexOf(parserTR);
		
		
		if( iBeginRedir < iEndExt ){
			iBeginRedir = content.indexOf(parserTR, iEndExt);
			if( iBeginRedir == -1 ){}
			
			iTemp1 = content.indexOf(parserBeginET, iBeginExt);
			if( iTemp1 == -1 ){}
			
			
			iTemp2 = content.indexOf(parserEndET, iBeginExt);
			if( iTemp2 == -1 ){}
			
			
			//if(iTemp )
			
		}
		
		
		String s =content.substring(iBeginExt, iEndExt+parserEndET.length());
		
		
		
		System.out.println("\n\n\n");
		System.out.println("iBeginExt=" + iBeginExt);
		System.out.println("iEndExt=" + iEndExt);
		
		System.out.println("s="+s);
		
		System.out.println("iBeginRedir=" + iBeginRedir);
		
		*/
		
		
		String parserBeginExt = "{{";
		String parserEndExt = "}}";	
		
		int iBeginExt;
		int iEndExt; 
		int iNextExt;
		
		String subContent;
		
		
		ArrayList<String> parsedExtensionTags = new ArrayList<String>();
		ArrayList<Integer> parsedExtensionTagsPosition = new ArrayList<Integer>();
		
		
		iNextExt = 0;
		while(true){
		//for(int i=0; i<2; i++){
			
			
			iBeginExt = content.indexOf(parserBeginExt, iNextExt);
			if(iBeginExt == -1){break;}
			
			iEndExt = content.indexOf(parserEndExt, iNextExt);
			if(iEndExt == -1){break;}
			
			
			subContent = content.substring(iBeginExt, iEndExt + parserEndExt.length());
			
			parsedExtensionTags.add(subContent);
			parsedExtensionTagsPosition.add(iBeginExt);
			
			iNextExt = iEndExt + parserEndExt.length();
			
			//System.out.println("iBeginExt="+iBeginExt);
			//System.out.println("iEndExt="+iEndExt);
			//System.out.println("iNextExt="+iNextExt);
			
			
			
		}//End of while
		
		
		
		System.out.println("\n\n\n");
		for(int i=0; i < parsedExtensionTags.size(); i++ ){
			System.out.println("parsedExtensionTags["+i+"]= "+parsedExtensionTags.get(i));
		}
		
		System.out.println("\n\n\n");
		for(int i=0; i < parsedExtensionTags.size(); i++){
			System.out.println("patsedExtensionTagsPosition["+i+"]= "+parsedExtensionTagsPosition.get(i));
		}
		
		
		//in testing
		//checkAndRemoveTransformedRedirecting(content, parsedExtensionTags, parsedExtensionTagsPosition);
		
		
		
	}//End of method
	
	
	// under constructing ***
	public static void checkAndRemoveTransformedRedirecting(String content, ArrayList<String> parsedExtensionTags, ArrayList<Integer> parsedExtensionTagsPosition){
		
		
		int positionBegin = 0;
		int positionNext = -1;
		int positionNextEnd = -1;
		int countExtensionTags = parsedExtensionTagsPosition.size() - 1;	
		String subContent = "";
		
		int i = 0;
		
		
		
		
		positionNext = parsedExtensionTagsPosition.get(i);
		if( positionBegin == positionNext ){											// check for Extension Tag at beginning
			positionNextEnd = positionNext + parsedExtensionTags.get(i).length();
			positionBegin = positionNextEnd;
		}
		
		
		if( (i+1) <= countExtensionTags){												// check for available Next Extension Tag
			
			
			positionNext = parsedExtensionTagsPosition.get(i+1);
			
			subContent = content.substring(positionBegin, positionNext);
			
			
			
		}else{						
			positionBegin = parsedExtensionTags.get(i).length();						// get position end from first Extension Tag
			content = removeTransformedRedirecting(content, positionBegin);				// remove the found Transformed Tags	
		}
		
		
		
		
		
		System.out.println("\n\n\n");
		System.out.println("content = "+content);
		//System.out.println("positionBegin= " + positionBegin);
		//System.out.println("positionNext= " + positionNext);
		//System.out.println("positionNextEnd= "+positionNextEnd);
		
		
		
	}//End of method
	
	
	public static String removeTransformedRedirecting(String content, int position){
		
		
		String key = "|";		
		String wordBegin;
		String parser = " ";
		int posKey;
		int posBegin;	
		int posEnd;
		int c = -1;		
		
		
		while(true){
		
			
			posKey = content.indexOf(key, position);							// get "|" position					
			if(posKey == -1){													// if don't have position then break
				break;
			}
			System.out.println("posKey= "+posKey);	
			posEnd = content.indexOf(parser, posKey);							// get word end position
			if(posEnd == -1){													// id don't have position then break
				break;
			}
				
			
			// this block gets the position of the wordBegin
			c = -1;
			while(true){
				c++; // revers offset		
				if ( content.charAt(posKey - c) == ' ' ){						// check for space to parse by it
					posBegin = posKey - c + 1;									// set new position
					break;
				}		 					
			}//End of while
			
						
			wordBegin = content.substring(posBegin, posKey + key.length());		// get wordBegin String
			content = content.replace(wordBegin, "");							// delete wordBegin from content
					
			
		}//End of while
		
		
		return content;
	}//End of method
	
	
	private String removeTransformedLinks(String content){
		content = content.replace("[[", "");
		content = content.replace("]]", "");
		
		return content;
	}
	
	
	private ArrayList<String> parseBlocksContent(String content, ArrayList<String> blocksTitle) throws Exception{
		
		ArrayList<String> blocksContent =  new ArrayList<String>();
		
		int posBegin;
		int posNext = 0;
		String subContent = "";
		String tableBlock = "{{";
		int posTableBlock;
			
		
		for(int i=0; i<blocksTitle.size(); i++){
			
			
			posBegin = content.indexOf(blocksTitle.get(i));					// get Begin index
			
			if(i+1 < blocksTitle.size()){
				posNext = content.indexOf(blocksTitle.get(i+1));			// get End index
				subContent = content.substring(posBegin + blocksTitle.get(i).length(), posNext);
				subContent = removeTransformedLinks(subContent);
			}else{				
				posTableBlock = content.indexOf(tableBlock, posNext);				
				posNext = posTableBlock;								
				subContent = content.substring(posBegin + blocksTitle.get(i).length(), posNext);
				subContent = removeTransformedLinks(subContent);
			}
			
			
			blocksContent.add(subContent);
		}//End of for
		
		subContent = content.substring(posNext,content.length());
		blocksContent.add(subContent);
		
		
		return blocksContent;
	}//End of method
	
	
	// input arrayTitle - == Name ==
	// output arrayTitleNames - Name
	private ArrayList<String> parseBlocksTitleName(ArrayList<String> blocksTitle) throws Exception{
			
		ArrayList<String> blocksTitleParsed = new ArrayList<String>();
		
		String parseType1 = "'''";
		String parseType2 = "==";
		String content;
		int check;
			
		for(int i=0; i < blocksTitle.size(); i++){
			
			content = blocksTitle.get(i);					// get Title String
			
			check = content.indexOf(parseType1);	
			if(check != -1){
				content = content.replace(parseType1, "");
				content = content.trim();			
				blocksTitleParsed.add(content);
				continue;
			}
			
			check = content.indexOf(parseType2);		
			if(check != -1){
				content = content.replace(parseType2, "");
				content = content.trim();		
				blocksTitleParsed.add(content);
				continue;
			}
							
		}//End of for	
		
		return blocksTitleParsed;
	}//End of method
	
	
	// input the string to be parsed
	// output the parsed array
	private ArrayList<String> parseBlocksTitle(String content) throws Exception{
		
		ArrayList<String> blocksTitle = new ArrayList<String>();
		
		String parseBlockNameStart;
		String parseBlockNameEnd;
		int offset = 1;	
		int indexStart;
		int indexEnd;
		String Title;
		
		int indexNext = 0;
		
		
		parseBlockNameStart = "'''";
		parseBlockNameEnd = "'''";
		
		
		indexStart = content.indexOf(parseBlockNameStart);
		indexEnd = content.indexOf(parseBlockNameEnd, indexStart + offset);
		
		Title = content.substring(indexStart, indexEnd + parseBlockNameEnd.length());
		blocksTitle.add(Title);
		
		parseBlockNameStart = "==";
		parseBlockNameEnd = "==";
		
		while(true){
					
			
			indexStart = content.indexOf(parseBlockNameStart, indexNext);
			if(indexStart == -1){break;}
			indexEnd = content.indexOf(parseBlockNameEnd, indexStart + offset);
			if(indexEnd == -1){break;}
				
			
			Title = content.substring(indexStart, indexEnd + parseBlockNameEnd.length());
			blocksTitle.add(Title);
		
					
			indexNext = indexEnd + offset;
		
		}//End of while	
		
		return blocksTitle;
	}//End of method
	
	
	private void parseBlocks(String content, WikiData wikiData) throws Exception{
		
		
		wikiData.blocksTitle = parseBlocksTitle(content);								// parse BlocksTitles and return the parsed array	
		wikiData.blocksTitleParsed = parseBlocksTitleName(wikiData.blocksTitle);		// parse BlocksTitlesNames and return the parsed array
		wikiData.blocksContent = parseBlocksContent(content, wikiData.blocksTitle);		// parse BlockContent and return the parsed array
		
		
		/*
		System.out.println("\n\n\n");
		for(int i=0;i < wikiData.blockContent.size(); i++){
			System.out.println("blockContent["+i+"]="+wikiData.blockContent.get(i));
		}
		*/
	}//End of method
	
	
	
	// set Image Files Data to global Array
	private void getImagesFiles(WikiData wikiData, Wiki_v29 wiki29, String pageTitle) throws Exception{
		
			
		byte[] byteArr;
		
		for(int i=0; i < wikiData.imagesNames.size(); i++){		
			byteArr = wiki29.getImage_v27(wikiData.imagesNames.get(i), -1, -1);		
			wikiData.imagesFiles.add(byteArr);				
		}
		
		Log.w(null, "imagesNames.size = "+wikiData.imagesNames.size());
		
		
		if( wikiData.imagesNames.size() == 0){
			Log.w(null, "in ImagesNames if");
			
			String[] imagesOnPage = wiki29.getImagesOnPage(pageTitle);
			if( imagesOnPage.length > 2 ){
				Log.w(null, "in imagesOnPage >2 if");
				
				int length = imagesOnPage.length;
				Log.w(null, "in imagesOnPage length ="+length);
				
				for(int i=0; i<imagesOnPage.length; i++){
					Log.w(null, "imageName["+i+"]= "+imagesOnPage[i]);
				}
				
				String imageName = imagesOnPage[length-3];
				Log.w(null, "imageName= "+imageName);
				
				byteArr = wiki29.getImage_v27(imageName, -1, -1);
				Log.w(null, "byteArr= "+byteArr.length);
				wikiData.imagesFiles.add(byteArr);
			}
		}
		
		
		
	}//End of method
	
	
	
	
	// parse ImageTag to ImageName - [[File:Konstantinos Sapountzakis.JPG|thumb|200px|right|Photograph of Lt Gen Sapountzakis]] to File:Konstantinos Sapountzakis.JPG    
	private void getImagesNames(WikiData wikiData) throws Exception{
		
		ArrayList<String> imagesTags = wikiData.imagesTags;
		ArrayList<String> imagesNames = new ArrayList<String>();
		String parseImageTagStart = "[[";
		String parseImageTagDivider = "|";
		//String parseImageTagEnd = "]]";
		String imageTag;	
		int indexStart;
		int indexDivider;
		//int indexEnd;
		
		
		for(int i=0; i<imagesTags.size(); i++ ){
			 imageTag = imagesTags.get(i);											// get imageTag String 
			 indexStart = imageTag.indexOf(parseImageTagStart);						// get indexStart
			 indexStart = indexStart + parseImageTagStart.length();					// get the parser length offset
			 indexDivider = imageTag.indexOf(parseImageTagDivider);	 				// get indexDivider
			 imagesNames.add(imageTag.substring(indexStart, indexDivider));			// substring the fileName
		}
		
					
		wikiData.imagesNames = imagesNames;		
	}//End of method
	
	
	
	
	// gets Images Names from content
	// return content without Images Names
	private String getImagesTags(String content, WikiData wikiData) throws Exception{
		
		String imageStartParse = "[[File:"; 
		String imageEndParse = "\n";				// or ]]		
		String subContent;		
		int indexStart;
		int indexEnd;
		
			
		while(true){
			
				
			indexStart = content.indexOf(imageStartParse);				// get indexStart of image file - [[File			
			if(indexStart == -1){break;}								// break if index not found

			indexEnd = content.indexOf(imageEndParse,indexStart);		// get indexEnd of image file - ]] by \n		
			
			
			if(indexEnd == -1){break;}									// breal if index not found
			subContent = content.substring(indexStart,indexEnd); 		// get image file tag - [[File --- ]]
					
			wikiData.imagesTags.add(subContent);						// add Image tag to ArrayList
			
			content = content.substring(indexEnd);						// modify content
			
						
		}//End of while
		
		
		return content;													// return content
	}//End of method
	
	
	// gets Images Names from content
	// return content without Images Names
	private String getImagesTagsV3(String content, WikiData wikiData) throws Exception{
			
			
		String imageStartParse = "[[File:"; 
		String imageStartParse2 = "[[Image:";
		String imageEndParse = "\n";				// or ]]		
		String subContent;		
		int indexStart;
		int indexEnd;
			
				
		while(true){
				
					
			indexStart = content.indexOf(imageStartParse);				// get indexStart of image file - [[File			
			if(indexStart == -1){
				indexStart = content.indexOf(imageStartParse2);
				if(indexStart == -1){break;}							// break if index not found
			}								
				
				
			indexEnd = content.indexOf(imageEndParse,indexStart);		// get indexEnd of image file - ]] by \n		
				
				
			if(indexEnd == -1){break;}									// breal if index not found
			subContent = content.substring(indexStart,indexEnd); 		// get image file tag - [[File --- ]]
						
				
			wikiData.imagesTags.add(subContent);						// add Image tag to ArrayList	
			content = content.replace(subContent, "");					// modify content
												
							
		}//End of while
			
		
		getImagesTagsUndefined(content, wikiData);
		
		
		return content;													// return content
	}//End of method
	
	
	// proverqva za kartinki s nedefinirani tagove
	private void getImagesTagsUndefined(String content, WikiData wikiData) throws Exception{
			
			
		String imageStartParse = "image";
		String imageDivider = "=";
		String imageEndParse = ".jpg";
		String subContent;
		int indexStart;
		int indexDivider;
		int indexEnd;
		int position = 0;
			
			
		while (true) {

			indexStart = content.indexOf(imageStartParse, position);
			if (indexStart == -1) {break;}

			indexDivider = content.indexOf(imageDivider, indexStart);
			if (indexDivider == -1) {break;}

			indexEnd = content.indexOf(imageEndParse, indexDivider);
			if (indexEnd == -1) {break;}

			subContent = content.substring(indexDivider, indexEnd
					+ imageEndParse.length());
			wikiData.imagesTags.add(subContent);

			position = indexStart + 1;
		}// End of while
			
			
	}//End of method
	
	
	// cut Unreferenced Element from content
	// and return content without Unreferenced Elements
	private String cutUnreferenced(String content) throws Exception{
		
		
		String parseUnreferencedStart = "{{Unreferenced";					// set Unreferenced start type
		String parseUnreferencedEnd = "}}";									// set Unreferenced end type
		
		int indexStart = content.indexOf(parseUnreferencedStart);			// get Unreferenced start position
		int indexEnd = content.indexOf(parseUnreferencedEnd, indexStart);	// get Unreferenced end position
		
		
		try{
		
			String subContent = content.substring(indexStart, indexEnd + parseUnreferencedEnd.length());
			return content.replace(subContent, "");
		
		}catch(Exception e){
			// if Unreferenced have been not found
			return content;
		}
					
		
	}//End of method
	
	
	// input TextTag Element
	// output 1 TextTag Text content
	// output 2 TextTag configuration
	private String getTextTagText(String content, WikiData wikiData) throws Exception{
		
		
		String parseTextTagStart = "<text";									// set Tag start type
		String parseTextTagEnd = ">";										// set Tag end type
		
		int indexStart = content.indexOf(parseTextTagStart);				// get Tag start position
		int indexEnd = content.indexOf(parseTextTagEnd);					// get Tag end position
		
		
		wikiData.textTag = content.substring(indexStart, indexEnd + parseTextTagEnd.length()); 	// set TextTag configuration
			
		
		return content.substring(indexEnd+parseTextTagEnd.length());		// return TextTag Text content	
	}//End of method	
	
	
	// input Xml document
	// output TextTag Element
	private String getTextTagElement(String content, WikiData wikiData) throws Exception{
		
		int indexStart = content.indexOf("<text xml:space=\"preserve\" bytes=");
		int indexEnd = content.indexOf("</text>");
		
		String subContent = content.substring(indexStart,indexEnd); 	
		wikiData.textTagElement = subContent;
		
		return subContent;					
	}//End of method	
	
	
	
	public WikiData parse(String content, Wiki_v29 wiki29, String pageTitle) throws Exception{
		
		WikiData wikiData = new WikiData();								// create new WiikiData instance
		
		//try{
			

			content = this.getTextTagElement(content, wikiData);		// get Text Tag Element 		
			content = this.getTextTagText(content, wikiData);			// get Text Tag Text	
			content = this.cutUnreferenced(content);					// cut Unreferenced Xml Element	
			content = this.getImagesTagsV3(content, wikiData);			// sets Images Tags in global Array and cuts them from content 
			
			this.getImagesNames(wikiData);								// sets Images Names in global Array
			this.parseBlocks(content, wikiData);						// parse Text Blocks
			this.getImagesFiles(wikiData, wiki29, pageTitle);			// sets byte Data to global ImageFiles Array
				
			
		//}catch(Exception e){
		//	e.printStackTrace();
		//}
		
		
		return wikiData;
	}//End of method
	
	
	public WikiData parse(String content) throws Exception{
		
		
		WikiData wikiData = new WikiData();
		
				
		content = this.getTextTagElement(content, wikiData);		// get Text Tag Element 		
		content = this.getTextTagText(content, wikiData);			// get Text Tag Text	
		content = this.cutUnreferenced(content);					// cut Unreferenced Xml Element	
		content = this.getImagesTags(content, wikiData);			// sets Images Tags in global Array and cuts them from content 
		
		this.getImagesNames(wikiData);								// sets Images Names in global Array
		

		parseBlocks(content, wikiData);								// parse Text Blocks	
		
		
		
		
		
		
		
		
		return wikiData;
		
		
	}//End of parse
	
	
	
	
	
	
}//End of class


















