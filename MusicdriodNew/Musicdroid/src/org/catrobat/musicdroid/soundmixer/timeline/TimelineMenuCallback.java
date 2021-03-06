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
package org.catrobat.musicdroid.soundmixer.timeline;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.soundmixer.SoundMixer;

public class TimelineMenuCallback implements ActionMode.Callback {
	MainActivity parent = null;
	Timeline timeline = null;

	public TimelineMenuCallback(MainActivity p, Timeline t) {
		timeline = t;
		parent = p;
	}

	/**
	 * Invoked whenever the action mode is shown. This is invoked immediately
	 * after onCreateActionMode
	 */
	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	/** Called when user exits action mode */
	@Override
	public void onDestroyActionMode(ActionMode mode) {
		SoundMixer.getInstance().enableUnselectedViews();
	}

	/**
	 * This is called when the action mode is created. This is called by
	 * startActionMode()
	 */
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		parent.getMenuInflater().inflate(R.menu.timeline_menu, menu);
		mode.setTitle("Timeline");
		return true;
	}

	/** This is called when an item in the context menu is selected */
	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.timeline_context_add_start_point:
				SoundMixer.getInstance().setStartPoint(timeline.getClickLocation());
				mode.finish();
				break;
			case R.id.timeline_context_add_end_point:
				SoundMixer.getInstance().setEndPoint(timeline.getClickLocation());
				mode.finish();
				break;
		}
		return false;
	}
}
