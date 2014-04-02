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

    static class StringConverter implements Converter {

        @Override
        public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
            String text = null;
            try {
                text = fromStream(typedInput.in());
            } catch (IOException ignored) {/*NOP*/ }

            return text;
        }

        @Override
        public TypedOutput toBody(Object o) {
            return null;
        }

        public static String fromStream(InputStream in) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append(newLine);
            }
            return out.toString();
        }
    }

    public static class MockClient implements Client {
        @Override
        public Response execute(Request request) throws IOException {
            URI uri = URI.create(request.getUrl());
            String responseString;

            if (uri.getPath().equals("/api/")) {
                responseString = "{result:\"ok\"}";
            } else {
                responseString = "{result:\"error\"}";
            }

            return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST,
                    new TypedByteArray("application/json", responseString.getBytes()));
        }
    }
}
