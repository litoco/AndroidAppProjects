package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.Birthday;
import com.google.api.services.people.v1.model.Person;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessToken accessToken;
    private boolean isLoggedIn=false;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount googleSignInAccount;
    private SignInButton signInButton;
    private ProfileTracker profileTracker;
    private TextView infoText;
    private String name, age="", profileUri;
    private SharedPreferences sp;

    private final int RC_SIGN_IN=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = MainActivity.this.getSharedPreferences("assignment", Context.MODE_PRIVATE);
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        signInButton = findViewById(R.id.sign_in_button);
        infoText = findViewById(R.id.login_text);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        configureFacebookLogin();
        configureGoogleSignIn();

        if(!isLoggedIn){
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                            Arrays.asList("public_profile"));
                }
            });
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            });
        }
    }

    private void configureGoogleSignIn() {
        Scope myScope = new Scope(PeopleServiceScopes.USER_BIRTHDAY_READ);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_api_id_web))
                .requestScopes(myScope)
                .requestServerAuthCode(getString(R.string.google_api_id_web), false)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void configureFacebookLogin() {
        loginButton.setPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        final float scale = MainActivity.this.getResources().getDisplayMetrics().density;
                        int pixels = (int) (200 * scale + 0.5f);
                        if(currentProfile!=null){
                            name = currentProfile.getName();
                            profileUri =""+currentProfile.getProfilePictureUri(pixels, pixels);
                        } else if( oldProfile!=null){
                            name = oldProfile.getName();
                            profileUri =""+oldProfile.getProfilePictureUri(pixels, pixels);
                        }
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("name", name);
                        editor.putString("uri", profileUri);
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        finish();
                    }
                };
                profileTracker.startTracking();
            }

            @Override
            public void onCancel() {
                Log.e("MainActivity", "permission not granted");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("MainActivity", "error logging in:"+error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            final Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("MainActivity","Failed to login: "+e.toString());
                }
            }).addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                @Override
                public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                    Log.e("MainActivity","google user: "+googleSignInAccount.toString());
                    handleSignInResult(task);
                }
            });

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e("MainActivity", "account server auth code is: " + account);
            name = account.getDisplayName();
            profileUri = ""+account.getPhotoUrl();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", name);
            editor.putString("uri",profileUri);
            editor.apply();
            new PeopleDetailsAsync().execute(account.getServerAuthCode());
        } catch (ApiException e) {
            Log.e("MainActivity", "signInResult:failed code: " + e.getStatusCode());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = (accessToken != null && !accessToken.isExpired()) || (googleSignInAccount!=null);

        if(isLoggedIn){
            googleSignInClient.signOut();
            LoginManager.getInstance().logOut();
            loginButton.setVisibility(View.GONE);
            signInButton.setVisibility(View.GONE);
            String s = "Logging in...";
            infoText.setText(s);
            if(sp.getString("name","").length()>0){
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                finish();
            }

        }else{
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name","");
            editor.putString("uri", "");
            editor.putString("age", "");
            editor.apply();
            loginButton.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.VISIBLE);
            String s = "Logging with";
            infoText.setText(s);
        }
    }

    class PeopleDetailsAsync extends AsyncTask<String, Void, Person> {

        @Override
        protected Person doInBackground(String... params) {

            Person currPerson=null;

            try {
                PeopleService peopleService = PeopleHelper.setUp(MainActivity.this, params[0]);

                currPerson = peopleService.people().get("people/me")
                        .setRequestMaskIncludeField("person.photos,person.names,person.birthdays")
                        .execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return currPerson;
        }

        @Override
        protected void onPostExecute(Person person) {
            super.onPostExecute(person);
            if(person!=null) {
                List<Birthday> arrayList = person.getBirthdays();
                for (Birthday b : arrayList) {
                    if (b.getDate().getDay() != null && b.getDate().getMonth() != null && b.getDate().getYear() != null) {
                        Date d = Calendar.getInstance().getTime();
                        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
                        String[] date = format.format(d).split(" ");
                        if (Integer.parseInt(date[2]) > b.getDate().getYear()
                                && Integer.parseInt(date[1]) > b.getDate().getMonth()
                                && Integer.parseInt(date[0]) > b.getDate().getDay()) {
                            age = "" + (Integer.parseInt(date[2]) - b.getDate().getYear());
                        } else {
                            if (Integer.parseInt(date[2]) - b.getDate().getYear() > 1) {
                                age = "" + (Integer.parseInt(date[2]) - b.getDate().getYear() - 1);
                            } else {
                                age = "0";
                            }
                        }
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("age", age);
                        editor.apply();
                        Log.e("MainActivity", "person age is:" + age);
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        finish();
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(profileTracker!=null)
            profileTracker.stopTracking();
    }
}
