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

public class Key<T> {

  private final String key;
  private final Breed<T> breed;

  public Key(final String key, final Breed<T> breed) {
    this.key = key;
    this.breed = breed;
  }

  private T get(final Object value, final T defaultValue) {
    return value == null ? defaultValue : breed.apply(value.toString());
  }

  public T get(final Map<?,?> source) {
    Preconditions.checkNotNull(source);
    return get(source.get(key), null);
  }

  public T get(final Map<?,?> source, final T defaultValue) {
    Preconditions.checkNotNull(source);
    return get(source.get(key), defaultValue);
  }

}
