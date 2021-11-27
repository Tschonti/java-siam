package main;

/**
 * Egy kör lehetséges állapotai
 */
public enum RoundState {
    PICK_FIGURINE,
    PICK_ACTION,
    PICK_DESTINATION,
    PICK_DIRECTION;

    /**
     * Visszaadja a soron következő állapotot
     * @return következő állapot
     */
    public RoundState next() {
        switch (this) {
            case PICK_FIGURINE: return RoundState.PICK_ACTION;
            case PICK_ACTION: return RoundState.PICK_DESTINATION;
            case PICK_DESTINATION: return RoundState.PICK_DIRECTION;
            default: return RoundState.PICK_FIGURINE;
        }
    }

    /**
     * Visszaadja az előző állapotot
     * @return előző állapot
     */
    public RoundState previous() {
        switch (this) {
            case PICK_ACTION: return RoundState.PICK_FIGURINE;
            case PICK_DESTINATION: return RoundState.PICK_ACTION;
            case PICK_DIRECTION: return RoundState.PICK_DESTINATION;
            default: return RoundState.PICK_DIRECTION;
        }
    }

    /**
     * Visszaadja a kör alapállapotát
     * @return RoundState.PICK_FIGURINE
     */
    public static RoundState first() {
        return RoundState.PICK_FIGURINE;
    }
}
