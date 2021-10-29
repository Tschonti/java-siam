public class SiamController {
    private GameState game_s;
    private RoundState round_s;
    private Player onTurn;
    Board board;
    GUI g;

    public SiamController() {
        game_s = GameState.NOT_STARTED;
        round_s = RoundState.PICK_FIGURINE;
        onTurn = Player.ELEPHANT;
        board = new Board();
        Cell.setController(this);
        g = new GUI(board);
    }

    public void clickedOnElephant(Position p) {
        System.out.println("Elephant x: " + p.getX() + ", y: " + p.getY());
    }

    public void clickedOnRhino(Position p) {
        System.out.println("Rhino x: " + p.getX() + ", y: " + p.getY());
    }

    public void clickedOnCell(Position p) {
        System.out.println("Cell x: " + p.getX() + ", y: " + p.getY());
    }
}
