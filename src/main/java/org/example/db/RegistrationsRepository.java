
package org.example.db;

import java.sql.Connection;
import java.sql.Statement;

public class RegistrationsRepository {
    private Connection connection = null;

    public RegistrationsRepository() {
        connection = DbConnector.getInstance().getConnection();
    }

    public void addNew(Registration registration) throws Exception {
        Statement statement = connection.createStatement();

        String insertQuery = String.format("INSERT INTO registrations (team_name, number_of_participants, selected_task) VALUES ('%s',%d,%d);", registration.getTeamName(), registration.getNumberOfParticipants(), registration.getSelectedTask());

        statement.executeUpdate(insertQuery);

        statement.close();
    }
}
