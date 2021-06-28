package io.vertx.examples;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * This verticle uses the Maven Service Factory to deploy `my-verticle`.
 */
public class MyDeployingVerticle extends AbstractVerticle {


  @Override
  public void start(Promise<Void> promise) throws Exception {

    CompositeFuture.all(deployOne().future(), deployTwo().future())
      .onComplete(result -> {
        if (result.succeeded()) {
          promise.complete();
        } else {
          promise.fail(result.cause());
        }
      });
  }

  public Promise<String> deployOne() {
    Promise<String> promise = Promise.promise();

      vertx.deployVerticle("maven:io.vertx:maven-service-factory-verticle-one:3.9.0::my-first-verticle",
      ar -> {
        if (ar.succeeded()) {
          promise.complete(ar.result());
        } else {
          promise.fail(ar.cause());
        }
      }
    );

    return promise;
  }


  public Promise<String> deployTwo() {
    Promise<String> promise = Promise.promise();

    vertx.deployVerticle("maven:io.vertx:maven-service-factory-verticle-two:3.9.0::my-second-verticle",
      ar -> {
        if (ar.succeeded()) {
          promise.complete(ar.result());
        } else {
          promise.fail(ar.cause());
        }
      }
    );

    return promise;
  }

}
