package main;

import cells.Cell;

import java.io.*;

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
    }

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
                board.reAddCellListeners();

                source = newCont.source;
                dest = newCont.dest;
                strength = newCont.strength;
                movingDirection = newCont.movingDirection;
                stateChange(game_s, newCont.round_s, newCont.onTurn);

                if (source.equals(Position.bench()) && round_s != RoundState.PICK_FIGURINE) {
                    g.toggleSupplyHighlight(onTurn, true, true);
                } else if (!source.equals(Position.bench()) && round_s == RoundState.PICK_DESTINATION) {
                    g.toggleSupplyHighlight(onTurn, true, false);
                }
            } catch(IOException | ClassNotFoundException ex) {
                g.errorMessage("This file can't be imported, it most likely isn't a file created by this game.");
            }
        }
    }

    public void saveGame() {
        File file = g.showFileChooser("Save");
        if (file != null) {
            try {
                FileOutputStream f =new FileOutputStream(file);
                ObjectOutputStream out =new ObjectOutputStream(f);
                out.writeObject(this);
                out.close();
            }
            catch(IOException ex) {
                g.errorMessage("There was a problem with saving, please try again!");
            }
        }
    }

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
                    stateChange(game_s, RoundState.next(round_s), onTurn);
                    break;
                case PICK_DESTINATION:
                    if (p.equals(Position.bench()) && !source.equals(Position.bench())) {
                        board.toggleMoveHighlights(source, false);
                        board.toggleCenterHighlights(source, false);
                        g.toggleSupplyHighlight(onTurn, false, false);
                        board.moveToBench(source, onTurn);
                        stateChange(game_s, RoundState.first(), Player.swap(onTurn));
                    } else if (source.equals(p)) {
                        g.toggleSupplyHighlight(onTurn, false, false);
                        movingDirection = null;
                        board.toggleMoveHighlights(source, false);
                        stateChange(game_s, RoundState.next(round_s), onTurn);
                    }
            }
        }
        System.out.println((pl == Player.ELEPHANT ? "Elefánt" : "Orrszarvú") + " x: " + p.getX() + ", y: " + p.getY());
    }

    public void clickedOnCell(Position p) {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_DESTINATION) {
            if (source.equals(Position.bench())) {
                if (!Position.isInOuterCells(p)) {
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
            stateChange(game_s, RoundState.next(round_s), onTurn);
        }
        System.out.println("cell x: " + p.getX() + ", y: " + p.getY());
    }

    public void clickedOnMove() {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_ACTION) {
            if (source.equals(Position.bench())) {
                board.toggleOuterHighlights(true);
            } else {
                board.toggleMoveHighlights(source, true);
                g.toggleSupplyHighlight(onTurn, true, false);
            }
            stateChange(game_s, RoundState.next(round_s), onTurn);
        }
    }

    public void clickedOnPush() {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_ACTION) {
            board.toggleCenterHighlights(source, false);
            board.push(source);
            stateChange(game_s, RoundState.first(), Player.swap(onTurn));
        }
    }

    public void clickedOnDirection(Direction d) {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_DIRECTION) {
            if (source.equals(Position.bench())) {
                board.toggleCenterHighlights(dest, false);
                g.toggleSupplyHighlight(onTurn, false, true);
                board.moveFromBench(dest, onTurn, d);
            } else {
                board.toggleCenterHighlights(source, false);
                board.toggleCenterHighlights(dest, false);
                board.moveOnBoard(source, movingDirection, d);
            }
            stateChange(game_s, RoundState.first(), Player.swap(onTurn));
        }
    }

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
            stateChange(game_s, RoundState.previous(round_s), onTurn);
        }
    }

    public void gameOver(Player winner) {
        stateChange(GameState.GAME_OVER, round_s, winner);
    }

    public int getStrength() {
        return strength;
    }

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
}
