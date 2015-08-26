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

/**
 * It provides a mechanism to validate a configuration property, either before or after conversion
 * (or both). A successful conversion will go through {@link #checkPreconditions(String)}, then
 * {@link #convert(String)}, then {@link #checkPostconditions(Object)}. See {@link #process(String)}
 * . Subclasses may choose to throw a specific {@link RuntimeException} like
 * {@link IllegalArgumentException} or {@link NumberFormatException}, in case of error, or they may
 * wish to log the error and continue processing some default behavior (like returning a null).
 *
 * @param <T> the target Java type which this class represents
 */
public abstract class Type<T> {

  /**
   * Optional. Validate the given element prior to conversion. Override this method to enforce any
   * preconditions for conversion. One could also apply any locale-based, or case-conversion, or
   * other normalization here.
   *
   * @param raw the raw value to be validated
   * @return the valid string
   * @throws RuntimeException an exception appropriate to the failure, if the element fails to
   *         validate
   */
  protected String checkPreconditions(final String raw) throws RuntimeException {
    return raw;
  }

  /**
   * Convert the value to the appropriate type.
   *
   * @param raw the raw value to be converted
   * @return the value, after conversion
   * @throws RuntimeException an exception appropriate to the failure, if the element fails to
   *         convert
   */
  protected abstract T convert(final String raw) throws RuntimeException;

  /**
   * Optional. Override to apply any constraints on the converted value.
   *
   * @param value the raw value to be validated
   * @throws RuntimeException an exception appropriate to the failure, if the element fails to
   *         validate
   */
  protected T checkPostconditions(final T value) throws RuntimeException {
    return value;
  }

  /**
   * Processes the raw value by first applying {@link #checkPreconditions(String)}, then
   * {@link #convert(String)}, then {@link #checkPostconditions(Object)}.
   *
   * @param raw the raw value to be converted
   * @return the value, after validation, conversion, and applying any post-conversion constraints
   * @throws RuntimeException an exception appropriate to the failure, if the element fails at any
   *         point in the conversion
   */
  public final T process(final String raw) throws RuntimeException {
    return checkPostconditions(convert(checkPreconditions(raw)));
  }

}
