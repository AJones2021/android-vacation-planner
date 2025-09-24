package com.example.d424jones.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.d424jones.dao.ExcursionDAO;
import com.example.d424jones.dao.UserDAO;
import com.example.d424jones.dao.VacationDAO;
import com.example.d424jones.entities.Excursion;
import com.example.d424jones.entities.User;
import com.example.d424jones.entities.Vacation;

@Database(entities = {Excursion.class, Vacation.class, User.class}, version = 1, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    public abstract UserDAO userDAO();
    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (VacationDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),VacationDatabaseBuilder.class, "MyVacationDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }

            }
        }
        return INSTANCE;
    }
}