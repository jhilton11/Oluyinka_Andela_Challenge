
package androidchallenge.yinkaige.com.andelaandroidapplicationchallenge;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyPage extends AppCompatActivity {
    ProgressDialog progressDialog;
    TextView ethTv, BTCtv;
    private String URL;
    private String abv, name;
    private int ethRate, btcRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_page);

        name = getIntent().getStringExtra("name");
        abv = getIntent().getStringExtra("abv");
        URL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=" + abv;

        //Display progress dialog while android volley is loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        BTCtv = (TextView)findViewById(R.id.btcTv);
        ethTv = (TextView)findViewById(R.id.ETHtv);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject btcJson = response.getJSONObject("BTC");
                            BTCtv.setText(name + ": BTC " + btcJson.getString(abv));
                            btcRate = Integer.parseInt(btcJson.getString(abv));
                            JSONObject ethJson = response.getJSONObject("ETH");
                            ethRate = Integer.parseInt(ethJson.getString(abv));
                            ethTv.setText(name + ": ETH " + ethJson.getString(abv));
                        } catch (JSONException e) {
                            BTCtv.setText(response.toString() + " - "+ e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        BTCtv.setText(error.toString());
                        ethTv.setText(error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}