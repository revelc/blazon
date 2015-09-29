/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.revelc.code.blazon.types.network;

import com.google.common.collect.Range;

import net.revelc.code.blazon.Type;
import net.revelc.code.blazon.types.numeric.IntegerRangeType;

/**
 * A {@link Type} which represents an integer in the range of 0-65535.
 */
public class Port extends IntegerRangeType {

  private static final Range<Integer> ALL_PORTS_RANGE = Range.closed(0, 65535);

  /**
   * System Ports, as defined by RFC 6335, in the range 0-1023. Also known as Well Known Ports.
   */
  public static final Port SYSTEM = new Port(0, 1023);

  /**
   * User Ports, as defined by RFC 6335, in the range 1024-49151. Also known as Registered Ports.
   */
  public static final Port USER = new Port(1024, 49151);

  /**
   * Dynamic Ports, as defined by RFC 6335, in the range 49152-65535. Also known as Private or
   * Ephemeral Ports.
   */
  public static final Port DYNAMIC = new Port(49152, 65535);

  /**
   * Any valid port.
   */
  public static final Port ANY = new Port(0, 65535);

  public Port() {
    this(0, 65535);
  }

  public Port(final int lowerBound, final int upperBound) {
    super(checkBound(lowerBound), checkBound(upperBound));
  }

  private static int checkBound(final int bound) {
    if (ALL_PORTS_RANGE.contains(bound)) {
      return bound;
    }
    throw new IllegalArgumentException(
        bound + " is not in the range " + ALL_PORTS_RANGE.toString());
  }

}
