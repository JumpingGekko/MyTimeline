package net.smartgekko.mytimeline;

import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> eventList=new ArrayList<>();
        public EventList(){
            addEvent(new SimpleEvent(20,140));
            addEvent(new SimpleEvent(200,1000));
            addEvent(new SimpleEvent(300,660));
            addEvent(new SimpleEvent(500,740));
            addEvent(new SimpleEvent(600,1440));
            addEvent(new SimpleEvent(950,1100));
            addEvent(new SimpleEvent(1050,1440));
        }

    public void addEvent(Event event) {
        this.eventList.add(event);
    }
    public ArrayList<Event> getEventList(){
        return this.eventList;
    }
    public int getEventsCount(){
        return eventList.size();
    }
    public Event getEventById(int id){
        return eventList.get(id);
    }
}

