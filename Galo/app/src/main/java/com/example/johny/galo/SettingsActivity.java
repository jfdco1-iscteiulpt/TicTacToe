package com.example.johny.galo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by FormaPC10 on 18/07/2017.
 */

public class SettingsActivity extends PreferenceActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.definicoes_jogo);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        final ListPreference lista_simbolos = (ListPreference)findPreference("startsym");

        lista_simbolos.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                lista_simbolos.setSummary((String)newValue);
                return true;
            }
        });

        String valorlista = sharedPref.getString("startsym",null);

        if(valorlista!=null){

            lista_simbolos.setSummary(valorlista);

        }


        final EditTextPreference jogador1 = (EditTextPreference)findPreference("p1");

        jogador1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String novo_valor = (String)newValue;
                jogador1.setSummary(novo_valor);
                return true;
            }
        });

        String jogador1_atual = sharedPref.getString("p1",null);

        if(jogador1_atual != null){

            jogador1.setSummary(jogador1_atual);

        }

        final EditTextPreference jogador2 = (EditTextPreference)findPreference("p2");

        jogador2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String novo_valor = (String)newValue;
                jogador2.setSummary(novo_valor);
                return true;
            }
        });

        String jogador2_atual = sharedPref.getString("p2",null);

        if(jogador2_atual != null){

            jogador2.setSummary(jogador2_atual);

        }


    }
}
