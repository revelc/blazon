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

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RangeTypeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testRangeLowerBounds() {
    final RangeType bounds = new RangeType(-24, 35);
    assertEquals((Long) (-24L), bounds.process("-24"));
    assertEquals((Long) (0L), bounds.process("0"));
    assertEquals((Long) 35L, bounds.process("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.process("-25");
  }

  @Test
  public void testRangeUpperBounds() {
    final RangeType bounds = new RangeType(-24, 35);
    assertEquals((Long) (-24L), bounds.process("-24"));
    assertEquals((Long) (0L), bounds.process("0"));
    assertEquals((Long) 35L, bounds.process("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.process("36");
  }

  @Test
  public void testSingleLowerBounds() {
    final RangeType bounds = new RangeType(35, 35);
    assertEquals((Long) 35L, bounds.process("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.process("34");
  }

  @Test
  public void testSingleUpperBounds() {
    final RangeType bounds = new RangeType(35, 35);
    assertEquals((Long) 35L, bounds.process("35"));
    exception.expect(IllegalArgumentException.class);
    bounds.process("36");
  }

  @Test
  public void testWithWhitespace() {
    final RangeType bounds = new RangeType(-24, 35);
    assertEquals((Long) (-24L), bounds.process(" -24 "));
    assertEquals((Long) (0L), bounds.process(" \t 0 \t "));
    assertEquals((Long) (0L), bounds.process(" \n 0 \n "));
    assertEquals((Long) 35L, bounds.process(" \n \t \n35\n \n \t"));
  }

  @Test
  public void testBadFormat() {
    final RangeType bounds = new RangeType(-24, 35);
    exception.expect(IllegalArgumentException.class);
    bounds.process("-123abc");
  }

  @Test
  public void testConstructorBadOrder() {
    exception.expect(IllegalArgumentException.class);
    new RangeType(36, 35);
  }

}
