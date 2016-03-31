package org.csiweek1;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CriminalProvider cp = new CriminalProvider(this);

        Button b = (Button) findViewById(R.id.btnReport);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        Intent i = getIntent();
        int chosenCriminalPosition = i.getIntExtra("positionCriminal", -1);
        Criminal c = cp.GetCriminal(chosenCriminalPosition);

        TextView nameText = (TextView) findViewById(R.id.text_name_value);
        nameText.setText(c.name);

        TextView bountyText = (TextView) findViewById(R.id.text_bounty_value);
        bountyText.setText(String.format("%1$d", c.getBountyInDollars()));

        TextView genderText = (TextView) findViewById(R.id.text_gender_value);
        genderText.setText(String.format("%1$s", c.gender));

        TextView ageText = (TextView) findViewById(R.id.text_age_value);
        ageText.setText(String.format("%1$d", c.age));

        ListView crimelist = (ListView) findViewById(R.id.crimeList);
        crimelist.setAdapter(new CrimeListAdapter(this, c.crimes));

     //   ImageView imageView1 = (ImageView) findViewById(R.id.criminalPicture);
     //   imageView1.setImageDrawable(c.mugshot);
    }
}
