package co.alexdev.winy.utils;

public class Constants {

    public static class AnimDurations {
        public static final int SLIDE_ANIM = 300;
    }

    public static class FIREBASE_DATABASE {
        public static final String USER_REFERENCE = "users";

        public static class MESSAGES {
            public static final String SUCCES = "You have successfully registered and logged in.";
        }

        public enum SIGNUP_STATE {
            STARTED,FAILURE,NOT_SET,SUCCES
        }
    }
}


