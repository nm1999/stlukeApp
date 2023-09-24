package com.chaplaincy.stlukeapp.Apis;

public class Urls {
   public static final String BASE_URL = "http://192.168.192.95/stlukeApp_Api/v1/";
   public static final String LOGIN_URL =BASE_URL+"auth.php?apiCall=login";
   public static final String REGISTER_URL =BASE_URL+"apiCall=register";
   public static final String TAKE_NOTES = BASE_URL+"sync_notes.php";
   public static final String HYMN = BASE_URL+"fetch_hymns.php";
   public static final String GET_HYMNS ="";
}
