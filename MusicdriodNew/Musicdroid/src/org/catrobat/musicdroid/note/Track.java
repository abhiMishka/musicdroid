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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Track implements Serializable {

	private static final long serialVersionUID = 7483021689872527955L;

	private Instrument instrument;
	private Key key;
	private Tact tact;
	private List<Symbol> symbols;

	public Track() {
		this.symbols = new ArrayList<Symbol>();
		this.instrument = Instrument.ACOUSTIC_GRAND_PIANO;
		this.key = Key.VIOLIN;
		this.tact = new Tact();
	}

	public Track(Instrument instrument, Key key, Tact tact) {
		this.symbols = new ArrayList<Symbol>();
		this.instrument = instrument;
		this.key = key;
		this.tact = tact;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public Key getKey() {
		return key;
	}

	public Tact getTact() {
		return tact;
	}

	public void addSymbol(Symbol symbol) {
		symbols.add(symbol);
	}

	public void removeSymbol(Symbol symbol) {
		symbols.remove(symbol);
	}

	public Symbol getSymbol(int location) {
		return symbols.get(location);
	}

	public int size() {
		return symbols.size();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Track)) {
			return false;
		}

		Track track = (Track) obj;

		if (track.getInstrument() != getInstrument()) {
			return false;
		}

		if ((key.equals(track.getKey())) && (tact.equals(track.getTact()) && (size() == track.size()))) {
			for (int i = 0; i < size(); i++) {
				if (!getSymbol(i).equals(track.getSymbol(i))) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "[Track] instrument= " + instrument + " key=" + key + " symbolCount=" + size();
	}
}
