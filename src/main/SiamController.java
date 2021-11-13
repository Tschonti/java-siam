package main;

import cells.Cell;

public class SiamController {
    private GameState game_s;
    private RoundState round_s;
    private Player onTurn;
    Board board;
    GUI g;
    private Position source;
    private Position dest;
    private Direction movingDirection;

    public SiamController() {
        game_s = GameState.STARTED;
        round_s = RoundState.PICK_FIGURINE;
        onTurn = Player.ELEPHANT;
        board = new Board();
        Cell.setController(this);
        g = new GUI(board, this);
        board.setGUI(g);
    }

    public void clickedOnAnimal(Position p, Player pl) {
        if (game_s == GameState.STARTED) {
            if (round_s == RoundState.PICK_FIGURINE && onTurn == pl) {
                source = p;
                stateChange(game_s, RoundState.next(round_s), onTurn);
            } else if (round_s == RoundState.PICK_DESTINATION && onTurn == pl) {
                if (p.equals(Position.bench()) && !source.equals(Position.bench())) {
                    board.moveToBench(source, onTurn);
                    stateChange(game_s, RoundState.first(), Player.swap(onTurn));
                }
            }
        }
        System.out.println((pl == Player.ELEPHANT ? "Elefánt" : "Orrszarvú") + " x: " + p.getX() + ", y: " + p.getY());
    }

    public void clickedOnCell(Position p) {
        if (game_s == GameState.STARTED) {
            if (round_s == RoundState.PICK_DESTINATION) {
                Position bench = new Position(-1, -1);
                if (source.equals(bench)) {
                    if (!Board.isInOuterCells(p)) {
                        return;
                    }
                } else {
                    movingDirection = Position.whichWayToStep(source, p);
                    if (movingDirection == null) {
                        return;
                    }
                }
                dest = p;
                stateChange(game_s, RoundState.next(round_s), onTurn);
            }
        }
        System.out.println("cell x: " + p.getX() + ", y: " + p.getY());
    }

    public void clickedOnMove() {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_ACTION) {
            stateChange(game_s, RoundState.next(round_s), onTurn);
        }
    }

    public void clickedOnPush() {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_ACTION) {
            board.push(source);

            stateChange(game_s, RoundState.first(), Player.swap(onTurn));
        }
    }

    public void clickedOnDirection(Direction d) {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_DIRECTION) {
            if (source.equals(Position.bench())) {
                board.moveFromBench(dest, onTurn, d);
            } else {
                board.moveOnBoard(source, movingDirection, d);
            }
            stateChange(game_s, RoundState.first(), Player.swap(onTurn));
        }
    }

    public void clickedOnCancel(RoundState cancelFromState) {
        if (game_s == GameState.STARTED && round_s == cancelFromState) {
            stateChange(game_s, RoundState.previous(round_s), onTurn);
        }
    }

    public void clickedOnActionCancel() {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_DESTINATION) {
            stateChange(game_s, RoundState.previous(round_s), onTurn);
        }
    }

    public void clickedOnFigurineCancel() {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_ACTION) {
            source = null;
            stateChange(game_s, RoundState.previous(round_s), onTurn);
        }
    }

    public void clickedOnDestinationCancel() {
        if (game_s == GameState.STARTED && round_s == RoundState.PICK_DIRECTION) {
            dest = null;
            stateChange(game_s, RoundState.previous(round_s), onTurn);
        }
    }

    private void stateChange( GameState gs, RoundState rs, Player p) {
        round_s = rs;
        game_s = gs;
        onTurn = p;
        g.stateSwitch(game_s, round_s, onTurn);
    }
}
