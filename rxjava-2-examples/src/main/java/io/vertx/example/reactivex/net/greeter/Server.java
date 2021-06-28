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

package io.vertx.example.reactivex.net.greeter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.example.util.Runner;
import io.vertx.reactivex.FlowableHelper;

/*
 * @author Thomas Segismont
 */
public class Server extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runExample(Server.class);
  }

  @Override
  public void start() throws Exception {

    vertx.createNetServer().connectHandler(sock -> {

      RecordParser parser = RecordParser.newDelimited("\n", sock);

      FlowableHelper.toFlowable(parser)
        .map(buffer -> buffer.toString("UTF-8"))
        .map(name -> "Hello " + name)
        .subscribe(greeting -> sock.write(greeting + "\n", "UTF-8"), throwable -> {
          throwable.printStackTrace();
          sock.close();
        }, sock::close);

    }).listen(1234);

    System.out.println("Echo server is now listening");

  }
}
