package zilverline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
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

  @Order(1)
  @Configuration
  public static class ApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
          .antMatcher("/api/**")
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .csrf().disable()
          .addFilterBefore(new BasicAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
          .authorizeRequests()
          .antMatchers("/api/**").hasRole("USER");
    }
  }

  @Configuration
  public static class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
          .antMatcher("/**")
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
          .and()
          .csrf().disable()
          .addFilterBefore(new BasicAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
          .authorizeRequests()
          .antMatchers("/**").hasRole("USER");
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/user")
  public ResponseEntity user() {
    return ResponseEntity.ok().build();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/user")
  public ResponseEntity admin() {
    return ResponseEntity.ok().build();
  }
}

