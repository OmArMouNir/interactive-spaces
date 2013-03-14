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

package interactivespaces.service.audio.player;

/**
 * Something which can play tracks.
 *
 * @author Keith M. Hughes
 */
public interface AudioTrackPlayer {
	/**
	 * Start playing the track.
	 * 
	 * @param begin
	 * 		number of milliseconds into the track to start.
	 */
	void start(long begin, long duration);
	
	/**
	 * Stop playing the track.
	 */
	void stop();
	
	/**
	 * Is the track playing?
	 * 
	 * @return {@link true} if the track is playing, false otherwise.
	 */
	boolean isPlaying();
}
