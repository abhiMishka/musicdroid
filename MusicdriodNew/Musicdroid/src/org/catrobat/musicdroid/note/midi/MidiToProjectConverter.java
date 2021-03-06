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
package org.catrobat.musicdroid.note.midi;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;

import org.catrobat.musicdroid.note.Instrument;
import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Track;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MidiToProjectConverter {

	private static final Instrument DEFAULT_INSTRUMENT = Instrument.ACOUSTIC_GRAND_PIANO;

	private int beatsPerMinute;

	public MidiToProjectConverter() {
		beatsPerMinute = Project.DEFAULT_BEATS_PER_MINUTE;
	}

	public Project readFileAndConvertMidi(File file) throws FileNotFoundException, IOException, MidiException {
		MidiFile midi = new MidiFile(file);

		try {
			return convertMidi(midi, file.getName());
		} catch (Exception e) {
			throw new MidiException("Unsupported MIDI format!");
		}
	}

	protected Project convertMidi(MidiFile midi, String name) throws MidiException {
		List<Track> tracks = new ArrayList<Track>();

		for (MidiTrack midiTrack : midi.getTracks()) {
			tracks.add(createTrack(midiTrack));
		}

		Project project = new Project(name, beatsPerMinute);

		for (Track track : tracks) {
			if (track.size() > 0) {
				project.addTrack(track);
			}
		}

		return project;
	}

	private Track createTrack(MidiTrack midiTrack) throws MidiException {
		Instrument instrument = getInstrumentFromMidiTrack(midiTrack);
		Track track = new Track(instrument);
		Iterator<MidiEvent> it = midiTrack.getEvents().iterator();

		while (it.hasNext()) {
			MidiEvent midiEvent = it.next();

			if (midiEvent instanceof NoteOn) {
				NoteOn noteOn = (NoteOn) midiEvent;
				long tick = noteOn.getTick();
				NoteName noteName = NoteName.getNoteNameFromMidiValue(noteOn.getNoteValue());
				NoteEvent noteEvent = new NoteEvent(noteName, true);

				track.addNoteEvent(tick, noteEvent);
			} else if (midiEvent instanceof NoteOff) {
				NoteOff noteOff = (NoteOff) midiEvent;
				long tick = noteOff.getTick();
				NoteName noteName = NoteName.getNoteNameFromMidiValue(noteOff.getNoteValue());
				NoteEvent noteEvent = new NoteEvent(noteName, false);

				track.addNoteEvent(tick, noteEvent);
			} else if (midiEvent instanceof Tempo) {
				Tempo tempo = (Tempo) midiEvent;

				beatsPerMinute = (int) tempo.getBpm();
			}
		}

		return track;
	}

	private Instrument getInstrumentFromMidiTrack(MidiTrack midiTrack) {
		Iterator<MidiEvent> it = midiTrack.getEvents().iterator();
		Instrument instrument = DEFAULT_INSTRUMENT;

		while (it.hasNext()) {
			MidiEvent midiEvent = it.next();

			if (midiEvent instanceof ProgramChange) {
				ProgramChange program = (ProgramChange) midiEvent;

				instrument = Instrument.getInstrumentFromProgram(program.getProgramNumber());
				break;
			}
		}

		return instrument;
	}
}
