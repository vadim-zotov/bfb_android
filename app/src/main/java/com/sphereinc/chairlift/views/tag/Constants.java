package com.sphereinc.chairlift.views.tag;

import android.graphics.Color;

public class Constants {

	//use dp and sp, not px
	
	//----------------- separator TagView-----------------//
	public static final float DEFAULT_LINE_MARGIN = 5;
	public static final float DEFAULT_TAG_MARGIN = 5;
	public static final float DEFAULT_TAG_TEXT_PADDING_LEFT = 8;
	public static final float DEFAULT_TAG_TEXT_PADDING_TOP = 5;
	public static final float DEFAULT_TAG_TEXT_PADDING_RIGHT = 8;
	public static final float DEFAULT_TAG_TEXT_PADDING_BOTTOM = 5;
	
	public static final float LAYOUT_WIDTH_OFFSET = 2;
	
	//----------------- separator Tag Item-----------------//
	public static final float DEFAULT_TAG_TEXT_SIZE = 14f;
	public static final float DEFAULT_TAG_DELETE_INDICATOR_SIZE = 14f;
	public static final float DEFAULT_TAG_LAYOUT_BORDER_SIZE = 0f;
	public static final float DEFAULT_TAG_RADIUS = 100;
	public static final int DEFAULT_TAG_LAYOUT_COLOR = Color.parseColor("#AED374");
	public static final int DEFAULT_TAG_LAYOUT_COLOR_PRESS = Color.parseColor("#88363636");
	public static final int DEFAULT_TAG_TEXT_COLOR = Color.parseColor("#ffffff");
	public static final int DEFAULT_TAG_DELETE_INDICATOR_COLOR = Color.parseColor("#ffffff");
	public static final int DEFAULT_TAG_LAYOUT_BORDER_COLOR = Color.parseColor("#ffffff");
	public static final String DEFAULT_TAG_DELETE_ICON = "×";
	public static final boolean DEFAULT_TAG_IS_DELETABLE = false;

	/**
	 * list from:
	 *  https://gist.github.com/Keeguon/2310008
	 */
	public static final String COUNTRIES = "[ \n" +
			"{\"name\": \"Afghanistan\", \"code\": \"AF\"}, \n" +
			"{\"name\": \"Åland Islands\", \"code\": \"AX\"}, \n" +
			"{\"name\": \"Albania\", \"code\": \"AL\"}, \n" +
			"{\"name\": \"Algeria\", \"code\": \"DZ\"}, \n" +
			"{\"name\": \"American Samoa\", \"code\": \"AS\"}, \n" +
			"{\"name\": \"AndorrA\", \"code\": \"AD\"}, \n" +
			"{\"name\": \"Angola\", \"code\": \"AO\"}, \n" +
			"{\"name\": \"Anguilla\", \"code\": \"AI\"}, \n" +
			"{\"name\": \"Antarctica\", \"code\": \"AQ\"}, \n" +
			"{\"name\": \"Antigua and Barbuda\", \"code\": \"AG\"}, \n" +
			"{\"name\": \"Argentina\", \"code\": \"AR\"}, \n" +
			"{\"name\": \"Armenia\", \"code\": \"AM\"}, \n" +
			"{\"name\": \"Aruba\", \"code\": \"AW\"}, \n" +
			"{\"name\": \"Australia\", \"code\": \"AU\"}, \n" +
			"{\"name\": \"Austria\", \"code\": \"AT\"}, \n" +
			"{\"name\": \"Azerbaijan\", \"code\": \"AZ\"}, \n" +
			"]";
}
