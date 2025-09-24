package com.example.d424jones.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.d424jones.dao.ExcursionDAO;
import com.example.d424jones.dao.UserDAO;
import com.example.d424jones.dao.VacationDAO;
import com.example.d424jones.entities.Excursion;
import com.example.d424jones.entities.User;
import com.example.d424jones.entities.Vacation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private UserDAO userDAO;

    private ExcursionDAO mExcursionDAO;
    private VacationDAO mVacationDAO;

    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO=db.excursionDAO();
        mVacationDAO= db.vacationDAO();
        userDAO = db.userDAO();
    }

    public List<Vacation> getAllVacations(){
        databaseExecutor.execute(()->{
            mAllVacations=mVacationDAO.getAllVacations();
        });

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllVacations;
    }

    public void insert (Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.insert(vacation);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.update(vacation);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.delete(vacation);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public List<Excursion> getAllExcursions(){
        databaseExecutor.execute(()->{
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllExcursions;
    }
    public List<Excursion> getAssociatedExcursions(int vacationID){
        databaseExecutor.execute(()->{
            mAllExcursions = mExcursionDAO.getAssociatedExcursions(vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public void insert(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.insert(excursion);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.delete(excursion);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    public List<Vacation> searchVacationsByDateRange(String startDate, String endDate) {

        databaseExecutor.execute(() -> {
            mAllVacations = mVacationDAO.searchVacationsByDateRange(startDate, endDate);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllVacations;
    }
    public static String hashPassword(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for(byte b : hash){
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }

    }

    public interface UserCallback{
        void onUserLoaded(User user);
    }

    public void insertUser(User user){
        databaseExecutor.execute(() -> {
            user.setPassword(hashPassword(user.getPassword()));
            userDAO.insert(user);
        });

    }
/*
  public boolean checkPassword(String rawPassword, String storedHashedPassword){
        String hashedInput = hashPassword(rawPassword);
        return hashedInput != null && hashedInput.equals(storedHashedPassword);
  }
*/
public static boolean checkPassword(String rawPassword, String storedHashedPassword) {
    String hashedInput = hashPassword(rawPassword);
    return hashedInput.equals(storedHashedPassword);
}
  public void getUserByUsername(String username, final UserCallback callback) {
      databaseExecutor.execute(() -> {
          User user = userDAO.getUserByUsername(username);
          callback.onUserLoaded(user);
      });
  }
}
