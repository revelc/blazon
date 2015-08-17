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

  @Test
  public void test() {
    BoundedLong bounds = new BoundedLong(-24, 35);
    assertNull(bounds.apply("-abc"));
    assertNull(bounds.apply("abc"));
    assertNull(bounds.apply("-25"));
    assertEquals((Long) (-24L), bounds.apply("-24"));
    assertEquals((Long) 35L, bounds.apply("35"));
    assertNull(bounds.apply("36"));
  }

  @Test
  public void testConstructor() {
    BoundedLong bounds = new BoundedLong(35, 35);
    assertNull(bounds.apply("34"));
    assertEquals((Long) 35L, bounds.apply("35"));
    assertNull(bounds.apply("36"));
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
