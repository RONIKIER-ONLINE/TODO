package online.ronikier.todo.library;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Parameters {

    public static final int SYSTEM_VERSION_MAJOR = 0;
    public static final int SYSTEM_VERSION_MINOR = 0;
    public static final int SYSTEM_VERSION_UPDATE = 1;

    public static final boolean SYSTEM_SAVE_NOT_REQUIRED = true;
    public static final boolean SYSTEM_ALLOW_TASK_REPLACE = false;

    public static final String WEB_FORM_DATE_FORMAT = "yyyy-MM-dd";
    public static final String NEW_USERNAME = "user_";

    public static final int FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX = 200;
    public static final int FORM_TASK_VALIDATION_NAME_SIZE_MAX = 20;

    public static final int FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX = 10;


    public static final String WEB_CONTROLLER_TASK = "task";
    public static final String WEB_CONTROLLER_PARAMETER_TASK_ID = "taskId";

    public static final String WEB_CONTROLLER_PERSON = "person";
    public static final String WEB_CONTROLLER_PARAMETER_PERSON_ID = "personId";

    public static final String WEB_CONTROLLER_OPERATION_SAVE = "_save";
    public static final String WEB_CONTROLLER_OPERATION_DELETE = "_delete";

    public static final boolean SYSTEM_SKIP_MAINTENANCE_TASKS = true;
    public static final Integer INTERFACE_WIDTH = 800;
}
