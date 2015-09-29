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

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LongTypeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testRadix() {
    assertEquals(2, LongType.BIN.getRadix());
    assertEquals(8, LongType.OCT.getRadix());
    assertEquals(10, LongType.DEC.getRadix());
    assertEquals(10, new LongType().getRadix());
    assertEquals(16, LongType.HEX.getRadix());
    assertEquals(11, new LongType(11).getRadix());
  }

  @Test
  public void testBounds() {
    assertEquals((Long) Long.MIN_VALUE, LongType.BIN.getLowerBound());
    assertEquals((Long) Long.MIN_VALUE, LongType.OCT.getLowerBound());
    assertEquals((Long) Long.MIN_VALUE, LongType.DEC.getLowerBound());
    assertEquals((Long) Long.MIN_VALUE, LongType.HEX.getLowerBound());

    assertEquals((Long) Long.MAX_VALUE, LongType.BIN.getUpperBound());
    assertEquals((Long) Long.MAX_VALUE, LongType.OCT.getUpperBound());
    assertEquals((Long) Long.MAX_VALUE, LongType.DEC.getUpperBound());
    assertEquals((Long) Long.MAX_VALUE, LongType.HEX.getUpperBound());
  }

  @Test
  public void testParse() {
    assertEquals((Long) 11L, LongType.BIN.parse("1011"));
    assertEquals((Long) 13L, LongType.HEX.parse("d"));
    assertEquals((Long) 13L, LongType.HEX.parse("D"));
    assertEquals((Long) (-255L), LongType.HEX.parse("-fF"));
    assertEquals((Long) (-19L), LongType.DEC.parse(" -19 "));
  }

  @Test
  public void testTooBig() {
    exception.expect(NumberFormatException.class);
    LongType.DEC.parse(Long.toString(Long.MAX_VALUE) + "0");
  }

  @Test
  public void testTooSmall() {
    exception.expect(NumberFormatException.class);
    LongType.DEC.parse(Long.toString(Long.MIN_VALUE) + "0");
  }

  @Test
  public void testBadChars() {
    exception.expect(NumberFormatException.class);
    LongType.DEC.parse(" 5_2");
  }

  @Test
  public void testBadRadix() {
    LongType.DEC.parse("5");
    exception.expect(NumberFormatException.class);
    LongType.BIN.parse("5");
  }

}
