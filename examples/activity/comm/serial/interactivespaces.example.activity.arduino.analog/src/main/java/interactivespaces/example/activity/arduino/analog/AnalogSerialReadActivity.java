/*
 * Copyright (C) 2013 Google Inc.
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

package interactivespaces.example.activity.arduino.analog;

import interactivespaces.activity.impl.ros.BaseRoutableRosActivity;
import interactivespaces.event.trigger.SimpleThresholdTrigger;
import interactivespaces.event.trigger.Trigger;
import interactivespaces.event.trigger.TriggerEventType;
import interactivespaces.event.trigger.TriggerListener;
import interactivespaces.event.trigger.TriggerState;
import interactivespaces.service.comm.serial.SerialCommunicationEndpoint;
import interactivespaces.service.comm.serial.SerialCommunicationEndpointService;
import interactivespaces.util.concurrency.CancellableLoop;
import interactivespaces.util.resource.ManagedResourceWithTask;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * An Interactive Spaces Java-based activity which communicates with an Arduino
 * sketch which reads an analog port and sends its value over a serial
 * connection. The value of the analog signal is sent on a route.
 * 
 * <p>
 * The Arduino sends the values as raw bytes, not a string. The high order byte
 * is transmitted first. The serial line is set for 9600 baud.
 * 
 * <p>
 * This example is a little complicated. It uses a serial connection to talk to
 * the arduino. It requires a loop which runs independently to read values from
 * the arduino. The values from the arduino are sent to a threshold trigger
 * which, once a given threshold is met, will trigger. When triggered, the
 * activity will send a message out on a route that it has triggered.
 * 
 * <p>
 * The assumption for the analog signal is that a trigger is wanted when the
 * value from the analog port is larger than the threshold. If you want to
 * trigger below a certain value you can always check for a falling edge event
 * type.
 * 
 * @author Keith M. Hughes
 */
public class AnalogSerialReadActivity extends BaseRoutableRosActivity {

	/**
	 * The name of the config property for obtaining the serial port.
	 */
	public static final String CONFIGURATION_PROPERTY_HARDWARE_SERIAL_PORT = "space.hardware.serial.port";

	/**
	 * Size of the message, in bytes, we want from the arduino.
	 */
	private static final int MESSAGE_LENGTH = 2;

	/**
	 * The source for serial communication endpoints.
	 */
	private SerialCommunicationEndpointService communicationEndpointService;

	/**
	 * The communication endpoint for the arduino.
	 */
	private SerialCommunicationEndpoint arduinoEndpoint;

	@Override
	public void onActivitySetup() {
		communicationEndpointService = getSpaceEnvironment()
				.getServiceRegistry().getRequiredService(
						SerialCommunicationEndpointService.SERVICE_NAME);
		getLog().info(
				String.format("Serial ports available: %s",
						communicationEndpointService.getSerialPorts()));

		String portName = getConfiguration().getRequiredPropertyString(
				CONFIGURATION_PROPERTY_HARDWARE_SERIAL_PORT);

		arduinoEndpoint = communicationEndpointService
				.newSerialEndpoint(portName);

		addManagedResource(new ManagedResourceWithTask(arduinoEndpoint,
				new CancellableLoop() {
					byte[] buffer = new byte[MESSAGE_LENGTH];

					@Override
					protected void loop() throws InterruptedException {
						readStream(buffer);
					}

					@Override
					protected void handleException(Exception e) {
						getLog().error("Exception while reading serial port", e);
					}
				}, getSpaceEnvironment()));
	}

	/**
	 * Attempt to read the serial data from the arduino.
	 * 
	 * @param buffer
	 *            the buffer for storing bytes read
	 */
	private void readStream(byte[] buffer) {
		if (arduinoEndpoint.available() >= MESSAGE_LENGTH) {
			arduinoEndpoint.read(buffer);

			// If activated, process the bytes. But they must be read
			// regardless.
			if (isActivated()) {
				int val = ((buffer[0] & 0xff) << 8) | (buffer[1] & 0xff);

				// The new value from the Arduino is known. Tell the trigger
				// that the value has changed,
				sendAnalogMessage(val);
			}
		}
	}

	/**
	 * Send an analog message.
	 * 
	 * @param value
	 * 		value of the analog message
	 */
	private void sendAnalogMessage(int value) {
		// Production code should probably have this as a debug rather
		// and an info, then have logging at ERROR.
		//
		// For now will write logs of data to log since an example.
		getLog().info(String.format("Analog value is %d\n", value));

		Map<String, Object> message = Maps.newHashMap();
		message.put("analog", value);
		sendOutputJson("signal", message);
	}
}
