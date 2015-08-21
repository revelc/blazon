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

package net.revelc.code.breed.breeds;

import net.revelc.code.breed.Breed;

/**
 * A {@link Breed} which represents one of a fixed selection of enum options.
 */
public class OneOf<T extends Enum<T>> extends Breed<T> {

  private Class<T> enumType;

  public OneOf(Class<T> enumType) {
    this.enumType = enumType;
  }

  @Override
  protected T convert(final String raw) throws RuntimeException {
    return Enum.valueOf(enumType, raw);
  }

}
