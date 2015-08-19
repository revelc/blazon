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

import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.Properties;

public class Key<T> {

  private final String key;
  private final Breed<T> breed;

  /**
   * Create a new Key with the given identifier and type. By default, Keys can be retrieved from a
   * {@link Map}&lt;String,String&gt; or from {@link Properties}. However, one can extend this class
   * and create a getter to retrieve values from additional sources. Subclasses should call
   * {@link #parseValue(String, Object)} after retrieving the String value from the configuration
   * source, with an optional default value.
   *
   * @param key
   *          the unique identifier for this particular configuration property
   * @param breed
   *          the breed/type of property this key represents, which determines how the property's
   *          value is parsed and validated
   */
  public Key(final String key, final Breed<T> breed) {
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(breed);
    this.key = key;
    this.breed = breed;
  }

  /**
   * Subclasses should call this method to parse and validate the given String value for this Key.
   *
   * @param value
   *          the raw value retrieved from the configuration source, or null if it wasn't found
   * @param defaultValue
   *          the default value to return if the value passed to it was null, or if the
   *          {@link Breed} parsed the string as a null
   * @return an instance of the type this Key represents, after it has been parsed and validated
   */
  protected T parseValue(final String value, final T defaultValue) {
    T parsed = null;
    if (value != null || defaultValue == null) {
      if (!breed.preCheck().apply(value)) {
        /* TODO deal with error */
      }
      parsed = breed.converter().apply(value);
      if (!breed.postCheck().apply(parsed)) {
        /* TODO deal with error */
      }
    }
    return parsed == null ? defaultValue : parsed;
  }

  public T get(final Map<String,String> source) {
    Preconditions.checkNotNull(source);
    return parseValue(source.get(key), null);
  }

  public T get(final Map<String,String> source, final T defaultValue) {
    Preconditions.checkNotNull(source);
    return parseValue(source.get(key), defaultValue);
  }

  public T get(final Properties source) {
    Preconditions.checkNotNull(source);
    return parseValue(source.getProperty(key), null);
  }

  public T get(final Properties source, final T defaultValue) {
    Preconditions.checkNotNull(source);
    return parseValue(source.getProperty(key), defaultValue);
  }

}
