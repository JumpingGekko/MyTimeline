package net.smartgekko.mytimeline;

public interface Timeable {
    int getStartTime();
    int getDuration();
    int getStartTimeLine();
    int getDurationLine();
    void setStartTime(int startTime);
    void setEndTime(int duration);

}
