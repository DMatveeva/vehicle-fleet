package ru.dmatveeva.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class JdbcVehicleRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public JdbcVehicleRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Map<String, Integer> getReportResultsForYear(int id, LocalDate start, LocalDate end) {
        Map<String, Integer> reportResults = new HashMap<>();
        List<Map<String, Object>> queryResults = jdbcTemplate.queryForList("select y, cast (round(sum(dist)) as INTEGER) s\n" +
                "from(\n" +
                "select *,\n" +
                "       ST_Distance(\n" +
                "               t.position::geometry,\n" +
                "               t.lead::geometry,\n" +
                "               true) / 1000 dist\n" +
                "from(\n" +
                "SELECT position, visited,\n" +
                "       to_char(visited, 'yyyy') y,\n" +
                "       LEAD(position, 1) OVER (ORDER BY visited) AS lead\n" +
                "FROM vehicle_coordinates\n" +
                "where vehicle_id = ?" +
                "and visited >= ? and visited <= ?) t) t1\n" +
                "group by y", id, start, end);
        for (Map m : queryResults) {
            reportResults.put((String) m.get("y"), (Integer) m.get("s"));
        }
        return reportResults;
    }
}

