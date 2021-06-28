package io.vertx.example.reactivex.services.serviceproxy;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;

public class ServiceConsumerVerticle extends AbstractVerticle {
  // In order to use Rx-ified methods, you need to declare the service in Rx-style.
  io.vertx.example.reactivex.services.serviceproxy.reactivex.SomeDatabaseService someDatabaseService;

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runClusteredExample(ServiceConsumerVerticle.class);
  }

  @Override
  public void start() throws Exception {
    someDatabaseService = SomeDatabaseService.createProxy(vertx.getDelegate(), "proxy.address");

    int id = 1;

    // Now you can use your Rx-ified methods.
    Single<JsonObject> single = someDatabaseService.rxGetDataById(id);

    single.subscribe(
      jsonObject -> System.out.println(jsonObject.encodePrettily()),
      throwable -> System.out.println(throwable.getMessage())
    );
  }
}
