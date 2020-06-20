package online.ronikier.todo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {

    //TODO: Remove development message
    public static final String DEV_IMPLEMENT_ME = "DEV_IMPLEMENT_ME";

    public static final String SEPARATOR = " - ";

    public static final String TYPE_RESOURCE_PERSON = "TYPE_RESOURCE_PERSON";
    public static final String TYPE_RESOURCE_MONEY = "TYPE_RESOURCE_MONEY";
    public static final String TYPE_RESOURCE_PICTURE = "TYPE_RESOURCE_PICTURE";
    public static final String TYPE_RESOURCE_DOCUMENT = "TYPE_RESOURCE_DOCUMENT";
    public static final String TYPE_RESOURCE_URL = "TYPE_RESOURCE_URL";
    public static final String TYPE_RESOURCE_PHONE_NO = "TYPE_RESOURCE_PHONE_NO";
    public static final String TYPE_RESOURCE_EMAIL = "TYPE_RESOURCE_EMAIL";

    public static final String TYPE_TASK_GENERAL = "General";
    public static final String TYPE_TASK_PERSONAL = "Personal";
    public static final String TYPE_TASK_MONEY = "Money";

    public static final String STATE_TASK_INITIALIZED = "Initialized";
    public static final String STATE_TASK_STARTED = "Started";
    public static final String STATE_TASK_ON_HOLD = "On hold";
    public static final String STATE_TASK_COMPLETED = "Completed";

    public static final String COST_UNIT_PLN = "PLN";
    public static final String COST_UNIT_DAY = "DAYS";
    public static final String COST_UNIT_SOLDIER = "YARI";


    public static final String TYPE_PLACE_CITY = "TYPE_PLACE_CITY";
    public static final String TYPE_PLACE_BUILDING = "TYPE_PLACE_BUILDING";

    public static final String TYPE_PERSON_PUBLIC = "TYPE_PERSON_PUBLIC";
    public static final String TYPE_PERSON_FAMILY = "TYPE_PERSON_FAMILY";
    public static final String TYPE_PERSON_HOSTILE = "TYPE_PERSON_HOSTILE";

    public static final String TYPE_ORGANISATION_OFFICE = "TYPE_ORGANISATION_OFFICE";
    public static final String TYPE_ORGANISATION_COMPANY = "TYPE_ORGANISATION_COMPANY";

    public static final String EXCEPTION_TASK_CREATION = "EXCEPTION_TASK_CREATION";
    public static final String EXCEPTION_PERSON_CREATION = "EXCEPTION_PERSON_CREATION";

    public static final String EXCEPTION_TASK_DATE_PARSE = "EXCEPTION_TASK_DATE_PARSE";

    public static final String ERROR_TASK_ADD = "ERROR_TASK_ADD";
    public static final String ERROR_PARAMETER_NAME_EMPTY = "ERROR_PARAMETER_NAME_EMPTY";
    public static final String ERROR_TASK_DOES_NOT_EXIST = "ERROR_TASK_DOES_NOT_EXIST";

    public static final String ERROR_PERSON_ADD = "ERROR_PERSON_ADD";
    public static final String ERROR_PARAMETER_USERNAME_EMPTY = "ERROR_PARAMETER_NAME_EMPTY";
    public static final String ERROR_PERSON_DOES_NOT_EXIST = "ERROR_PERSON_DOES_NOT_EXIST";

    public static final String INFO_DATE_NOT_SET = "N/A";

    public static final String INFO_INITIALISING_DATABASE = "INFO_INITIALISING_DATABASE";
    public static final String INFO_NEO4J_CONNECTION_SETUP = "INFO_NEO4J_CONNECTION_SETUP";
    public static final String INFO_TASK_NOT_FOUND = "INFO_TASK_NOT_FOUND";
    public static final String INFO_TASK_CREATED = "INFO_TASK_CREATED";
    public static final String INFO_TASK_EXISTS = "INFO_TASK_EXISTS";
    public static final String INFO_TASK_MODIFIED = "INFO_TASK_MODIFIED";
    public static final String INFO_TASK_DELETING = "INFO_TASK_DELETING";
    public static final String INFO_SKIPPING_MAINTENANCE_TASKS = "INFO_SKIPPING_MAINTENANCE_TASKS";
    public static final String INFO_SKIPPING_NOT_REQUIRED = "INFO_SKIPPING_NOT_REQUIRED";

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

    public static final String INFO_PERSON_NOT_FOUND = "INFO_PERSON_NOT_FOUND";
    public static final String INFO_PERSON_CREATED = "INFO_PERSON_CREATED";
    public static final String INFO_PERSON_EXISTS = "INFO_PERSON_EXISTS";
    public static final String INFO_PERSON_MODIFIED = "INFO_PERSON_MODIFIED";
    public static final String INFO_PERSON_DELETING = "INFO_PERSON_DELETING";

    public static final String REPOSITORY_TASK_NOT_FOUND = "REPOSITORY_TASK_NOT_FOUND";
    public static final String REPOSITORY_PERSON_NOT_FOUND = "REPOSITORY_PERSON_NOT_FOUND";

    public static final String SERVICE_RESPONSE_404_TASK = "SERVICE_RESPONSE_404_TASK";

    public static final String DEBUG_MESSAGE_PREFIX = "DEBUG_MESSAGE_PREFIX";
}