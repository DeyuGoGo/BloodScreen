package go.deyu.bloodscreen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import go.deyu.bloodscreen.app.app;


public class MainActivity extends ActionBarActivity {

    private BloodControllerInterface controller = app.App.controller;
    @OnClick(R.id.main_btn_start)
    public void start(){
        startService(new Intent(this,DrawService.class));
    }

    @OnClick(R.id.main_stop_btn)
    public void stop(){
        stopService(new Intent(this, DrawService.class));
    }

    @OnClick(R.id.main_btn_clean)
    public void clean(){
        if(controller!=null){
            controller.cleanBlood();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
