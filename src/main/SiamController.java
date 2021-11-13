package main;

public class SiamController {
    private GameState game_s;
    private RoundState round_s;
    private Player onTurn;
    Board board;
    GUI g;
    private Position selectedPos;

    public SiamController() {
        game_s = GameState.STARTED;
        round_s = RoundState.PICK_FIGURINE;
        onTurn = Player.ELEPHANT;
        board = new Board();
        Cell.setController(this);
        g = new GUI(board, this);
        board.setGUI(g);
    }

    public Player getOnTurn() {
        return onTurn;
    }

    public void clickedOnAnimal(Position p, Player pl) {
        if (game_s == GameState.STARTED) {
            if (round_s == RoundState.PICK_FIGURINE && onTurn == pl) {
                selectedPos = p;
                round_s = RoundState.PICK_DESTINATION;
                g.stateSwitch(game_s, round_s, onTurn);
            } else if (round_s == RoundState.PICK_DESTINATION && onTurn == pl) {
                if (p.equals(Position.bench()) && !selectedPos.equals(Position.bench())) {
                    board.moveToBench(selectedPos, onTurn);
                    round_s = RoundState.PICK_FIGURINE;
                    onTurn = Player.swap(onTurn);
                    g.stateSwitch(game_s, round_s, onTurn);
                }
            }
        }
        System.out.println((pl == Player.ELEPHANT ? "Elefánt" : "Orrszarvú") + " x: " + p.getX() + ", y: " + p.getY());
    }

    public void clickedOnCell(Position p) {
        if (game_s == GameState.STARTED) {
            if (round_s == RoundState.PICK_DESTINATION) {
                Position bench = new Position(-1, -1);
                if (selectedPos.equals(bench)) {
                    if (!board.moveFromBench(p, onTurn)) {
                        return;
                    }
                } else {
                    Direction d = Position.whichWayToStep(selectedPos, p);
                    if (d == null) {
                        return;
                    } else {
                        board.moveOnBoard(selectedPos, d);
                    }
                }
                round_s = RoundState.PICK_FIGURINE;
                onTurn = Player.swap(onTurn);
                g.stateSwitch(game_s, round_s, onTurn);
            }
        }
        System.out.println("cell x: " + p.getX() + ", y: " + p.getY());
    }
}
