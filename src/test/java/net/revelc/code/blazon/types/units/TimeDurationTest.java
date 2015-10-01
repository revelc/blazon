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

package net.revelc.code.blazon.types.units;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import net.revelc.code.blazon.types.units.TimeDuration.TimeDurationUnit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TimeDurationTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private static final TimeDuration type = TimeDuration.NON_NEGATIVE_SECONDS;

  @Test
  public void testValid() {
    Quantity<Long, TimeDurationUnit> value = type.parse("123ms");
    assertEquals((Long) 123L, value.getMagnitude());
    assertEquals(TimeDurationUnit.ms, value.getUnit());

    value = type.parse("123");
    assertEquals((Long) 123L, value.getMagnitude());
    assertEquals(TimeDurationUnit.s, value.getUnit());

    assertNull(type.parse(""));

    value = type.parse(Long.toString(Long.MAX_VALUE) + "d");
    assertEquals((Long) Long.MAX_VALUE, value.getMagnitude());
    assertEquals(TimeDurationUnit.d, value.getUnit());

    value = type.parse("0ms");
    assertEquals((Long) 0L, value.getMagnitude());
    assertEquals(TimeDurationUnit.ms, value.getUnit());

    // this gets truncated to zero seconds and still passes the constraint check
    value = type.parse("-720  \nms\n");
    assertEquals((Long) (-720L), value.getMagnitude());
    assertEquals(TimeDurationUnit.ms, value.getUnit());
  }

}
