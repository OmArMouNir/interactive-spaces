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

package interactivespaces.master.server.ui;

import interactivespaces.domain.basic.LiveActivity;
import interactivespaces.master.server.services.ActiveLiveActivity;

/**
 * Information about a {@link LiveActivity} for the UI.
 *
 * @author Keith M. Hughes
 */
public class UiLiveActivity {

  /**
   * The live activity.
   */
  private LiveActivity activity;

  /**
   * The active live activity. Can be {@code null}.
   */
  private ActiveLiveActivity active;

  public UiLiveActivity(LiveActivity activity, ActiveLiveActivity active) {
    this.activity = activity;
    this.active = active;
  }

  /**
   * Get the activity.
   *
   * @return the activity
   */
  public LiveActivity getActivity() {
    return activity;
  }

  /**
   * Get the active live activity, if any.
   *
   * @return the active live activity, or {@code null} if none.
   */
  public ActiveLiveActivity getActive() {
    return active;
  }
}
