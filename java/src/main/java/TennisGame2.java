public class TennisGame2 implements TennisGame {
    private Player player1;
    private Player player2;

    public TennisGame2(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
    }

    @Override
    public void wonPoint(String playerName) {
        if ("player1".equals(playerName)) {
            player1.incrementScore();
        } else {
            player2.incrementScore();
        }
    }

    @Override
    public String getScore() {
        ScoreCalculator calculator = determineScoreCalculator();
        return calculator.calculate(player1, player2);
    }

    private ScoreCalculator determineScoreCalculator() {
        if (isDeuce()) {
            return new DeuceScore();
        }
        if (isEndGame()) {
            return new EndGameScore();
        }
        if (isDraw()) {
            return new DrawScore();
        }
        return new RegularScore();
    }

    private boolean isDeuce() {
        return player1.getPoints() == player2.getPoints() && player1.getPoints() >= 3;
    }

    private boolean isEndGame() {
        return player1.getPoints() >= 4 || player2.getPoints() >= 4;
    }

    private boolean isDraw() {
        return player1.getPoints() == player2.getPoints();
    }
}

class Player {
    private static final String[] SCORE_NAMES = {"Love", "Fifteen", "Thirty", "Forty"};
    private String name;
    private int points;

    public Player(String name) {
        this.name = name;
        this.points = 0;
    }

    public void incrementScore() {
        points++;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public String getScoreName() {
        if (points < SCORE_NAMES.length) {
            return SCORE_NAMES[points];
        }
        return "";
    }
}

interface ScoreCalculator {
    String calculate(Player player1, Player player2);
}

class DrawScore implements ScoreCalculator {
    @Override
    public String calculate(Player player1, Player player2) {
        return player1.getScoreName() + "-All";
    }
}

class DeuceScore implements ScoreCalculator {
    @Override
    public String calculate(Player player1, Player player2) {
        return "Deuce";
    }
}

class EndGameScore implements ScoreCalculator {
    private static final int ADVANTAGE_DIFFERENCE = 1;
    private static final int WIN_DIFFERENCE = 2;

    @Override
    public String calculate(Player player1, Player player2) {
        int difference = player1.getPoints() - player2.getPoints();
        
        if (Math.abs(difference) == ADVANTAGE_DIFFERENCE) {
            String leadingPlayer = difference > 0 ? "player1" : "player2";
            return "Advantage " + leadingPlayer;
        }
        
        String winner = difference > 0 ? "player1" : "player2";
        return "Win for " + winner;
    }
}

class RegularScore implements ScoreCalculator {
    @Override
    public String calculate(Player player1, Player player2) {
        return player1.getScoreName() + "-" + player2.getScoreName();
    }
}