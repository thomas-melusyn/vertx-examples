/*
 * Copyright 2017 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.example.core.net.greeter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.example.util.Runner;

import java.util.stream.Stream;

/*
 * @author Thomas Segismont
 */
public class Client extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runExample(Client.class);
  }

  @Override
  public void start() throws Exception {
    vertx.createNetClient().connect(1234, "localhost", res -> {

      if (res.succeeded()) {
        NetSocket socket = res.result();

        RecordParser.newDelimited("\n", socket)
          .endHandler(v -> socket.close())
          .exceptionHandler(t -> {
            t.printStackTrace();
            socket.close();
          })
          .handler(buffer -> {
            String greeting = buffer.toString("UTF-8");
            System.out.println("Net client receiving: " + greeting);
          });

        // Now send some data
        Stream.of("John", "Joe", "Lisa", "Bill").forEach(name -> {
          System.out.println("Net client sending: " + name);
          socket.write(name).write("\n");
        });

      } else {
        System.out.println("Failed to connect " + res.cause());
      }
    });
  }
}
