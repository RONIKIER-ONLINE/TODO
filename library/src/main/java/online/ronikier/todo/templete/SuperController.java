package online.ronikier.todo.templete;

import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

//public abstract class SuperController<F> implements WebMvcConfigurer {
public abstract class SuperController implements WebMvcConfigurer {

    public static final String REQUEST_VARIABLE_LANGUAGE = "lang";

    protected void resetModel(Model model) {

    }

    protected void initializeForm(SuperForm form, Model model) {

    }

    protected void refreshForm(SuperForm form, Model model) {

    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(REQUEST_VARIABLE_LANGUAGE);
        return localeChangeInterceptor;
    }

}
