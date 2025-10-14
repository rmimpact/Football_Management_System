package model.application;

import controller.TeamDashboardController;

import javax.naming.CannotProceedException;

public class Team {
    public static int REQUIRED_TEAM_SIZE = 5;

    private final String localName;
    private final String teamName;
    private Manager manager;
    private final Players allPlayers;
    private final Player[] currentTeam;

    public Team(String localName, String teamName, Manager manager, Players allPlayers) {
        this.localName = localName;
        this.teamName = teamName;
        this.manager = manager;
        this.allPlayers = allPlayers;
        this.currentTeam = new Player[REQUIRED_TEAM_SIZE];
    }

    public String getTeamName() {
        return this.teamName;
    }

    public Manager getManager() {
        return this.manager;
    }

    public Players getAllPlayers() { return this.allPlayers; }

    public void setManager(Manager manager) {
        this.manager = manager;
    }





    //-------------- Active Team

    public Player[] getActiveTeam() {
        return this.currentTeam;
    }

    // Check if player is already in the active team
    public int alreadyOnActiveTeam(Player player) {
        int count = 0;
        for (int i = 0; i < this.currentTeam.length; i++) {
            if (this.currentTeam[i] == player) {count++;}
        }
        return count;
    }



    @Override
    public String toString() {
        return this.localName + " " + this.teamName;
    }
}
