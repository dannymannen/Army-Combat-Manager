package git.comdannymannenarmy_combat_manager.httpsgithub.armycombatmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * Created by Daniel Cheveyo on 2018-03-19.
 */

public class EditActivity extends AppCompatActivity {
    protected Troop editTroop;
    protected Button editButton;

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
        setContentView(R.layout.activity_edit);

        // Get the Troop object, that is going to be edited
        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        String jsonObject = bundle.getString("edit troop");
        editTroop = (Troop)new Gson().fromJson(jsonObject,Troop.class);

        TextView editHead = (TextView) findViewById(R.id.EditHead);
        editHead.setText("Edit: " + editTroop.showname());

        editButton = (Button)findViewById(R.id.editTroopButton);
        editname = (EditText)findViewById(R.id.NameInput);
        editname.setHint(editTroop.showname());
        editATK = (EditText)findViewById(R.id.AttackInput);
        editATK.setHint(editTroop.showATK());
        editDEF = (EditText)findViewById(R.id.DefendInput);
        editDEF.setHint(editTroop.showDEF());
        editDMGmin = (EditText)findViewById(R.id.DmgMinInput);
        editDMGmin.setHint(editTroop.showDMGmin());
        editDMGmax = (EditText)findViewById(R.id.DmgMaxInput);
        editDMGmax.setHint(editTroop.showDMGmax());
        editSPEED = (EditText)findViewById(R.id.SpeedInput);
        editSPEED.setHint(editTroop.showSPEED());
        editHP = (EditText)findViewById(R.id.HitPointInput);
        editHP.setHint(editTroop.showHP());
        editRANGE = (EditText)findViewById(R.id.RangeInput);
        editRANGE.setHint(editTroop.showRANGE());

        editButton.setOnClickListener(
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

                        // If a input text is empty, keep the old text
                        if (newname.isEmpty()) {
                            editTroop.setname(editTroop.showname());
                        } else {
                            editTroop.setname(newname);
                        }

                        if (newATK.isEmpty()) {
                            editTroop.setATK(editTroop.showATK());
                        } else {
                            editTroop.setATK(newATK);
                        }

                        if (newDEF.isEmpty()) {
                            editTroop.setDEF(editTroop.showDEF());
                        } else {
                            editTroop.setDEF(newDEF);
                        }

                        if (newDMGmin.isEmpty()) {
                            editTroop.setDMGmin(editTroop.showDMGmin());
                        } else {
                            editTroop.setDMGmin(newDMGmin);
                        }

                        if (newDMGmax.isEmpty()) {
                            editTroop.setDMGmax(editTroop.showDMGmax());
                        } else {
                            editTroop.setDMGmax(newDMGmax);
                        }

                        if (newSPEED.isEmpty()) {
                            editTroop.setSPEED(editTroop.showSPEED());
                        } else {
                            editTroop.setSPEED(newSPEED);
                        }

                        if (newHP.isEmpty()) {
                            editTroop.setHP(editTroop.showHP());
                        } else {
                            editTroop.setHP(newHP);
                        }

                        if (newRANGE.isEmpty()) {
                            editTroop.setRANGE(editTroop.showRANGE());
                        } else {
                            editTroop.setRANGE(newRANGE);
                        }

                        // Send back the edited version of the Troop
                        setResult(Activity.RESULT_OK, new Intent().putExtra("edit troop back", new Gson().toJson(editTroop)));
                        finish();
                    }
                });
    }
}