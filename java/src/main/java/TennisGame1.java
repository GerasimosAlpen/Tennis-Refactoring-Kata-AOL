public class TennisGame1 implements TennisGame {

    private int player1Score = 0;
    private int player2Score = 0;

    private String player1Name;
    private String player2Name;

    public TennisGame1(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    @Override
    public void wonPoint(String playerName) {
        if ("player1".equals(playerName)) {
            player1Score++;
        } else {
            player2Score++;
        }
    }

    @Override
    public String getScore() {

        if (isDraw()) {
            return getDrawScore();
        }

        if (isEndGame()) {
            return getEndGameScore();
        }

        return getRegularScore();
    }

    private boolean isDraw() {
        return player1Score == player2Score;
    }

    private boolean isEndGame() {
        return player1Score >= 4 || player2Score >= 4;
    }

    private String getDrawScore() {

        switch (player1Score) {
            case 0:
                return "Love-All";
            case 1:
                return "Fifteen-All";
            case 2:
                return "Thirty-All";
            default:
                return "Deuce";
        }
    }

    private String getEndGameScore() {

        int difference = player1Score - player2Score;

        if (difference == 1) {
            return "Advantage player1";
        }

        if (difference == -1) {
            return "Advantage player2";
        }

        if (difference >= 2) {
            return "Win for player1";
        }

        return "Win for player2";
    }

    private String getRegularScore() {

        String score = "";

        for (int i = 1; i < 3; i++) {

            int tempScore;

            if (i == 1) {
                tempScore = player1Score;
            } else {
                score += "-";
                tempScore = player2Score;
            }

            switch (tempScore) {
                case 0:
                    score += "Love";
                    break;
                case 1:
                    score += "Fifteen";
                    break;
                case 2:
                    score += "Thirty";
                    break;
                case 3:
                    score += "Forty";
                    break;
            }
        }

        return score;
    }
}