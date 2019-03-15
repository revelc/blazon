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

package net.revelc.code.blazon.types.strings;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import net.revelc.code.blazon.types.AbstractTrimmedType;

/**
 * An {@link AbstractTrimmedType} which represents one of a fixed selection of enum options, after
 * removing leading and trailing whitespace.
 *
 * <div>Use caution when setting case-sensitive search to false. An exact match will be preferred,
 * but if one cannot be found and case-sensitive search is set to false, the first case-insensitive
 * match will be returned without examining the remaining. This might be a problem when using enum
 * constants which differ only by case (allowed in Java, but not recommended).</div>
 */
public class OneOf<T extends Enum<T>> extends AbstractTrimmedType<T> {

  private final Class<T> enumType;
  private final boolean caseSensitive;

  public OneOf(final Class<T> enumType) {
    this(enumType, true);
  }

  public OneOf(final Class<T> enumType, final boolean caseSensitive) {
    this.enumType = Preconditions.checkNotNull(enumType);
    this.caseSensitive = caseSensitive;
  }

  @Override
  protected Optional<T> convert(final String raw) {
    return Optional.<T>of(findEnum(enumType, caseSensitive, raw));
  }

  protected static <E extends Enum<E>> E findEnum(final Class<E> enumType,
      final boolean caseSensitive, final String raw) {
    try {
      return Enum.valueOf(enumType, raw);
    } catch (final IllegalArgumentException e) {
      if (!caseSensitive) {
        // case-insensitive search if we can't find it
        for (final E t : enumType.getEnumConstants()) {
          if (t.name().equalsIgnoreCase(raw)) {
            return t;
          }
        }
      }
      throw e;
    }
  }

  @Override
  public String description() {
    return "A case-" + (caseSensitive ? "" : "in") + "sensitive string matching one of { "
        + Joiner.on(", ").join(enumType.getEnumConstants()) + " }";
  }

}
