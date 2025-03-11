package com.mcc_backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CommonConstants {
    public static final String SYSTEM_SCHEDULER = "system_schedulers";

    public static final char STATUS_ACTIVE = 'A';
    public static final char STATUS_INACTIVE = 'I';
    public static final char STATUS_DELETE = 'D';
    public static final char STATUS_PAID = 'P';
    public static final char STATUS_TRANSACTION_PENDING = 'P';
    public static final char STATUS_RESERVATION_CANCELLED = 'D';
    public static final char STATUS_COMPLETE = 'C';
    public static final char STATUS_NOT_PAID = 'N';
    public static final char STATUS_PARTIALLY_PAID = 'H';
    public static final char STATUS_SENT = 'S';
    public static final char STATUS_UNSENT = 'U';
    public static final char STATUS_FAILED = 'F';
    public static final char STATUS_YES = 'Y';
    public static final char STATUS_NO = 'N';

    public static final String EMPTY_STRING = "";
    public static final String EMPTY_SPACE_STRING = " ";
    public static final String CHARACTER_TYPE = "ISO-8859-1";
    public static final String HREF_ATTR = "href";

    // Menu type constants
    public static final char MENU_TYPE_MAIN_MENU = 'M';
    public static final char MENU_TYPE_SUB_MENU = 'S';

    // Roles
    public static final int ROLE_ID_ADMIN = 0;
    public static final int ROLE_ID_CUSTOMER = 1;
    public static final int ROLE_ID_DRIVER = 2;

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    public static final String ROLE_DRIVER = "DRIVER";

    public static final String ROLE_ADMIN_WITH_ROLE_PREFIX = "ROLE_ADMIN";
    public static final String ROLE_CUSTOMER_WITH_ROLE_PREFIX = "ROLE_CUSTOMER";
    public static final String ROLE_DRIVER_WITH_ROLE_PREFIX = "ROLE_DRIVER";

    public static final String IDENTIFIER_ROLE_ADMIN = "LTWRHaJVNMKk";
    public static final String IDENTIFIER_ROLE_CUSTOMER = "mGwDgRbpBKyf";
    public static final String IDENTIFIER_ROLE_DRIVER = "EBWSfvxfmWpr";

    // YML Properties
    @Value("${application.cors-allow-origins}")
    public static String YML_CORS_ALLOW_ORIGINS;

    @Value("${spring.mail.retry-count}")
    public static String MAX_RETRY_COUNT;

    @Value("${spring.mail.from}")
    public static String FROM_MAIL;

    @Value("${application.url.confirm-email}")
    public static String CONFIRM_USER_EMAIL_URL;

    @Value("${application.url.login}")
    public static String LOGIN_URL;

    @Value("${application.url.pwd-reset}")
    public static String PWD_RESET_URL;

    @Value("${gcp.project-id}")
    public static String GCP_PROJECT_ID;

    @Value("${gcp.bucket-name}")
    public static String GCP_BUCKET;

    // Date Format
    public static final String US_DATE_FORMATS_STRING = "MM/dd/yyyy";
    public static final String PHOTON_OCR_DATE_FORMAT_STRING = "yyyy-MM-dd";
    public static final String SEPERATOR_LESS_DATE_FORMATS_STRING = "MMddyyyy";
    public static final String UNATTENDED_PAYMENT_DATE_FORMAT = "yyy-MM-dd HH:mm:ss";

    // Email Template
    public static final Integer EMAIL_TEMPLATE_CONFIGURE_USER = 1;
    public static final Integer EMAIL_TEMPLATE_REGISTRATION_SUCCESS = 2;
    public static final Integer EMAIL_TEMPLATE_PWD_RESET = 3;
    public static final Integer EMAIL_GUEST_ACCOUNT = 4;

    // Params
    public static final String PARAM_EMAIL_SEND_TO = "EMAIL_SEND_TO";
    public static final String PARAM_CONFIGURATION_URL = "CONFIGURATION_URL";
    public static final String PARAM_PASSWORD_RESET_URL = "PWD_RESET_URL";
    public static final String PARAM_LOGIN_URL = "LOGIN_URL";
    public static final String PARAM_UUID = "UUID";
    public static final String PARAM_ID = "PARAM_ID";
    public static final String PARAM_USERNAME = "USERNAME";
    public static final String PARAM_PWD_RESET_TOKEN = "PWD_RESET_TOKEN";

    // SQL Query Constants
    public static final String SQL_AND = "AND";
    public static final String SQL_OR = "OR";
    public static final String SQL_ORDER_BY = "ORDER BY";

    // Payment Type
    public static final Integer PAYMENT_TYPE_CARD = 1;
    public static final Integer PAYMENT_TYPE_BANK = 2;

    // Other
    public static final String STRING_CURRENCY = "currency";
    public static final String STRING_AMOUNT = "amount";
    public static final String STRING_TRANSACTION_ID = "transactionId";

    // JWT Constants
    @Value("${jwt.secret}")
    public static String JWT_SECRET = "1c029f6b5a56cc4ebdf3b4347fbdd6444af72eb15b59cee3e3cd5f6a225cfa2651fa7f5b53437e849854372522009fcc0e6950ad14a88aedbae7aa4a498b0129";

    @Value("${jwt.expiration}")
    public static Long JWT_EXPIRATION = 86400000L;

    @Value("${jwt.token-prefix}")
    public static String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.header-string}")
    public static String HEADER_STRING = "Authorization";

    // Security Constants
    @Value("${security.public-paths}")
    private String publicPathsString = "/api/auth/login,/api/auth/register,/api/vehicles/taxi,/api/vehicles/rental,/api/bookings";

    public List<String> getPublicPaths() {
        return Arrays.asList(publicPathsString.split(","));
    }

    // Response Messages
    @Value("${response.error.unauthorized}")
    public static String UNAUTHORIZED_MESSAGE;

    @Value("${response.error.token-expired}")
    public static String TOKEN_EXPIRED_MESSAGE;

    @Value("${response.error.invalid-token}")
    public static String INVALID_TOKEN_MESSAGE;

    @Value("${response.success.valid-token}")
    public static String VALID_TOKEN_MESSAGE;

    // HTTP Status Codes
    public static final int STATUS_OK = 200;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_INTERNAL_ERROR = 500;

    // Date Format Patterns
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

    // Booking Constants
    public static final String DEFAULT_BOOKING_STATUS = "PENDING";
    public static final int MAX_ACTIVE_BOOKINGS = 5;

    // Vehicle Constants
    public static final int MIN_VEHICLE_YEAR = 2000;
    public static final String DEFAULT_VEHICLE_IMAGE = "default-vehicle.jpg";

    // Validation Messages
    public static final String REQUIRED_FIELD = "This field is required";
    public static final String INVALID_EMAIL = "Invalid email format";
    public static final String INVALID_PHONE = "Invalid phone number format";
    public static final String INVALID_DATE = "Invalid date format";
}
