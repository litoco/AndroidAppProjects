package com.example.assignment;

import android.content.Context;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;

import java.io.IOException;

public class PeopleHelper {
    private static final String APPLICATION_NAME = "Assignment";


    public static PeopleService setUp(Context context, String serverAuthCode) throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        String redirectUrl = "";


        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                httpTransport,
                jsonFactory,
                context.getString(R.string.google_api_id_web),
                context.getString(R.string.google_api_secret),
                serverAuthCode,
                redirectUrl).execute();

        GoogleCredential credential = new GoogleCredential.Builder()
                .setClientSecrets(context.getString(R.string.google_api_id_web),
                        context.getString(R.string.google_api_secret))
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .build()
                .setFromTokenResponse(tokenResponse);

        return new PeopleService.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
