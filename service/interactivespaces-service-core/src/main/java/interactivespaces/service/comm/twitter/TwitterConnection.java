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

import interactivespaces.comm.CommunicationEndpoint;

/**
 * A connection to a chat service.
 *
 * @author Keith M. Hughes
 */
public interface TwitterConnection extends CommunicationEndpoint {

  /**
   * Is the connection connected?
   *
   * @return {@code true} if connected.
   */
  boolean isConnected();

  /**
   * Get the user of the connection.
   *
   * @return the user the service is using
   */
  String getUser();

  /**
   * Send a message to the other end.
   *
   * @param status
   *          the status to send
   */
  void updateStatus(String status);

  /**
   * Add a hash tag search to the client.
   *
   * @param tag
   *          the Twitter tag (without the hash) to search for
   * @param since
   *          the date to start looking for the tag from
   */
  void addHashTagSearch(String tag, String since);

  /**
   * Add a new listener.
   *
   * @param listener
   *          the listener to add
   */
  void addListener(TwitterConnectionListener listener);

  /**
   * Remove a listener.
   *
   * <p>
   * Does nothing if the listener was never added.
   *
   * @param listener
   *          the listener to remove
   */
  void removeListener(TwitterConnectionListener listener);
}
