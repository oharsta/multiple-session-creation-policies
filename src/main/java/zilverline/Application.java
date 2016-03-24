package zilverline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableWebSecurity
@RestController
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  private static void configureHttpSecurity(HttpSecurity http, AuthenticationManager authenticationManager,
                                            SessionCreationPolicy sessionCreationPolicy, String antPattern) throws Exception {
    http
        .antMatcher(antPattern)
        .sessionManagement().sessionCreationPolicy(sessionCreationPolicy)
        .and()
        .csrf().disable()
        .addFilterBefore(new BasicAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/**").hasRole("USER");
  }

  @Order(1)
  @Configuration
  public static class ApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      configureHttpSecurity(http, authenticationManager(),SessionCreationPolicy.STATELESS, "/api/**");
    }
  }

  @Configuration
  public static class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      configureHttpSecurity(http, authenticationManager(),SessionCreationPolicy.ALWAYS, "/**");
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/user")
  public ResponseEntity user() {
    return ResponseEntity.ok().build();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/user")
  public ResponseEntity apiUser() {
    return ResponseEntity.ok().build();
  }
}

