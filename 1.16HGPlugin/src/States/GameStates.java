package States;

public enum GameStates {
	STARTING, INVINCIBILITY, INGAME, ENDING;
	
	private static GameStates currentState = STARTING;
	
	public static GameStates getState() {
		return currentState;
	}
	
	public static void setState(GameStates newState) {
		currentState = newState;
	}
}
