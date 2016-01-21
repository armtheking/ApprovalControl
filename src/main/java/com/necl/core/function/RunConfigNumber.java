package com.necl.core.function;

import com.necl.core.model.ConfigSystem;

/**
 *
 * return Number current with variable ConfigMemo (TYPE1511/0001) * ticket current use 
 * and return current number ConfigRun + 1  (TYPE1511/0002) 
 */
public class RunConfigNumber {

    public static ConfigSystem getNumberTicket(ConfigSystem configSystem) {
        RunConfigNumber runConfigNumber = new RunConfigNumber();
        return runConfigNumber.createNumberTicket(configSystem);
    }

    private ConfigSystem createNumberTicket(ConfigSystem configSystem) {
        String ticketType = configSystem.getConfigPrefix();
        String ticketYYMM = configSystem.getConfigText();
        String ticketNumberRun = configSystem.getConfigRun();

        String numberTicket = ticketType + ticketYYMM + ticketNumberRun;

        String numberRunAfterPlus = plusNumberRun(configSystem.getConfigRun());

        configSystem.setConfigMemo(numberTicket);
        configSystem.setConfigRun(numberRunAfterPlus);

        return configSystem;
    }

    // Plus TicketNumber 
    private String plusNumberRun(String strNumber) {

        int number = Integer.parseInt(strNumber);
        int lengthInDB = 4;
        number++;

        strNumber = String.valueOf(number);

        int round = lengthInDB - strNumber.length();

        for (int i = 0; i < round; i++) {
            strNumber = "0" + strNumber;
        }

        return strNumber;
    }

}
