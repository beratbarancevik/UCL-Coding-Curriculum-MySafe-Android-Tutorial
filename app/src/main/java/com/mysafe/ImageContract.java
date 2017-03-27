package com.mysafe;

import android.provider.BaseColumns;

public final class ImageContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ImageContract() {
    }

    /**
     * Inner class that defines constant values for the images database table.
     * Each entry in the table represents a single image.
     */
    public static final class ImageEntry implements BaseColumns {

        /**
         * Name of database table for images
         */
        public final static String TABLE_NAME = "images";

        /**
         * Unique ID number for the image (only for use in the database table).
         * <p>
         * Type: String
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Path of the image
         * <p>
         * Type: String
         */
        public final static String COLUMN_IMAGE_PATH = "image_path";

    }

}
