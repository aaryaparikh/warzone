javac -Xdoclint:all ".\Constants\GameConstants.java"

javac -Xdoclint:all ".\Controller\GameEngine.java"
javac -Xdoclint:all ".\Controller\LogEntryBuffer.java"
javac -Xdoclint:all ".\Controller\LogWriter.java"
javac -Xdoclint:all ".\Controller\Observable.java"
javac -Xdoclint:all ".\Controller\Observer.java"

javac -Xdoclint:all ".\Controller\Phases\EditMapPhase.java"
javac -Xdoclint:all ".\Controller\Phases\EndGamePhase.java"
javac -Xdoclint:all ".\Controller\Phases\Phase.java"
javac -Xdoclint:all ".\Controller\Phases\PlayGamePhase.java"

javac -Xdoclint:all ".\Controller\Phases\SubPhases\ExecuteOrderPhase.java"
javac -Xdoclint:all ".\Controller\Phases\SubPhases\IssueOrderPhase.java"
javac -Xdoclint:all ".\Controller\Phases\SubPhases\PlayMainPhase.java"
javac -Xdoclint:all ".\Controller\Phases\SubPhases\PLaySetupPhase.java"

javac -Xdoclint:all ".\DemoDriver\Build1Demo.java"
javac -Xdoclint:all ".\DemoDriver\Build2Demo.java"

javac -Xdoclint:all ".\Models\Continent.java"
javac -Xdoclint:all ".\Models\Country.java"
javac -Xdoclint:all ".\Models\GameMap.java"
javac -Xdoclint:all ".\Models\Player.java"

javac -Xdoclint:all ".\Models\Orders\AdvanceOrder.java"
javac -Xdoclint:all ".\Models\Orders\AirliftOrder.java"
javac -Xdoclint:all ".\Models\Orders\BlockadeOrder.java"
javac -Xdoclint:all ".\Models\Orders\BombOrder.java"
javac -Xdoclint:all ".\Models\Orders\DeployOrder.java"
javac -Xdoclint:all ".\Models\Orders\DiplomacyOrder.java"
javac -Xdoclint:all ".\Models\Orders\Order.java"

javac -Xdoclint:all ".\Utils\CommandHandler.java"
javac -Xdoclint:all ".\Utils\GameCommandHandler.java"
javac -Xdoclint:all ".\Utils\MapCommandHandler.java"
javac -Xdoclint:all ".\Utils\MapEditor.java"
javac -Xdoclint:all ".\Utils\PlayerCommandHandler.java"

javac -Xdoclint:all ".\Views\CommandView.java"
javac -Xdoclint:all ".\Views\MapView.java"
javac -Xdoclint:all ".\Views\PhaseView.java"
pause