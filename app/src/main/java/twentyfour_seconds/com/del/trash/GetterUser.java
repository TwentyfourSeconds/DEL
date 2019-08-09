package twentyfour_seconds.com.del.trash;

import android.os.Parcel;
import android.os.Parcelable;

public class GetterUser implements Parcelable {
    String uid;
    String username;
    String profileImageUrl;

    public GetterUser(){}

    public GetterUser(String uid, String username, String profileImageUrl){
        this.uid = uid;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
    }

//Parcelelable化による自動生成

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.username);
        dest.writeString(this.profileImageUrl);
    }

    protected GetterUser(Parcel in) {
        this.uid = in.readString();
        this.username = in.readString();
        this.profileImageUrl = in.readString();
    }

    public static final Parcelable.Creator<GetterUser> CREATOR = new Parcelable.Creator<GetterUser>() {
        @Override
        public GetterUser createFromParcel(Parcel source) {
            return new GetterUser(source);
        }

        @Override
        public GetterUser[] newArray(int size) {
            return new GetterUser[size];
        }
    };
}
