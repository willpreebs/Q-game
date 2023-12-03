package qgame.state;

import static qgame.util.ValidationUtil.nonNull;
import static qgame.util.ValidationUtil.validateState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import qgame.player.PlayerInfo;
import qgame.state.map.IMap;
import qgame.state.map.QMap;
import qgame.state.map.Tile;
import qgame.util.ValidationUtil;

/**
 * Represents an implementation of the Game State interface that uses the 
 * rules known as "Basic Rules". The game state updates its board 
 * and other variables like player score and turn order as per request
 * from the referee's turn visitor.
 */
public class QGameState implements IGameState {

  private IMap board;
  private final Bag<Tile> refereeTiles;
  private final List<PlayerInfo> playerInformation; // Players should always be kept in
  // sequential order

  /**
   * Constructs the initial game state at the start of the game
   * @param map, the initial Map of the board
   * @param tiles the list of tiles in a game
   * @param players list of who will play in the game
   */
  public QGameState(IMap map, Bag<Tile> tiles, List<PlayerInfo> players)
    throws IllegalArgumentException {
    validate(map, tiles, players);
    this.board = new QMap(map.getBoardState());
    this.refereeTiles = new Bag<>(tiles);
    this.playerInformation = new ArrayList<>(players);
  }

  public QGameState(IGameState state) {
    this.board = new QMap(state.getBoard().getBoardState());
    this.refereeTiles = new Bag<>(state.getRefereeTiles());
    this.playerInformation = new ArrayList<>(state.getAllPlayerInformation());
  }

  public QGameState() {
    this.board = new QMap(new HashMap<>());
    this.refereeTiles = new Bag<>();
    this.playerInformation = new ArrayList<>();
  }

  public QGameState(IMap map) {
    this.board = map;
    this.refereeTiles = new Bag<>();
    this.playerInformation = new ArrayList<>();
  }

  /**
   * Determines whether all constructor parameters are valid before creating
   * a new gameState.
   * @param map the Map of all tiles on the board
   * @param tiles the referee's tiles
   * @param players the list of players represented through their information
   */
  private void validate(IMap map,  Bag<Tile> tiles,
                   List<PlayerInfo> players) {
    ValidationUtil.nonNullObj(map, "Map cannot be null");
    nonNull(List.of(tiles.getItems()), "Tiles");
    nonNull(players, "Players");
  }


  private void validateGameHasPlayers() {
    validateState(Predicate.not(List::isEmpty), this.playerInformation,
      "There are no players in the game.");
  }

  @Override
  public List<PlayerInfo> getAllPlayerInformation() {
    List<PlayerInfo> copy = new ArrayList<>();

    for (PlayerInfo i : this.playerInformation) {
      copy.add(new PlayerInfo(i.getScore(), new Bag<>(i.getTiles()).getItems(), i.getName()));
    }

    return copy;
  }

  /**
   * Returns the current player whose turn it is
   * @return PlayerInfo corresponding to current turn's player.
   */
  @Override
  public PlayerInfo getCurrentPlayerInfo() {
    return this.playerInformation.get(0);
  }

  @Override
  public IMap getBoard() {
    return this.board;
  }

  @Override
  public Bag<Tile> getRefereeTiles() {
    return new Bag<>(this.refereeTiles.getItems());
  }

  private List<Integer> allScores() {
    return new ArrayList<>(this.playerInformation
      .stream()
      .map(PlayerInfo::getScore)
      .toList());
  }
  @Override
  public IPlayerGameState getCurrentPlayerState() {
    List<Integer> scores = allScores();
    IMap boardState = getBoard();
    int tileCount = getRefereeTiles().size();
    Bag<Tile> playerTile = getCurrentPlayerInfo().getTiles();
    String playerName = getCurrentPlayerInfo().getName();
    return new QPlayerGameState(scores, boardState, tileCount, playerTile, playerName);
  }

  @Override
  public void shiftCurrentToBack() throws IllegalStateException {
    validateGameHasPlayers();
    this.playerInformation.add(this.playerInformation.remove(0));
  }

  @Override
  public void addScoreToCurrentPlayer(int score) throws IllegalStateException {
    validateGameHasPlayers();
    this.playerInformation.get(0).incrementScore(score);
  }

  @Override
  public void removeCurrentPlayer() {
    validateGameHasPlayers();
    PlayerInfo removed = this.playerInformation.remove(0);
    Bag<Tile> removedPlayerTiles = removed.getTiles();
    this.refereeTiles.addAll(removedPlayerTiles);
  }

  @Override
  public void setCurrentPlayerHand(Bag<Tile> tiles) throws IllegalStateException {
    validateGameHasPlayers();
    this.playerInformation.get(0).setTiles(tiles);
  }

  @Override
  public void placeTile(Placement placement) {
    board.placeTile(placement);
  }

  @Override
  public Collection<Tile> takeOutRefTiles(int count) throws IllegalArgumentException {
    Collection<Tile> newTiles = this.refereeTiles.removeFirstNItems(count);
    // this.refereeTiles.removeAll(newTiles);
    return newTiles;
  }

  @Override
  public void giveRefereeTiles(Bag<Tile> tiles) throws IllegalArgumentException {
    this.refereeTiles.addAll(tiles);
  }
}
