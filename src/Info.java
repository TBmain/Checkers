public class Info {
    public static String RULES =
            "<html>" +
                "<body style='font-size: 14px'>" +
                "<h1>GENERAL</h1>" +
                "1. White player plays first." +
                "<br><br>" +
                "2. Pieces can only be placed on dark tiles." +
                "<br><br>" +
                "3. Pieces can only move diagonally." +
                "<br><br>" +
                "4. First player to have no moves lose." +
                "<br><br>" +
                "5. The game ends in a tie if there have been 15 <i>no progress moves</i> in a row." +
                "<br>" +
                "<i>no progress move</i> - a move with a king that wasn't a jump." +
                "<br><br>" +
                "6. When jumping (eating) is possible it's forced." +
                "<br><br>" +
                "7. If there's an option to jump immediately after jumping, the player has to jump again (combo-jump)." +
                "<br><br>" +
                "8. Pieces can jump to any direction in combo jumps (after the first jump)." +
                "<h1>REGULAR</h1>" +
                "9. Pieces can only move one tile at a time." +
                "<br><br>" +
                "10. Pieces can only move forward." +
                "<br><br>" +
                "11. Pieces can only jump forward (except for combo-jumps - rule #8)." +
                "<br><br>" +
                "12. When a piece reaches the last row on the board it becomes a king." +
                "<h1>KING</h1>" +
                "13. Pieces can move any number of tiles at once." +
                "<br><br>" +
                "14. Pieces can move forward & backwards." +
                "<br><br>" +
                "15. Pieces can jump forward & backwards." +
                "</body>" +
            "</html>";
    public static String CREDITS =
            "<html><body style='width: 200px; font-size:14px; text-align: center;'>" +
            "Nadav Barak" +
            "</body></html>";
}


