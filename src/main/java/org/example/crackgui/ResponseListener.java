package org.example.crackgui;

public interface ResponseListener {
    void onResponse(String response);
    void onComplete();
    void onError(String error);
}
