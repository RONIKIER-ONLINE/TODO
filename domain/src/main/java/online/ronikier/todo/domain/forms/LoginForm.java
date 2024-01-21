package online.ronikier.todo.domain.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.templete.SuperForm;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm extends SuperForm {

    private String username;
    private String password;

}
