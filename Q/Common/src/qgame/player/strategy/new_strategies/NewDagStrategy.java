package qgame.player.strategy.new_strategies;

import static qgame.util.ValidationUtil.validateArg;

import java.util.List;

import qgame.rule.placement.board.BoardRule;
import qgame.rule.placement.move.MoveRule;
import qgame.rule.placement.state.StateRule;
import qgame.state.IPlayerGameState;
import qgame.state.Placement;
import qgame.state.map.Posn;
import qgame.state.map.Tile;
import qgame.util.PosnUtil;

public class NewDagStrategy extends NewSmallestRowColumnTileStrategy {

    public NewDagStrategy(StateRule stateRule, BoardRule boardRule, MoveRule moveRule) {
        super(stateRule, boardRule, moveRule);
    }

    /**
     * 
     * Mutates given list of posns
     */
    @Override
    public Placement getBestPlacement(IPlayerGameState state, List<Posn> posns, Tile t) {
        validateArg(list -> !list.isEmpty(), posns, "posns cannot be empty");

        posns.sort(PosnUtil::rowColumnCompare);
        return new Placement(posns.get(0), t);
    }
}
