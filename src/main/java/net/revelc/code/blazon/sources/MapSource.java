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

package net.revelc.code.blazon.sources;

import net.revelc.code.blazon.Source;

import java.util.Map;

/**
 * A {@link Source} which wraps a {@link Map}. If values contained in the map are not of type
 * {@link String}, their {@link #toString()} method will be called to convert them.
 */
public class MapSource extends Source<Map<?, ?>> {

  /**
   * Utilize a Map as the source.
   */
  public MapSource(final Map<?, ?> source) {
    super(source);
  }

  @Override
  public String getValue(final String key) {
    Object value = getSource().get(key);
    return value == null ? null : value.toString();
  }

}
