package krt.com.cityguide.Utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by bryden on 1/3/17.
 */

public class Constants {
    public static String lat = null;
    public static String lon = null;
    public static LatLng mylocation = null;
//    AgenciasDeViaje
//            (2:57 AM) Atracciones
//    (2:57 AM) Cafeterias
//            (2:59 AM) Compras
//    (3:00 AM) Urgencias
    public static String TABLE_AGENCY = "AgenciasDeViaje";
    public static String LANG = "lang";
    public static final String default_mail = "nick@visitabuga.com";
    public static final String MAILGUN_URL = "https://api.mailgun.net/v3/sandboxbbab06b3c54a44139f759ffcb8704b32.mailgun.org/messages";
    public static final String MAILGUN_API = "key-5010d5c4e2fd9270a01b83b4a102c10a";

    public static String TABLE_ATTRACTION = "Atracciones";
    public static String TABLE_CAFETERIA = "Cafeterias";
    public static String TABLE_URGENCY = "Urgencias";
    public static String TABLE_COMPRAS = "Compras";
    public static String[] weekDays = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public static String PLACE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo";//?width=
        public static final String USER_LOGINED = "user_logined";
    public static final String USER_NAME = "user_name";
    public static int object_kind ;
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_PHOTO = "user_photo";
    public static final String  USER_FIRSTNAME = "user_firstname";
    public static final String  USER_LASTNAME = "user_lastname";
    public static final String  USER_ID = "user_id";
    public static final String  ACCOUNT_ID = "account_id";
    public static final String GOOGLE_LOGIN = "google_login";
    public static final String FACEBOOK_LOGIN = "facebook_login";
    public static final String EMAIL_LOGIN = "email_login";
    public static final String DEVICE_ID = "device_id";
    public static final String OAUTH_REDIRECT_URL = "https://onebaby-f9aaf.firebaseapp.com/__/auth/handler";
    public static final String MANDRILL_KEY = "88fvV_HnVHd66uPEt51fFQ";
    public static final String USER_TOKEN = "user_token";
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    public static final String USER_DB = "users";
    public static final String ONE_ARTICLE = "one_article";
    public static final String COMMENTS = "comments";
    public static final String FAVORITE = "favorite";
    public static final String FAVORITE_FLAG = "favorite_flag";
    public static final String COMMENTS_FLAG = "comments_flag";
    public static String help_text = "";
    public static String legal_text = "";
    public static  String[] holidays = {"1/1", "1/9", "3/20",
            "4/13", "4/14", "5/1",
    "5/29", "6/19", "6/26", "7/3", "7/20",
    "8/7", "8/21", "10/16", "11/6",
    "11/13", "12/8", "12/25"};
    public static final String[] WeekDays = {"mon", "tue", "thu", "wed",
                                            "fri", "sat", "sun"};
    public static final String MON= "mon";
    public static final String TUE = "tue";
    public static final String THU = "thu";
    public static final String SAT = "sat";
    public static final String SUN = "sun";
    public static final String WED = "wed";
    public static final String FRI = "fri";
    public static final String HOL = "holi";
    public static final String DURATION = "duration";
    public static final String STARTTIME = "time";
}
