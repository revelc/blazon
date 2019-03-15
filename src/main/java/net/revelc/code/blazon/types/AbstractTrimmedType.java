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

package net.revelc.code.blazon.types;

import com.google.common.base.Optional;
import net.revelc.code.blazon.Type;

/**
 * A {@link Type} which normalizes the raw input {@link String} by trimming leading and trailing
 * whitespace. Empty strings, after trimming, are normalized to being absent. Subclasses which
 * override {@link #checkPreconditions(Optional)} should remember to act upon
 * <code>super.checkPreconditions(raw)</code>, which could be absent.
 *
 * @see String#trim()
 */
public abstract class AbstractTrimmedType<T> extends Type<T> {

  @Override
  protected Optional<String> checkPreconditions(final Optional<String> raw) {
    String result = null;
    if (raw.isPresent()) {
      result = raw.get().trim();
    }
    return Optional.fromNullable((result == null || result.isEmpty()) ? null : result);
  }

}
