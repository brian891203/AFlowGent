package io.csd.cloudtechnology.aflowgent.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.csd.cloudtechnology.aflowgent.model.aggregates.WorkFlow;

public class WorkFlowRowMapper implements RowMapper<WorkFlow>{
    @Override
    public WorkFlow mapRow(ResultSet rs, int rowNum) throws SQLException {
        WorkFlow wf = new WorkFlow();
        wf.setId(rs.getString("id"));
        wf.setHash(rs.getString("hash"));
        wf.setCreated_by(rs.getString("created_by"));
        wf.setTool_published(rs.getBoolean("tool_published"));
        return wf;
    }
}
