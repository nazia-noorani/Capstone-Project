package nazianoorani.sportsfestapp.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nazianoorani on 17/05/16.
 */
public class ScheduleDto implements Parcelable {

    String event;
    String nameTeamA;
    String nameTeamB;
    String matchDate;
    String matchTime;
    String teamAChestURL;
    String teamBChestURL;
    int id;


    //TODO  getter and setter
    //Also for Lat and longitude


    public ScheduleDto(){}

    protected ScheduleDto(Parcel in) {
        event = in.readString();
        nameTeamA = in.readString();
        nameTeamB = in.readString();
        matchDate = in.readString();
        matchTime = in.readString();
        teamAChestURL = in.readString();
        teamBChestURL = in.readString();
        id = in.readInt();
    }

    public static final Creator<ScheduleDto> CREATOR = new Creator<ScheduleDto>() {
        @Override
        public ScheduleDto createFromParcel(Parcel in) {
            return new ScheduleDto(in);
        }

        @Override
        public ScheduleDto[] newArray(int size) {
            return new ScheduleDto[size];
        }
    };

    public String getTeamAChestURL() {
        return teamAChestURL;
    }

    public void setTeamAChestURL(String teamAChestURL) {
        this.teamAChestURL = teamAChestURL;
    }

    public String getTeamBChestURL() {
        return teamBChestURL;
    }

    public void setTeamBChestURL(String teamBChestURL) {
        this.teamBChestURL = teamBChestURL;
    }

    public String getNameTeamA() {
        return nameTeamA;
    }

    public void setNameTeamA(String nameTeamA) {
        this.nameTeamA = nameTeamA;
    }

    public String getNameTeamB() {
        return nameTeamB;
    }

    public void setNameTeamB(String nameTeamB) {
        this.nameTeamB = nameTeamB;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
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
        dest.writeString(nameTeamA);
        dest.writeString(nameTeamB);
        dest.writeString(matchDate);
        dest.writeString(matchTime);
        dest.writeString(teamAChestURL);
        dest.writeString(teamBChestURL);
        dest.writeInt(id);
    }
}
