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

package net.revelc.code.blazon.types.network;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PortTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testAnyValid() {
    final Port type = Port.ANY;
    assertEquals((Integer) 0, type.getLowerBound());
    assertEquals((Integer) 65535, type.getUpperBound());
    assertEquals(10, type.getRadix());

    assertEquals((Integer) 0, type.parse("0"));
    assertEquals((Integer) 1000, type.parse("1000"));
    assertEquals((Integer) 30000, type.parse("30000"));
    assertEquals((Integer) 60000, type.parse("60000"));
    assertEquals((Integer) 65535, type.parse("65535"));
  }

  @Test
  public void testAnyTooLow() {
    final Port type = Port.ANY;
    exception.expect(IllegalArgumentException.class);
    type.parse("-1");
  }

  @Test
  public void testAnyTooHigh() {
    final Port type = Port.ANY;
    exception.expect(IllegalArgumentException.class);
    type.parse("65536");
  }

  @Test
  public void testSystemValid() {
    final Port type = Port.SYSTEM;
    assertEquals((Integer) 0, type.getLowerBound());
    assertEquals((Integer) 1023, type.getUpperBound());
    assertEquals(10, type.getRadix());

    assertEquals((Integer) 0, type.parse("0"));
    assertEquals((Integer) 1000, type.parse("1000"));
    assertEquals((Integer) 1023, type.parse("1023"));
  }

  @Test
  public void testSystemTooLow() {
    final Port type = Port.SYSTEM;
    exception.expect(IllegalArgumentException.class);
    type.parse("-1");
  }

  @Test
  public void testSystemTooHigh() {
    final Port type = Port.SYSTEM;
    exception.expect(IllegalArgumentException.class);
    type.parse("1024");
  }

  @Test
  public void testUserValid() {
    final Port type = Port.USER;
    assertEquals((Integer) 1024, type.getLowerBound());
    assertEquals((Integer) 49151, type.getUpperBound());
    assertEquals(10, type.getRadix());

    assertEquals((Integer) 1024, type.parse("1024"));
    assertEquals((Integer) 30000, type.parse("30000"));
    assertEquals((Integer) 49151, type.parse("49151"));
  }

  @Test
  public void testUserTooLow() {
    final Port type = Port.USER;
    exception.expect(IllegalArgumentException.class);
    type.parse("1023");
  }

  @Test
  public void testUserTooHigh() {
    final Port type = Port.USER;
    exception.expect(IllegalArgumentException.class);
    type.parse("49152");
  }

  @Test
  public void testDynamicValid() {
    final Port type = Port.DYNAMIC;
    assertEquals((Integer) 49152, type.getLowerBound());
    assertEquals((Integer) 65535, type.getUpperBound());
    assertEquals(10, type.getRadix());

    assertEquals((Integer) 49152, type.parse("49152"));
    assertEquals((Integer) 60000, type.parse("60000"));
    assertEquals((Integer) 65535, type.parse("65535"));
  }

  @Test
  public void testDynamicTooLow() {
    final Port type = Port.DYNAMIC;
    exception.expect(IllegalArgumentException.class);
    type.parse("49151");
  }

  @Test
  public void testDynamicTooHigh() {
    final Port type = Port.DYNAMIC;
    exception.expect(IllegalArgumentException.class);
    type.parse("65536");
  }

  @Test
  public void testCustomPortRangeValid() {
    final Port type = new Port(24, 26);
    assertEquals((Integer) 24, type.parse("24"));
    assertEquals((Integer) 25, type.parse("25"));
    assertEquals((Integer) 26, type.parse("26"));
    assertEquals((Integer) 24, type.getLowerBound());
    assertEquals((Integer) 26, type.getUpperBound());
  }

  @Test
  public void testCustomPortRangeBadLower() {
    exception.expect(IllegalArgumentException.class);
    new Port(-1, 26);
  }

  @Test
  public void testCustomPortRangeBadUpper() {
    exception.expect(IllegalArgumentException.class);
    new Port(0, 65536);
  }

  @Test
  public void testDefaultConstructor() {
    final Port type = new Port();
    assertEquals((Integer) 0, type.getLowerBound());
    assertEquals((Integer) 65535, type.getUpperBound());
    assertEquals(10, type.getRadix());
  }

}
