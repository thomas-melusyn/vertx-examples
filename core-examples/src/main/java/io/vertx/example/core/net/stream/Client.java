package io.vertx.example.core.net.stream;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetSocket;
import io.vertx.example.util.Runner;

import java.time.Instant;
import java.util.UUID;

/*
 *  @author <a href="mailto:emad.albloushi@gmail.com">Emad Alblueshi</a>
 */
public class Client extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runExample(Client.class);
  }

  @Override
  public void start() throws Exception {
    vertx.createNetClient().connect(1234, "localhost", ar -> {
      if (ar.succeeded()) {

        NetSocket socket = ar.result();

        // Create batch stream for reading and writing
        BatchStream batchStream = new BatchStream(socket, socket);

        // Pause reading data
        batchStream.pause();

        // Register read stream handler
        batchStream.handler(batch -> {
          System.out.println("Client Received : " + batch.getRaw().toString());
        }).endHandler(v -> batchStream.end())
          .exceptionHandler(t -> {
            t.printStackTrace();
            batchStream.end();
          });

        // Resume reading data
        batchStream.resume();

        // JsonObject
        JsonObject jsonObject = new JsonObject()
          .put("id", UUID.randomUUID().toString())
          .put("name", "Vert.x")
          .put("timestamp", Instant.now());

        // JsonArray
        JsonArray jsonArray = new JsonArray()
          .add(UUID.randomUUID().toString())
          .add("Vert.x")
          .add(Instant.now());

        // Buffer
        Buffer buffer = Buffer.buffer("Vert.x is awesome!");

        // Write to socket
        batchStream.write(new Batch(jsonObject));
        batchStream.write(new Batch(jsonArray));
        batchStream.write(new Batch(buffer));

      } else {
        System.out.println("Failed to connect " + ar.cause());
      }
    });
  }
}
