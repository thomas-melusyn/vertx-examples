package examples;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.examples.MyDeployingVerticle;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.maven.MavenVerticleFactory;
import io.vertx.service.ServiceVerticleFactory;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class MyDeployingVerticleTest extends AbstractVerticle {

  @ClassRule
  static public final RunTestOnContext vertxRule = new RunTestOnContext();

  Vertx vertx = vertxRule.vertx();

  @Test
  public void startTest(TestContext testContext) {
    Async async = testContext.async();

    try {
      vertx.registerVerticleFactory(new ServiceVerticleFactory());
      vertx.registerVerticleFactory(new MavenVerticleFactory());

      vertx.deployVerticle(new MyDeployingVerticle(), handler -> {

          testContext.assertTrue(handler.succeeded());
          if (handler.failed()) {
            testContext.fail(handler.cause());
          }
          async.complete();
        });
    } catch (Exception e) {
      testContext.fail(e);
      async.complete();

    }
  }
}
