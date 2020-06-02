package online.ronikier.todo.interfaces.configuration;

import online.ronikier.todo.Messages;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//@Configuration
public class Setup {
/*
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    @Bean
    public AuditEventRepository auditEventRepository() {
        //return customAuditEventRepository();
        return new InMemoryAuditEventRepository();
    }

    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new HttpTraceRepository() {
            private List<HttpTrace> traceList = new ArrayList<>();
            @Override
            public List<HttpTrace> findAll() {
                return traceList;
            }

            @Override
            public void add(HttpTrace trace) {
                traceList.add(trace);
            }
        };
    }

    private AuditEventRepository customAuditEventRepository() {
        return new AuditEventRepository() {

            @Override
            public void add(AuditEvent event) {
                throw new UnsupportedOperationException(Messages.DEV_IMLEMENT_ME);
            }

            @Override
            public List<AuditEvent> find(String principal, Instant after, String type) {
                throw new UnsupportedOperationException(Messages.DEV_IMLEMENT_ME);
            }
        };
    }
*/
}
