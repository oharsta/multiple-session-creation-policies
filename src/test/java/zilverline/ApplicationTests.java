package zilverline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class ApplicationTests {

  @Value("${local.server.port}")
  private int port;

  private TestRestTemplate restTemplate = new TestRestTemplate("user", "secret");

  @Test
  public void ensureCookieTest() {
    ResponseEntity<Map> entity = restTemplate.getForEntity("http://localhost:" + port + "/user", Map.class);
    assertFalse(entity.getHeaders().get("Set-Cookie").isEmpty());
  }

  @Test
  public void ensureNoCookieTest() {
    ResponseEntity<Map> entity = restTemplate.getForEntity("http://localhost:" + port + "/api/user", Map.class);
    assertNull(entity.getHeaders().get("Set-Cookie"));
  }


}
