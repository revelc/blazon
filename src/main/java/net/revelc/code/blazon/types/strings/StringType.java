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

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.revelc.code.blazon.Type;
import net.revelc.code.blazon.types.AbstractTrimmedType;

/**
 * A basic normalized {@link String} {@link Type}, with an optional predicate to validate against.
 *
 * @see AbstractTrimmedType
 */
public class StringType extends AbstractTrimmedType<String> {

  private final Predicate<String> predicate;
  private final String predicateDescription;

  public StringType() {
    this(Predicates.<String>alwaysTrue(), "");
  }

  public StringType(final Predicate<String> predicate, final String predicateDescription) {
    this.predicate = Preconditions.checkNotNull(predicate);
    this.predicateDescription = Preconditions.checkNotNull(predicateDescription);
  }

  @Override
  protected Optional<String> convert(final String normalized) {
    return Optional.of(normalized);
  }

  @Override
  protected Optional<String> checkPostconditions(final String converted) {
    if (predicate.apply(converted)) {
      return super.checkPostconditions(converted);
    }
    throw new IllegalArgumentException(
        "The string '" + converted + "' does not match '" + predicateDescription + "'");
  }

  @Override
  public String description() {
    if (Predicates.alwaysTrue().equals(predicate)) {
      return "Any string.";
    }
    return predicateDescription;
  }

}
