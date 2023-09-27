package com.chaplaincy.stlukeapp.Apis;

public class Urls {
   public static final String BASE_URL = "http://stluke.api.ecosensecities.com/v1/";
//   public static final String BASE_URL = "http://192.168.18.29/stlukeApp_Apis/v1/";
   public static final String LOGIN_URL =BASE_URL+"auth.php?apiCall=login";
   public static final String REGISTER_URL =BASE_URL+"auth.php?apiCall=register";
   public static final String TAKE_NOTES = BASE_URL+"sync_notes.php";
   public static final String SEND_HYMNS = BASE_URL+"fetch_hymns.php";
   public static final String HYMN = BASE_URL+"hymns.php";
   public static final String TESTIMONIES = BASE_URL+"add_testimonies.php";
   public static final String ALL_STORIES = BASE_URL+"testimonies.php";
}
