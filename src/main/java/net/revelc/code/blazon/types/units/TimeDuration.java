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

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Range;

import net.revelc.code.blazon.types.units.TimeDuration.TimeDurationUnit;

import java.util.concurrent.TimeUnit;

/**
 * Specifies a non-negative quantity of time, followed by the units that quantity represents. Only
 * one quantity, followed by one unit suffix is permitted. Possible units are:
 * <ul>
 * <li>'ns' for nanoseconds</li>
 * <li>'ms' for milliseconds</li>
 * <li>'s' for seconds (default when none specified)</li>
 * <li>'m' for minutes</li>
 * <li>'h' for hours</li>
 * <li>'d' for days</li>
 * </ul>
 * Example: 100ms, 3d, 4h, 1000ns, 42
 */
public class TimeDuration extends BasicUnits<Long, TimeDurationUnit> {

  public static enum TimeDurationUnit {
    ns(TimeUnit.NANOSECONDS), ms(TimeUnit.MILLISECONDS), s(TimeUnit.SECONDS), m(
        TimeUnit.MINUTES), h(TimeUnit.HOURS), d(TimeUnit.DAYS);

    private final TimeUnit timeUnit;

    private TimeDurationUnit(final TimeUnit timeUnit) {
      this.timeUnit = Preconditions.checkNotNull(timeUnit);
    }

    public long convertTo(final TimeDurationUnit destinationUnit, final long value) {
      return convertTo(Preconditions.checkNotNull(destinationUnit).timeUnit, value);
    }

    public long convertTo(final TimeUnit destinationUnit, final long value) {
      return Preconditions.checkNotNull(destinationUnit).convert(value, timeUnit);
    }
  }

  public static final TimeDuration POSITIVE_SECONDS =
      new TimeDuration(1L, Long.MAX_VALUE, TimeDurationUnit.s);
  public static final TimeDuration NON_NEGATIVE_SECONDS =
      new TimeDuration(0L, Long.MAX_VALUE, TimeDurationUnit.s);

  private final Range<Long> range;
  private final TimeDurationUnit rangeUnits;

  /**
   * Constructs a duration of time, bounded with a minimum and maximum value.
   *
   * @param lowerBound The minimum value permitted.
   * @param upperBound The maximum value permitted.
   */
  public TimeDuration(final long lowerBound, final long upperBound,
      final TimeDurationUnit boundUnits) {
    super(TimeDurationUnit.s, false);
    this.range = Range.closed(lowerBound, upperBound);
    this.rangeUnits = Preconditions.checkNotNull(boundUnits);
  }

  @Override
  protected Optional<Quantity<Long, TimeDurationUnit>> checkPostconditions(
      final Quantity<Long, TimeDurationUnit> converted) {
    final Long value = converted.getUnit().convertTo(rangeUnits, converted.getMagnitude());
    if (!range.contains(value)) {
      throw new IllegalArgumentException(
          value + rangeUnits.name() + " is not in the range " + range.toString());
    }
    return Optional.of(converted);
  }

  @Override
  public String description() {
    // TODO
    return "TODO";
  }

  @Override
  public Long convertNumber(final String number) {
    return Long.parseLong(number);
  }

}
