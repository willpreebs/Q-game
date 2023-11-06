package qgame.harnesses;

import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import qgame.json.JsonConverter;
import qgame.player.Player;
import qgame.referee.BasicQGameReferee;
import qgame.referee.GameResults;
import qgame.referee.QGameReferee;
import qgame.rule.placement.PlacementRule;
import qgame.rule.scoring.ScoringRule;
import qgame.state.QGameState;

public class XGamesWithObserver {
    
    public static void main(String[]args) {
        PlacementRule rules = HarnessUtil.createPlaceRules();
        ScoringRule scoreRules = HarnessUtil.createScoreRules();
        JsonStreamParser parser = new JsonStreamParser(new InputStreamReader(System.in));
        JsonElement jState = parser.next();
        JsonElement jActorSpecA = parser.next();

        QGameState state = JsonConverter.JStateToQGameState(jState);
        List<Player> players = JsonConverter.playersFromJActorSpecA(jActorSpecA, rules);
        
        QGameReferee ref = new BasicQGameReferee(rules, scoreRules, 10000, 6);
        GameResults r = ref.playGame(state, players);
        System.out.println(JsonConverter.jResultsFromGameResults(r));
    }
}