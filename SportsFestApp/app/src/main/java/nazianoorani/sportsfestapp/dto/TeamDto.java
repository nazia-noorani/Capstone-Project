package nazianoorani.sportsfestapp.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nazianoorani on 24/04/16.
 */
public class TeamDto  implements Parcelable{
    String name;
    String college;
    String phone;
    String gender;

    public TeamDto(){}

    protected TeamDto(Parcel in) {
        name = in.readString();
        college = in.readString();
        phone = in.readString();
        gender = in.readString();
    }

    public static final Creator<TeamDto> CREATOR = new Creator<TeamDto>() {
        @Override
        public TeamDto createFromParcel(Parcel in) {
            return new TeamDto(in);
        }

        @Override
        public TeamDto[] newArray(int size) {
            return new TeamDto[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(college);
        dest.writeString(phone);
        dest.writeString(gender);
    }
}
