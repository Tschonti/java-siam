package main;

import cells.Cell;

public class SiamController {
    private GameState game_s;
    private RoundState round_s;
    private Player onTurn;

    private Board board;
    private final GUI g;

    private Position source;
    private Position dest;
    private Direction movingDirection;
    private int strength;

    public SiamController() {
        game_s = GameState.NOT_STARTED;
        board = new Board();
        Cell.setController(this);
        Cell.setBoard(board);
        g = new GUI(board, this);
        board.setGUI(g);
    }

    public void newGame() {
        if (game_s == GameState.STARTED) {
            if (!g.confirmNewGame()) {
                return;
            }
        }

        board = new Board();
        Cell.setBoard(board);
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
        String filename = g.showFileChooser("Load");
        if (filename != null) {
            System.out.println(filename);
        }
    }

    public void saveGame() {
        String filename = g.showFileChooser("Save");
        if (filename != null) {
            System.out.println(filename);
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
        g.roundStateSwitch(round_s, onTurn);
    }
}
