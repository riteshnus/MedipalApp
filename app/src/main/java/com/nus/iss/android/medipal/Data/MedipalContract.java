package com.nus.iss.android.medipal.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ritesh on 3/8/2017.
 */

public class MedipalContract {
    public static final String CONTENT_AUTHORITY = "com.nus.iss.android.medipal";
    public static final Uri BASE_CONTENT = Uri.parse("content://"+ CONTENT_AUTHORITY);
    public static final String PATH_PERSONAL = "personal";

    public static final class personalEntry implements BaseColumns {
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_PERSONAL;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_PERSONAL;
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT,PATH_PERSONAL);
        public static final String TABLE_NAME = "personalBio";
        public static final String _ID = BaseColumns._ID;
        public static final String USER_NAME = "FirstName";
        public static final String USER_ADDRESS = "Address";

    }
}
