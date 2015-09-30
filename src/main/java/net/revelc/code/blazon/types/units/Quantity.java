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

package net.revelc.code.blazon.types.units;

import com.google.common.base.Preconditions;

public class Quantity<MAGNITUDE extends Number, UNIT extends Enum<UNIT>> {

  private final MAGNITUDE magnitude;
  private final UNIT unit;

  public Quantity(final MAGNITUDE magnitude, final UNIT unit) {
    this.magnitude = Preconditions.checkNotNull(magnitude);
    this.unit = Preconditions.checkNotNull(unit);
  }

  public MAGNITUDE getMagnitude() {
    return magnitude;
  }

  public UNIT getUnit() {
    return unit;
  }

}
