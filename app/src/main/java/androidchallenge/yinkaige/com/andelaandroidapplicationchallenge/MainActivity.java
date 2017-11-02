package androidchallenge.yinkaige.com.andelaandroidapplicationchallenge;

import android.content.res.TypedArray;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    private static final String RECYCLER_STATE = "recycler_state";
    ArrayList<Currency> currencyArray;
    String[] arrNames, arrAbv;
    TypedArray currencyDrawables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrAbv = getResources().getStringArray(R.array.abv_array);
        arrNames = getResources().getStringArray(R.array.names_array);
        currencyDrawables = getResources().obtainTypedArray(R.array.currency_drawables);

        populateCurrencyArray();

        getSupportActionBar();
        gridLayoutManager = new GridLayoutManager(this, 3);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(currencyArray, currencyDrawables);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putParcelable(RECYCLER_STATE, gridLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Parcelable recyclerState = savedInstanceState.getParcelable(RECYCLER_STATE);
        if(recyclerState != null) {
            gridLayoutManager.onRestoreInstanceState(recyclerState);
        }
    }

    private void populateCurrencyArray() {
        currencyArray = new ArrayList<>();
        for (int i=0; i<10; i++) {
            String name = arrNames[i];
            String abv = arrAbv[i];
            Currency currency = new Currency(i, name, abv);
            currencyArray.add(currency);
        }
        Toast.makeText(this, "loaded " + currencyArray.size() + " objects", Toast.LENGTH_LONG).show();
    }
}
