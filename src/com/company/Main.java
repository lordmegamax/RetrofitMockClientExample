package com.company;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.http.GET;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collections;

public class Main {
    public interface ApiService {
        @GET("/api/")
        public void getJson(Callback<String> callback);
    }

    public static void main(String[] args) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new MockClient())
                .setConverter(new StringConverter())
                .setEndpoint("http://www.example.com").build();

        ApiService service = restAdapter.create(ApiService.class);
        service.getJson(new Callback<String>() {
            @Override
            public void success(String str, Response ignored) {
                // Prints the correct String representation of body.
                System.out.println(str);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.println("Failure, retrofitError" + retrofitError);
            }
        });
    }
}
