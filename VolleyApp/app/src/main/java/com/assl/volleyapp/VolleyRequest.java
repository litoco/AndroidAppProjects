package com.assl.volleyapp;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class VolleyRequest {

    private Context context;

    public VolleyRequest(Context context) {
        this.context = context;
    }

    public MutableLiveData<String> createNewRegistrationRequest(String urlString, RegistrationBody registrationBody){

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        final MutableLiveData<String> responseJSON = new MutableLiveData<>();

        try {
            JSONObject registrationObject = new JSONObject();
            registrationObject.put("FirstName", registrationBody.getFirstName());
            registrationObject.put("LastName", registrationBody.getLastName());
            registrationObject.put("UserName", registrationBody.getUsername());
            registrationObject.put("EmailID", registrationBody.getEmailID());
            registrationObject.put("IsEmailVerified", registrationBody.getIsEmailVerified());
            registrationObject.put("MobileNo", registrationBody.getMobileNumber());
            registrationObject.put("IsMobileVerified", registrationBody.getIsMobileVerified());
            registrationObject.put("DateOfBirth", registrationBody.getDateOfBirth());
            registrationObject.put("Password", registrationBody.getPassword());
            final JSONObject signUpRequestObject = new JSONObject();
            signUpRequestObject.put("ProfileCode", "PFR240720200007");
            signUpRequestObject.put("SellerCode", "SLR240720200007");
            signUpRequestObject.put("Registration", registrationObject);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlString,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseJSON.postValue("s "+response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseJSON.postValue("e "+error);
                        }
                    }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjkjZUNvbW1lcmNlIzciLCJyb2xlIjoiQWRtaW4iLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3ZlcnNpb24iOiJWMS4wIiwibmJmIjoxNTk1NTgzNTU5LCJleHAiOjE1OTgxNzU1NTksImlhdCI6MTU5NTU4MzU1OX0.izjIp-GCO-yWB3Pa6mbCZTAG4ikfrCoB2h8V1uX2F_o");
                    return map;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return signUpRequestObject.toString().getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };//05001070002346 , HDFC0000500

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error", "Ill formatted JSON registration object: "+e.toString());
        }

        return responseJSON;
    }

    public MutableLiveData<String> signInUserRequest(SignInUserDetails signInUserDetails, String urlString) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        final MutableLiveData<String> responseJSON = new MutableLiveData<>();

        try {
            final JSONObject signInRequestObject = new JSONObject();
            signInRequestObject.put("ProfileCode", "PFR240720200007");
            signInRequestObject.put("SellerCode", "SLR240720200007");
            signInRequestObject.put("UserName", signInUserDetails.getUsername());
            signInRequestObject.put("Password", signInUserDetails.getPassword());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlString,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseJSON.postValue("s "+response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseJSON.postValue("e "+error);
                        }
                    }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjkjZUNvbW1lcmNlIzciLCJyb2xlIjoiQWRtaW4iLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3ZlcnNpb24iOiJWMS4wIiwibmJmIjoxNTk1NTgzNTU5LCJleHAiOjE1OTgxNzU1NTksImlhdCI6MTU5NTU4MzU1OX0.izjIp-GCO-yWB3Pa6mbCZTAG4ikfrCoB2h8V1uX2F_o");
                    return map;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return signInRequestObject.toString().getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error", "Ill formatted JSON registration object: "+e.toString());
        }

        return responseJSON;
    }
}
