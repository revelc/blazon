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

package net.revelc.code.blazon.types.numeric;

import com.google.common.base.Optional;
import com.google.common.collect.Range;

/**
 * A {@link LongType} which is bounded with a minimum and maximum value (both inclusive).
 */
public class LongRangeType extends LongType {

  private final Range<Long> range;

  @Override
  public Long getLowerBound() {
    return range.lowerEndpoint();
  }

  @Override
  public Long getUpperBound() {
    return range.upperEndpoint();
  }

  /**
   * Represents a bounded range.
   *
   * @param lowerBound the lower bound, inclusive
   * @param upperBound the upper bound, inclusive
   */
  public LongRangeType(final long lowerBound, final long upperBound) {
    range = Range.<Long>closed(lowerBound, upperBound);
  }

  @Override
  protected Optional<Long> checkPostconditions(final Long value) {
    if (!range.contains(value)) {
      throw new IllegalArgumentException(value + " is not in the range " + range.toString());
    }
    return Optional.of(value);
  }

}
