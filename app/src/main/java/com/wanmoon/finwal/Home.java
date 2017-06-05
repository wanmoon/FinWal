package com.wanmoon.finwal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by pimpischaya on 5/25/2017 AD.
 */

public class Home extends AppCompatActivity {

    FloatingActionButton fab_plus, fab_speech, fab_scan, fab_typing;
    Animation fab_open, fab_close, fab_backward, fab_forward;
    boolean isopen = false;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        fab_plus =(FloatingActionButton)findViewById(R.id.fab_plus);
        fab_speech =(FloatingActionButton)findViewById(R.id.fab_speech);
        fab_scan =(FloatingActionButton)findViewById(R.id.fab_scan);
        fab_typing =(FloatingActionButton)findViewById(R.id.fab_typing);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isopen) {
                    fab_speech.startAnimation(fab_close);
                    fab_scan.startAnimation(fab_close);
                    fab_typing.startAnimation(fab_close);
                    fab_plus.startAnimation(fab_backward);
                    fab_speech.setClickable(false);
                    fab_scan.setClickable(false);
                    fab_typing.setClickable(true);
                    isopen = false;

                } else {
                    fab_speech.startAnimation(fab_open);
                    fab_scan.startAnimation(fab_open);
                    fab_typing.startAnimation(fab_open);
                    fab_plus.startAnimation(fab_backward);
                    fab_speech.setClickable(true);
                    fab_scan.setClickable(true);
                    fab_typing.setClickable(true);
                    isopen = true;


                }
            }
        });

        fab_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SpeechToText.class);
                startActivity(i);
            }
        });
        fab_typing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AddTransaction.class);
                startActivity(i);
            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public void showMyText(String myText){
        // สร้าง instance สำหรับเรียกใช้งาน TextView
        // TextView myShowText = (TextView) findViewById(R.id.my_text);
       // myShowText.setText(myText);  // แสดงค่าที่ส่งเข้ามา
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // เก็บค่า id ของปุ่ม action butoon ที่กดเลือก
        int id = item.getItemId();

        // ตรวจสอบค่า ว่า เป็น id ใด  แล้วเรียกใช้ method ที่เราสร้างขึ้น
        // ในตัวอย่างนี้ เราจะส่งค่าข้อความเข้าไปใน method
        switch (id) {
            case R.id.action_search:
                showMyText("Search");
                return true;
            case R.id.action_refresh:
                showMyText("Refresh");
                return true;
            case R.id.action_settings:
                showMyText("Settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}