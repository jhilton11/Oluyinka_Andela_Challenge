
package androidchallenge.yinkaige.com.andelaandroidapplicationchallenge;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.Currency;

public class CurrencyPage extends AppCompatActivity {
    ProgressDialog progressDialog;
    TextView currencyTv, rateTv, amountTv;
    EditText amountEt;
    Spinner spinner;
    Button refreshButton;
    ImageView currencyImage;
    Toolbar toolbar;
    private String URL;
    private String abv, name, currencySymbol;
    private String[] baseCurrencies = new String[] {"Bitcoin", "Ethereum"};
    private long ethRate = 0, btcRate = 0, amount;
    final private int BTC = 1, ETH = 2;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_page);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = getIntent().getStringExtra("name");
        abv = getIntent().getStringExtra("abv");
        URL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=" + abv;
        java.util.Currency curr = Currency.getInstance(abv);
        currencySymbol = curr.getSymbol();

        mode = BTC;
        amountEt = (EditText)findViewById(R.id.amountET);
        amountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0)
                    amount = (int)Double.parseDouble(charSequence.toString());
                else
                    amount = 0;

                if (ethRate > 0 && btcRate > 0) {
                    if (mode == BTC) {
                        long convertedAmount = amount * btcRate;
                        amountTv.setText(currencySymbol + String.valueOf(convertedAmount));
                    } else if (mode == ETH) {
                        long convertedAmount = amount * ethRate;
                        amountTv.setText(currencySymbol + String.valueOf(convertedAmount));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        amountTv = (TextView)findViewById(R.id.amountTV);
        rateTv = (TextView)findViewById(R.id.rateTv);
        currencyTv = (TextView)findViewById(R.id.currencyTv);

        currencyImage = (ImageView)findViewById(R.id.currencyImage);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, baseCurrencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0) {
                    mode = BTC;
                    currencyTv.setText("Bitcoin");
                    currencyImage.setImageResource(R.drawable.btc);
                    rateTv.setText("BTC - " + abv + " = " + btcRate);
                } else if (i==1) {
                    mode = ETH;
                    currencyTv.setText("Ethereum");
                    currencyImage.setImageResource(R.drawable.eth);
                    rateTv.setText("ETH - " + abv + " = " + ethRate);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        refreshButton = (Button)findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeVolleyRequest();
            }
        });

        makeVolleyRequest();
    }

    private void makeVolleyRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Display progress dialog while android volley is loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject object = response.getJSONObject("BTC");
                            String btcString = object.getString(abv);
                            btcRate = (long)Double.parseDouble(btcString);

                            object = response.getJSONObject("ETH");
                            String ethString = object.getString(abv);
                            ethRate = (long)Double.parseDouble(ethString);

                            if (mode == 1) {
                                rateTv.setText("BTC - " + abv + " = " + btcRate);
                            } else if (mode == 2) {
                                rateTv.setText("ETH - " + abv + " = " + ethRate);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        amountTv.setText("Unable to load. Please click the refresh button");
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}