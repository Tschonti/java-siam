package main;

import cells.Cell;

import java.io.*;

/**
 * A játék kontrollere. Számon tartja az aktuális játék és kör állapotát,
 * a cellák és gombok kattintás-értesítései alapján meghívja a tábla és a GUI megfelelő függvényeit.
 * Ilyen objektum szerializálódik mentéskor.
 */
public class SiamController implements Serializable {
    private GameState game_s;
    private RoundState round_s;
    private Player onTurn;

    private Board board;
    transient private final GUI g;

    private Position source;
    private Position dest;
    private Direction movingDirection;
    private int strength;

    public SiamController() {
        game_s = GameState.NOT_STARTED;
        board = new Board();
        g = new GUI(board, this);
        board.setGUI(g);
        Cell.setController(this);
    }

    /**
     * Új játék kezdése. Ha egy játék jelenleg aktív,
     * akkor megkérdezi a felhasználót, hogy biztosan új játékot akar-e kezdeni.
     * Ha igen, akkor új táblát készít, beállítja a cellák statikus mezőit,
     * kirajzoltatja a táblát és a cserepadokat a GUI-val, valamint beállítja a kezdőállapotokat.
     * @param b Az új tábla, amit használni fog a játék. Lehet null, ekkor teljesen új tábla jön létre.
     */
    public void newGame(Board b) {
        if (game_s == GameState.STARTED) {
            if (!g.confirmNewGame()) {
                return;
            }
        }
        board = b == null ? new Board() : b;
        Cell.setBoard(board);
        Cell.setController(this);
        g.setBoard(board);
        g.drawBoard();
        g.drawSupply(Player.ELEPHANT);
        g.drawSupply(Player.RHINO);
        board.setGUI(g);
        stateChange(GameState.STARTED, RoundState.PICK_FIGURINE, Player.ELEPHANT);

        g.toggleSupplyHighlight(Player.ELEPHANT, false, true);
        g.toggleSupplyHighlight(Player.RHINO, false, true);
    }

    /**
     * Egy FileChooser-t mutat a felhasználónak, hogy válassza ki a betölteni kívánt játékot.
     * Ha a visszakapott fájl létezik, akkor megpróbálja deszerializálni a fájlt. Ha nem sikerül, hibaüzenetet dob.
     * Ha egy játék jelenleg aktív, akkor megkérdezi a felhasználót, hogy biztosan el akarja-e dobni azt.
     * Ha igen, akkor a newGame() függvénnyel inicializálja a betöltött játékot és minden szükséges változót átmásol ebbe az objektumba.
     */
    public void loadGame() {
        File file = g.showFileChooser("Load");
        if (file != null) {
            try {
                FileInputStream f = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(f);
                SiamController newCont = (SiamController)in.readObject();
                in.close();

                if (game_s == GameState.STARTED) {
                    if (!g.confirmNewGame()) {
                        return;
                    }
                }

                game_s = GameState.NOT_STARTED;
                newGame(newCont.board);
                board.reload();

                source = newCont.source;
                dest = newCont.dest;
                strength = newCont.strength;
                movingDirection = newCont.movingDirection;
                stateChange(game_s, newCont.round_s, newCont.onTurn);

                if (source != null &&  source.equals(Position.bench()) && round_s != RoundState.PICK_FIGURINE) {
                    g.toggleSupplyHighlight(onTurn, true, true);
                } else if (source != null && !source.equals(Position.bench()) && round_s == RoundState.PICK_DESTINATION) {
                    g.toggleSupplyHighlight(onTurn, true, false);
                }
            } catch(IOException | ClassNotFoundException ex) {
                g.errorMessage("This file can't be imported, it most likely isn't a file created by this game.");
            }
        }
    }

    /**
     * Egy FileChooser-t mutat a felhasználónak, hogy válassza ki a mentés helyét és a fájl nevét.
     * Ide szerializálja önmagát. Ha valami hiba lép fel, hibaüzenetet dob.
     * Ha sikerült, értesíti a felhasználót.
     */
    public void saveGame() {
        File file = g.showFileChooser("Save");
        if (file != null) {
            try {
                FileOutputStream f =new FileOutputStream(file);
                ObjectOutputStream out =new ObjectOutputStream(f);
                out.writeObject(this);
                out.close();
                g.successMessage("The game has been saved successfully!");
            }
            catch(IOException ex) {
                g.errorMessage("There was a problem with saving, please try again!");
                ex.printStackTrace();
            }
        }
    }

