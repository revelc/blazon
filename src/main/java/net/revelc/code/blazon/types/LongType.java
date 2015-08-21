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

/**
 * An {@link AbstractTrimmedType} which converts the {@link String} to a {@link Long}. If the input
 * is null, or the input contains only whitespace, the result is null. If a
 * {@link NumberFormatException} occurs, it will be thrown.
 */
public class LongType extends AbstractTrimmedType<Long> {

  @Override
  protected Long convert(final String raw) {
    // the check for isEmpty here relies on the string being trimmed by the super class
    return (raw == null || raw.isEmpty()) ? null : Long.parseLong(raw);
  }

}
