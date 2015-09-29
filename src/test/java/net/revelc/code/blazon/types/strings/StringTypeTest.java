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

package net.revelc.code.blazon.types.strings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.common.base.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringTypeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private static final StringType type = new StringType();

  @Test
  public void testAbsent() {
    assertNull(type.parse(null));
    assertNull(type.parse(""));
    assertNull(type.parse("   "));
    assertNull(type.parse(" \t \n"));
  }

  @Test
  public void testPresent() {
    assertEquals("42", type.parse("42"));
    assertEquals("42", type.parse(" 42  "));
    assertEquals("42", type.parse(" \t42 \n"));
    assertEquals("4 2", type.parse("      4 2      "));
    assertEquals("4\n2", type.parse(" \t4\n2 \n"));
  }

  @Test
  public void testWithPredicate() {
    final StringType pred = new StringType(new Predicate<String>() {

      @Override
      public boolean apply(final String input) {
        return input.length() >= 4;
      }
    }, "at least length 2");

    assertEquals("blah", pred.parse("   blah    \n"));
    assertEquals("blah2", pred.parse("blah2"));
    exception.expect(IllegalArgumentException.class);
    pred.parse("abc");
  }

}
