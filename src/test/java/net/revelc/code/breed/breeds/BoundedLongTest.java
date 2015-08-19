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

package net.revelc.code.breed.breeds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BoundedLongTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private static Long tester(final BoundedLong bounds, final String input) {
    return bounds.converter().apply(input);
  }

  @Test
  public void test() {
    BoundedLong bounds = new BoundedLong(-24, 35);
    assertNull(tester(bounds, "-abc"));
    assertNull(tester(bounds, "abc"));
    assertNull(tester(bounds, "-25"));
    assertEquals((Long) (-24L), tester(bounds, "-24"));
    assertEquals((Long) 35L, tester(bounds, "35"));
    assertNull(tester(bounds, "36"));
  }

  @Test
  public void testConstructor() {
    BoundedLong bounds = new BoundedLong(35, 35);
    assertNull(tester(bounds, "34"));
    assertEquals((Long) 35L, tester(bounds, "35"));
    assertNull(tester(bounds, "36"));
  }

  @Test
  public void testConstructorBadOrder() {
    exception.expect(IllegalArgumentException.class);
    new BoundedLong(36, 35);
  }

  @Test
  public void testConstructorBadOrder2() {
    exception.expect(IllegalArgumentException.class);
    new BoundedLong(34, false, 34, true);
  }
}
