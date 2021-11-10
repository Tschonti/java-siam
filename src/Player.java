public enum Player {
    ELEPHANT,
    RHINO;

    public static Player swap(Player p) {
        if (p == Player.ELEPHANT) {
            return Player.RHINO;
        }
        return Player.ELEPHANT;
    }
}
