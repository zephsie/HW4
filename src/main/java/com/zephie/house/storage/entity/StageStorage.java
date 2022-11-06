package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IStage;
import com.zephie.house.core.dto.SystemStageDTO;
import com.zephie.house.storage.api.IStageStorage;
import com.zephie.house.util.mappers.ResultSetToStageMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class StageStorage implements IStageStorage {
    private final DataSource dataSource;

    private static final String SELECT_BY_ID = "SELECT id stage_id, description stage_description, dt_create, dt_update FROM structure.stage WHERE id = ?";

    private static final String SELECT = "SELECT id stage_id, description stage_description FROM structure.stage";

    private static final String SELECT_BY_DESCRIPTION = "SELECT id stage_id, description stage_description, dt_create, dt_update FROM structure.stage WHERE description = ?";

    private static final String IS_EXIST = "SELECT EXISTS(SELECT 1 FROM structure.stage WHERE id = ?)";

    private static final String INSERT = "INSERT INTO structure.stage (description, dt_create, dt_update) VALUES (?, ?, ?)";

    private static final String UPDATE = "UPDATE structure.stage SET description = ?, dt_update = ? WHERE id = ? AND dt_update = ?";

    private static final String DELETE = "DELETE FROM structure.stage WHERE id = ? AND dt_update = ?";

    public StageStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public IStage create(SystemStageDTO systemStageDTO) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, systemStageDTO.getDescription());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(systemStageDTO.getCreateDate()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemStageDTO.getUpdateDate()));

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return read(generatedKeys.getLong(1)).orElseThrow(() -> new RuntimeException("Something went wrong while reading created Stage"));
                } else {
                    throw new RuntimeException("Keys were not generated");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating Stage");
        }
    }

    @Override
    public Optional<IStage> read(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(ResultSetToStageMapper.fullMap(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Stage");
        }
    }

    @Override
    public Collection<IStage> read() {
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                Collection<IStage> stages = new HashSet<>();

                while (resultSet.next()) {
                    stages.add(ResultSetToStageMapper.partialMap(resultSet));
                }

                return stages;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Stages");
        }
    }

    @Override
    public IStage update(Long id, SystemStageDTO systemStageDTO, LocalDateTime dateUpdate) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, systemStageDTO.getDescription());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(systemStageDTO.getUpdateDate()));

            preparedStatement.setLong(3, id);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(dateUpdate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Stage was not updated");
            }

            return read(id).orElseThrow(() -> new RuntimeException("Something went wrong while getting updated Stage"));
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while updating Stage");
        }
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(1, id);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateUpdate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Stage was not deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting Stage");
        }
    }

    @Override
    public boolean isPresent(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(IS_EXIST)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while checking Stage");
        }
    }

    @Override
    public Optional<IStage> read(String name) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_DESCRIPTION)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(ResultSetToStageMapper.fullMap(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Stage");
        }
    }
}