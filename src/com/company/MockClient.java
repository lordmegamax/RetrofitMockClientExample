package com.company;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

public class MockClient implements Client {
    @Override
    public Response execute(Request request) throws IOException {
        URI uri = URI.create(request.getUrl());
        String responseString;

        if (uri.getPath().equals("/api/"))
            responseString = "{result:\"ok\"}";
        else
            responseString = "{result:\"error\"}";

        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST,
                new TypedByteArray("application/json", responseString.getBytes()));
    }
}
