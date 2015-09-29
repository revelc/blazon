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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.EnumSet;

public class OneOfTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private static enum DistinctChoices {
    ONE, TWO, THREE
  }

  private static enum DifferByCaseChoices {
    ONE, Two, tWo, twO
  }

  @Test
  public void testDistinct() {
    final OneOf<DistinctChoices> type = new OneOf<>(DistinctChoices.class);
    assertEquals(DistinctChoices.ONE, type.parse("ONE"));
    assertEquals(DistinctChoices.TWO, type.parse("TWO"));
    assertEquals(DistinctChoices.THREE, type.parse("THREE"));

    final OneOf<DistinctChoices> type2 = new OneOf<>(DistinctChoices.class, true);
    assertEquals(DistinctChoices.ONE, type2.parse("ONE"));
    assertEquals(DistinctChoices.TWO, type2.parse("TWO"));
    assertEquals(DistinctChoices.THREE, type2.parse("THREE"));
  }

  @Test
  public void testDistinctCaseInsensitive() {
    final OneOf<DistinctChoices> type = new OneOf<>(DistinctChoices.class, false);
    assertEquals(DistinctChoices.ONE, type.parse("ONE"));
    assertEquals(DistinctChoices.TWO, type.parse("TWO"));
    assertEquals(DistinctChoices.THREE, type.parse("THREE"));
    assertEquals(DistinctChoices.ONE, type.parse("one"));
    assertEquals(DistinctChoices.TWO, type.parse("two"));
    assertEquals(DistinctChoices.THREE, type.parse("three"));
  }

  @Test
  public void testBadCaseImplicit() {
    final OneOf<DistinctChoices> type = new OneOf<>(DistinctChoices.class);
    assertEquals(DistinctChoices.THREE, type.parse("THREE"));
    exception.expect(IllegalArgumentException.class);
    type.parse("three");
  }

  @Test
  public void testBadCaseExplicit() {
    final OneOf<DistinctChoices> type = new OneOf<>(DistinctChoices.class, true);
    assertEquals(DistinctChoices.THREE, type.parse("THREE"));
    exception.expect(IllegalArgumentException.class);
    type.parse("three");
  }

  @Test
  public void testBadCasePermitted() {
    final OneOf<DistinctChoices> type = new OneOf<>(DistinctChoices.class, false);
    assertEquals(DistinctChoices.THREE, type.parse("THREE"));
    assertEquals(DistinctChoices.THREE, type.parse("three"));
    assertEquals(DistinctChoices.THREE, type.parse("Three"));
    assertEquals(DistinctChoices.THREE, type.parse("  thrEE "));
  }

  @Test
  public void testDifferByCase() {
    final OneOf<DifferByCaseChoices> type = new OneOf<>(DifferByCaseChoices.class, false);
    assertEquals(DifferByCaseChoices.ONE, type.parse("ONE"));
    assertEquals(DifferByCaseChoices.ONE, type.parse("one"));
    assertEquals(DifferByCaseChoices.ONE, type.parse("One"));
    assertEquals(DifferByCaseChoices.Two, type.parse("Two"));
    assertEquals(DifferByCaseChoices.twO, type.parse("twO"));
    assertEquals(DifferByCaseChoices.tWo, type.parse("tWo"));

    final EnumSet<DifferByCaseChoices> twosOnly =
        EnumSet.of(DifferByCaseChoices.Two, DifferByCaseChoices.twO, DifferByCaseChoices.tWo);
    assertEquals(3, twosOnly.size());
    for (final DifferByCaseChoices two : twosOnly) {
      assertEquals("two", two.name().toLowerCase());
    }

    // make sure "TWO" matches to one of the known enums, and the name of the enum matches "two"
    // with case-insensitive, but not case-sensitive matching
    assertTrue(twosOnly.contains(type.parse("TWO")));
    assertTrue(type.parse("TWO").name().equalsIgnoreCase("TWO"));
    assertFalse(type.parse("TWO").name().equals("TWO"));

    // make sure "two" matches to one of the known enums, and the name of the enum matches "two"
    // with case-insensitive, but not case-sensitive matching
    assertTrue(twosOnly.contains(type.parse("two")));
    assertTrue(type.parse("two").name().equalsIgnoreCase("two"));
    assertFalse(type.parse("two").name().equals("two"));
  }

}
