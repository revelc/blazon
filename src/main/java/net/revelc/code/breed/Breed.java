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

package net.revelc.code.breed;

/**
 * A Breed(noun) is a type. It provides a mechanism to validate a configuration property, either
 * before or after conversion (or both). A successful conversion will go through
 * {@link #validate(String)}, then {@link #convert(String)}, then {@link #constrain(Object)}. See
 * {@link #process(String)}.
 *
 * @param <T>
 *          The target Java type this Breed represents.
 */
public abstract class Breed<T> {

  /**
   * Optional. Validate the given element prior to conversion. Override this method to enforce any
   * preconditions for conversion. One could also apply any locale-based, or case-conversion, or
   * other normalization here.
   *
   * @param raw
   *          the raw value to be validated
   * @return the valid string
   * @throws RuntimeException
   *           an exception appropriate to the failure, if the element fails to validate
   */
  protected String validate(final String raw) throws RuntimeException {
    return raw;
  }

  /**
   * Convert the value to the appropriate type.
   *
   * @param raw
   *          the raw value to be converted
   * @return the value, after conversion
   * @throws RuntimeException
   *           an exception appropriate to the failure, if the element fails to convert
   */
  protected abstract T convert(final String raw) throws RuntimeException;

  /**
   * Optional. Override to apply any constraints on the converted value.
   *
   * @param value
   *          the raw value to be validated
   * @throws RuntimeException
   *           an exception appropriate to the failure, if the element fails to validate
   */
  protected T constrain(final T value) throws RuntimeException {
    return value;
  }

  /**
   * Processes the raw value by first applying {@link #validate(String)}, then
   * {@link #convert(String)}, then {@link #constrain(Object)}.
   *
   * @param raw
   *          the raw value to be converted
   * @return the value, after validation, conversion, and applying any post-conversion constraints
   * @throws RuntimeException
   *           an exception appropriate to the failure, if the element fails at any point in the
   *           conversion
   */
  public final T process(final String raw) throws RuntimeException {
    return constrain(convert(validate(raw)));
  }

}
