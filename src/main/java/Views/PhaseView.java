package Views;

import Controller.GameEngine;

public class PhaseView {
	GameEngine d_gameEngine;

	public PhaseView(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}
	
	public void showGameInfo() {
        System.out.println("<<Game Start>>");
	}
	
	public void showNextPhaseInfo(String p_phase) {
        switch (p_phase) {
        case "edit":
            System.out.println("\n<<Edit map phase>><end><showmap><savemap><editmap><validatemap>");
            break;
        case "start":
            System.out.println("\n<<Game startup phase>><end><showmap><loadmap><gameplayer>");
            break;
        case "play":
            System.out.println("\n<<Game issue order phase>><commit><end><deploy>");
            break;
        case "execute":
            System.out.println("\n<<Game execute order phase>>");
            break;
        case "end":
            System.out.println("\n<<Game end phase>><TBD>");
            break;
        default:
            break;
        }
	}
}
