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
import java.util.concurrent.TimeUnit;
import net.revelc.code.blazon.types.units.Duration.Unit;
import net.revelc.code.blazon.types.units.Quantity.Converter;

/**
 * Specifies a non-negative base-10 integer quantity of time, followed by the units of time which
 * that quantity represents. Only one quantity, followed by one unit suffix is permitted (3 days and
 * 5 minutes must be represented in minutes, not 3d5m). The unit suffix is not case-sensitive.
 * Whitespace between the number and the unit suffix is optional. The resulting {@link Quantity}
 * will contain the quantity and units specified. Use caution when converting between values. Large
 * values will not exceed {@link Long#MAX_VALUE} when converted to a more precise unit of time. Any
 * values of a more precise unit may lose precision when converted to a less precise type. For
 * example, 9223372036854775807 days converts to 9223372036854775807 millis, which converts to
 * 106751991167 days, which then converts to only 9223372036828800000 millis.<br>
 *
 * <div>Possible units are:<br>
 * <ul>
 * <li>'ns' for nanoseconds</li>
 * <li>'ms' for milliseconds</li>
 * <li>'s' for seconds (default when none specified)</li>
 * <li>'m' for minutes</li>
 * <li>'h' for hours</li>
 * <li>'d' for days</li>
 * </ul>
 * </div> Examples: '100ms', '3 d', '4h', '1000ns', '42'
 */
public class Duration extends QuantityType<Long, Unit> {

  /* This list of enums should be ordered according to increasing magnitudes. */
  public static enum Unit implements Converter<Long, Unit> {

    ns(TimeUnit.NANOSECONDS),

    ms(TimeUnit.MILLISECONDS),

    s(TimeUnit.SECONDS),

    m(TimeUnit.MINUTES),

    h(TimeUnit.HOURS),

    d(TimeUnit.DAYS);

    private final TimeUnit timeUnit;

    private Unit(final TimeUnit timeUnit) {
      this.timeUnit = Preconditions.checkNotNull(timeUnit);
    }

    @Override
    public Long convertTo(final Unit destinationUnit, final Long value) {
      return convertTo(Preconditions.checkNotNull(destinationUnit).timeUnit,
          Preconditions.checkNotNull(value));
    }

    public long convertTo(final TimeUnit destinationUnit, final long value) {
      return Preconditions.checkNotNull(destinationUnit).convert(value, timeUnit);
    }

  }

  public static final Duration NON_NEGATIVE = new Duration();

  /**
   * Constructs a non-negative duration of time. Units are not case-sensitive.
   */
  private Duration() {
    super(Unit.s, false);
  }

  @Override
  protected Optional<Quantity<Long, Unit>> checkPostconditions(
      final Quantity<Long, Unit> converted) {
    if (converted.getMagnitude() >= 0) {
      return super.checkPostconditions(converted);
    }
    throw new IllegalArgumentException(
        "A duration of time cannot be negative (" + converted.getMagnitude() + ")");
  }

  @Override
  public String description() {
    return "A non-negative base-10 integer amount of time, "
        + "with an optional unit suffix from {ns, ms, s, m, h, d}. "
        + "If no unit suffix is specified, the unit of time is assumed to be seconds.";
  }

  @Override
  public Long parseNumericalPart(final String number) {
    return Long.parseLong(number);
  }

}
