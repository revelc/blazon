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

public class LongRangeTypeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testRangeLowerBounds() {
    final LongRangeType bounds = new LongRangeType(-24, 35);
    assertEquals((Long) (-24L), bounds.getLowerBound());

    assertEquals((Long) (-24L), bounds.parse("-24"));
    assertEquals((Long) (0L), bounds.parse("0"));
    assertEquals((Long) 35L, bounds.parse("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.parse("-25");
  }

  @Test
  public void testRangeUpperBounds() {
    final LongRangeType bounds = new LongRangeType(-24, 35);
    assertEquals((Long) 35L, bounds.getUpperBound());

    assertEquals((Long) (-24L), bounds.parse("-24"));
    assertEquals((Long) (0L), bounds.parse("0"));
    assertEquals((Long) 35L, bounds.parse("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.parse("36");
  }

  @Test
  public void testSingleLowerBounds() {
    final LongRangeType bounds = new LongRangeType(35, 35);
    assertEquals((Long) 35L, bounds.parse("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.parse("34");
  }

  @Test
  public void testSingleUpperBounds() {
    final LongRangeType bounds = new LongRangeType(35, 35);
    assertEquals((Long) 35L, bounds.parse("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.parse("36");
  }

  @Test
  public void testWithWhitespace() {
    final LongRangeType bounds = new LongRangeType(-24, 35);
    assertEquals((Long) (-24L), bounds.parse(" -24 "));
    assertEquals((Long) (0L), bounds.parse(" \t 0 \t "));
    assertEquals((Long) (0L), bounds.parse(" \n 0 \n "));
    assertEquals((Long) 35L, bounds.parse(" \n \t \n35\n \n \t"));
  }

  @Test
  public void testBadFormat() {
    final LongRangeType bounds = new LongRangeType(-24, 35);
    exception.expect(IllegalArgumentException.class);
    bounds.parse("-123abc");
  }

  @Test
  public void testConstructorBadOrder() {
    exception.expect(IllegalArgumentException.class);
    new LongRangeType(36, 35);
  }

  @Test
  public void testRadix() {
    final LongRangeType bounds = new LongRangeType(0, 0);
    assertEquals(10, bounds.getRadix());
  }

}
