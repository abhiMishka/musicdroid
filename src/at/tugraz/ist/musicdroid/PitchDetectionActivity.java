package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.puredata.android.service.PdService;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.IoUtils;
import org.puredata.core.utils.PdDispatcher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

public class PitchDetectionActivity extends Activity {
	private final static String Appname = "Pitchdetection";

	private File dir;
	private PdService pdService = null;
	private String path;
	private int instrument = 0;
	
	ArrayList<Integer> values;
	
	private final ServiceConnection pdConnection = new ServiceConnection() {

    	public void onServiceConnected(ComponentName name, IBinder service) {
    		pdService = ((PdService.PdBinder)service).getService();
    		try {
    			initPd();
    			loadPatch();
    		} catch (IOException e) {
    			Log.e(Appname, e.toString());
    			finish();
    		} 
    	}
    
    
    public void onServiceDisconnected(ComponentName name) {    	
        }
    };
    
    /* We'll use this to catch print statements from Pd
    when the user has a [print] object */
 private final PdDispatcher myDispatcher = new PdUiDispatcher() {
   @Override
   public void print(String s) {
     Log.i("Pd print", s);
   }
 };
    

/* We'll use this to listen out for messages from Pd.
   Later we'll hook this up to a named receiver. */
private final PdListener myListener = new PdListener() {	
  public void receiveMessage(String source, String symbol, Object... args) {
    Log.i("receiveMessage symbol:", symbol);
    for (Object arg: args) {
      Log.i("receiveMessage atom:", arg.toString());
    }
  }

  /* What to do when we receive a list from Pd. In this example
     we're collecting the list from Pd and outputting each atom */
  public void receiveList(String source, Object... args) {
    for (Object arg: args) {
      Log.i("receiveList atom:", arg.toString());
    }
  }

  /* When we receive a symbol from Pd */
  public void receiveSymbol(String source, String symbol) {
    Log.i("receiveSymbol", symbol);

  }
  /* When we receive a float from Pd */
  public void receiveFloat(String source, float x) {
    Log.i("receiveFloat", ((Float)x).toString());
    
    if(x < 0)
    {
    	printResults();
    	return;
    }
    
    values.add( (int)x); 
    
  }
  /* When we receive a bang from Pd */
  public void receiveBang(String source) {
    Log.i("receiveBang", "bang!");
   
  }
};
    
	
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pitchdetection);
        values = new ArrayList<Integer>();
        Bundle b = getIntent().getExtras();
        path = b.getString("path");


        bindService(new Intent(this, PdService.class),pdConnection,BIND_AUTO_CREATE);        
    }
    
    public void onRunClick(View view) {
    	File inputFile = new File(path);
    	String input_wav = inputFile.getAbsolutePath();		
    	
    	if(!inputFile.exists())
    	{
    		Toast.makeText(this, "Sound-file not found!", Toast.LENGTH_LONG);
    		Log.i("Pitchdet","Sound-file not found!");
    		return;
    	}
    	
    	values.clear();
    	TextView t = (TextView)findViewById(R.id.outTextView);
	    t.setText("");
    	
    	PdBase.sendSymbol("input-wav", input_wav);
    }
    
    private void printResults()
    {
    	String out = "";
	    MidiTable midi = new MidiTable();
	    for(int i=0;i< values.size();i++)
	    {
	    	out += values.get(i).toString() +
	    			" -> " +
	    			midi.midiToName(values.get(i))	+ 
	    			"\n";
	    }
	    
	    TextView t = (TextView)findViewById(R.id.outTextView);
	    t.setText(out);
    }
    
    public void onResultClick(View view) {   
	    printResults();
    	
    }
    
    public void onMidiClick(View view) {   

    	instrument = 0;
    	registerForContextMenu(view); 
        openContextMenu(view);
        unregisterForContextMenu(view);

    	
    	MidiFile mf = new MidiFile();  
    	
    	if(values.size() <= 0) return;
    	if(instrument <= 0) return;
    	try
    	{
	    	mf.progChange(instrument);  //select instrument
	    	for(int i=0;i< values.size();i++)
		    {
	    		mf.noteOnOffNow(MidiFile.QUAVER, values.get(i), 127);
		    }
		    File f = new File(path);
		    String filename = f.getParentFile() + File.separator + "test.mid";
		    mf.writeToFile(filename);
		    
		    f = new File(filename);
		    if(!f.exists()) throw new Exception("Midi file could not be created!");
		    
		    playfile();
    	}
    	catch (Exception e) {
			Log.e("Midi", e.getMessage());
		}    	
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.context_menu_instruments, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.instrument_piano:
            	instrument = 1;
                return true;
            case R.id.instrument_guitar:
            	instrument = 25;
                return true;
            case R.id.instrument_flute:
            	instrument = 74;
                return true;
            case R.id.instrument_accordion:
            	instrument = 22;
                return true;
            case R.id.instrument_sax:
            	instrument = 65;
                return true;
            case R.id.instrument_trumpet:
            	instrument = 57;
                return true;
            case R.id.instrument_xylophone:
            	instrument = 14;
                return true;
            default:
            	instrument = 0;
                return super.onContextItemSelected(item);
        }
    }
    
    public void playfile() {
    	File f = new File(path);
	    String filename = f.getParentFile() + File.separator + "test.mid";
	    File f2 = new File(filename);
	    
	    if(!f2.exists()) return;
	    
    	Uri myUri = Uri.fromFile(f2);
    	MediaPlayer mediaPlayer = new MediaPlayer();
    	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    	try {
    		mediaPlayer.setDataSource(getApplicationContext(), myUri);
    	} catch (IllegalArgumentException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (SecurityException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IllegalStateException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	try {
    		mediaPlayer.prepare();
    	} catch (IllegalStateException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	mediaPlayer.start();	
    }

    private void loadPatch() throws IOException {
    	Log.e("Pitchdet", "test");
		dir = getFilesDir();
		IoUtils.extractZipResource(getResources().openRawResource(R.raw.pitchdet),
				dir, true);
		File patchFile = new File(dir, "pitchdet.pd");
		PdBase.openPatch(patchFile.getAbsolutePath());	
    }
    
    
    
    private void initPd() throws IOException {
		String name = getResources().getString(R.string.app_name);
			
		pdService.initAudio(-1, 0, -1, -1);
		pdService.startAudio();		
		//.startAudio(new Intent(this, PitchDetectionActivity.class), 
		//	             R.drawable.musicdroid_launcher, name, "Return to " 
	    //                                                      + name + ".");
		    	
    	/* here is where we bind the print statement catcher defined below */
    	  PdBase.setReceiver(myDispatcher);
    	  /* here we are adding the listener for various messages
    	     from Pd sent to "GUI", i.e., anything that goes into the object
    	     [s GUI] will send to the listener defined below */
    	  
    	  myDispatcher.addListener("pitch-midi", myListener);
	}
    
    
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(pdConnection);
	}
    	
    	
}

