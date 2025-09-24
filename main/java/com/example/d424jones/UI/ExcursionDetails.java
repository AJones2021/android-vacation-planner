package com.example.d424jones.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d424jones.R;
import com.example.d424jones.database.Repository;
import com.example.d424jones.entities.Excursion;
import com.example.d424jones.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String title;
    int excursionID;
    int vacationID;
    EditText editTitle;
    Repository repository;
    Excursion currentExcursion;
    TextView editExcursionDate;
    DatePickerDialog.OnDateSetListener excursionDate;
    final Calendar myCalendarDate = Calendar.getInstance();
    String setDate;

    //polymorphism using method overriding
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.excursionTitle);
        editTitle.setText(title);
        excursionID = getIntent().getIntExtra("id",-1);
        vacationID = getIntent().getIntExtra("vacationID",-1);
        setDate = getIntent().getStringExtra("excursionDate");
        String Format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

        if(setDate!=null){
            try{
                Date excursionDate = sdf.parse(setDate);
                myCalendarDate.setTime(excursionDate);
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        editExcursionDate = findViewById(R.id.excursiondate);
        editExcursionDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Date date;
                String info = editExcursionDate.getText().toString();
                if(info.equals("")) info = setDate;
                try{
                    myCalendarDate.setTime(sdf.parse(info));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, excursionDate, myCalendarDate
                        .get(Calendar.YEAR), myCalendarDate.get(Calendar.MONTH), myCalendarDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        excursionDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarDate.set(Calendar.YEAR, year);
                myCalendarDate.set(Calendar.MONTH, month);
                myCalendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


    }
    private void updateLabel(){
        String Format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(Format,Locale.US);
        editExcursionDate.setText(sdf.format(myCalendarDate.getTime()));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        //validation for excursion information
        if (item.getItemId() == R.id.saveexcursion) {

            String titleText = editTitle.getText().toString().trim();
            if (titleText.isEmpty()) {
                Toast.makeText(this, "Excursion title cannot be empty", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (myCalendarDate == null) {
                Toast.makeText(this, "Please select an excursion date", Toast.LENGTH_SHORT).show();
                return true;
            }


            String format = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            String excursionDateString = sdf.format(myCalendarDate.getTime());
            Vacation vacation = null;

            List<Vacation> vacations = repository.getAllVacations();
            for (Vacation vacay : vacations) {
                if (vacay.getVacationID() == vacationID) {
                    vacation = vacay;
                    break;
                }
            }

            try {
                Date excursionDate = sdf.parse(excursionDateString);
                Date startDate = sdf.parse(vacation.getStartDate());
                Date endDate = sdf.parse(vacation.getEndDate());

                if (excursionDate.before(startDate) || excursionDate.after(endDate)) {
                    Toast.makeText(this, "Excursion date must be WITHIN the vacation's start and end dates", Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    Excursion excursion;
                    if (excursionID == -1) {
                        if(repository.getAllExcursions().size() == 0) excursionID = 1;
                        else {excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size()-1).getExcursionID() + 1;}
                        excursion = new Excursion(excursionID, editTitle.getText().toString(),vacationID,excursionDateString);
                        repository.insert(excursion);
                        this.finish();

                    } else {
                        excursion = new Excursion(excursionID, editTitle.getText().toString(), vacationID, excursionDateString);
                        repository.update(excursion);
                        this.finish();
                    }
                    return true;
                }


            }catch (ParseException e) {
                e.printStackTrace();
            }

            return true;
        }


        if(item.getItemId() == R.id.deleteexcursion){
            for (Excursion excursion: repository.getAllExcursions()){
                if(excursion.getExcursionID() == excursionID) currentExcursion = excursion;
            }
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this,currentExcursion.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
            ExcursionDetails.this.finish();
        }

        if(item.getItemId() == R.id.alertexcursion){
            String dateFromScreen = editExcursionDate.getText().toString();
            String alert = "Excursion " + title + " is today!";

            String Format = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);
            Date myDate = null;
            try{
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e){ e.printStackTrace();}
            Long trigger = myDate.getTime();
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            intent.putExtra("key", alert);
            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent,PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE) ;
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger,sender);

            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onResume(){
        super.onResume();
        updateLabel();
    }
}
