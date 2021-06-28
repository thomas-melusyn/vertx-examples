package io.vertx.examples;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;


/**
 * A simple verticle.
 * The verticle is described in the `my-verticle.json` file.
 */
public class MySecondVerticle extends AbstractVerticle {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void start(Promise<Void> startPromise) {
    // Create an HTTP server which simply returns "Hello World!" to each request.
    vertx.createHttpServer()
        .requestHandler(req -> req.response().end("Hello World!"))
        .listen(8080);

    logger.debug("Mongo client");
    MongoClient mongoClient = MongoClient.createShared(vertx, new JsonObject());


    logger.debug("Complete");

    startPromise.complete();

  }
}
