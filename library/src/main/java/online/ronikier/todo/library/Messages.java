package online.ronikier.todo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {

    public static final String SEPARATOR = " - ";

    public static final String TYPE_RESOURCE_PERSON = "TYPE_RESOURCE_PERSON";
    public static final String TYPE_RESOURCE_MONEY = "TYPE_RESOURCE_MONEY";
    public static final String TYPE_RESOURCE_PICTURE = "TYPE_RESOURCE_PICTURE";
    public static final String TYPE_RESOURCE_DOCUMENT = "TYPE_RESOURCE_DOCUMENT";
    public static final String TYPE_RESOURCE_URL = "TYPE_RESOURCE_URL";
    public static final String TYPE_RESOURCE_PHONE_NO = "TYPE_RESOURCE_PHONE_NO";
    public static final String TYPE_RESOURCE_EMAIL = "TYPE_RESOURCE_EMAIL";

    public static final String TYPE_TASK_PERSONAL = "TYPE_TASK_PERSONAL";
    public static final String TYPE_TASK_MONEY = "TYPE_TASK_MONEY";

    public static final String STATE_TASK_STARTED = "STATE_TASK_STARTED";
    public static final String STATE_TASK_ON_HOLD = "STATE_TASK_ON_HOLD";
    public static final String STATE_TASK_COMPLETED = "STATE_TASK_COMPLETED";

    public static final String TYPE_PLACE_CITY = "TYPE_PLACE_CITY";
    public static final String TYPE_PLACE_BUILDING = "TYPE_PLACE_BUILDING";

    public static final String TYPE_PERSON_PUBLIC = "TYPE_PERSON_PUBLIC";
    public static final String TYPE_PERSON_FAMILY = "TYPE_PERSON_FAMILY";
    public static final String TYPE_PERSON_HOSTILE = "TYPE_PERSON_HOSTILE";

    public static final String TYPE_ORGANISATION_OFFICE = "TYPE_ORGANISATION_OFFICE";
    public static final String TYPE_ORGANISATION_COMPANY = "TYPE_ORGANISATION_COMPANY";

    public static final String EXCEPTION_TASK_CREATION = "EXCEPTION_TASK_CREATION";

    public static final String ERROR_TASK_ADD = "ERROR_TASK_ADD";
    public static final String ERROR_TASK_EXIST = "ERROR_TASK_EXIST";

    public static final String INFO_TASK_EXIST = "INFO_TASK_EXIST";
    public static final String INFO_TASK_CREATED = "INFO_TASK_CREATED";
    public static final String INFO_TASK_MODIFIED = "INFO_TASK_MODIFIED";

    public static final String FORM_TASK_VALIDATION_IMPORTANT_NOT_NULL = "FORM_TASK_VALIDATION_IMPORTANT_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_URGENT_NOT_NULL = "FORM_TASK_VALIDATION_URGENT_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_CREATED_NOT_NULL = "FORM_TASK_VALIDATION_CREATED_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_START_NOT_NULL = "FORM_TASK_VALIDATION_START_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_DUE_NOT_NULL = "FORM_TASK_VALIDATION_DUE_NOT_NULL";
    public static final String FORM_TASK_VALIDATION_NAME_NOT_EMPTY = "FORM_TASK_VALIDATION_NAME_NOT_EMPTY";
    public static final String FORM_TASK_VALIDATION_NAME_SIZE_MAX = "FORM_TASK_VALIDATION_NAME_SIZE_MAX";
    public static final String FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX = "FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX";
    public static final String FORM_TASK_VALIDATION_DESCRIPTION_NOT_EMPTY = "FORM_TASK_VALIDATION_DESCRIPTION_NOT_EMPTY";

    public static final String REPOSITORY_TASK_NOT_FOUND = "REPOSITORY_TASK_NOT_FOUND";

}
