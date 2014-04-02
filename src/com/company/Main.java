package com.company;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;

public class Main {
    public static final String WEBSITE_URL = "http://www.example.com";

    public interface ApiService {
        @GET("/api/")
        public void getJson(Callback<String> callback);
    }

    public static void main(String[] args) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new MockClient())
                .setConverter(new StringConverter())
                .setEndpoint(WEBSITE_URL).build();

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
