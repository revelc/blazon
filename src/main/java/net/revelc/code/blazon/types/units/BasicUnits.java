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

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import net.revelc.code.blazon.types.AbstractTrimmedType;
import net.revelc.code.blazon.types.strings.OneOf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An {@link AbstractTrimmedType} which converts the {@link String} to a {@link Long}. If the input
 * is null, or the input contains only whitespace, the result is null. If a
 * {@link NumberFormatException} occurs, it will be thrown.
 */
public abstract class BasicUnits<M extends Number, U extends Enum<U>>
    extends AbstractTrimmedType<Quantity<M, U>> {

  private final U defaultUnit;
  private final boolean caseSensitive;

  private static final class OneOfFriend extends OneOf<java.lang.annotation.ElementType> {
    private OneOfFriend() {
      super(java.lang.annotation.ElementType.class);
    }

    protected static <E extends Enum<E>> E findEnum(final Class<E> enumType,
        final boolean caseSensitive, final String raw) {
      return OneOf.findEnum(enumType, caseSensitive, raw);
    }
  }

  protected BasicUnits(final U defaultUnit, final boolean caseSensitive) {
    this.defaultUnit = Preconditions.checkNotNull(defaultUnit);
    this.caseSensitive = caseSensitive;
  }

  public abstract M convertNumber(final String number);

  @Override
  protected Optional<Quantity<M, U>> convert(final String normalized) {
    @SuppressWarnings("unchecked")
    final Class<U> unitClass = (Class<U>) defaultUnit.getClass();

    final Matcher matcher =
        Pattern.compile("^(.*?)(" + Joiner.on('|').join(unitClass.getEnumConstants()) + ")?$",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher(normalized);

    if (!matcher.find()) {
      throw new AssertionError(
          "String does not match pattern '" + matcher.pattern().pattern() + "':\n" + normalized);
    }
    final String numberPart = matcher.group(1).trim();
    final String unitPart = matcher.group(2);

    final U unit =
        unitPart == null ? defaultUnit : OneOfFriend.findEnum(unitClass, caseSensitive, unitPart);
    final Quantity<M, U> quantity = new Quantity<>(convertNumber(numberPart), unit);

    return Optional.of(quantity);
  }

}
