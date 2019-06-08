package co.alexdev.winy.core.util;

public class Constants {

    public static class AnimValues {
        public static final float MAX_ALPHA = 1;
        public static final float MIN_ALPHA = 0;
    }

    public enum AUTH_LAYOUT_STATE {
        LOGIN, SIGNUP
    }

    public static class FIREBASE_DATABASE {
        public static final String USER_REFERENCE = "users";

        public static class MESSAGES {
            public static final String SUCCES = "You have successfully registered and logged in.";
            public static final String ERROR = "Please complete all required fields.";
            public static final String EMAIL = "Your email format is invalid.";
            public static final String AUTHENTICATION_FAILED = "Authentication Failed.";
            public static final String FORGOT_EMAIL_SENT = "An email has been sent.";
            public static final String FORGOT_EMAIL_INCORECT = "This email doesn't exist.";
        }

        public enum SIGNUP_STATE {
            STARTED, FAILURE, NOT_SET, SUCCES
        }

        public enum LOGIN_STATE {
            FAILURE, SUCCESS, NOT_SET, LOADING
        }
    }

    public static class NETWORK {
        public static final String HEADER = "header";
        public static final String X_RAPID_API_KEY = "X-RapidAPI-Key";
    }
    }


