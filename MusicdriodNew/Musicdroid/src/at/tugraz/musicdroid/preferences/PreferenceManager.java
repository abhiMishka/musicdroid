package at.tugraz.musicdroid.preferences;

import java.util.HashMap;

import android.util.Log;

public class PreferenceManager {
	public static final int SOUNDTRACK_DEFAULT_LENGTH_DEFAULT_VALUE = 45;
	public static final String SOUNDTRACK_DEFAULT_LENGTH_KEY = "soundtrack_length_default";
	public static final int SOUNDTRACK_LENGTH_DEFAULT_VALUE = 45;
	public static final String SOUNDTRACK_LENGTH_KEY = "soundtrack_length";
	public static final int METRONOM_VISUALIZATION_DEFAULT_VALUE = 2;
	public static final String METRONOM_VISUALIZATION_KEY = "metronom_visualization";
	public static final int METRONOM_BPM_DEFAULT_VALUE = 172;
	public static final String METRONOM_BPM_KEY = "metronom_bpm";
	public static final int PLAY_PLAYBACK_DEFAULT_VALUE = 1;
	public static final String PLAY_PLAYBACK_KEY = "play_playback";	
	public static final int ADD_AT_CURRENT_POSITION_DEFAULT_VALUE = 0;
	public static final String ADD_AT_CURRENT_POSITION_KEY = "add_at_current_position";
	
	private HashMap<String, Integer> preferences = null;
	private static PreferenceManager instance = null;
	
	private PreferenceManager()
	{
		preferences = new HashMap<String, Integer>();
		initializeDefaultPreferences();
	}
	
	public static PreferenceManager getInstance()
	{
		if(instance == null)
		{
			instance = new PreferenceManager(); 
			return instance;
		}
		else
		{
			return instance;
		}
	}
	
	private void initializeDefaultPreferences()
	{
		preferences.put(SOUNDTRACK_LENGTH_KEY, SOUNDTRACK_LENGTH_DEFAULT_VALUE);
		preferences.put(SOUNDTRACK_DEFAULT_LENGTH_KEY, SOUNDTRACK_DEFAULT_LENGTH_DEFAULT_VALUE);
		preferences.put(METRONOM_VISUALIZATION_KEY, METRONOM_VISUALIZATION_DEFAULT_VALUE);
		preferences.put(METRONOM_BPM_KEY, METRONOM_BPM_DEFAULT_VALUE);
		preferences.put(PLAY_PLAYBACK_KEY, PLAY_PLAYBACK_DEFAULT_VALUE);
		preferences.put(ADD_AT_CURRENT_POSITION_KEY, ADD_AT_CURRENT_POSITION_DEFAULT_VALUE);
	}
	
	public void setPreference(String key, int value)
	{
		preferences.put(key, value);
	}
	
	public Integer getPreference(String key)
	{
		return preferences.get(key);
	}
	
}