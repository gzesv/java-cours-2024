package edu.java.scrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest extends IntegrationTest {
    @Test
    @SneakyThrows
    public void chatTableShouldBeAddedTest() {
        Connection connection = POSTGRES.createConnection("");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat");

        ResultSet resultSet = statement.executeQuery();

        assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("id");
        assertThat(resultSet.getMetaData().getColumnName(2)).isEqualTo("name");
    }

    @Test
    @SneakyThrows
    public void linkTableShouldBeAddedTest() {
        Connection connection = POSTGRES.createConnection("");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM link");

        ResultSet resultSet = statement.executeQuery();

        assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("id");
        assertThat(resultSet.getMetaData().getColumnName(2)).isEqualTo("url");
        assertThat(resultSet.getMetaData().getColumnName(3)).isEqualTo("updated_at");
        assertThat(resultSet.getMetaData().getColumnName(4)).isEqualTo("checked_at");
    }

    @Test
    @SneakyThrows
    public void chatToLinkTableShouldBeAddedTest() {
        Connection connection = POSTGRES.createConnection("");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat_to_link");

        ResultSet resultSet = statement.executeQuery();

        assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("chat_id");
        assertThat(resultSet.getMetaData().getColumnName(2)).isEqualTo("link_id");
    }
}
