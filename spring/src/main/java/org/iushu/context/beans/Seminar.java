package org.iushu.context.beans;

import org.springframework.context.Lifecycle;

import java.util.List;

/**
 * @author iuShu
 * @since 2/3/21
 */
public class Seminar implements Lifecycle {

    private String title;
    private List<Senator> participants;
    private long start;
    private long duration;

    @Override
    public void start() {
        System.out.println(String.format("[Seminar-%s] start with participants: %s", title, participants));
    }

    @Override
    public void stop() {
        System.out.println(String.format("[Seminar-%s] stop with participants: %s", title, participants));
    }

    @Override
    public boolean isRunning() {
        long present = System.currentTimeMillis();
        boolean isRunning = present >= start && present - duration <= start;
        System.out.println(String.format("[Seminar-%s] is %s", title, isRunning ? "running" : "ended"));
        return isRunning;
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
        return "Seminar{" +
                "title='" + title + '\'' +
                ", participants=" + participants +
                ", start=" + start +
                ", duration=" + duration +
                '}';
    }
}
