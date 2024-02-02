package online.ronikier.todo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {

    //TODO: Remove development message
    public static final String DEV_IMPLEMENT_ME = "DEV_IMPLEMENT_ME";

    public static final String SEPARATOR = " - ";

    public static final String HTML_BR = "<BR/>";

    public static final String LONG_RULE =  "======================================";

    public static final String TYPE_RESOURCE_PERSON = "TYPE_RESOURCE_PERSON";
    public static final String TYPE_RESOURCE_MONEY = "TYPE_RESOURCE_MONEY";
    public static final String TYPE_RESOURCE_PICTURE = "TYPE_RESOURCE_PICTURE";
    public static final String TYPE_RESOURCE_DOCUMENT = "TYPE_RESOURCE_DOCUMENT";
    public static final String TYPE_RESOURCE_URL = "TYPE_RESOURCE_URL";
    public static final String TYPE_RESOURCE_PHONE_NO = "TYPE_RESOURCE_PHONE_NO";
    public static final String TYPE_RESOURCE_EMAIL = "TYPE_RESOURCE_EMAIL";

    public static final String TASK_TYPE_GENERAL = "\uD83D\uDC7E";
    public static final String TASK_TYPE_PERSONAL = "\uD83E\uDD16";
    public static final String TASK_TYPE_MONEY = "\uD83D\uDC7D";
    public static final String TASK_TYPE_WORK = "♻";

//    public static final String TASK_TYPE_GENERAL = "\uD83C\uDF0D";
//    public static final String TASK_TYPE_PERSONAL = "\uD83D\uDD31";
//    public static final String TASK_TYPE_MONEY = "\uD83D\uDCB0";
//    public static final String TASK_TYPE_WORK = "♻";

//    public static final String TASK_TYPE_GENERAL = "\uD83D\uDD30";
//    public static final String TASK_TYPE_PERSONAL = "⚜";
//    public static final String TASK_TYPE_MONEY = "\uD83D\uDCB2";
//    public static final String TASK_TYPE_WORK = "♻";


//    public static final String TASK_STATE_STARTED = "Started";
//    public static final String TASK_STATE_ON_HOLD = "On hold";
//    public static final String TASK_STATE_COMPLETED = "Completed";
//    public static final String TASK_STATE_REJECTED= "Rejected";
//
//    public static final String TASK_STATUS_OK = "OK";
//    public static final String TASK_STATUS_THIS_WEEK = "This week";
//    public static final String TASK_STATUS_TODAY = "Today";
//    public static final String TASK_STATUS_TOMMOROW = "Tommorow";
//    public static final String TASK_STATUS_APPROACHING = "Approaching";
//    public static final String TASK_STATUS_DELAYED = "Delayed";

    public static final String TASK_STATE_STARTED = "▶";
    public static final String TASK_STATE_ON_HOLD = "⭕";
    public static final String TASK_STATE_COMPLETED = "\uD83D\uDFE9";
    public static final String TASK_STATE_REJECTED= "❌";

    public static final String TASK_STATUS_OK = "\uD83D\uDFE2";
    public static final String TASK_STATUS_TODAY = "\uD83D\uDD34";
    public static final String TASK_STATUS_TOMMOROW = "\uD83D\uDFE0";
    public static final String TASK_STATUS_NEXT_WEEKEND = "\uD83D\uDD35";
    public static final String TASK_STATUS_NEXT_WEEK = "\uD83D\uDFE1";
    public static final String TASK_STATUS_NEXT_MONTH = "\uD83D\uDFE3";
    public static final String TASK_STATUS_DELAYED = "⚫";

    public static final String TASK_STATUS_THIS_WEEK = "\uD83D\uDFE6";
    public static final String TASK_STATUS_APPROACHING = "⬜";

    public static final String TASK_PROCESSING_SKIPPED = "TASK_PROCESSING_SKIPPED";
    public static final String TASK_PROCESSING_STARTED = "TASK_PROCESSING_STARTED";
    public static final String TASK_PROCESSING_IN_PROGRESS = "TASK_PROCESSING_IN_PROGRESS";
    public static final String TASK_PROCESSING_HALT = "TASK_PROCESSING_HALT";
    public static final String TASK_PROCESSING_CUSTOM_ACTION = "TASK_PROCESSING_CUSTOM_ACTION";

    public static final String FILE_STORE_FAILED = "FILE_STORE_FAILED";

    public static final String COST_UNIT_PLN = "PLN";
    public static final String COST_UNIT_HOUR = "Hours";
    public static final String COST_UNIT_DAY = "Days";
    public static final String COST_UNIT_SOLDIER = "YARI";


    public static final String TYPE_PLACE_CITY = "TYPE_PLACE_CITY";
    public static final String TYPE_PLACE_BUILDING = "TYPE_PLACE_BUILDING";

    public static final String TYPE_PERSON_PUBLIC = "TYPE_PERSON_PUBLIC";
    public static final String TYPE_PERSON_FAMILY = "TYPE_PERSON_FAMILY";
    public static final String TYPE_PERSON_HOSTILE = "TYPE_PERSON_HOSTILE";

    public static final String TYPE_ORGANISATION_OFFICE = "TYPE_ORGANISATION_OFFICE";
    public static final String TYPE_ORGANISATION_COMPANY = "TYPE_ORGANISATION_COMPANY";

    public static final String ERROR_DIALOG_ACTION = "ERROR_DIALOG_ACTION";
    public static final String EXCEPTION_TASK_DATE_PARSE = "EXCEPTION_TASK_DATE_PARSE";
    public static final String EXCEPTION_DIALOG_ACTION_NULL = "EXCEPTION_DIALOG_ACTION_NULL";
    public static final String EXCEPTION_DIALOG_TASK_ID_NULL = "EXCEPTION_DIALOG_TASK_ID_NULL";
    public static final String EXCEPTION_DIALOG_ACTION_TASK = "EXCEPTION_DIALOG_ACTION_TASK";

    public static final String EXCEPTION_TASK_CREATION = "EXCEPTION_TASK_CREATION";
    public static final String EXCEPTION_PERSON_CREATION = "EXCEPTION_PERSON_CREATION";

    public static final String TASK_STATUS_CHANGED = "TASK_STATUS_CHANGED";

    public static final String ERROR_TASK_ADD = "ERROR_TASK_ADD";
    public static final String ERROR_PARAMETER_NAME_EMPTY = "ERROR_PARAMETER_NAME_EMPTY";
    public static final String ERROR_PARAMETER_VALUE_EMPTY = "ERROR_PARAMETER_VALUE_EMPTY";
    public static final String ERROR_TASK_DOES_NOT_EXIST = "ERROR_TASK_DOES_NOT_EXIST";
    public static final String ERROR_PARAMETER_COMMAND_BUTTON_ACTION = "ERROR_PARAMETER_COMMAND_BUTTON_ACTION";

    public static final String ERROR_PERSON_ADD = "ERROR_PERSON_ADD";
    public static final String ERROR_PARAMETER_USERNAME_EMPTY = "ERROR_PARAMETER_NAME_EMPTY";
    public static final String ERROR_PERSON_DOES_NOT_EXIST = "ERROR_PERSON_DOES_NOT_EXIST";

    public static final String DATE_NOT_SET = "- - -";

    public static final String INITIALISING_DATABASE = "INITIALISING_DATABASE";
    public static final String NEO4J_CONNECTION_SETUP = "NEO4J_CONNECTION_SETUP";
    public static final String TASK_NOT_FOUND = "TASK_NOT_FOUND";
    public static final String TASK_CREATED = "TASK_CREATED";
    public static final String TASK_EXISTS = "TASK_EXISTS";
    public static final String TASK_MODIFIED = "TASK_MODIFIED";
    public static final String TASK_DELETING = "TASK_DELETING";
    public static final String TASK_RESCHEDULED = "TASK_RESCHEDULED";
    public static final String SKIPPING_MAINTENANCE_TASKS = "SKIPPING_MAINTENANCE_TASKS";
    public static final String SKIPPING_NOT_REQUIRED = "SKIPPING_NOT_REQUIRED";

    public static final String UNKNOWN_TASK_STATE = "UNKNOWN_TASK_STATE";

    public static final String FORM_TASK_VALIDATION_IMPORTANT_NOT_NULL = "FORM_TASK_VALIDATION_IMPORTANT_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_URGENT_NOT_NULL = "FORM_TASK_VALIDATION_URGENT_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_CREATED_NOT_NULL = "FORM_TASK_VALIDATION_CREATED_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_START_NOT_NULL = "FORM_TASK_VALIDATION_START_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_DUE_NOT_NULL = "FORM_TASK_VALIDATION_DUE_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_NAME_NOT_EMPTY = "FORM_TASK_VALIDATION_NAME_NOT_EMPTY";
    public static final String FORM_TASK_VALIDATION_NAME_SIZE_MAX = "FORM_TASK_VALIDATION_NAME_SIZE_MAX";
    public static final String FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX = "FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX";
    public static final String FORM_TASK_VALIDATION_DESCRIPTION_NOT_EMPTY = "FORM_TASK_VALIDATION_DESCRIPTION_NOT_EMPTY";

    public static final String FORM_PERSON_VALIDATION_USERNAME_NOT_EMPTY = "FORM_PERSON_VALIDATION_NAME_NOT_EMPTY";
    public static final String FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX = "FORM_PERSON_VALIDATION_NAME_SIZE_MAX";

    public static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";
    public static final String PERSON_CREATED = "PERSON_CREATED";
    public static final String PERSON_EXISTS = "PERSON_EXISTS";
    public static final String PERSON_MODIFIED = "PERSON_MODIFIED";
    public static final String PERSON_DELETING = "PERSON_DELETING";

    public static final String REPOSITORY_TASK_NOT_FOUND = "REPOSITORY_TASK_NOT_FOUND";
    public static final String REPOSITORY_PERSON_NOT_FOUND = "REPOSITORY_PERSON_NOT_FOUND";

    public static final String SCHEDULER_TASK_PROCESSING = "SCHEDULER_TASK_PROCESSING";

    public static final String SERVICE_RESPONSE_404_TASK = "SERVICE_RESPONSE_404_TASK";

    public static final String DEBUG_MESSAGE_PREFIX = "DEBUG_MESSAGE_PREFIX";

}