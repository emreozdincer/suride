package com.example.myfirstapp;

import android.app.Activity;
//import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Ride> ridesList;
    private User user;
    private ListAdapter adapter;
    private String userID;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001; // Request Code for Sign-in
    private static final int RC_POST_RIDE = 9002; // Request Code for PostRide

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Implement user creation with log-in/register
        user = new User("Jason");

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // TODO: Get the server_client_id from teammates
//                 .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        // listen to sign-in button
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        // listen to sign-out button
        findViewById(R.id.button_signOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        // Listen to post button
        findViewById(R.id.button_postRide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID == null) {
                    Toast.makeText(MainActivity.this, "You need to log-in first", Toast.LENGTH_SHORT).show();
                } else {
                    postRide(userID);
                }
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                        //Add your action onClick
                        findViewById(R.id.button_signOut).setVisibility(View.VISIBLE);
                        findViewById(R.id.button_postRide).setVisibility(View.GONE);
                        selectedFragment = ProfilePageFragment.newInstance();
                        break;
                    case R.id.navigation_rides:
                        findViewById(R.id.button_postRide).setVisibility(View.VISIBLE);
                        findViewById(R.id.button_signOut).setVisibility(View.GONE);
                        selectedFragment = ViewRidesFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, InitialFragment.newInstance());
        transaction.commit();

        // Initialize rides list
        ridesList = new ArrayList<Ride>();
        Ride dummyRide = new Ride("Dummy ID","Dummy Destination", new Date(),2, "Dummy Description");
        ridesList.add(dummyRide);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        // A ride was posted, extract the ride and update the list view.
        else if (requestCode == RC_POST_RIDE){
            if (resultCode == Activity.RESULT_OK) {
                String returnValue = data.getStringExtra("Take this ride");
                Ride newRide = new Gson().fromJson(returnValue, Ride.class);
                addRide(newRide);
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            // Get Sign-in Account
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
//            Log.i("IDTOKEN", idToken);

            if (idToken != null) {
                try {
                    // send ID Token to server and validate
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("https://yourbackend.example.com/tokensignin");

                    List nameValuePairs = new ArrayList(1);
                    nameValuePairs.add(new BasicNameValuePair("idToken", idToken));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);
                    int statusCode = response.getStatusLine().getStatusCode();
                    final String responseBody = EntityUtils.toString(response.getEntity());
                    Log.i("SERVER RESPONSE", "Signed in as: " + responseBody);

                } catch (UnsupportedEncodingException e) {
                    Log.e("handleSignInResult", "Error sending ID token to backend.", e);
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    Log.e("handleSignInResult", "Error sending ID token to backend.", e);
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("handleSignInResult", "Error sending ID token to backend.", e);
                    e.printStackTrace();
                }
            }

            //TODO: Verify the integrity of the ID Token
            // https://developers.google.com/identity/sign-in/android/backend-auth#verify-the-integrity-of-the-id-token
            // Signed in successfully, show authenticated UI.
            Log.d("handleSignInResult", "success");
            updateUI(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("handleSignInResult", e.getMessage(), e);
            updateUI(null);
        }
    }

    @Override
    protected  void onStart(){
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    public void  updateUI(GoogleSignInAccount account) {

        // References to view objects
        FrameLayout fl = (FrameLayout) findViewById(R.id.frame_layout);
        Button btPostRide = (Button) findViewById(R.id.button_postRide);
        SignInButton btSignIn = findViewById(R.id.sign_in_button);
        Button btSignOut = findViewById(R.id.button_signOut);
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id. navigationView);

        //TODO: Update UI with seperate login activity rather than setting visibility of items (?)
        if (account != null) {
            userID = account.getDisplayName();
            fl.setVisibility(View.VISIBLE);
            btPostRide.setVisibility(View.VISIBLE);
            btSignOut.setVisibility(View.VISIBLE);
            btSignIn.setVisibility(View.GONE);
            bnv.setVisibility(View.VISIBLE);
        } else {
            userID = null;
            fl.setVisibility(View.GONE);
            btSignIn.setVisibility(View.VISIBLE);
            btSignOut.setVisibility(View.GONE);
            btPostRide.setVisibility(View.GONE);
            bnv.setVisibility(View.GONE);
        }
    }

    /* Called when user taps the Post a Ride button */
    public void postRide(String userId) {
        Intent i=new Intent(this, PostRideActivity.class);
        i.putExtra("User id", userId);
        try {
            startActivityForResult(i,RC_POST_RIDE);
        }
        catch(Exception e){
            Log.d("exception", e.getMessage());
        }
    }

    public void addRide(Ride ride) {
        ridesList.add(ride);
        // TODO: notify ViewRidesFragment
    }

    public List<Ride> getRidesList() {
        return ridesList;
    }

    public User getUser() { return user; }
}
