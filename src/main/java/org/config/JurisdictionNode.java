package org.config;

import org.config.util.ActionTypeFlag;


public final class JurisdictionNode {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private String jurisdiction;
    private Boolean[] flags;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public void setFlags(Boolean[] flags) {
        this.flags = flags.clone();
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public Boolean getJurisdictionFlagValue(ActionTypeFlag flag) {
        return (flag != null) ? flags[flag.ordinal()] : null;
    }
}
