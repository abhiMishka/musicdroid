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
package org.catrobat.musicdroid.recorder;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.RecorderActivity;

public class AudioVisualizer extends Handler {
	private static final int MAX_AMPLITUDE = 32800;
	private Context context = null;
	private View equalizerView = null;
	private ImageView microphoneImageView = null;

	public AudioVisualizer(Context context) {
		this.context = context;
		equalizerView = ((RecorderActivity) context).findViewById(R.id.microphone_equalizer);
		microphoneImageView = (ImageView) ((RecorderActivity) context).findViewById(R.id.microphone);
	}

	@Override
	public void handleMessage(Message msg) {
		Bundle b = msg.getData();
		int amplitude = b.getInt("amplitude");
		int newHeight = microphoneImageView.getHeight() * amplitude / MAX_AMPLITUDE;
		LayoutParams params = (LayoutParams) equalizerView.getLayoutParams();
		params.height = newHeight;
		params.width = microphoneImageView.getWidth();
		equalizerView.setLayoutParams(params);
	}
}
