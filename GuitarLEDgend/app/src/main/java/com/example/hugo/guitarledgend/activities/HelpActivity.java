package com.example.hugo.guitarledgend.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView helpText = (TextView) findViewById(R.id.help);
        helpText.setText("PROFILS : \n " +
                "Vous pouvez changer de profil à tout moment. " +
                "Pour cela, sélectionnez le menu déroulant sur le côté, et cliquez sur Profils. " +
                "Attention, cela vous fait cependant revenir au début de l'application. \n \n" +
                "STATISTIQUES : \n" +
                "Dans le menu statistiques, vous pouvez voir, pour chaque morceau, vos scores au fil du temps." +
                "Dans un premier temps, vous voyez un graphique de vos score globaux, " +
                "puis vous pouvez voir les détails de chacune des fois où vous avez joué un morceau. \n \n" +
                "PARTITIONS : \n" +
                "Pour ajouter une partiton, allez dans Start -> Ajouter." +
                "Vous pouvez supprimer à tout moment une partition dans le menu Préférences. \n \n" +
                "JOUER : \n" +
                "Choisissez un morceau à jouer (Start -> Choisir)." +
                "Choisissez ensuite votre vitesse, puis jouez ! \n \n" +
                "APRÈS AVOIR JOUÉ : \n" +
                "Vous pouvez rejouer le morceau en entier, ou rejouer une suele partie." +
                "Pour cela, définissez une zone à rejouer en cliquant sur le graphique de résultat. \n \n");
    }
}
