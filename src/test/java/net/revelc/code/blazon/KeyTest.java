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

import net.revelc.code.blazon.sources.MapSource;
import net.revelc.code.blazon.sources.PropertiesSource;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KeyTest {

  private Key<Integer> testKey;
  private Key<Integer> testKeyWithDefault;

  /**
   * Create a test key for use in tests.
   */
  @Before
  public void setup() {
    testKey = new Key<>("my.test.key", new Type<Integer>() {
      @Override
      protected Integer convert(final String raw) throws RuntimeException {
        return Integer.parseInt(raw);
      }
    });
    testKeyWithDefault = new Key<>("my.test.key.with.default", new Type<Integer>() {
      @Override
      protected Integer convert(final String raw) throws RuntimeException {
        return Integer.parseInt(raw);
      }
    }, 42);
  }

  @Test
  public void testPropertiesSource() {
    Properties props = new Properties();
    props.setProperty("my.test.key", "23");
    PropertiesSource source = new PropertiesSource(props);
    assertEquals((Integer) 23, testKey.getValue(source));
    assertEquals((Integer) 42, testKeyWithDefault.getValue(source));
  }

  @Test
  public void testMapSource() {
    Map<String, String> map = new HashMap<>();
    map.put("my.test.key", "23");
    MapSource source = new MapSource(map);
    assertEquals((Integer) 23, testKey.getValue(source));
    assertEquals((Integer) 42, testKeyWithDefault.getValue(source));
  }

}
