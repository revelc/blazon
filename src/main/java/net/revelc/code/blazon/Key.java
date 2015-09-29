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
import com.google.common.base.Preconditions;

/**
 * A representation of a configuration property or key, which corresponds to a distinct type (or
 * {@link Type}). A key optionally has a default value and can be retrieved from a configuration
 * source using a {@link Source}. When it is retrieved, it will be validated and converted to its
 * expected type according to its {@link Type}.
 *
 * @param <T> the type which the value will be represented as when it is retrieved from the
 *        configuration source
 */
public class Key<T> {

  private final String key;
  private final Type<T> type;
  private final Optional<T> defaultValue;

  /**
   * Create a new Key with the given identifier and type, so that its value can be retrieved and
   * from a {@link Source} and processed by the {@link Type} which defines its type.
   *
   * @param key the unique identifier for this particular configuration property
   * @param type the type of property this key represents, which determines how the property's value
   *        is parsed and validated
   */
  public Key(final String key, final Type<T> type) {
    this(key, type, Optional.<T>absent());
  }

  /**
   * Create a new Key with the given identifier and type, so that its value can be retrieved and
   * from a {@link Source} and processed by the {@link Type} which defines its type.
   *
   * @param key the unique identifier for this particular configuration property
   * @param type the type of property this key represents, which determines how the property's value
   *        is parsed and validated
   * @param defaultValue the default value to return if the value passed to it was null, or if the
   *        {@link Type} interprets the a value as equivalent to null
   */
  public Key(final String key, final Type<T> type, final T defaultValue) {
    this(key, type, Optional.of(defaultValue));
  }

  private Key(final String key, final Type<T> type, final Optional<T> defaultValue) {
    this.key = Preconditions.checkNotNull(key);
    this.type = Preconditions.checkNotNull(type);
    this.defaultValue = defaultValue;
  }

  /**
   * Getter for the key.
   *
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * Getter for the {@link Type}.
   *
   * @return the type
   */
  public Type<T> getType() {
    return type;
  }

  /**
   * Getter for the default value.
   *
   * @return the default value or null, if there wasn't one
   */
  public T getDefaultValue() {
    return defaultValue.orNull();
  }

  /**
   * Subclasses should override this method if they wish to change the way the default values for
   * keys are handled. The default handling behavior is as follows:
   * <ol>
   * <li>If the value is null and a default value was provided, the default value is returned.
   * <li>If the value is non-null or the default value was not provided, it will be processed by the
   * specified type.
   * <li>If the result after processing is non-null, that result is returned.
   * <li>If the result after processing is null and a default value was provided, the default value
   * is returned.
   * <li>If the result after processing is null and a default value was not provided, null is
   * returned.
   * </ol>
   * Essentially, the behavior is such that the default value is returned whenever the key is not
   * available in the configuration source, or if the {@link Type} determines that what is available
   * is equivalent to being unset. An example of this might be an empty string or a dash(-).
   * Further, if the key does not have a default value, the {@link Type} has an opportunity to
   * provide one which is appropriate for that type. For example, if the {@link Type} represents a
   * number, it might return 0 when the value is unset and the user didn't provide a default value.
   *
   * @param value the raw value retrieved from the configuration source, or null if it wasn't found
   * @return an instance of the type this Key represents, after it has been parsed and validated
   */
  protected T parseRawValue(final String value, final T defaultValue) {
    final T defaultV = defaultValue;
    if (value == null && defaultV != null) {
      return defaultV;
    }
    final T parsed = getType().parse(value);
    if (parsed == null) {
      return defaultV;
    }
    return parsed;
  }

  /**
   * Retrieve a value from the given {@link Source}, using this {@link #getKey()}. While this method
   * can be overridden, it's preferred to override {@link #parseRawValue(String, Object)} instead.
   *
   * @param source a source of {@link String} values arranged by {@link String} keys
   * @return an instance of the type this Key represents, after it has been parsed and validated
   */
  public T getValue(final Source<?> source) {
    return parseRawValue(Preconditions.checkNotNull(source).getValue(getKey()), getDefaultValue());
  }

}
