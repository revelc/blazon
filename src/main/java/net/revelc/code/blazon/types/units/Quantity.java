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
import net.revelc.code.blazon.types.units.Quantity.Converter;

/**
 * A value class representing a magnitude and unit.
 *
 * @param <M> A subclass of {@link Number}, which represents the magnitude of the quantity.
 * @param <U> An {@link Enum} representing the distinct available unit types.
 */
public class Quantity<M extends Number & Comparable<M>, U extends Enum<U> & Converter<M, U>>
    implements Comparable<Quantity<M, U>> {

  public static interface Converter<N extends Number, V extends Enum<V>> {
    N convertTo(V destinationUnit, N value);
  }

  private final M magnitude;
  private final U unit;

  public Quantity(final M magnitude, final U unit) {
    this.magnitude = Preconditions.checkNotNull(magnitude);
    this.unit = Preconditions.checkNotNull(unit);
  }

  public M getMagnitude() {
    return magnitude;
  }

  public U getUnit() {
    return unit;
  }

  @Override
  public int hashCode() {
    return getMagnitude().hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Quantity)) {
      return false;
    }

    final Quantity<?, ?> q = (Quantity<?, ?>) obj;
    return getMagnitude().equals(q.getMagnitude()) && getUnit().equals(q.getUnit());
  }

  @Override
  public int compareTo(final Quantity<M, U> other) {
    if (other == null) {
      return -1;
    }

    if (this == other) {
      return 0;
    }

    // if units are the same, compare the magnitude only
    final int magOnlyComparison = getMagnitude().compareTo(other.getMagnitude());
    if (getUnit() == other.getUnit()) {
      return magOnlyComparison;
    }

    // if one is bigger in magnitude and units, no need to convert
    final boolean thisHasBiggerUnits = getUnit().compareTo(other.getUnit()) < 0;
    if ((thisHasBiggerUnits && magOnlyComparison <= 0)
        || (!thisHasBiggerUnits && magOnlyComparison >= 0)) {
      return magOnlyComparison;
    }

    // if units are different, convert before comparing
    final U granularUnits = thisHasBiggerUnits ? getUnit() : other.getUnit();
    return as(granularUnits).compareTo(other.as(granularUnits));
  }

  @Override
  public String toString() {
    return getMagnitude().toString() + getUnit().name();
  }

  /**
   * Presents this {@link Quantity} as an equivalent {@link Quantity} in the target unit. If the
   * target unit is the same as the current unit, the current object(this) is returned. Otherwise,
   * the magnitude is converted from the current unit to the target unit, and a new {@link Quantity}
   * is returned composed of the converted magnitude and the target unit.
   *
   * @param targetUnit the target unit
   * @return the new {@link Quantity} with after converting the current magnitude to the equivalent
   *         magnitude in the target unit
   */
  public Quantity<M, U> as(final U targetUnit) {
    if (getUnit() == Preconditions.checkNotNull(targetUnit)) {
      return this;
    }
    return new Quantity<M, U>(getUnit().convertTo(targetUnit, getMagnitude()), targetUnit);
  }

}
