package com.example.a2lavea02.mapping;


import android.preference.PreferenceActivity;
import android.os.Bundle;


public class Preference_Activity extends PreferenceActivity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
