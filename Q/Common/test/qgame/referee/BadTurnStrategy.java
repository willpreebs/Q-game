package qgame.referee;

import java.util.List;

import qgame.action.PlaceAction;
import qgame.action.TurnAction;
import qgame.state.map.Posn;
import qgame.state.map.Tile;
import qgame.state.map.QTile;
import qgame.player.strategy.TurnStrategy;
import qgame.state.Placement;
import qgame.state.IPlayerGameState;

public class BadTurnStrategy implements TurnStrategy {

  @Override
  public TurnAction chooseAction(IPlayerGameState state) {
    Placement placement = new Placement(new Posn(1000, -1000),
      new QTile(Tile.Color.ORANGE, Tile.Shape.CIRCLE));
    return new PlaceAction(List.of(placement));
  }
}
