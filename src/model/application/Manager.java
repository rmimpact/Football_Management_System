package model.application;

import javafx.beans.property.*;

public class Manager {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final ObjectProperty<Team> teamProperty;

    public Manager(int id, String firstName, String lastName, Team team) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamProperty = new SimpleObjectProperty<>(team);
    }

    public boolean hasId(int id) {
        return this.id == id;
    }

    public ReadOnlyObjectProperty<Team> teamProperty() {
        return this.teamProperty;
    }

    public Team getTeam() {
        return teamProperty.get();
    }

    public void assignTeam(Team team) {
        this.teamProperty.set(team);
    }



    //custom methods------------->
    public String getJerseyPatchPath() {
        switch (this.getTeam().getTeamName()) {
            case "Bulldogs": return "/view/image/bulldogs.png";
            case "Eels": return "/view/image/eels.png";
            case "Panthers": return "/view/image/panthers.png";
            case "Sharks": return "/view/image/sharks.png";
            case "Storm": return "/view/image/storm.png";
            case "Titans": return "/view/image/titans.png";
            case "Warriors": return "/view/image/warriors.png";
            default: return "/view/image/none.png";
        }
    }

    //custom end ^^^^^^^^^^^^^^^^^^

    @Override
    public String toString() {
        return firstName +  " " + lastName;
    }
}
