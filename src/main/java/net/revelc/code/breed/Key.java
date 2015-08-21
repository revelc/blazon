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

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.Properties;

public class Key<T> {

  private final String key;
  private final Breed<T> breed;
  private final Optional<T> defaultValue;

  /**
   * Create a new Key with the given identifier and type. By default, Keys can be retrieved from a
   * {@link Map}&lt;String,String&gt; or from {@link Properties}. However, one can extend this class
   * and create a getter to retrieve values from additional sources. Subclasses should call
   * {@link #parseValue(String)} after retrieving the String value from the configuration source,
   * with an optional default value.
   *
   * @param key
   *          the unique identifier for this particular configuration property
   * @param breed
   *          the breed/type of property this key represents, which determines how the property's
   *          value is parsed and validated
   */
  public Key(final String key, final Breed<T> breed) {
    this(key, breed, Optional.<T>absent());
  }

  /**
   * Create a new Key with the given identifier and type. By default, Keys can be retrieved from a
   * {@link Map}&lt;String,String&gt; or from {@link Properties}. However, one can extend this class
   * and create a getter to retrieve values from additional sources. Subclasses should call
   * {@link #parseValue(String)} after retrieving the String value from the configuration source,
   * with an optional default value.
   *
   * @param key
   *          the unique identifier for this particular configuration property
   * @param breed
   *          the breed/type of property this key represents, which determines how the property's
   *          value is parsed and validated
   * @param defaultValue
   *          the default value to return if the value passed to it was null, or if the
   *          {@link Breed} interprets the a value as equivalent to null
   */
  public Key(final String key, final Breed<T> breed, final T defaultValue) {
    this(key, breed, Optional.of(defaultValue));
  }

  private Key(final String key, final Breed<T> breed, final Optional<T> defaultValue) {
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(breed);
    this.key = key;
    this.breed = breed;
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
   * Getter for the {@link Breed}.
   *
   * @return the breed
   */
  public Breed<T> getBreed() {
    return breed;
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
   * <li>If the value is null and a default value is available, the default value is returned
   * <li>If the value is non-null or the default value is not available, the breed will process the
   * value
   * <li>If the result of the breed processing is non-null, that result is returned
   * <li>If the result of the breed processing is null and a default value is available, the default
   * value is returned
   * <li>If the result of the breed processing is null and a default value is not available, null is
   * returned
   * </ol>
   * Essentially, the behavior is such that the default value is returned whenever the key is not
   * available in the configuration provider, or if the breed determines that what is available is
   * equivalent to being unset. An example of this might be an empty string or a dash. Further, if
   * the key does not have a default value, the breed has an opportunity to provide one which makes
   * sense for the breed. For example, if the breed is a numerical type, it might provide a default
   * value of 0 when the value is unset and the user didn't provide a default value explicitly for
   * that key.
   *
   * @param value
   *          the raw value retrieved from the configuration source, or null if it wasn't found
   * @return an instance of the type this Key represents, after it has been parsed and validated
   */
  protected T parseValue(final String value) {
    if (value == null && defaultValue.isPresent()) {
      return defaultValue.get();
    }
    T parsed = breed.process(value);
    if (parsed == null) {
      return defaultValue.orNull();
    }
    return parsed;
  }

  public T get(final Map<String,String> source) {
    Preconditions.checkNotNull(source);
    return parseValue(source.get(key));
  }

  public T get(final Properties source) {
    Preconditions.checkNotNull(source);
    return parseValue(source.getProperty(key));
  }

}
