package com.example.a911month;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

//op
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HttpGetDataListener,View.OnClickListener {


    private HttpData httpData;// �첽�������
    private List<ListData> lists;
    private ListView lv;
    private EditText et_sendText;
    private Button btn_send;
    private String content_str; // �惦��EditText�@ȡ���Ĕ���
    private TextAdapter adapter;
    private String[] welcome_arry;//��ӭ��
    private double currentTime,oldTime = 0;//�Ի�ʱ��
    private Button yuying;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("haha7", "----------7");
        initView();
        Log.i("haha8", "----------8");

        yuying = findViewById(R.id.yuying);
        mysetListeners();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void mysetListeners(){
        myOnClick onClick = new myOnClick();
        yuying.setOnClickListener(onClick);

    }
    public void guanyuonclick(){
        Intent intent = null;
        intent = new Intent(MainActivity.this,guanyu.class);
        startActivity(intent);

    }

    private class myOnClick implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.yuying:
                    //跳转到yuyingview演示界面
                    intent = new Intent(MainActivity.this,yuyingActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }


    private void initView(){
        lists = new ArrayList<ListData>();
        lv = (ListView) findViewById(R.id.lv);
        et_sendText = (EditText) findViewById(R.id.et_sendText);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        adapter = new TextAdapter(lists, this);
        lv.setAdapter(adapter);//��adapter
        ListData listData;
        listData = new ListData(getRandomWelcomeTips(),ListData.RECEIVE, getTime());
        lists.add(listData);//��ӻ�ӭ��
    }

    public String getRandomWelcomeTips(){
//        String welcome_tip = null;
//        welcome_arry = this.getResources().getStringArray(getResources().getString(R.string.welcome_tips));//��string.xml�л�ȡ��Ϊwelcome_tips���ַ�������
//        int index = (int)(Math.random()*(welcome_arry.length - 1));//��ȡһ�������
//        welcome_tip = welcome_arry[index];
        return getResources().getString(R.string.welcome_tips);
    }


    private String getTime(){
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd��   HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        if(currentTime - oldTime >= 5*60*1000){//�������5���ӣ���ʾʱ��
            oldTime = currentTime;
            return str;
        }else{
            return "";
        }
    }


    public void getDataUrl(String data) {
        Log.i("haha---data=","------" + data);
        parseText(data);
    }

    public void parseText(String str){//����json
        try {
            JSONObject jb = new JSONObject(str);
            ListData listData;
            listData = new ListData(jb.getString("text"),ListData.RECEIVE, getTime());
            lists.add(listData);
            adapter.notifyDataSetChanged();//�����m�䣿��
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void onClick(View v) {
        getTime();
        content_str = et_sendText.getText().toString();//�@ȡEditText����
        et_sendText.setText("");
        String dropk = content_str.replace(" ", "");//ȥ���ո�
        String droph = dropk.replace("\n", "");//ȥ���س�
        ListData listData;
        listData = new ListData(content_str, ListData.SEND, getTime());
        lists.add(listData);
        Log.i("haha9", "----------content_str="+content_str);
        adapter.notifyDataSetChanged();//ˢ��

        Log.i("haha6", "----------content_str="+content_str);

        httpData = (HttpData) new HttpData(
                "http://www.tuling123.com/openapi/api?key=7c09a1b9c24d499ebbe7a4b08a95b636&info=" + droph + "&userid=91month",
                this).execute();// �����Ѿ�ȥ���ո�ͻس�content_str������droph   ; execute()�����첽ͨ��

        Log.i("haha5", "----------"+httpData);

        if(lists.size() > 30){//�����Ļ�ϵĶԻ����ݶ���30�����Ƴ�ǰ�������
            for (int i = 0; i < lists.size(); i++) {
                lists.remove(i);
            }
        }
    }
    //sdjshfsjhsssssssssssssssssssssssssssssssssssssssssssssssssssssssss

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        }
//        else if (id == R.id.nav_gallery) {
//
//        }
//        else if (id == R.id.nav_slideshow) {
//
//        }
        else if (id == R.id.nav_tools) {

        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
