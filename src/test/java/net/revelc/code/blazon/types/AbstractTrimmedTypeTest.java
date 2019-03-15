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
import static org.junit.Assert.assertNull;

import com.google.common.base.Optional;
import net.revelc.code.blazon.Type;
import org.junit.Test;

public class AbstractTrimmedTypeTest {

  private static final Type<String> type = new AbstractTrimmedType<String>() {

    @Override
    protected Optional<String> convert(final String normalized) {
      return Optional.of(normalized);
    }

    @Override
    public String description() {
      return "test type";
    }
  };

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

}
