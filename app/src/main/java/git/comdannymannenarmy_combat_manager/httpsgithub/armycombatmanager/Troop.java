package git.comdannymannenarmy_combat_manager.httpsgithub.armycombatmanager;

/**
 * Created by User on 2018-03-17.
 */

import java.io.Serializable;

public class Troop implements Serializable {
    private String name;
    private String ATK;
    private String DEF;
    private String DMGmin;
    private String DMGmax;
    private String SPEED;
    private String HP;
    private String RANGE;


    public Troop() {
        name = "";
        ATK = "";
        DEF = "";
        DMGmin = "";
        DMGmax = "";
        SPEED = "";
        HP = "";
        RANGE = "";
    }

    public String showname() {
        if (name != null) {
            return name;
        }
        return "Name not found";
    }

    public String showATK() {
        if (ATK != null) {
            return ATK;
        }
        return "";
    }

    public String showDEF() {
        if (DEF != null) {
            return DEF;
        }
        return "";
    }

    public String showDMGmin() {
        if (DMGmin != null) {
            return DMGmin;
        }
        return "";
    }

    public String showDMGmax() {
        if (DMGmax != null) {
            return DMGmax;
        }
        return "";
    }

    public String showSPEED() {
        if (SPEED != null) {
            return SPEED;
        }
        return "";
    }

    public String showHP() {
        if (HP != null) {
            return HP;
        }
        return "";
    }

    public String showRANGE() {
        if (RANGE != null) {
            return RANGE;
        }
        return "";
    }

    public void setname(String newname) {
        name = newname;
    }

    public void setATK(String newATK){
        ATK = newATK;
    }

    public void setDEF(String newDEF){
        DEF = newDEF;
    }

    public void setDMGmin(String newDMGmin){
        DMGmin = newDMGmin;
    }

    public void setDMGmax(String newDMGmax){
        DMGmax = newDMGmax;
    }

    public void setSPEED(String newSPEED){
        SPEED = newSPEED;
    }

    public void setHP(String newHP){
        HP = newHP;
    }

    public void setRANGE(String newRANGE){ RANGE = newRANGE; }
}
