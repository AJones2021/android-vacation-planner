package com.example.d424jones.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
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
import com.example.d424jones.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class ReportActivity extends AppCompatActivity {

    private TextView startDateInput;
    private TextView endDateInput;
    private TextView reportTitle;
    private Button generateReportButton;
    private RecyclerView reportRecyclerView;
    private Repository repository;
    private ReportAdapter reportAdapter;

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDateListener, endDateListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);

        repository = new Repository(getApplication());
        startDateInput = findViewById(R.id.startDateInput);
        endDateInput = findViewById(R.id.endDateInput);
        reportTitle = findViewById(R.id.reportTitle);
        generateReportButton = findViewById(R.id.generateReportButton);
        reportRecyclerView = findViewById(R.id.reportRecyclerView);

        reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter = new ReportAdapter(repository);
        reportRecyclerView.setAdapter(reportAdapter);


        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        startDateInput.setOnClickListener(view -> {
            new DatePickerDialog(ReportActivity.this, startDateListener,
                    startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH),
                    startCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        startDateListener = (view, year, month, dayOfMonth) -> {
            startCalendar.set(Calendar.YEAR, year);
                    startCalendar.set(Calendar.MONTH, month);
                    startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    startDateInput.setText(sdf.format(startCalendar.getTime()));
        };

        endDateInput.setOnClickListener(view -> {
            new DatePickerDialog(ReportActivity.this, endDateListener,
                    endCalendar.get(Calendar.YEAR),
                    endCalendar.get(Calendar.MONTH),
                    endCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        endDateListener = (view, year, month, dayOfMonth) -> {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, month);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endDateInput.setText(sdf.format(endCalendar.getTime()));
        };
        generateReportButton.setOnClickListener(view -> {
            String startDate = startDateInput.getText().toString();
            String endDate = endDateInput.getText().toString();

            if (startDate.isEmpty() || endDate.isEmpty()){
                Toast.makeText(this,"Please select both start and end dates", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                Date start = sdf.parse(startDate);
                Date end = sdf.parse(endDate);
                if(end.before(start)){
                    Toast.makeText(this,"End date cannot be before Start date", Toast.LENGTH_SHORT).show();
                    return;
                }
                reportTitle.setText("Report for dates " + startDate + " - "+ endDate);

                List<Vacation> results = repository.searchVacationsByDateRange(startDate, endDate);
                //  String timestamp = new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.getDefault()).format(new Date());
                String timestamp = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                reportAdapter.setData(results, timestamp);
            } catch(ParseException e){
                Toast.makeText(this,"Invalid date", Toast.LENGTH_SHORT).show();
            }

        });

    }
}



