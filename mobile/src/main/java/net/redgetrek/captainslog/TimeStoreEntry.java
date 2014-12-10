package net.redgetrek.captainslog;

import java.util.Date;

/**
 * A single entry of timing data
 */
public class TimeStoreEntry {

    private long mId;
    private Date mStarted;
    private Date mStopped;
    private String mDescription;

    public TimeStoreEntry() {
        mId = 0;
        mStarted = new Date();
        mStopped = null;
        mDescription = new String();
    }

    public TimeStoreEntry(Date started, Date stopped, String description) {
        mId = 0;
        mStarted = started;
        mStopped = stopped;
        mDescription = description;
    }

    public TimeStoreEntry(long id, Date started, Date stopped, String description) {
        mId = id;
        mStarted = started;
        mStopped = stopped;
        mDescription = description;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public Date getStarted() {
        return mStarted;
    }

    public Date getStopped() {
        return mStopped;
    }

    public void setStopped(Date stopped) {
        mStopped = stopped;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

}
