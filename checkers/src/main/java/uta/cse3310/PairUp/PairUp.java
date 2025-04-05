package uta.cse3310.PairUp;

import uta.cse3310.DB.DB;
import uta.cse3310.PairUp.module.PairUpModule;

public class PairUp {
    private PairUpModule pairUpModule;

    public PairUp(DB db) {
        this.pairUpModule = new PairUpModule();
    }

    public PairUpModule getPairUpModule() {
        return pairUpModule;
    }
}
