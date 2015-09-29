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
 * An {@link AbstractTrimmedType} which converts the {@link String} to an {@link Integer}. If the
 * input is null, or the input contains only whitespace, the result is null. If a
 * {@link NumberFormatException} occurs, it will be thrown.
 */
public class IntegerType extends AbstractTrimmedType<Integer> {

  public static final IntegerType BIN = new IntegerType(2);
  public static final IntegerType OCT = new IntegerType(8);
  public static final IntegerType DEC = new IntegerType(10);
  public static final IntegerType HEX = new IntegerType(16);

  private final int radix;

  public IntegerType() {
    this(10);
  }

  public IntegerType(final int radix) {
    this.radix = radix;
  }

  public int getRadix() {
    return radix;
  }

  protected Integer getLowerBound() {
    return Integer.MIN_VALUE;
  }

  protected Integer getUpperBound() {
    return Integer.MAX_VALUE;
  }

  @Override
  protected Optional<Integer> convert(final String raw) {
    return Optional.of(Integer.parseInt(raw, getRadix()));
  }

  @Override
  public String description() {
    return "A base-" + getRadix() + " integer in the range ["
        + Integer.toString(getLowerBound(), getRadix()) + '\u2025'
        + Integer.toString(getUpperBound(), getRadix()) + "].";
  }

}
