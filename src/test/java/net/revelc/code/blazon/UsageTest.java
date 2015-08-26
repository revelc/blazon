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

import net.revelc.code.blazon.sources.PropertiesSource;
import net.revelc.code.blazon.types.OneOf;
import net.revelc.code.blazon.types.Port;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class UsageTest {

  private static enum Choices {
    ONE, TWO, THREE
  }

  private static class ExampleKeys {
    public static final Key<Integer> SERVER1_PORT = new Key<>("server1.port", new Port());
    public static final Key<Integer> SERVER2_PORT = new Key<>("server2.port", new Port());
    public static final Key<Choices> CHOICE =
        new Key<>("choice", new OneOf<Choices>(Choices.class));
  }

  private PropertiesSource source;

  /**
   * Create test configuration.
   */
  @Before
  public void setup() {
    final Properties props = new Properties();
    props.setProperty("server1.port", "23");
    props.setProperty("server2.port", "42");
    props.setProperty("choice", "TWO");
    source = new PropertiesSource(props);
  }

  @Test
  public void test() {
    assertEquals((Integer) 23, ExampleKeys.SERVER1_PORT.getValue(source));
    assertEquals((Integer) 42, ExampleKeys.SERVER2_PORT.getValue(source));
    assertEquals(Choices.TWO, ExampleKeys.CHOICE.getValue(source));
  }

}
