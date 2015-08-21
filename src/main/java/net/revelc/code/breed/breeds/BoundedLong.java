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

package net.revelc.code.breed.breeds;

import com.google.common.base.Preconditions;

import net.revelc.code.breed.Breed;

/**
 * A {@link Long} which is bounded with a minimum and maximum.
 */
public class BoundedLong extends Breed<Long> {

  private final long lowerBound;
  private final long upperBound;
  private final boolean lowerInclusive;
  private final boolean upperInclusive;

  /**
   * Represents a bounded range.
   *
   * @param lowerBound
   *          the lower bound, inclusive
   * @param upperBound
   *          the upper bound, inclusive
   */
  public BoundedLong(final long lowerBound, final long upperBound) {
    this(lowerBound, true, upperBound, true);
  }

  /**
   * Represents a bounded range, where the bounds are optionally exclusive.
   *
   * @param lowerBound
   *          the lower bound
   * @param lowerInclusive
   *          true if the lower bound is inclusive, false if exclusive
   * @param upperBound
   *          the upper bound
   * @param upperInclusive
   *          true if the upper bound is inclusive, false if exclusive
   */
  public BoundedLong(final long lowerBound, final boolean lowerInclusive, final long upperBound,
      final boolean upperInclusive) {
    Preconditions.checkArgument(
        lowerBound < upperBound || (lowerInclusive && upperInclusive && lowerBound == upperBound),
        "Lower bound should be strictly less than the upper bound or, "
            + "if they are equal, it should be inclusive on both ends");
    this.lowerBound = lowerBound;
    this.lowerInclusive = lowerInclusive;
    this.upperBound = upperBound;
    this.upperInclusive = upperInclusive;
  }

  @Override
  protected Long convert(final String raw) throws RuntimeException {
    if (raw == null) {
      return null;
    }
    long number;
    try {
      number = Long.parseLong(raw);
    } catch (NumberFormatException e) {
      return null;
    }
    return number;
  }

  @Override
  protected Long checkPostconditions(Long value) throws RuntimeException {
    if (value == null) {
      return null;
    }
    if (value < lowerBound || (!lowerInclusive && value == lowerBound)) {
      return null;
    }
    if (value > upperBound || (!upperInclusive && value == upperBound)) {
      return null;
    }
    return value;
  }

}
