package dondesoi.don_de_soi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.*;

import dondesoi.don_de_soi.ressources.ConstantValues;
import dondesoi.don_de_soi.R;

public class InfosActivity extends BaseActivity {

    int i;
    //HashMap<String, Integer> lookup = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_infos);
        getLayoutInflater().inflate(R.layout.activity_infos, view_stub);
        setTitle(this.getTitle());

        CoordinatorLayout myRoot = (CoordinatorLayout) findViewById(R.id.my_root);
        LinearLayout a = new LinearLayout(this);
        a.setOrientation(LinearLayout.VERTICAL);
        List<String> givesList = ConstantValues.getGivesList();
        List<Integer> iconlist = ConstantValues.geticonlist();
        //get window size
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        System.out.println(width);
        for(i = 1; i < givesList.size(); i++){
            Button myButton = new Button(this);
            myButton.setId(i);
            //myButton.getLayoutParams().width = width;
            myButton.setBackgroundColor(0xffffff);
            myButton.setWidth(width);
            myButton.setHeight(height/(givesList.size()+1));
            myButton.setCompoundDrawablesWithIntrinsicBounds(iconlist.get(i), 0, 0, 0);

            myButton.setText(givesList.get(i));
            myButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)  //TODO : fix the bug : overwrite all the previous methods
                {
                    Intent intent = new Intent(InfosActivity.this, MainActivity.class);
                    int index = v.getId();
                    intent.putExtra(ConstantValues.INDEX_LIST, index);
                    startActivity(intent); // this will start new Activity where you plot a graph.
                }
            });
            a.addView(myButton);
        }
        myRoot.addView(a);
    }
}