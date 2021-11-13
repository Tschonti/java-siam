package main;

public enum RoundState {
    PICK_FIGURINE,
    PICK_ACTION,
    PICK_DESTINATION,
    PICK_DIRECTION;

    public static RoundState next(RoundState rs) {
        switch (rs) {
            case PICK_FIGURINE: return RoundState.PICK_ACTION;
            case PICK_ACTION: return RoundState.PICK_DESTINATION;
            case PICK_DESTINATION: return RoundState.PICK_DIRECTION;
            default: return RoundState.PICK_FIGURINE;
        }
    }

    public static RoundState previous(RoundState rs) {
        switch (rs) {
            case PICK_ACTION: return RoundState.PICK_FIGURINE;
            case PICK_DESTINATION: return RoundState.PICK_ACTION;
            case PICK_DIRECTION: return RoundState.PICK_DESTINATION;
            default: return RoundState.PICK_DIRECTION;
        }
    }

    public static RoundState first() {
        return RoundState.PICK_FIGURINE;
    }
}
