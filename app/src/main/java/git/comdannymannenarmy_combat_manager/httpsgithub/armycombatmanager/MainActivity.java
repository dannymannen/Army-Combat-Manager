package git.comdannymannenarmy_combat_manager.httpsgithub.armycombatmanager;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.content.Intent;
import android.widget.TextView;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel Cheveyo on 2018-03-04.
 */
public class MainActivity extends AppCompatActivity {

    static final int ADD_REQUEST = 1;
    static final int EDIT_REQUEST = 2;

    protected TabHost tabHost;
    protected Map<String,Troop> troops;
    protected ListView troopListTab1;
    protected ListView troopListTab2ATK;
    protected ListView troopListTab2DEF;
    protected ArrayList<String> troopNames;
    protected String selectedStringFromList;
    protected Troop selectedAttackerFromList;
    protected Troop selectedDefenderFromList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();
        //Manage troops Tab
        TabHost.TabSpec spec = tabHost.newTabSpec("ManageTroops");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Manage Troops");
        tabHost.addTab(spec);
        //Combat Tab
        spec = tabHost.newTabSpec("Combat");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Combat");
        tabHost.addTab(spec);
        // List views
        loadData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.troop_list_view, R.id.troopListTextView, troopNames);
        troopListTab1 = (ListView)findViewById(R.id.troopList);
        troopListTab2ATK = (ListView)findViewById(R.id.attackerList);
        troopListTab2DEF = (ListView)findViewById(R.id.defenderList);
        troopListTab1.setAdapter(adapter);
        troopListTab2ATK.setAdapter(adapter);
        troopListTab2DEF.setAdapter(adapter);

        // The main list in tab 1, Manage troops
        troopListTab1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStringFromList =(String) parent.getItemAtPosition(position);
                Troop selectedTroopFromList = (Troop) troops.get(selectedStringFromList);
                TextView attackRow = (TextView)findViewById(R.id.tableAttack);
                attackRow.setText(selectedTroopFromList.showATK());
                TextView defenceRow = (TextView)findViewById(R.id.tableDefence);
                defenceRow.setText(selectedTroopFromList.showDEF());
                TextView dmgminRow = (TextView)findViewById(R.id.tableDMGmin);
                dmgminRow.setText(selectedTroopFromList.showDMGmin());
                TextView dmgmaxRow = (TextView)findViewById(R.id.tableDMGmax);
                dmgmaxRow.setText(selectedTroopFromList.showDMGmax());
                TextView speedRow = (TextView)findViewById(R.id.tableSpeed);
                speedRow.setText(selectedTroopFromList.showSPEED());
                TextView hpRow = (TextView)findViewById(R.id.tableHp);
                hpRow.setText(selectedTroopFromList.showHP());
                TextView rangeRow = (TextView)findViewById(R.id.tableRange);
                rangeRow.setText(selectedTroopFromList.showRANGE());
            }
        });

        // Attacker list in tab 2, Combat, extract the chosen troop
        troopListTab2ATK.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStringFromAttackerList =(String) parent.getItemAtPosition(position);
                selectedAttackerFromList = (Troop) troops.get(selectedStringFromAttackerList);
            }
        });

        // Defender list in tab 2, Combat, extract the chosen troop
        troopListTab2DEF.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStringFromDefenderList =(String) parent.getItemAtPosition(position);
                selectedDefenderFromList = (Troop) troops.get(selectedStringFromDefenderList);
            }
        });
    }

    // Call the Add activity when the ADD button is pressed
    public void AddNewTroop(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_REQUEST);
    }

    // Remove the chosen troop of the list from the database and the list when the REMOVE button is pressed
    public void RemoveTroop(View view){
        // Update database
        troops.remove(selectedStringFromList);
        troopNames.remove(selectedStringFromList);
        // Update listviews
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.troop_list_view, R.id.troopListTextView, troopNames);
        troopListTab1 = (ListView)findViewById(R.id.troopList);
        troopListTab2ATK = (ListView)findViewById(R.id.attackerList);
        troopListTab2DEF = (ListView)findViewById(R.id.defenderList);
        troopListTab1.setAdapter(adapter);
        troopListTab2ATK.setAdapter(adapter);
        troopListTab2DEF.setAdapter(adapter);
        // Update stat info table
        TextView attackRow = (TextView)findViewById(R.id.tableAttack);
        attackRow.setText("9001");
        TextView defenceRow = (TextView)findViewById(R.id.tableDefence);
        defenceRow.setText("9001");
        TextView dmgminRow = (TextView)findViewById(R.id.tableDMGmin);
        dmgminRow.setText("9001");
        TextView dmgmaxRow = (TextView)findViewById(R.id.tableDMGmax);
        dmgmaxRow.setText("9001");
        TextView speedRow = (TextView)findViewById(R.id.tableSpeed);
        speedRow.setText("9001");
        TextView hpRow = (TextView)findViewById(R.id.tableHp);
        hpRow.setText("9001");
        TextView rangeRow = (TextView)findViewById(R.id.tableRange);
        rangeRow.setText("9001");
        // Save change
        saveData();
    }

    // Call the Edit activity when the EDIT button is pressed
    public void EditTroop(View view){
        if (selectedStringFromList !=  null) {
            Troop editTroop = troops.get(selectedStringFromList);
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("edit troop", new Gson().toJson(editTroop));
            startActivityForResult(intent, EDIT_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Get the resulting troop from the add intent i string format
                Bundle bundle = data.getExtras();
                String jsonObject = bundle.getString("troop");
                // Convert the string to a Troop object
                Troop troop = (Troop)new Gson().fromJson(jsonObject,Troop.class);
                // Add the new Troop to the database
                troops.put(troop.showname(),troop);
                troopNames.add(troop.showname());
                saveData();
            }
        }  else if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Get the resulting troop from the add intent i string format
                Bundle bundle = data.getExtras();
                String jsonObject = bundle.getString("edit troop back");
                // Convert the string to a Troop object
                Troop editTroopBack = (Troop)new Gson().fromJson(jsonObject,Troop.class);
                // Remove the old version of the Troop from the database
                troopNames.remove(selectedStringFromList);
                troops.remove(selectedStringFromList);
                // Add the recently edited version of the Troop to the database
                troops.put(editTroopBack.showname(),editTroopBack);
                troopNames.add(editTroopBack.showname());
                saveData();
            }

        }
        // Update the lists
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.troop_list_view, R.id.troopListTextView, troopNames);
        troopListTab1 = (ListView)findViewById(R.id.troopList);
        troopListTab2ATK = (ListView)findViewById(R.id.attackerList);
        troopListTab2DEF = (ListView)findViewById(R.id.defenderList);
        troopListTab1.setAdapter(adapter);
        troopListTab2ATK.setAdapter(adapter);
        troopListTab2DEF.setAdapter(adapter);
    }

    // Save the database, map, in string format, json, into shared preferences
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(troops);
        editor.putString("task list",json);
        editor.apply();
    }

    // Load the database, map, manually by parsing the obtained json string from the shared preferences
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        troops = new HashMap<String, Troop>();
        String[] bracketTokens = json.split("\\{");
        // For every troop, with its name saved as key, the name is also saved in the object so skip the first split value
        for (int bracketIndex=2;bracketIndex<bracketTokens.length;bracketIndex++) {
            Troop loadedTroop = new Troop();
            String loadedName = "";
            String[] commaTokens = bracketTokens[bracketIndex].split(",");
            // For every attribute a troop has, fixed number of attributes
            for (int commaIndex=0;commaIndex<commaTokens.length;commaIndex++) {
                // Take out the actual value
                String[] colonTokens = commaTokens[commaIndex].split(":");
                String statString = "";
                if (colonTokens.length == 2) {
                    statString = colonTokens[1];
                } else {
                    statString = colonTokens[0];
                }
                // Depending on the index of the attribute, set the corresponding attribute with a value in the current Troop
                switch (commaIndex) {
                    case 0:
                        loadedTroop.setATK(statString.substring(1,statString.length()-1));
                        break;
                    case 1:
                        loadedTroop.setDEF(statString.substring(1,statString.length()-1));
                        break;
                    case 2:
                        loadedTroop.setDMGmax(statString.substring(1,statString.length()-1));
                        break;
                    case 3:
                        loadedTroop.setDMGmin(statString.substring(1,statString.length()-1));
                        break;
                    case 4:
                        loadedTroop.setHP(statString.substring(1,statString.length()-1));
                        break;
                    case 5:
                        loadedTroop.setRANGE(statString.substring(1,statString.length()-1));
                        break;
                    case 6:
                        loadedTroop.setSPEED(statString.substring(1,statString.length()-1));
                        break;
                    case 7:
                        // The last troop in the map has an extra } at the end
                        if (statString.charAt(statString.length()-1) == statString.charAt(statString.length()-2)) {
                            loadedName = statString.substring(1,statString.length()-3);
                        } else {
                            loadedName = statString.substring(1,statString.length()-2);
                        }
                        loadedTroop.setname(loadedName);
                        break;
                    default:
                        break;
                }
            }
            // Add the troop to the map
            troops.put(loadedName,loadedTroop);
        }
        // Create the name list for the list views
        troopNames = new ArrayList<>();
        troopNames.addAll(troops.keySet());
        if (troopNames.contains("task list")) {
            troopNames.remove("task list");
        }
    }

    public void Fight(View view) {
        // If no troops are selected from the lists, don't do anything
        if (selectedAttackerFromList != null && selectedDefenderFromList != null) {
            EditText attackerRollInput = (EditText)findViewById(R.id.attackerRollInput);
            EditText attackerAmountInput = (EditText)findViewById(R.id.AttackerAmountInput);
            EditText rangeToDefInput = (EditText)findViewById(R.id.RangeToDefInput);
            EditText joustingInput = (EditText)findViewById(R.id.JoustingInput);
            EditText defenderRollInput = (EditText)findViewById(R.id.defenderRollInput);
            EditText defenderAmountInput = (EditText)findViewById(R.id.DefenderAmountInput);
            EditText DmgReductionInput = (EditText)findViewById(R.id.DmgReductionInput);

            Switch attackerMeleePenaltySwitch = (Switch) findViewById(R.id.AttackerMeleePenalty);
            Switch defenderMeleePenaltySwitch = (Switch) findViewById(R.id.DefenderMeleePenalty);
            Switch rangeCoverSwitch = (Switch) findViewById(R.id.RangeCover);
            Switch retaliationSwitch = (Switch) findViewById(R.id.Retaliation);
            // If the text for the input texts, that require to have some, don't exists, don't do anything
            if ( !attackerRollInput.getText().toString().isEmpty() && !defenderRollInput.getText().toString().isEmpty()
                    && !attackerAmountInput.getText().toString().isEmpty() && !defenderAmountInput.getText().toString().isEmpty()) {
                System.out.println("Attacker: " + selectedAttackerFromList.showname() + ", Defender: " + selectedDefenderFromList.showname());
                // ATTACKER
                int attackerRoll = -1;
                int attackerAmount = -1;
                int rangeToDef = -1;
                int jousting = -1;
                // Parse the text from the input texts to int:s
                try {
                    attackerRoll = Integer.parseInt(attackerRollInput.getText().toString());
                    attackerAmount = Integer.parseInt(attackerAmountInput.getText().toString());
                    if (rangeToDefInput.getText().toString().isEmpty()) {
                        rangeToDef = 0;
                    } else {
                        rangeToDef = Integer.parseInt(rangeToDefInput.getText().toString());
                    }
                    if (joustingInput.getText().toString().isEmpty()) {
                        jousting = 0;
                    } else {
                        jousting = Integer.parseInt(joustingInput.getText().toString());
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }

                if (attackerRoll <= 0 || attackerRoll > 20) {
                    attackerRoll = -1;
                }

                if (attackerAmount <= 0) {
                    attackerAmount = -1;
                }

                if (rangeToDef < 0) {
                    rangeToDef = -1;
                }

                if (jousting < 0) {
                    jousting = -1;
                }

                int attackerMeleePenalty = 0;
                if (attackerMeleePenaltySwitch.isChecked()) {
                    attackerMeleePenalty = 1;
                }

                // DEFENDER
                int defenderRoll = -1;
                int defenderAmount = -1;
                int DmgReduction = -1;
                // Parse the text from the input texts to int:s
                try {
                    defenderRoll = Integer.parseInt(defenderRollInput.getText().toString());
                    defenderAmount = Integer.parseInt(defenderAmountInput.getText().toString());
                    if (DmgReductionInput.getText().toString().isEmpty()) {
                        DmgReduction = 0;
                    } else {
                        DmgReduction = Integer.parseInt(DmgReductionInput.getText().toString());
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }

                if (defenderRoll <= 0 || defenderRoll > 20) {
                    defenderRoll = -1;
                }

                if (defenderAmount <= 0) {
                    defenderAmount = -1;
                }

                if (DmgReduction < 0 || DmgReduction > 100) {
                    DmgReduction = -1;
                }

                int defenderMeleePenalty = 0;
                if (defenderMeleePenaltySwitch.isChecked()) {
                    defenderMeleePenalty = 1;
                }

                int rangeCover = 0;
                if (rangeCoverSwitch.isChecked()) {
                    rangeCover = 1;
                }

                int retaliation = 0;
                if (retaliationSwitch.isChecked()) {
                    retaliation = 1;
                }

                // FIGHT calculations
                String defenderKilledString = "";
                String retaliationString = "";
                // Ensure that the inputs are in the right corresponding range
                if (attackerRoll != -1 && attackerAmount != -1 &&  rangeToDef != -1 && jousting != -1 && defenderRoll != -1 && defenderAmount != -1 && DmgReduction != -1) {
                    // ATTACKER FIGHT
                    int attackerATK = Integer.parseInt(selectedAttackerFromList.showATK());
                    int attackerDEF = Integer.parseInt(selectedAttackerFromList.showDEF());
                    int attackerDMGmax = Integer.parseInt(selectedAttackerFromList.showDMGmax());
                    int attackerDMGmin = Integer.parseInt(selectedAttackerFromList.showDMGmin());
                    int attackerSPEED = Integer.parseInt(selectedAttackerFromList.showSPEED());
                    int attackerHP = Integer.parseInt(selectedAttackerFromList.showHP());
                    int attackerRANGE = Integer.parseInt(selectedAttackerFromList.showRANGE());

                    int defenderATK = Integer.parseInt(selectedDefenderFromList.showATK());
                    int defenderDEF = Integer.parseInt(selectedDefenderFromList.showDEF());
                    int defenderDMGmax = Integer.parseInt(selectedDefenderFromList.showDMGmax());
                    int defenderDMGmin = Integer.parseInt(selectedDefenderFromList.showDMGmin());
                    int defenderSPEED = Integer.parseInt(selectedDefenderFromList.showSPEED());
                    int defenderHP = Integer.parseInt(selectedDefenderFromList.showHP());
                    int defenderRANGE = Integer.parseInt(selectedDefenderFromList.showRANGE());

                    // Base damage for attacker
                    int ATKDMGbase = (((attackerRoll/20)*(attackerDMGmax-attackerDMGmin))+attackerDMGmin)*attackerAmount;
                    // Attack-Defence difference buff
                    double ATKADDiff = attackerATK-defenderDEF;
                    double ATKI1 = 0;
                    if (ATKADDiff >= 0) {
                        ATKI1 = 0.05 * ATKADDiff;
                        if (ATKI1 > 3) {
                            ATKI1 = 3;
                        }
                    }
                    // Hero skills, not implemented
                    double ATKI2 = 0;
                    // Hero speciality, not implemented
                    double ATKI3 = 0;
                    // Lucky strike, not implemented
                    double ATKI4 = 0;
                    // Creature specials - jousting
                    double ATKI5 = 0.05*jousting;

                    // Total buff DMG for attacker
                    double ATKbuffs = 1 + ATKI1 + ATKI2 + ATKI3 + ATKI4 + ATKI5;

                    // Defence-Attack difference reduction
                    double ATKDADiff = defenderDEF-attackerATK;
                    double ATKR1 = 0;
                    if (ATKDADiff >= 0) {
                        ATKR1 = 0.025 * ATKDADiff;
                        if (ATKR1 > 0.7) {
                            ATKR1 = 0.7;
                        }
                    }
                    // Hero skills, not implemented
                    double ATKR2 = 0;
                    // Hero speciality, not implemented
                    double ATKR3 = 0;
                    // Magic shields, not implemented
                    double ATKR4 = 0;
                    // Melee penalty
                    double ATKR5Melee = 0;
                    if (attackerMeleePenalty == 1) {
                        ATKR5Melee = 0.5;
                    }
                    // Range penalty
                    double ATKR5Range = 0;
                    if (rangeToDef > attackerRANGE) {
                        ATKR5Range = 0.5;
                    }
                    // Obstacle penalty / Range cover
                    double ATKR6 = 0;
                    if (rangeCover == 1) {
                        ATKR6 = 0.5;
                    }
                    // Mind spells, not implemented
                    double ATKR7 = 0;
                    // Creature specials - DMG reduction
                    double ATKR8 = DmgReduction/100;

                    double ATKDMGfinal = ATKDMGbase*ATKbuffs*(1-ATKR1)*(1-ATKR2)*(1-ATKR3)*(1-ATKR4)*(1-ATKR5Melee)*(1-ATKR5Range)*(1-ATKR6)*(1-ATKR7)*(1-ATKR8);
                    int defendersKilled = (int) ATKDMGfinal/defenderHP;
                    if (defendersKilled >= defenderAmount) {
                        defendersKilled = defenderAmount;
                    }
                    int defendersLeft = defenderAmount - defendersKilled;
                    if (defendersLeft < 0) {
                        defendersLeft = 0;
                    }
                    int lastDefenderHp = (int) ATKDMGfinal - (defendersKilled*defenderHP);
                    int lastDefenderHpLeft = defenderHP - lastDefenderHp;
                    String defenderName = selectedDefenderFromList.showname();
                    defenderKilledString = defendersKilled + " out of " + defenderAmount + " " + defenderName + "(s) were killed.\n" + defendersLeft + " " + defenderName + "(s) remaining.\n Last " + defenderName + " took " + lastDefenderHp + " HP in damage and has " + lastDefenderHpLeft + " HP left.";

                    // DEFENDER FIGHT
                    if (retaliation == 1) {
                        // Base damage for defender
                        double DEFDMGbase = (((defenderRoll / 20) * (defenderDMGmax - defenderDMGmin)) + defenderDMGmin) * defenderAmount;
                        // Attack-Defence difference buff
                        double DEFADDiff = defenderATK - attackerDEF;
                        double DEFI1 = 0;
                        if (DEFADDiff >= 0) {
                            DEFI1 = 0.05 * DEFADDiff;
                            if (DEFI1 > 3) {
                                DEFI1 = 3;
                            }
                        }
                        // Total buff DMG for defender
                        double DEFbuffs = 1 + DEFI1;
                        // Defence-Attack difference reduction
                        double DEFDADiff = attackerDEF - defenderATK;
                        double DEFR1 = 0;
                        if (DEFDADiff >= 0) {
                            DEFR1 = 0.025 * DEFDADiff;
                            if (DEFR1 > 0.7) {
                                DEFR1 = 0.7;
                            }
                        }
                        // Melee penalty
                        double DEFRMelee = 0;
                        if (defenderMeleePenalty == 1) {
                            DEFRMelee = 0.5;
                        }

                        double DEFDMGfinal = DEFDMGbase * DEFbuffs * (1 - DEFR1) * (1 - DEFRMelee);
                        int attackersKilled = (int) DEFDMGfinal / attackerHP;
                        if (attackersKilled >= attackerAmount) {
                            attackersKilled = attackerAmount;
                        }
                        int attackersLeft = attackerAmount - attackersKilled;
                        if (attackersLeft < 0) {
                            attackersLeft = 0;
                        }
                        int lastAttackerHp = (int) DEFDMGfinal - (attackersKilled*attackerHP);
                        int lastAttackerHpLeft = attackerHP - lastAttackerHp;
                        String attackerName = selectedAttackerFromList.showname();
                        retaliationString = "\nRetaliation:\n" + attackersKilled + " out of " + attackerAmount + " " + attackerName + "(s) were killed.\n" + attackersLeft + " " + attackerName + "(s) remaining.\n Last " + attackerName + " took " + lastAttackerHp + " HP in damage and has " + lastAttackerHpLeft + " HP left.";
                    }

                    // Display the results in a dialog box
                    AlertDialog.Builder messageBox  = new AlertDialog.Builder(this);
                    String resultString = defenderKilledString + "\n" + retaliationString;
                    messageBox.setMessage(resultString);
                    messageBox.setTitle("Results of fight");
                    messageBox.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog automatically
                                }
                            });
                    messageBox.create().show();
                } else {
                    System.out.println("WRONG INPUT!");
                }
            }
        }
    }
}