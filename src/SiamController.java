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
        g = new GUI(board);
        board.setGUI(g);
    }

    public void clickedOnAnimal(Position p, Player pl) {
        if (game_s == GameState.STARTED) {
            if (round_s == RoundState.PICK_FIGURINE && onTurn == pl) {
                selectedPos = p;
                round_s = RoundState.PICK_DESTINATION;
            }
        }
        System.out.println((pl == Player.ELEPHANT ? "Elefánt" : "Orrszarvú") + " x: " + p.getX() + ", y: " + p.getY());
    }

    public void clickedOnCell(Position p) {
        if (game_s == GameState.STARTED) {
            if (round_s == RoundState.PICK_DESTINATION) {
                if (selectedPos.equals(new Position(-1, -1))) {
                    board.moveFromBench(p, onTurn);
                } else {
                    Direction d = Position.whichWayToStep(selectedPos, p);
                    if (d == null) {
                        System.out.println("ajjaj");
                    } else {
                        board.moveOnBoard(selectedPos, d);
                    }
                }
                round_s = RoundState.PICK_FIGURINE;
                onTurn = Player.swap(onTurn);
            }
        }
        System.out.println("cell x: " + p.getX() + ", y: " + p.getY());
    }
}
