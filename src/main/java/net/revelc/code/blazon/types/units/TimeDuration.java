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

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import java.util.concurrent.TimeUnit;

/**
 * Specifies a non-negative quantity of time, followed by the units that quantity represents. Only
 * one quantity, followed by one unit suffix is permitted. Possible units are:
 * <ul>
 * <li>'ns' for nanoseconds</li>
 * <li>'ms' for milliseconds</li>
 * <li>'s' for seconds (default)</li>
 * <li>'m' for minutes</li>
 * <li>'h' for hours</li>
 * <li>'d' for days</li>
 * </ul>
 * Example: 100ms, 3d, 4h, 1000ns, 5
 */
public class TimeDuration extends BasicUnits<Long, TimeUnit> {

  public static final TimeDuration NON_NEGATIVE = new TimeDuration(0L, Long.MAX_VALUE);

  public TimeDuration(final long lowerBound, final long upperBound) {
    super(new Function<String, Long>() {
      @Override
      public Long apply(final String input) {
        return Long.parseLong(input);
      }
    }, TimeUnit.SECONDS);
    Preconditions.checkArgument(lowerBound <= upperBound);
  }

  @Override
  protected Optional<Quantity<Long, TimeUnit>> convert(final String raw) {
    return Optional.absent();
  }

  @Override
  public String description() {
    // TODO
    return "TODO";
  }

}
