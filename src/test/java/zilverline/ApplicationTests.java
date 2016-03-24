package zilverline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class ApplicationTests {

  @Value("${local.server.port}")
  private int port;

  private TestRestTemplate restTemplate = new TestRestTemplate("user", "secret");

  @Test
  public void ensureCookieTest() {
    assertCookiePresence("/user", true);
  }

  @Test
  public void ensureNoCookieTest() {
    assertCookiePresence("/api/user", false);
  }

  private void assertCookiePresence(String path, boolean presence) {
    ResponseEntity<Void> response = restTemplate.getForEntity("http://localhost:" + port + path, Void.class);
    List<String> cookies = response.getHeaders().get("Set-Cookie");
    assertEquals(presence, cookies != null);
  }

}
