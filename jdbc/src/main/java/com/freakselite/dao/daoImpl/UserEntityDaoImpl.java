package com.freakselite.dao.daoImpl;

import com.freakselite.model.UserEntity;
import com.freakselite.rowMappers.RoleRowMapper;
import com.freakselite.rowMappers.UserEntityRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserEntityDaoImpl implements com.freakselite.dao.UserEntityDao {

    // == fields ==
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String dbName = "users";

    // == constructor ==
    @Autowired
    public UserEntityDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // == public methods ==
    @Override
    public UserEntity findById(int id) {
        SqlParameterSource args = new MapSqlParameterSource("id", id);
        String query = "SELECT * FROM " +
                dbName +
                " WHERE id = :id";

        UserEntity user = jdbcTemplate.queryForObject(query, args, new UserEntityRowMapper());

        return findUser(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        SqlParameterSource args = new MapSqlParameterSource("username", username);
        String query = "SELECT * FROM " +
                dbName +
                " WHERE username = :username";

        UserEntity user = jdbcTemplate.queryForObject(query, args, new UserEntityRowMapper());

        return findUser(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        SqlParameterSource args = new MapSqlParameterSource("email", email);
        String query = "SELECT * FROM " +
                dbName +
                " WHERE email = :email";

        UserEntity user = jdbcTemplate.queryForObject(query, args, new UserEntityRowMapper());

        return findUser(user);
    }

    // == private methods ==
    private UserEntity findUser(UserEntity user){
        String rolesQuery = "SELECT r.* FROM users_roles AS ur " +
                "JOIN roles AS r ON r.id = ur.role_id " +
                "WHERE ur.user_id = :id";

        if (user != null){
            SqlParameterSource args = new MapSqlParameterSource("id", user.getId());
            user.setRoles(
                    jdbcTemplate.query(rolesQuery, args, new RoleRowMapper())
            );
        }

        return user;
    }
}
