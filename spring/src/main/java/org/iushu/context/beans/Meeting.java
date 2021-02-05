package org.iushu.context.beans;

import org.springframework.context.SmartLifecycle;

import java.util.List;

/**
 * @author iuShu
 * @since 2/3/21
 */
public class Meeting implements SmartLifecycle {

    private String title;
    private List<Senator> participants;
    private long start;
    private long duration;

    @Override
    public void start() {
        System.out.println(String.format("[Meeting-%s] start with participants: %s", title, participants));
    }

    @Override
    public void stop() {
        System.out.println(String.format("[Meeting-%s] stop", title));
    }

    @Override
    public boolean isRunning() {
        long present = System.currentTimeMillis();
        boolean isRunning = present >= start && present - duration <= start;
        System.out.println(String.format("[Meeting-%s] is %s", title, isRunning ? "running" : "ended"));
        return isRunning;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    /**
     * NOTE: follow the default method implementation
     * @see org.springframework.context.SmartLifecycle#stop(Runnable)
     */
    @Override
    public void stop(Runnable callback) {
        System.out.println(String.format("[Meeting-%s] stop with callback: %s", title, callback));
        callback.run();     // SmartLifecycle needs to following the default method implementation.
    }

    @Override
    public int getPhase() {
        return 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Senator> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Senator> participants) {
        this.participants = participants;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "title='" + title + '\'' +
                ", participants=" + participants +
                ", start=" + start +
                ", duration=" + duration +
                '}';
    }
}
