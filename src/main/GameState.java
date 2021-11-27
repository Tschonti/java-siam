package main;

/**
 * A játék állapotai
 */
public enum GameState {
    /**
     * Nem kezdődött el játék, csak a New Game és a Load Game gomboknak van hatásuk.
     */
    NOT_STARTED,
    /**
     * Elkezdődött a játék
    */
    STARTED,
    /**
     * Végetért a játék, valamelyik játékos győzött. Csak a New Game és a Load Game gomboknak van hatásuk.
     */
    GAME_OVER
}
