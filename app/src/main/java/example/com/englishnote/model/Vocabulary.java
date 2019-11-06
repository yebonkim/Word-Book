package example.com.englishnote.model;

/**
 * Created by yebonkim on 2018. 3. 20..
 */

public class Vocabulary {
    private int mId;
    private String mEnglish;
    private String mMeans;
    private long mTimestamp;

    public Vocabulary(String english, String means, long timestamp) {
        this.mEnglish = english;
        this.mMeans = means;
        this.mTimestamp = timestamp;
    }

    public Vocabulary() {

    }

    public String getEnglish() {
        return mEnglish;
    }

    public void setEnglish(String english) {
        this.mEnglish = english;
    }

    public String getMeans() {
        return mMeans;
    }

    public void setMeans(String means) {
        this.mMeans = means;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }
}
