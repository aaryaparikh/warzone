package Constants;

import java.util.List;

/**
 * All Constants Value
 * 
 * @author YURUI
 */
public final class GameConstants {
	/**
	 * Each player should be assigned 5 armies in default
	 */
	public static final int DEFAULT_PLAYER_REINFORCEMENT = 5;

	/**
	 * Each player should be assigned at least 3 armies
	 */
	public static final int MINIMUN_PLAYER_REINFORCEMENT = 3;

	/**
	 * Card type in game
	 */
	public static final List<String> GAME_CARD_LIST = List.of("bomb", "blockade", "airlift", "negotiate");

	/**
	 * Player type in game
	 */
	public static final List<String> GAME_PLAYER_LIST = List.of("human", "aggressive", "benevolent", "random",
			"cheater");
}
