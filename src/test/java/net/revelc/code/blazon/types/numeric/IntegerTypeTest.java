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

public class IntegerTypeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testRadix() {
    assertEquals(2, IntegerType.BIN.getRadix());
    assertEquals(8, IntegerType.OCT.getRadix());
    assertEquals(10, IntegerType.DEC.getRadix());
    assertEquals(10, new IntegerType().getRadix());
    assertEquals(16, IntegerType.HEX.getRadix());
    assertEquals(11, new IntegerType(11).getRadix());
  }

  @Test
  public void testBounds() {
    assertEquals((Integer) Integer.MIN_VALUE, IntegerType.BIN.getLowerBound());
    assertEquals((Integer) Integer.MIN_VALUE, IntegerType.OCT.getLowerBound());
    assertEquals((Integer) Integer.MIN_VALUE, IntegerType.DEC.getLowerBound());
    assertEquals((Integer) Integer.MIN_VALUE, IntegerType.HEX.getLowerBound());

    assertEquals((Integer) Integer.MAX_VALUE, IntegerType.BIN.getUpperBound());
    assertEquals((Integer) Integer.MAX_VALUE, IntegerType.OCT.getUpperBound());
    assertEquals((Integer) Integer.MAX_VALUE, IntegerType.DEC.getUpperBound());
    assertEquals((Integer) Integer.MAX_VALUE, IntegerType.HEX.getUpperBound());
  }

  @Test
  public void testParse() {
    assertEquals((Integer) 11, IntegerType.BIN.parse("1011"));
    assertEquals((Integer) 13, IntegerType.HEX.parse("d"));
    assertEquals((Integer) 13, IntegerType.HEX.parse("D"));
    assertEquals((Integer) (-255), IntegerType.HEX.parse("-fF"));
    assertEquals((Integer) (-19), IntegerType.DEC.parse(" -19 "));
  }

  @Test
  public void testTooBig() {
    exception.expect(NumberFormatException.class);
    IntegerType.DEC.parse(Long.toString(1L + Integer.MAX_VALUE));
  }

  @Test
  public void testTooSmall() {
    exception.expect(NumberFormatException.class);
    IntegerType.DEC.parse(Long.toString(-5L - Integer.MAX_VALUE));
  }

  @Test
  public void testBadChars() {
    exception.expect(NumberFormatException.class);
    IntegerType.DEC.parse(" 5_2");
  }

  @Test
  public void testBadRadix() {
    IntegerType.DEC.parse("5");
    exception.expect(NumberFormatException.class);
    IntegerType.BIN.parse("5");
  }

}
