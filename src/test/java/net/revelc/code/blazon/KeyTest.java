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

package net.revelc.code.blazon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import net.revelc.code.blazon.types.numeric.IntegerType;
import org.junit.Test;

public class KeyTest {

  @Test
  public void testKey() {
    final Key<Integer> k = new Key<>("my.key.first", IntegerType.HEX);
    assertEquals("my.key.first", k.getKey());

    final Key<Integer> k2 = new Key<>("my.key.second", IntegerType.HEX, 42);
    assertEquals("my.key.second", k2.getKey());
  }

  @Test
  public void testType() {
    final Key<Integer> k = new Key<>("my.key", IntegerType.OCT);
    assertEquals(IntegerType.OCT, k.getType());

    final Key<Integer> k2 = new Key<>("my.key", IntegerType.HEX, 42);
    assertEquals(IntegerType.HEX, k2.getType());
  }

  @Test
  public void testDefaultValue() {
    final Key<Integer> k = new Key<>("my.key", IntegerType.HEX);
    assertNull(k.getDefaultValue());

    final Key<Integer> k2 = new Key<>("my.key", IntegerType.HEX, 42);
    assertEquals((Integer) 42, k2.getDefaultValue());
  }

}
