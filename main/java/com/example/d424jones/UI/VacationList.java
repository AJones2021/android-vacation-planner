package com.example.d424jones.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

import java.util.List;

public class VacationList extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }

        });
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        repository= new Repository(getApplication());
        List<Vacation> allVacations =repository.getAllVacations();
        final VacationAdapter vacationAdapter= new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }
    @Override
    protected void onResume(){
        super.onResume();
        List<Vacation> allVacations=repository.getAllVacations();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter=new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.sample){
            repository=new Repository(getApplication());
            Vacation vacation=new Vacation(0,"Summer Break","Hilton","01/01/26", "01/14/26");
            repository.insert(vacation);
            vacation=new Vacation(0,"Winter Break","Inn","02/02/26", "02/10/26");
            repository.insert(vacation);
            Excursion excursion = new Excursion(0,"Snorkle",1,"01/12/26");
            repository.insert(excursion);
            excursion = new Excursion(0,"Swim",2,"02/06/26");
            repository.insert(excursion);
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        if(item.getItemId() == R.id.reports){
            Intent intent = new Intent (VacationList.this, ReportActivity.class);
            startActivity(intent);
            return true;
        }

      //  return true;
        return super.onOptionsItemSelected(item);
    }

}