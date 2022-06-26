package tech.fallqvist.event;

import java.awt.*;

public class EventRectangle extends Rectangle {

    private int eventRectDefaultX, eventRectDefaultY;
    private boolean eventDone = false;

    public int getEventRectDefaultX() {
        return eventRectDefaultX;
    }

    public EventRectangle setEventRectDefaultX(int eventRectDefaultX) {
        this.eventRectDefaultX = eventRectDefaultX;
        return this;
    }

    public int getEventRectDefaultY() {
        return eventRectDefaultY;
    }

    public EventRectangle setEventRectDefaultY(int eventRectDefaultY) {
        this.eventRectDefaultY = eventRectDefaultY;
        return this;
    }

    public boolean isEventDone() {
        return eventDone;
    }

    public EventRectangle setEventDone(boolean eventDone) {
        this.eventDone = eventDone;
        return this;
    }
}
