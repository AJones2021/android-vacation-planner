package com.example.d424jones.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d424jones.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM VACATIONS ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();



    //@Query("SELECT * FROM VACATIONS WHERE vacationId = :vacationId LIMIT 1")
//  Vacation getVacationById (int vacationId);

    @Query("SELECT * FROM VACATIONS WHERE startDate >= :startDate AND endDate <= :endDate ORDER BY startDate ASC")
    List<Vacation> searchVacationsByDateRange(String startDate, String endDate);

}
