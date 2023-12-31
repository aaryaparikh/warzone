# Warzone
This is a repository to maintain the Java-based CLI implementation of the well-known incremental game *"Warzone"* (https://www.warzone.com/), which is compatible with the game's command-line play, rules, and map files. It was developed as a requirement for Concordia University's course: SOEN - 6441 (Advanced Programming Practices) for Fall 2023.

# Technology
### Java

# Team Name and Authors
- *Team W17*

- **Aarya Parikh**       : aaryaparikh   (aaryaparikh3@gmail.com)
- **Dev Pandya**         : DP8801        (devpandya651@gmail.com)
- **Gurleen Pannu**      : GurleenP06    (gurleenpannu2006@gmail.com)
- **Virag Patel**        : 19IT114       (virag290301@gmail.com)
- **Yurui Wang**         : wyurui1       (wyurui1@gmail.com)
- **Jonathan Lupague**   : jonloops      (jrlupague@gmail.com)

# Build1
For the presentation of build1, please run Build1Demo.java under src/main/java.

If everything runs correctly, it will show something like \<\<TBD\>\>\<Order1\>\<Order2\>

\<\<TBD\>\> TBD here indicates the current phase.

\<Order\> Order here indicates what command needs to enter as presentation requirment. If you enter the first order in the commands list, it will move to next phase or next turn. Especially, if enter "end" in issue order phase, it will move to end phase immediately, only if all players enter "commit" it will  move to execute order phase.

Also could run MapService.java to check the work of editing map. There is no conflict between them.

# Build2
In build2, please run Build2Demo.java under src/main/java.

Except for output from build1, all running information is saved in log.txt which is under src/main/resources.

Add new features for build2's requirement, and refactor the project to follow State Pattern, Command Pattern and Observer Pattern.

# Build3
As for build3, please run Build3Demo.java.

In EditMapPhase, "editmap Canada.map"/"savemap Canada.map" to edit/save a Conquest Map, "editmap firstmap.txt"/"savemap firstmap.txt" to edit/save a Domination Map.

In GameStartupPhase and IssueOrderPhase, input "savegame g1"/"loadgame g1" to save/load a game to/from a game file.

In GameStartupPhase, input "tournament -M firstmap.txt secondmap.txt thirdmap.txt -P aggressive cheater random -G 3 -D 7" to start a tournament.

Different from Build2, in GameStartupPhase, after "gamplayer -add", choose to select strategy for a new player.
