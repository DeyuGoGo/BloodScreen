package go.deyu.bloodscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import go.deyu.bloodscreen.app.app;


public class MainActivity extends ActionBarActivity {

    private final String TAG = getClass().getSimpleName();

    private BloodModelInterface model;

    @OnClick(R.id.main_btn_start)
    public void start(){
        Intent i = new Intent(this,DrawService.class);
        i.setAction(DrawService.ACTION_SHOW);
        startService(i);
    }

    @OnClick(R.id.main_stop_btn)
    public void stop(){
        Intent i = new Intent(this,DrawService.class);
        i.setAction(DrawService.ACTION_NOT_SHOW);
        stopService(i);
    }

    @OnClick(R.id.main_btn_clean)
    public void clean(){
        Intent i = new Intent(this,DrawService.class);
        i.setAction(DrawService.ACTION_CLEAN);
        startService(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.model = app.App.model;
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
