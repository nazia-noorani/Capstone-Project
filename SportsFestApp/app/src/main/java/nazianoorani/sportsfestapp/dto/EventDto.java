package nazianoorani.sportsfestapp.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nazianoorani on 19/05/16.
 */
public class EventDto implements Parcelable {
    String event;
    String eventImageURL;
    int eventNo;
    int id;


    public EventDto(){
    }
    protected EventDto(Parcel in) {
        event = in.readString();
        eventImageURL = in.readString();
        eventNo = in.readInt();
        id = in.readInt();
    }

    public static final Creator<EventDto> CREATOR = new Creator<EventDto>() {
        @Override
        public EventDto createFromParcel(Parcel in) {
            return new EventDto(in);
        }

        @Override
        public EventDto[] newArray(int size) {
            return new EventDto[size];
        }
    };

    public int getEventNo() {
        return eventNo;
    }

    public void setEventNo(int eventNo) {
        this.eventNo = eventNo;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventImageURL() {
        return eventImageURL;
    }

    public void setEventImageURL(String eventImageURL) {
        this.eventImageURL = eventImageURL;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(event);
        dest.writeString(eventImageURL);
        dest.writeInt(eventNo);
        dest.writeInt(id);
    }
}
