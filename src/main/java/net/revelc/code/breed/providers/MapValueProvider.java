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

package net.revelc.code.breed.providers;

import net.revelc.code.breed.ValueProvider;

import java.util.Map;

/**
 * A {@link ValueProvider} which wraps a {@link Map}. If values contained in the map are not of type
 * {@link String}, their {@link #toString()} method will be called to convert them.
 */
public class MapValueProvider extends ValueProvider<Map<?,?>> {

  /**
   * Utilize a Map as the source.
   */
  public MapValueProvider(final Map<?,?> source) {
    super(source);
  }

  @Override
  public String getValue(final String key) {
    Object value = getSource().get(key);
    return value == null ? null : value.toString();
  }

}
