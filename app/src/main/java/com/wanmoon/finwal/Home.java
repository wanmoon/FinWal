package com.wanmoon.finwal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by pimpischaya on 5/25/2017 AD.
 */

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
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
