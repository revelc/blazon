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

package net.revelc.code.blazon.types;

import com.google.common.base.Preconditions;

/**
 * A {@link LongType} which is bounded with a minimum and maximum value (both inclusive).
 */
public class RangeType extends LongType {

  private final long lowerBound;
  private final long upperBound;

  /**
   * Represents a bounded range.
   *
   * @param lowerBound the lower bound, inclusive
   * @param upperBound the upper bound, inclusive
   */
  public RangeType(final long lowerBound, final long upperBound) {
    Preconditions.checkArgument(lowerBound <= upperBound,
        "Lower bound should be less than or equal to the upper bound");
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  @Override
  protected Long checkPostconditions(final Long value) {
    if (value == null) {
      return null;
    }
    if (value < lowerBound || value > upperBound) {
      throw new IllegalArgumentException(
          value + " is not in the range [" + lowerBound + "," + upperBound + "]");
    }
    return value;
  }

}
