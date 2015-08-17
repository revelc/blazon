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

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * A Breed is a type. It provides a mechanism to validate a configuration property, either before or
 * after conversion (or both).
 *
 * @param <T>
 *          The target Java type this Breed represents.
 */
public abstract class Breed<T> implements Function<String,T> {

  public Predicate<String> preCheck() {
    return Predicates.alwaysTrue();
  }

  public Predicate<T> postCheck() {
    return Predicates.alwaysTrue();
  }

}
