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

package net.revelc.code.blazon;

import com.google.common.base.Optional;

/**
 * It provides a mechanism to validate a configuration property, either before or after conversion
 * (or both). A successful conversion will go through {@link #checkPreconditions(Optional)}, then
 * {@link #convert(String)}, then {@link #checkPostconditions(Object)}. See {@link #parse(String)}.
 * Subclasses may choose to throw a specific {@link RuntimeException} like
 * {@link IllegalArgumentException} or {@link NumberFormatException}, in case of error, or they may
 * wish to log the error and return {@link Optional#absent()} in any of the methods to signal that a
 * default should be used.
 *
 * @param <T> the target Java type which this class represents
 */
public abstract class Type<T> {

  /**
   * Optional. Validate and normalize the raw string input. Override this method to enforce any
   * preconditions for conversion, such as to require that it be present. One could also apply any
   * locale-based, or case-conversion, or other normalization here. When the preconditions are not
   * satisfied, this method may throw an appropriate {@link RuntimeException} or return
   * {@link Optional#absent()} to signal that a default value should be used.
   *
   * @param raw an {@link Optional} which is either absent or contains the raw string to be checked
   * @return a non-null instance of {@link Optional}, either containing a normalized form of the raw
   *         input after validation, or absent, to signal that a default should be used in its place
   */
  protected Optional<String> checkPreconditions(final Optional<String> raw) {
    return raw;
  }

  /**
   * Convert the value to the appropriate type. If the raw string cannot be converted to the
   * expected type, this method may throw an appropriate {@link RuntimeException} or return
   * {@link Optional#absent()} to signal that a default value should be used.
   *
   * @param normalized the non-null normalized value to be converted
   * @return a non-null instance of {@link Optional}, either containing the converted value, or
   *         absent, to signal that a default should be used in its place
   */
  protected abstract Optional<T> convert(final String normalized);

  /**
   * Optional. Apply any constraints on the converted value. If the postconditions are not
   * satisfied, this method may throw an appropriate {@link RuntimeException} or return
   * {@link Optional#absent()} to signal that a default value should be used.
   *
   * @param converted the non-null converted value to be checked
   * @return a non-null instance of {@link Optional}, either containing the converted value which
   *         was passed in, or absent, to signal that a default should be used in its place
   */
  protected Optional<T> checkPostconditions(final T converted) {
    return Optional.of(converted);
  }

  /**
   * Parses the raw value by first applying {@link #checkPreconditions(Optional)}, then
   * {@link #convert(String)}, then {@link #checkPostconditions(Object)}.
   *
   * @param raw the raw value to be converted
   * @return the value, after validation, conversion, and applying any post-conversion constraints
   */
  public final T parse(final String raw) {
    final Optional<String> normalized = checkPreconditions(Optional.fromNullable(raw));
    if (!normalized.isPresent()) {
      return null;
    }
    final Optional<T> converted = convert(normalized.get());
    if (!converted.isPresent()) {
      return null;
    }
    final Optional<T> verified = checkPostconditions(converted.get());
    return verified.orNull();
  }

  /**
   * Provides a description of this type, which can be useful for documentation or generated error
   * messages.
   *
   * @return a description of this instance
   */
  public abstract String description();

}
