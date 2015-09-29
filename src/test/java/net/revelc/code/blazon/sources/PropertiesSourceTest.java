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

package net.revelc.code.blazon.sources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import net.revelc.code.blazon.Key;
import net.revelc.code.blazon.types.numeric.IntegerType;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class PropertiesSourceTest {

  private PropertiesSource source;

  /**
   * Create a test key for use in tests.
   */
  @Before
  public void setup() {
    final Properties props = new Properties();
    props.setProperty("my.test.key", "23");
    source = new PropertiesSource(props);
  }

  @Test
  public void testSourceSetup() {
    assertEquals(1, source.getSource().size());
    assertTrue(source.getSource().containsKey("my.test.key"));
    assertFalse(source.getSource().containsKey("my.test.key.non-existent"));
    assertEquals("23", source.getValue("my.test.key"));
    assertNull(source.getValue("my.test.key.non-existent"));
    assertTrue(source.getSource() instanceof Properties);
  }

  @Test
  public void testContainsWithoutDefault() {
    final Key<Integer> k = new Key<>("my.test.key", IntegerType.DEC);
    assertEquals((Integer) 23, k.getValue(source));

    final Key<Integer> k2 = new Key<>("my.test.key.non-existent", IntegerType.DEC);
    assertNull(k2.getValue(source));
  }

  @Test
  public void testContainsWithDefault() {
    final Key<Integer> k = new Key<>("my.test.key.non-existent", IntegerType.DEC, 42);
    assertEquals((Integer) 42, k.getValue(source));
  }

}