    /**
     * Animal-ből származó objektumok vagy a cserepadok ezzel a fügvénnyel értesítik a controllert, hogy rájuk kattintottak.
     * Ha a játék nem kezdődött el vagy már végetért, akkor nem történik semmi.
     * Ha PICK_FIGURINE állapotban vagyunk, akkor a kattintott állat lesz a kiválasztott állat (ha a körön lévő játékosé). Következő állapot: PICK_ACTION
     * Ha PICK_DESTINATION állapotban vagyunk és a cserepadra kattintott, akkor a kiválasztott állatot a cserepadra rakjuk. Következő állapot: PICK_FIGURINE és körön lévő játékos váltás!
     * Ha PICK_DESTINATION állapotban vagyunk és akiválasztott állatra kattintott, akkor a felhasználó helyben akar maradni az állattal. Következő állapot: PICK_DIRECTION
     * @param p Annak az állatnak a pozíciója, akire kattintottak.
     * @param pl Állat típusa
     */
    public void clickedOnAnimal(Position p, Player pl) {
        if (game_s == GameState.STARTED && onTurn == pl) {
            switch (round_s) {
                case PICK_FIGURINE:
                    source = p;
                    strength = board.calculateStrength(source);
                    if (!source.equals(Position.bench())) {
                        board.toggleCenterHighlights(source, true);
                    } else {
                        g.toggleSupplyHighlight(onTurn, true, true);
                    }
                    stateChange(game_s, round_s.next(), onTurn);
                    break;
                case PICK_DESTINATION:
                    if (p.equals(Position.bench()) && !source.equals(Position.bench())) {
                        board.toggleMoveHighlights(source, false);
                        board.toggleCenterHighlights(source, false);
                        g.toggleSupplyHighlight(onTurn, false, false);
                        board.moveToBench(source, onTurn);
                        stateChange(game_s, RoundState.first(), onTurn.swap());
                    } else if (source.equals(p)) {
                        g.toggleSupplyHighlight(onTurn, false, false);
                        movingDirection = null;
                        board.toggleMoveHighlights(source, false);
                        stateChange(game_s, round_s.next(), onTurn);
                    }
            }
        }
    }

