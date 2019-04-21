package co.alexdev.winy.core.util;

public class Constants {

    public static class AnimDurations {
        public static final int SLIDE_ANIM = 300;
    }

    public static class FIREBASE_DATABASE {
        public static final String USER_REFERENCE = "users";

        public static class MESSAGES {
            public static final String SUCCES = "You have successfully registered and logged in.";
            public static final String ERROR = "Please complete all required fields.";
            public static final String EMAIL = "Your email format is invalid.";
        }

        public enum SIGNUP_STATE {
            STARTED, FAILURE, NOT_SET, SUCCES
        }

        public enum LOGIN_STATE {
            FAILURE, SUCCES, NOT_SET, LOADING
        }
    }
}


