package com.georgeampartzidis.bakie.model;

import android.os.Parcel;
import android.os.Parcelable;

class Step implements Parcelable {

    private int id;
    private String shortDescription;
    private String detailedDescription;
    private String videoUrl;
    private String thumbnailUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(detailedDescription);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }

    private Step(Parcel parcel) {
        id = parcel.readInt();
        shortDescription = parcel.readString();
        detailedDescription = parcel.readString();
        videoUrl = parcel.readString();
        thumbnailUrl = parcel.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR
            = new Parcelable.Creator<Step>() {
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
