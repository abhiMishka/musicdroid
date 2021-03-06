/**
 *  Catroid: An on-device visual programming system for Android devices
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
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.note;

import junit.framework.TestCase;

public class NoteLengthTest extends TestCase {

	private static final float SIXTEENTH_DURATION = 1 / 4f;
	private static final float EIGHT_DURATION = 1 / 2f;
	private static final float QUARTER_DURATION = 1f;
	private static final float HALF_DURATION = 2f;
	private static final float WHOLE_DURATION = 4f;

	public void testCalculateDuration1() {
		long expected = Math.round(NoteLength.DEFAULT_DURATION * SIXTEENTH_DURATION);
		long actual = NoteLength.SIXTEENTH.getTickDuration();

		assertEquals(expected, actual);
	}

	public void testCalculateDuration2() {
		long expected = Math.round(NoteLength.DEFAULT_DURATION * EIGHT_DURATION);
		long actual = NoteLength.EIGHT.getTickDuration();

		assertEquals(expected, actual);
	}

	public void testCalculateDuration3() {
		long expected = Math.round(NoteLength.DEFAULT_DURATION * QUARTER_DURATION);
		long actual = NoteLength.QUARTER.getTickDuration();

		assertEquals(expected, actual);
	}

	public void testCalculateDuration4() {
		long expected = Math.round(NoteLength.DEFAULT_DURATION * HALF_DURATION);
		long actual = NoteLength.HALF.getTickDuration();

		assertEquals(expected, actual);
	}

	public void testCalculateDuration5() {
		long expected = Math.round(NoteLength.DEFAULT_DURATION * WHOLE_DURATION);
		long actual = NoteLength.WHOLE.getTickDuration();

		assertEquals(expected, actual);
	}

	public void testGetNoteLengthFromDuration1() {
		NoteLength[] expectedNoteLengths = { NoteLength.QUARTER, NoteLength.EIGHT, NoteLength.SIXTEENTH };

		assertNoteLengths(expectedNoteLengths);
	}

	public void testGetNoteLengthFromDuration2() {
		NoteLength[] expectedNoteLengths = { NoteLength.WHOLE, NoteLength.WHOLE };

		assertNoteLengths(expectedNoteLengths);
	}

	private void assertNoteLengths(NoteLength[] expectedNoteLengths) {
		long duration = NoteLength.getTickDurationFromNoteLengths(expectedNoteLengths);

		NoteLength[] actualNoteLengths = NoteLength.getNoteLengthsFromTickDuration(duration);

		assertEquals(expectedNoteLengths.length, actualNoteLengths.length);

		for (int i = 0; i < expectedNoteLengths.length; i++) {
			assertEquals(expectedNoteLengths[i], actualNoteLengths[i]);
		}
	}
}
