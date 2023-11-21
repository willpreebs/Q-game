package qgame.rule.placement;

import java.util.ArrayList;
import java.util.List;

import qgame.state.Placement;
import qgame.rule.placement.board.BoardRule;
import qgame.state.IPlayerGameState;

/**
 * An and predicate of ARule, is satisfied if all sub-rules
 * are also satisfied.
 */
public class MultiPlacementRule extends ARule {

  private final List<PlacementRule> rules;

  public MultiPlacementRule(PlacementRule... rules) {
    this.rules = new ArrayList<>(List.of(rules));
  }
  public MultiPlacementRule(List<PlacementRule> rules) {
    this.rules = new ArrayList<>(rules);
  }

  public MultiPlacementRule getBoardRules() {
    List<PlacementRule> boardRules = new ArrayList<>();

    for (PlacementRule r : this.rules) {
      if (r instanceof BoardRule) {
        boardRules.add(r);
      }
    }

    return new MultiPlacementRule(boardRules);
  }

  @Override
  public boolean isPlacementListLegal(List<Placement> placements, IPlayerGameState gameState) {

    for (PlacementRule r : this.rules) {
      if (!r.isPlacementListLegal(placements, gameState)) {
        return false;
      }
    }
    return true;
  }
}