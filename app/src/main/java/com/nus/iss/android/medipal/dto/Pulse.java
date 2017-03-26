package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by thushara on 3/22/2017.
 */

public class Pulse extends Measurement {
    private int pulse;

    public Pulse(int pulse, Date date){
        super(date);
        this.pulse = pulse;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

}