    /**
     * Üres cellák ezzel a fügvénnyel értesítik a controllert, hogy rájuk kattintottak.
     * Ha a játék nem kezdődött el vagy már végetért, akkor nem történik semmi.
     * Ha nem PICK_DESTINATION állapotban vagyunk, akkor nem történik semmi.
     * Ha a kiválasztott pozícióba nem léphet a kiválasztott állat, akkor nem történik semmi.
     * Egyébként a cél elmentődik, és a következő állapot: PICK_DIRECTION
     * @param p Annak a cellának a pozíciója, akire kattintottak.
     */
    public void clickedOnCell(Position p) {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_DESTINATION) {
            if (source.equals(Position.bench())) {
                if (!p.isInOuterCells()) {
                    return;
                }
                board.toggleOuterHighlights(false);
            } else {
                movingDirection = Position.whichWayToStep(source, p);
                if (movingDirection == null) {
                    return;
                }
                g.toggleSupplyHighlight(onTurn, false, false);
                board.toggleMoveHighlights(source, false);
            }
            dest = p;
            board.toggleCenterHighlights(dest, true);
            stateChange(game_s, round_s.next(), onTurn);
        }
    }

    /**
     * PickActionPanel ezzel értesíti a controllert, hogy a felhasználó a mozgatás akciót választotta.
     * Ha a játék elkezdődött és PICK_ACTION állapotban vagyunk, akkor PICK_DESTINATION állapotba lépünk,
     * közben a lehetséges lépések celláit kiszinezzük.
     */
    public void clickedOnMove() {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_ACTION) {
            if (source.equals(Position.bench())) {
                board.toggleOuterHighlights(true);
            } else {
                board.toggleMoveHighlights(source, true);
                g.toggleSupplyHighlight(onTurn, true, false);
            }
            stateChange(game_s, round_s.next(), onTurn);
        }
    }

    /**
     * PickActionPanel ezzel értesíti a controllert, hogy a felhasználó a tolás akciót választotta.
     * Ha a játék elkezdődött és PICK_ACTION állapotban vagyunk, akkor kezdeményezzük a tolást a táblán,
     * és PICK_FIGURINE állapotba lépünk, valamint a másik játékos lesz soron.
     */
    public void clickedOnPush() {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_ACTION) {
            board.toggleCenterHighlights(source, false);
            board.startPush(source);
            stateChange(game_s, RoundState.first(), onTurn.swap());
        }
    }

    /**
     * PickDirectionPanel ezzel értesíti a controllert, hogy a felhasználó melyik irányt választotta.
     * Ha a játék elkezdődött és PICK_DIRECTION állapotban vagyunk, akkor a kiválasztott állatot a
     * kiválasztott pozícióba mozgatjuk, majd d irányba forgatjuk a board megfelelő metódusaival.
     * Végül PICK_FIGURINE állapotba lépünk, valamint a másik játékos lesz soron.
     * @param d Az irány, amire a felhasználó kattintott
     */
    public void clickedOnDirection(Direction d) {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_DIRECTION) {
            if (source.equals(Position.bench())) {
                board.toggleCenterHighlights(dest, false);
                g.toggleSupplyHighlight(onTurn, false, true);
                board.moveFromBench(dest, onTurn, d);
            } else {
                board.toggleCenterHighlights(source, false);
                board.toggleCenterHighlights(dest, false);
                board.moveOnBoardAndRotate(source, movingDirection, d);
            }
            stateChange(game_s, RoundState.first(), onTurn.swap());
        }
    }

    /**
     * A panelek ezzel a metódussal értesítik a controllert, hogy a felhasználó a Cancel gombra kattintott.
     * Ha a jelen állapot megegyezik a panel-től kapott állapottal, akkor egy állapotot visszalépünk,
     * és visszaállítjuk a háttérszíneket és változókat is ennek megfelelően.
     * @param cancelFromState Az értesítő panelhez tartozó állapot
     */
    public void clickedOnCancel(RoundState cancelFromState) {
        if (game_s == GameState.STARTED && round_s == cancelFromState) {
            switch (cancelFromState) {
                case PICK_ACTION:
                    if (!source.equals(Position.bench())) {
                        board.toggleCenterHighlights(source, false);
                    } else  {
                        g.toggleSupplyHighlight(onTurn, false, true);
                    }
                    source = null;

                    break;
                case PICK_DIRECTION:
                    board.toggleCenterHighlights(dest, false);
                    if (source.equals(Position.bench())) {
                        board.toggleOuterHighlights(true);
                    } else {
                        board.toggleMoveHighlights(source, true);
                        g.toggleSupplyHighlight(onTurn, true, false);
                    }
                    dest = null;
                    break;
                case PICK_DESTINATION:
                    if (source.equals(Position.bench())) {
                        board.toggleOuterHighlights(false);
                    } else {
                        board.toggleMoveHighlights(source, false);
                        g.toggleSupplyHighlight(onTurn, false, false);
                    }
            }
            stateChange(game_s, round_s.previous(), onTurn);
        }
    }

    /**
     * Egy állat objektum ezzel a metódussal értesíti a controllett, hogy ő tolt le egy sziklát és megnyerte a játékot.
     * GAME_OVER állapotba lépünk.
     * @param winner A győztes játékos
     */
    public void gameOver(Player winner) {
        stateChange(GameState.GAME_OVER, round_s, winner);
    }

    public int getStrength() {
        return strength;
    }

    /**
     * Állapotváltozás
     * @param gs az új GameState
     * @param rs az új RoundState
     * @param p az új játékos a soron
     */
    private void stateChange( GameState gs, RoundState rs, Player p) {
        if (game_s != gs ) {
            g.gameStateSwitch(gs, p);
        }
        round_s = rs;
        game_s = gs;
        onTurn = p;
        if (gs != GameState.GAME_OVER) {
            g.roundStateSwitch(round_s, onTurn);
        }
    }

    public static void main(String[] args) {
        new SiamController();
    }
}
