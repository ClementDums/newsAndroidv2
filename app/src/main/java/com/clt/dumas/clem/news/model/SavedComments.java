package com.clt.dumas.clem.news.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Comments.class,
        parentColumns = "id",
        childColumns = "id"),
        indices = {@Index(value = "id", unique = true)},
        tableName = "SavedComments"
)
public class SavedComments {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @androidx.annotation.NonNull
    private final int id;

    public SavedComments(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getId() {
        return id;
    }
}
