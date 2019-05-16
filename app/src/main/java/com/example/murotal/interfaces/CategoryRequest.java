package com.example.murotal.interfaces;

import com.example.murotal.ownmodal.Category;

import java.util.ArrayList;

public interface CategoryRequest {
    void onStart();
    void onEndt(String success, ArrayList<Category> categories);
}
