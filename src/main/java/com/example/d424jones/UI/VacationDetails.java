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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424jones.R;
import com.example.d424jones.database.Repository;
import com.example.d424jones.entities.Excursion;
import com.example.d424jones.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    String title;
    String hotel;
    int vacationID;
    String settingStartDate;
    String settingEndDate;
    EditText editTitle;
    EditText editHotel;
    TextView editStartDate;
    TextView editEndDate;
    Repository repository;
    Vacation currentVacation;
    int numExcursions;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar MyCalendarStart = Calendar.getInstance();
    final Calendar MyCalendarEnd = Calendar.getInstance();
    List<Excursion> filteredExcursions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(getApplication());
        editTitle = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hoteltext);
        vacationID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        hotel = getIntent().getStringExtra("hotel");
        settingStartDate = getIntent().getStringExtra("startDate");
        settingEndDate = getIntent().getStringExtra("endDate");
        editTitle.setText(title);
        editHotel.setText(hotel);




        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);

        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // List<Excursion> filteredExcursions = new ArrayList<>();
        for(Excursion e : repository.getAllExcursions()){
            if(e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
        String Format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

        if(settingStartDate != null){
            try {
                Date startDate = sdf.parse(settingStartDate);
                Date endDate = sdf.parse(settingEndDate);
                MyCalendarStart.setTime(startDate);
                MyCalendarEnd.setTime(endDate);
            }
            catch (ParseException e){
                e.printStackTrace();
            }
        }
        editStartDate = findViewById(R.id.startDate);
        editEndDate = findViewById(R.id.endDate);
        editStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Date date;
                String info = editStartDate.getText().toString();
                if(info.equals("")) info = settingEndDate;
                try{
                    MyCalendarStart.setTime(sdf.parse(info));
                }catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this,startDate, MyCalendarStart.get(Calendar.YEAR),MyCalendarStart.get(Calendar.MONTH),MyCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                MyCalendarStart.set(Calendar.YEAR, year);
                MyCalendarStart.set(Calendar.MONTH, month);
                MyCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();

            }
        };
        editEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Date date;
                String info = editEndDate.getText().toString();
                if(info.equals("")) info= settingEndDate;
                try{
                    MyCalendarEnd.setTime(sdf.parse(info));
                }
                catch (ParseException e){e.printStackTrace();}
                new DatePickerDialog(VacationDetails.this, endDate, MyCalendarEnd.get(Calendar.YEAR), MyCalendarEnd.get(Calendar.MONTH), MyCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                MyCalendarEnd.set(Calendar.YEAR,year);
                MyCalendarEnd.set(Calendar.MONTH,month);
                MyCalendarEnd.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabelEnd();
            }
        };
    }

    private void updateLabelStart(){
        String Format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);
        editStartDate.setText(sdf.format(MyCalendarStart.getTime()));
    }
    private void updateLabelEnd(){
        String Format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(Format,Locale.US );
        editEndDate.setText(sdf.format(MyCalendarEnd.getTime()));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        if(item.getItemId()==R.id.savevacation) {
            String Format = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);
            String startDateString = sdf.format(MyCalendarStart.getTime());
            String endDateString = sdf.format(MyCalendarEnd.getTime());
            try {
                Date startDate = sdf.parse(startDateString);
                Date endDate = sdf.parse(endDateString);
                if (endDate.before(startDate)) {
                    Toast.makeText(this, "The end date must come after the start date", Toast.LENGTH_LONG).show();
                } else {
                    Vacation vacation;
                    if (vacationID == -1) {
                        if (repository.getAllVacations().size() == 0) vacationID = 1;
                        else
                            vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                        vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), startDateString, endDateString);
                        repository.insert(vacation);
                        this.finish();
                    } else {
                        vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), startDateString, endDateString);
                        repository.update(vacation);
                        this.finish();

                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if(item.getItemId() == R.id.deletevacation){
            for(Vacation vacay:repository.getAllVacations()) {
                if (vacay.getVacationID() == vacationID) currentVacation = vacay;
            }
            numExcursions=0;
            for(Excursion excursion: repository.getAllExcursions()){
                if(excursion.getVacationID()==vacationID)++numExcursions;
            }
            if(numExcursions==0){
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() +" was deleted ",Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            }
            else{
                Toast.makeText(VacationDetails.this, "Can not delete a vacation with excursions",Toast.LENGTH_LONG).show();
            }
        }
        //validation for vacation information
        if(item.getItemId()==R.id.beginAlert) {
            String dateFromScreen = editStartDate.getText().toString();
            String alert = "Vacation " + title+ " is starting";
            alertPicker(dateFromScreen, alert);
            return true;
        }
        if(item.getItemId()==R.id.endalert){
            String dateFromScreen = editEndDate.getText().toString();
            String alert = "Vacation " + title+ " is ending";
            alertPicker(dateFromScreen, alert);
            return true;
        }
        if(item.getItemId() == R.id.alertvacation){
            String dateFromScreen = editStartDate.getText().toString();
            String alert = "Vacation " + title+ " is starting";
            alertPicker(dateFromScreen, alert);
            dateFromScreen = editEndDate.getText().toString();
            alert = "Vacation " + title+ " is ending";
            alertPicker(dateFromScreen, alert);
            return true;

        }
        if(item.getItemId() == R.id.sharevacation){
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE,"Vacation shared") ;
            StringBuilder shareDate = new StringBuilder();
            shareDate.append("Vacation title: " + editTitle.getText().toString()+ "\n");
            shareDate.append("Hotel name: "+ editHotel.getText().toString()+ "\n");
            shareDate.append("Start date: "+editStartDate.getText().toString() + "\n");
            shareDate.append("End date: "+ editEndDate.getText().toString()+ "\n");


            for (int i =0; i < filteredExcursions.size();i++){
                shareDate.append("Excursions "+(i + 1) + ": " + filteredExcursions.get(i).getExcursionTitle() + "\n");
                shareDate.append(("Excursion " + (i+ 1)) + " Date: " + filteredExcursions.get(i).getExcursionDate() + "\n");
            }

            sentIntent.putExtra(Intent.EXTRA_TEXT, shareDate.toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }
        return true;
    }
    public void alertPicker(String dateFromScreen, String alert){
        String Format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);
        Date myDate = null;
        try{
            myDate = sdf.parse(dateFromScreen);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
        intent.putExtra("key", alert);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);


    }
    @Override
    public void onResume(){
        super.onResume();
        RecyclerView recyclerView=findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter= new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion>filteredExcursions = new ArrayList<>();
        for (Excursion e: repository.getAllExcursions()){
            if(e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
        updateLabelStart();
        updateLabelEnd();
    }
}