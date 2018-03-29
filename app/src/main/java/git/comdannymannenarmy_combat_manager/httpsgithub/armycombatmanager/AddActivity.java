package git.comdannymannenarmy_combat_manager.httpsgithub.armycombatmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

/**
 * Created by Daniel Cheveyo on 2018-03-11.
 */

public class AddActivity extends AppCompatActivity {

    protected Troop troop;
    protected Button addButton;

    protected EditText editname;
    protected EditText editATK;
    protected EditText editDEF;
    protected EditText editDMGmin;
    protected EditText editDMGmax;
    protected EditText editSPEED;
    protected EditText editHP;
    protected EditText editRANGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        troop = new Troop();

        addButton = (Button)findViewById(R.id.addTroopButton);
        editname = (EditText)findViewById(R.id.NameInput);
        editATK = (EditText)findViewById(R.id.AttackInput);
        editDEF = (EditText)findViewById(R.id.DefendInput);
        editDMGmin = (EditText)findViewById(R.id.DmgMinInput);
        editDMGmax = (EditText)findViewById(R.id.DmgMaxInput);
        editSPEED = (EditText)findViewById(R.id.SpeedInput);
        editHP = (EditText)findViewById(R.id.HitPointInput);
        editRANGE = (EditText)findViewById(R.id.RangeInput);

        addButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String newname = editname.getText().toString();
                        String newATK = editATK.getText().toString();
                        String newDEF = editDEF.getText().toString();
                        String newDMGmin = editDMGmin.getText().toString();
                        String newDMGmax = editDMGmax.getText().toString();
                        String newSPEED = editSPEED.getText().toString();
                        String newHP = editHP.getText().toString();
                        String newRANGE = editRANGE.getText().toString();

                        // Every input text has to have a text to add a valid Troop
                        if (!newname.isEmpty() && !newATK.isEmpty() && !newDEF.isEmpty() && !newDMGmin.isEmpty() && !newDMGmax.isEmpty() && !newSPEED.isEmpty() && !newHP.isEmpty()) {
                            troop.setname(newname);
                            troop.setATK(newATK);
                            troop.setDEF(newDEF);
                            troop.setDMGmin(newDMGmin);
                            troop.setDMGmax(newDMGmax);
                            troop.setSPEED(newSPEED);
                            troop.setHP(newHP);
                            if (newRANGE.isEmpty()) {
                                troop.setRANGE("0");
                            } else {
                                troop.setRANGE(newRANGE);
                            }
                            // Send back the new Troop
                            setResult(Activity.RESULT_OK, new Intent().putExtra("troop", new Gson().toJson(troop)));
                            finish();
                        }
                    }
                });
    }
}
