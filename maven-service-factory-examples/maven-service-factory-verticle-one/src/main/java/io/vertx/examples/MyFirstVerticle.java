package io.vertx.examples;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;


/**
 * A simple verticle.
 * The verticle is described in the `my-verticle.json` file.
 */
public class MyFirstVerticle extends AbstractVerticle {

  @Override
  public void start() {
    // Create an HTTP server which simply returns "Hello World!" to each request.
    vertx.createHttpServer()
        .requestHandler(req -> req.response().end("Hello World!"))
        .listen(8080);

    MongoClient mongoClient = MongoClient.createShared(vertx, new JsonObject());
  }
}
