package qgame.harnesses;

import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import java.io.InputStreamReader;
import java.util.List;

import qgame.json.JsonConverter;
import qgame.json.JsonToObject;
import qgame.json.ObjectToJson;
import qgame.rule.placement.IPlacementRule;
import qgame.state.Placement;
import qgame.util.RuleUtil;
import qgame.state.IPlayerGameState;

public class XLegal {
  public static void main(String[] args) {
    JsonStreamParser parser = new JsonStreamParser(new InputStreamReader(System.in));
    JsonElement jPub = parser.next();
    JsonElement jPlacements = parser.next();

    IPlayerGameState gameState = JsonToObject.playerGameStateFromJPub(jPub);
    List<Placement> placements = JsonToObject.placementsFromJPlacements(jPlacements);
    IPlacementRule rules = RuleUtil.createPlaceRules();
    if (rules.isPlacementListLegal(placements, gameState)) {
      placements.forEach(gameState::makePlacement);
      JsonElement jMap = ObjectToJson.jMapFromQGameMap(gameState.getBoard());
      System.out.println(jMap);
    }
    else {
      System.out.println("false");
    }
  }
}
