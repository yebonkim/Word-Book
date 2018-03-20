package example.com.englishnote.model;

import java.util.Date;

/**
 * Created by yebonkim on 2018. 3. 20..
 */

public class Vocabulary {
    int id;
    String english;
    String means;
    long timestamp;

    public Vocabulary(String english, String means, long timestamp) {
        this.english = english;
        this.means = means;
        this.timestamp = timestamp;
    }

    public Vocabulary() {

    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
