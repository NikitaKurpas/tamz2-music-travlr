package info.sigmaproject.musictravlr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import info.sigmaproject.musictravlr.data.Track;

public class SelectTrackActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mSearchTrackList;
    private SelectTrackActivity self;
    private Track selectedTrack;

    public static final String USERNAME = "username";
    public static final String TITLE = "title";
    public static final String STREAM_URL = "streamUrl";
    public static final String TRACK_ID = "id";
    public static final String TRACK_STREAMABLE = "streamable";
    public static final String ACTION_CANCELED = "actionCanceled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_track);

        mSearchTrackList = (ListView) findViewById(R.id.search_tracks_list);
        mSearchTrackList.setOnItemClickListener(this);
        self = this;

        // TODO: add search bar
        new SearchSongsTask().execute();
    }

    @Override
    public void finish() {
        Intent result = new Intent();
        result.putExtras(getIntent().getExtras());
        if (selectedTrack == null) {
            result.putExtra(ACTION_CANCELED, true);
        } else {
            result.putExtra(USERNAME, selectedTrack.getUsername());
            result.putExtra(TITLE, selectedTrack.getTitle());
            result.putExtra(STREAM_URL, selectedTrack.getStreamUrl());
            result.putExtra(TRACK_ID, selectedTrack.getId());
            result.putExtra(TRACK_STREAMABLE, selectedTrack.getStreamable());
        }
        setResult(RESULT_OK, result);
        super.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Track temp = (Track) parent.getItemAtPosition(position);
        if (!temp.getStreamable()) {
            Toast.makeText(this, "Sorry, this track is not streamable, so you cannot select it. Try selecting a different track :(", Toast.LENGTH_LONG).show();
            return;
        }
        selectedTrack = temp;
        finish();
    }

    class SearchSongsTask extends AsyncTask<String, Void, Track[]> {

        @Override
        protected Track[] doInBackground(String... params) {
            String searchUrl = "http://api.soundcloud.com/tracks?client_id={clientId}&q={query}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            return restTemplate.getForObject(searchUrl, Track[].class, MapsActivity.SC_CLIENT_ID, "nanobii");
        }

        @Override
        protected void onPostExecute(Track[] tracks) {
            for (Track track : tracks) {
                Log.d("TRACK", track.toString());
            }
            ArrayAdapter<Track> adapter = new ArrayAdapter<>(self, R.layout.search_tracks_list_item, R.id.search_tracks_item_text, tracks);
            mSearchTrackList.setAdapter(adapter);
        }
    }
}
