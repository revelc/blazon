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

public class IntegerRangeTypeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testRangeLowerBounds() {
    final IntegerRangeType bounds = new IntegerRangeType(-24, 35);
    assertEquals((Integer) (-24), bounds.getLowerBound());

    assertEquals((Integer) (-24), bounds.parse("-24"));
    assertEquals((Integer) 0, bounds.parse("0"));
    assertEquals((Integer) 35, bounds.parse("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.parse("-25");
  }

  @Test
  public void testRangeUpperBounds() {
    final IntegerRangeType bounds = new IntegerRangeType(-24, 35);
    assertEquals((Integer) 35, bounds.getUpperBound());

    assertEquals((Integer) (-24), bounds.parse("-24"));
    assertEquals((Integer) 0, bounds.parse("0"));
    assertEquals((Integer) 35, bounds.parse("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.parse("36");
  }

  @Test
  public void testSingleLowerBounds() {
    final IntegerRangeType bounds = new IntegerRangeType(35, 35);
    assertEquals((Integer) 35, bounds.parse("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.parse("34");
  }

  @Test
  public void testSingleUpperBounds() {
    final IntegerRangeType bounds = new IntegerRangeType(35, 35);
    assertEquals((Integer) 35, bounds.parse("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.parse("36");
  }

  @Test
  public void testWithWhitespace() {
    final IntegerRangeType bounds = new IntegerRangeType(-24, 35);
    assertEquals((Integer) (-24), bounds.parse(" -24 "));
    assertEquals((Integer) 0, bounds.parse(" \t 0 \t "));
    assertEquals((Integer) 0, bounds.parse(" \n 0 \n "));
    assertEquals((Integer) 35, bounds.parse(" \n \t \n35\n \n \t"));
  }

  @Test
  public void testBadFormat() {
    final IntegerRangeType bounds = new IntegerRangeType(-24, 35);
    exception.expect(IllegalArgumentException.class);
    bounds.parse("-123abc");
  }

  @Test
  public void testConstructorBadOrder() {
    exception.expect(IllegalArgumentException.class);
    new IntegerRangeType(36, 35);
  }

  @Test
  public void testRadix() {
    final IntegerRangeType bounds = new IntegerRangeType(0, 0);
    assertEquals(10, bounds.getRadix());
  }

}
