public class TennisGame4 implements TennisGame {

    private static final int GAME_POINT_THRESHOLD = 4;
    private static final int DEUCE_THRESHOLD = 3;
    private static final int ADVANTAGE_DIFFERENCE = 1;
    private static final int WIN_DIFFERENCE = 2;

    private TennisPlayer server;
    private TennisPlayer receiver;

    public TennisGame4(String player1, String player2) {
        this.server = new TennisPlayer(player1);
        this.receiver = new TennisPlayer(player2);
    }

    @Override
    public void wonPoint(String playerName) {

        if (server.hasName(playerName)) {
            server.incrementScore();
        } else {
            receiver.incrementScore();
        }
    }

    @Override
    public String getScore() {

        TennisScoreCalculator calculator = determineScoreCalculator();
        return calculator.calculate(server, receiver);
    }

    private TennisScoreCalculator determineScoreCalculator() {

        if (isDeuce()) {
            return new TennisDeuceScore();
        }

        if (hasWinner()) {
            return new TennisWinScore();
        }

        if (hasAdvantage()) {
            return new TennisAdvantageScore();
        }

        return new TennisRegularScore();
    }

    private boolean isDeuce() {

        return server.getPoints() >= DEUCE_THRESHOLD
                && receiver.getPoints() >= DEUCE_THRESHOLD
                && server.getPoints() == receiver.getPoints();
    }

    private boolean hasWinner() {

        return (server.getPoints() >= GAME_POINT_THRESHOLD
                && server.getPoints() - receiver.getPoints() >= WIN_DIFFERENCE)
                || (receiver.getPoints() >= GAME_POINT_THRESHOLD
                && receiver.getPoints() - server.getPoints() >= WIN_DIFFERENCE);
    }

    private boolean hasAdvantage() {

        return (server.getPoints() >= GAME_POINT_THRESHOLD
                && server.getPoints() - receiver.getPoints() == ADVANTAGE_DIFFERENCE)
                || (receiver.getPoints() >= GAME_POINT_THRESHOLD
                && receiver.getPoints() - server.getPoints() == ADVANTAGE_DIFFERENCE);
    }
}

class TennisPlayer {

    private static final String[] SCORE_NAMES = {
            "Love",
            "Fifteen",
            "Thirty",
            "Forty"
    };

    private final String name;
    private int points;

    public TennisPlayer(String name) {
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

    public boolean hasName(String playerName) {
        return name.equals(playerName);
    }

    public String getScoreName() {

        if (points < SCORE_NAMES.length) {
            return SCORE_NAMES[points];
        }

        return "";
    }
}

interface TennisScoreCalculator {

    String calculate(TennisPlayer player1, TennisPlayer player2);
}

class TennisRegularScore implements TennisScoreCalculator {

    @Override
    public String calculate(TennisPlayer player1, TennisPlayer player2) {

        String score1 = player1.getScoreName();
        String score2 = player2.getScoreName();

        if (score1.equals(score2)) {
            return score1 + "-All";
        }

        return score1 + "-" + score2;
    }
}

class TennisDeuceScore implements TennisScoreCalculator {

    @Override
    public String calculate(TennisPlayer player1, TennisPlayer player2) {
        return "Deuce";
    }
}

class TennisAdvantageScore implements TennisScoreCalculator {

    private static final int ADVANTAGE_DIFFERENCE = 1;

    @Override
    public String calculate(TennisPlayer player1, TennisPlayer player2) {

        int difference = player1.getPoints() - player2.getPoints();

        if (Math.abs(difference) == ADVANTAGE_DIFFERENCE) {

            String leadingPlayer =
                    difference > 0
                            ? player1.getName()
                            : player2.getName();

            return "Advantage " + leadingPlayer;
        }

        return "";
    }
}

class TennisWinScore implements TennisScoreCalculator {

    @Override
    public String calculate(TennisPlayer player1, TennisPlayer player2) {

        int difference = player1.getPoints() - player2.getPoints();

        String winner =
                difference > 0
                        ? player1.getName()
                        : player2.getName();

        return "Win for " + winner;
    }
}