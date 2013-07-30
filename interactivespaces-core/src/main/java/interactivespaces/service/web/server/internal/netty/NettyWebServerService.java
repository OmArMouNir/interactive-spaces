/*
 * Copyright (C) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package interactivespaces.service.web.server.internal.netty;

import com.google.common.collect.Maps;

import interactivespaces.service.web.server.WebServer;
import interactivespaces.service.web.server.WebServerService;
import interactivespaces.service.web.server.internal.AbstractWebServerService;

import org.apache.commons.logging.Log;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A {@link WebServerService} which gives NETTY web servers.
 *
 * @author Keith M. Hughes
 */
public class NettyWebServerService extends AbstractWebServerService {

  /**
   * The metadata for the service.
   */
  private Map<String, Object> metadata = Maps.newHashMap();

  @Override
  public Map<String, Object> getMetadata() {
    return metadata;
  }

  @Override
  public String getName() {
    return WebServerService.SERVICE_NAME;
  }

  @Override
  public WebServer newWebServer(String serverName, int port, Log log) {
    ScheduledExecutorService threadPool = getSpaceEnvironment().getExecutorService();

    WebServer server = new NettyWebServer(serverName, port, threadPool, threadPool, log);

    return server;
  }
}
