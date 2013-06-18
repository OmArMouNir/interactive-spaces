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

package interactivespaces.service.comm.serial.xbee.internal;

import interactivespaces.service.comm.serial.xbee.RequestXBeeFrame;
import interactivespaces.service.comm.serial.xbee.XBeeAddress64;
import interactivespaces.util.ByteUtils;

import java.util.Arrays;

/**
 * 64 bit address for an XBee
 *
 * @author Keith M. Hughes
 */
public class XBeeAddress64Impl implements XBeeAddress64 {

  /**
   * The magic address for the coordinator radio.
   */
  public static final XBeeAddress64 COORDINATOR_ADDRESS = new XBeeAddress64Impl(0x00, 0x00, 0x00,
      0x00, 0x00, 0x00, 0x00, 0x00);

  /**
   * The magic address for broadacasting to all radios.
   */
  public static final XBeeAddress64 BROADCAST_ADDRESS = new XBeeAddress64Impl(0x00, 0x00, 0x00,
      0x00, 0x00, 0x00, 0xff, 0xff);

  /**
   * The address for the xbee.
   */
  private int[] address;

  public XBeeAddress64Impl(String addr) {
    this(Integer.valueOf(addr.substring(0, 2), 16), Integer.valueOf(addr.substring(2, 4), 16),
        Integer.valueOf(addr.substring(4, 6), 16), Integer.valueOf(addr.substring(6, 8), 16),
        Integer.valueOf(addr.substring(8, 10), 16), Integer.valueOf(addr.substring(10, 12), 16),
        Integer.valueOf(addr.substring(12, 14), 16), Integer.valueOf(addr.substring(14, 16), 16));
  }

  /**
   * Construct an XBee 64 address using the individual bytes.
   *
   * <p>
   * First is the most significant byte
   *
   * @param a1
   * @param a2
   * @param a3
   * @param a4
   * @param a5
   * @param a6
   * @param a7
   * @param a8
   */
  public XBeeAddress64Impl(int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8) {
    address = new int[] { a1, a2, a3, a4, a5, a6, a7, a8 };
  }

  @Override
  public void write(RequestXBeeFrame frameWriter) {
    frameWriter.add(address);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(address);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    XBeeAddress64Impl other = (XBeeAddress64Impl) obj;
    if (!Arrays.equals(address, other.address))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "XBeeAddress64Impl [address=" + ByteUtils.toHexString(address) + "]";
  }
}
