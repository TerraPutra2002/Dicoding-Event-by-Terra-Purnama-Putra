package com.example.dicodingevent.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_event")
public class FavoriteEventEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id = 0;

    @ColumnInfo(name = "eventName")
    public String eventName;

    @ColumnInfo(name = "eventDescription")
    public String eventDescription;

    @ColumnInfo(name = "beginTime")
    public String beginTime;

    @ColumnInfo(name = "endTime")
    public String endTime;

    @ColumnInfo(name = "imageUrl")
    public String imageUrl;

    @ColumnInfo(name = "owner")
    public String owner;

    @ColumnInfo(name = "quota")
    public int quota;

    @ColumnInfo(name = "eventId")
    public int eventId;

    @ColumnInfo(name = "registrants")
    public int registrant;

    @ColumnInfo(name = "link")
    public String link;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "cityName")
    public String cityName;

    @ColumnInfo(name = "mediaCover")
    public String mediaCover;

    @ColumnInfo(name = "summary")
    public String summary;
}