package qgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qgame.state.map.Tile;
import qgame.state.map.QTile;

public class TestUtil {

  public static Map<Tile.Color, Map<Tile.Shape, Tile>> allTiles() {
    Map<Tile.Color, Map<Tile.Shape, Tile>> allTiles = new HashMap<>();
    for (Tile.Color color : Tile.Color.values()) {
      allTiles.put(color, new HashMap<>());
      for (Tile.Shape shape : Tile.Shape.values()) {
        allTiles.get(color).put(shape, new QTile(color, shape));
      }
    }
    return allTiles;
  }

  public static List<Tile> generateOneEachTile(){
    List<Tile> list = new ArrayList<Tile>();
    for(Tile.Color c: Tile.Color.values()){
      for(Tile.Shape s: Tile.Shape.values()){
        list.add(new QTile(c,s));
      }
    }
    return list;
  }
}
