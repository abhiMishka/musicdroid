/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.catrobat.musicdroid.note;

import junit.framework.TestCase;

public class NoteNameTest extends TestCase {

	public void testMidi() {
		assertEquals(36, NoteName.C1.getMidi());
		assertEquals(48, NoteName.C2.getMidi());
		assertEquals(60, NoteName.C3.getMidi());
		assertEquals(72, NoteName.C4.getMidi());
		assertEquals(84, NoteName.C5.getMidi());
	}

	public void testNext1() {
		NoteName a5s = NoteName.A5S;
		NoteName b5 = NoteName.B5;

		assertEquals(b5, a5s.next());
		assertEquals(b5, b5.next());
	}

	public void testNext2() {
		NoteName b5 = NoteName.B5;

		assertEquals(b5, b5.next());
	}

	public void testPrevious1() {
		NoteName c1 = NoteName.C1;
		NoteName c1s = NoteName.C1S;

		assertEquals(c1, c1.previous());
		assertEquals(c1, c1s.previous());
	}

	public void testPrevious2() {
		NoteName c1 = NoteName.C1;

		assertEquals(c1, c1.previous());
	}

	public void testCalculateDistance1() {
		NoteName c1 = NoteName.C1S;
		NoteName c1s = NoteName.C1;

		assertEquals(-1, NoteName.calculateDistance(c1, c1s));
	}

	public void testCalculateDistance2() {
		NoteName c1 = NoteName.C1S;
		NoteName c1s = NoteName.C1;

		assertEquals(1, NoteName.calculateDistance(c1s, c1));
	}

	public void testCalculateDistance3() {
		NoteName c1 = NoteName.C1;

		assertEquals(0, NoteName.calculateDistance(c1, c1));
	}

	public void testIsSigned1() {
		NoteName c1 = NoteName.C1;

		assertFalse(c1.isSigned());
	}

	public void testIsSigned2() {
		NoteName c1s = NoteName.C1S;

		assertTrue(c1s.isSigned());
	}

	public void testGetNoteNameFromMidiValue1() {
		NoteName expectedNoteName = NoteName.C1;
		int midiValue = expectedNoteName.getMidi();

		NoteName actualNoteName = NoteName.getNoteNameFromMidiValue(midiValue);

		assertEquals(actualNoteName, expectedNoteName);
	}

	public void testGetNoteNameFromMidiValue2() {
		NoteName expectedNoteName = NoteName.B5;
		int midiValue = expectedNoteName.getMidi();

		NoteName actualNoteName = NoteName.getNoteNameFromMidiValue(midiValue);

		assertEquals(actualNoteName, expectedNoteName);
	}

	public void testGetNoteNameFromMidiValue3() {
		NoteName expectedNoteName = NoteName.C3;
		int midiValue = expectedNoteName.getMidi();

		NoteName actualNoteName = NoteName.getNoteNameFromMidiValue(midiValue);

		assertEquals(actualNoteName, expectedNoteName);
	}

	public void testCalculateDistanceInHalfNotelineDistances1() {
		NoteName n1 = NoteName.D1;
		NoteName n2 = NoteName.C1S;

		assertEquals(NoteName.calculateDistanceInHalfNotelineDistances(n1, n2), -1);
	}

	public void testCalculateDistanceInHalfNotelineDistances2() {
		NoteName n1 = NoteName.C1;
		NoteName n2 = NoteName.C1S;

		assertEquals(NoteName.calculateDistanceInHalfNotelineDistances(n1, n2), 0);
	}

	public void testCalculateDistanceInHalfNotelineDistances3() {
		NoteName n2 = NoteName.B3;
		NoteName n1 = NoteName.D3;

		assertEquals(NoteName.calculateDistanceInHalfNotelineDistances(n1, n2), 5);
	}
}
