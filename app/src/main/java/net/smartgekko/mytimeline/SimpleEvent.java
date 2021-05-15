package net.smartgekko.mytimeline;

public class SimpleEvent extends Event implements Timeable{
    private int startTime;
    private int endTime;
    public SimpleEvent(int start, int end){
        super(start,end);
        if(end>start){
            this.startTime=start;
            this.endTime=end;
        }
    }

    @Override
    public int getStartTime() {
        return this.startTime;
    }

    @Override
    public int getDuration() {
        return this.endTime-this.startTime;
    }

    @Override
    public int getStartTimeLine() {
        return this.startTime*2;
    }

    @Override
    public int getDurationLine() {
        return this.endTime*2-this.startTime*2;
    }

    @Override
    public void setStartTime(int startTime) {
        this.startTime=startTime;
    }

    @Override
    public void setEndTime(int endTime) {
        if(endTime>this.startTime){
            this.endTime=endTime;
        }
    }
}
