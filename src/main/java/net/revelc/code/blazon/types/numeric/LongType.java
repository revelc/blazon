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

package net.revelc.code.blazon.types.numeric;

import com.google.common.base.Optional;
import net.revelc.code.blazon.types.AbstractTrimmedType;

/**
 * An {@link AbstractTrimmedType} which converts the {@link String} to a {@link Long}. If the input
 * is null, or the input contains only whitespace, the result is null. If a
 * {@link NumberFormatException} occurs, it will be thrown.
 */
public class LongType extends AbstractTrimmedType<Long> {

  public static final LongType BIN = new LongType(2);
  public static final LongType OCT = new LongType(8);
  public static final LongType DEC = new LongType(10);
  public static final LongType HEX = new LongType(16);

  private final int radix;

  public LongType() {
    this(10);
  }

  public LongType(final int radix) {
    this.radix = radix;
  }

  public int getRadix() {
    return radix;
  }

  protected Long getLowerBound() {
    return Long.MIN_VALUE;
  }

  protected Long getUpperBound() {
    return Long.MAX_VALUE;
  }

  @Override
  protected Optional<Long> convert(final String raw) {
    return Optional.of(Long.parseLong(raw, getRadix()));
  }

  @Override
  public String description() {
    return "A base-" + getRadix() + " integer in the range ["
        + Long.toString(getLowerBound(), getRadix()) + '\u2025'
        + Long.toString(getUpperBound(), getRadix()) + "].";
  }

}
