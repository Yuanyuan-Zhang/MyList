package com.example.student.mylist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {

    String[] colorNames;
    String[] colorValues;
    RelativeLayout mainLayout;
    String getColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout=(RelativeLayout)findViewById(R.id.main);
            try {
                File myFile = new File("/sdcard/mysdfile.txt");
                if(myFile.exists()) { //if the file exists, set the background to that color
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader( //get buffer from the file
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer = aDataRow;//get the color
                    }
                    mainLayout.setBackgroundColor(Color.parseColor(aBuffer)); //set the background color
                    myReader.close();
                }

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        colorNames = getResources().getStringArray(R.array.listArray);
        colorValues = getResources().getStringArray(R.array.listValues);

        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_listview, colorNames);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                String selection = (String) parent.getItemAtPosition(position);

                for(int i=0;i<124;i++){
                    if(selection.equals(colorNames[i])){
                        getColor = "#" + colorValues[i]; //get the color-hex
                        getColor = getColor.replaceFirst("00","ff"); //make the transparent color into opaque color
                        //Log.i("Color",getColor);
                        mainLayout.setBackgroundColor(Color.parseColor(getColor));//set the background color
                        break;
                    }
                }

            }
        });
        registerForContextMenu(lv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Write Colour To SDCard");
        menu.add(0, v.getId(), 0, "Read Colour From SDCard");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Write Colour To SDCard") {
            Toast.makeText(getApplicationContext(), "Wring a color", Toast.LENGTH_LONG).show();

            try {
                File myFile = new File("/sdcard/mysdfile.txt");
                myFile.createNewFile(); //creating or overriding a new file every time
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(getColor); //save the color into the file
                myOutWriter.close();
                fOut.close();
                Toast.makeText(getBaseContext(),
                        "Done writing SD 'mysdfile.txt'",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        } else if (item.getTitle() == "Read Colour From SDCard") {
            Toast.makeText(getApplicationContext(), "Reading color from SDCard", Toast.LENGTH_LONG).show();
            try {
                File myFile = new File("/sdcard/mysdfile.txt");
                if(myFile.exists()) { //if the file exists, set the background to that color
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader( //get buffer from the file
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer = aDataRow;//get the color
                    }
                    mainLayout.setBackgroundColor(Color.parseColor(aBuffer)); //set the background color
                    myReader.close();
                }else{
                    Toast.makeText(getBaseContext(),
                            "No color in the SDCard",
                            Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }


        } else {
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
