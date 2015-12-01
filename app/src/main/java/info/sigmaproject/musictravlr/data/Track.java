package info.sigmaproject.musictravlr.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
    private Boolean streamable;
    private String title;
    @JsonProperty("stream_url")
    private String streamUrl;
    private String username;
    private Integer id;
//    private Map<String, Object> otherProperties = new HashMap<>();

    public Track() {
    }

    @JsonProperty("user")
    public void setUser(Map<String, Object> user) {
        username = (String) user.get("username");
    }

//    @JsonAnySetter
//    public void handleUnknown(String key, Object value) {
//        otherProperties.put(key, value);
//    }

    public Boolean getStreamable() {
        return streamable;
    }

    public void setStreamable(Boolean streamable) {
        this.streamable = streamable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username + " - " + title;
    }
}
