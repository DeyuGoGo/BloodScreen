package go.deyu.bloodscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import go.deyu.bloodscreen.fragment.MainFragment;
import go.deyu.bloodscreen.fragment.SettingFragment;


public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private Menu mMenu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container_main) != null ) {
            if (savedInstanceState != null) {
                return;
            }
            MainFragment MainFragment = new MainFragment();
            MainFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, MainFragment)
                    .commit();
        }
        initToolBar();
    }


    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, fragment);
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            changeFragment(new SettingFragment());
            item.setVisible(false);
            mMenu.findItem(R.id.action_back).setVisible(true);
            return true;
        }
        if(id == R.id.action_back){
            changeFragment(new MainFragment());
            item.setVisible(false);
            mMenu.findItem(R.id.action_settings).setVisible(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initToolBar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.phoneuselogo);
    }
}
