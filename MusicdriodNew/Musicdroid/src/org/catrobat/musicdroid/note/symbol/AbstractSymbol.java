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
package org.catrobat.musicdroid.note.symbol;

import android.content.Context;

import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;

public abstract class AbstractSymbol {

	protected NoteLength[] noteLengths;

	public AbstractSymbol(NoteLength[] noteLengths) {
		this.noteLengths = noteLengths;
	}

	public NoteLength[] getNoteLengths() {
		return noteLengths;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof AbstractSymbol)) {
			return false;
		}

		AbstractSymbol abstractSymbol = (AbstractSymbol) obj;
		NoteLength[] otherNoteLengths = abstractSymbol.getNoteLengths();

		if (otherNoteLengths.length != noteLengths.length) {
			return false;
		}

		for (int i = 0; i < noteLengths.length; i++) {
			if (noteLengths[i] != otherNoteLengths[i]) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "[AbstractSymbol] duration= " + NoteLength.getTickDurationFromNoteLengths(noteLengths);
	}

	public abstract void draw(NoteSheetCanvas noteSheetCanvas, Key key, Context context);
}
