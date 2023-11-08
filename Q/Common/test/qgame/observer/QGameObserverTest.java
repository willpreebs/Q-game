package qgame.observer;

import java.util.List;

import org.junit.Test;

import qgame.player.PlayerInfo;
import qgame.state.IGameState;
import qgame.state.QStateBuilder;
import qgame.state.map.Posn;
import qgame.state.map.QTile;
import qgame.state.map.Tile;

public class QGameObserverTest {

    Tile.Color red = Tile.Color.RED;
    Tile.Color blue = Tile.Color.BLUE;
    Tile.Color green = Tile.Color.GREEN;
    Tile.Color yellow = Tile.Color.YELLOW;
    Tile.Color orange = Tile.Color.ORANGE;
    Tile.Color purple = Tile.Color.PURPLE;

    Tile.Shape square = Tile.Shape.SQUARE;
    Tile.Shape circle = Tile.Shape.CIRCLE;
    Tile.Shape diamond = Tile.Shape.DIAMOND;
    Tile.Shape clover = Tile.Shape.CLOVER;
    Tile.Shape star = Tile.Shape.STAR;
    Tile.Shape eightStar = Tile.Shape.EIGHT_STAR;
    
    @Test
    public void testGameObserver() {
        QGameObserver o = new QGameObserver();
    
        IGameState state =
              new QStateBuilder()
        .addTileBag(new QTile(blue, eightStar))
        .placeTile(new Posn(0, 0), new QTile(red, clover))
        .addPlayerInfo(new PlayerInfo(1, List.of(new QTile(green, square)), "P 1"))
        .addPlayerInfo(new PlayerInfo(5, List.of(new QTile(yellow, star)), "P 2"))
        .build();

        o.receiveState(state);   
        o.save("filepath.txt");
        o.saveStatesAsPng();
    }
}
