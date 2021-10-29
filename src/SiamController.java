public class SiamController {
    private GameState game_s;
    private RoundState round_s;
    Board board;
    GUI g;

    public SiamController() {
        game_s = GameState.NOT_STARTED;
        round_s = RoundState.PICK_FIGURINE;
        board = new Board();
        g = new GUI(board);
    }
}
