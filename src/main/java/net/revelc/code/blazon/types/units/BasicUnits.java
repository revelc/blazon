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

import net.revelc.code.blazon.types.AbstractTrimmedType;

/**
 * An {@link AbstractTrimmedType} which converts the {@link String} to a {@link Long}. If the input
 * is null, or the input contains only whitespace, the result is null. If a
 * {@link NumberFormatException} occurs, it will be thrown.
 */
public abstract class BasicUnits<M extends Number, U extends Enum<U>>
    extends AbstractTrimmedType<Quantity<M, U>> {

  private final Function<String, M> numberParser;
  private final U defaultUnit;

  protected BasicUnits(final Function<String, M> numberParser, final U defaultUnit) {
    this.numberParser = Preconditions.checkNotNull(numberParser);
    this.defaultUnit = Preconditions.checkNotNull(defaultUnit);
  }

  @Override
  protected Optional<Quantity<M, U>> convert(final String normalized) {
    // TODO
    final String numberPart = "TODO";
    final U unitPart = null; // TODO
    final Quantity<M, U> quantity =
        new Quantity<>(numberParser.apply(numberPart), unitPart == null ? defaultUnit : unitPart);
    return Optional.<Quantity<M, U>>of(quantity);
  }

}
