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

package interactivespaces.service.comm.twitter;

import interactivespaces.service.SupportedService;

/**
 * A Twitter service.
 *
 * <p>
 * On shutdown all open connections will be closed.
 *
 * @author Keith M. Hughes
 */
public interface TwitterService extends SupportedService {

  /**
   * The name for the service.
   */
  public static final String SERVICE_NAME = "comm.twitter";

  /**
   * Create a new chat connection.
   *
   * @param consumerKey
   *          the consumer key for the connection
   * @param consumerSecret
   *          the consumer secret for the connection
   * @param accessToken
   *          the access token for the connection
   * @param accessTokenSecret
   *          the access token secret for the connection
   *
   * @return the connection, which will not be connected
   */
  TwitterConnection newTwitterConnection(String consumerKey, String consumerSecret,
      String accessToken, String accessTokenSecret);
}
