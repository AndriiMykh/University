package org.foxminded.university.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.AddressDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class AddressDaoImpl implements AddressDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL = "SELECT * FROM addresses";
    private static final String FIND_BY_ID = "SELECT * FROM addresses where id = ?";
    private static final String INSERT_ADDRESS = "INSERT INTO addresses( city, street, flatNumber) values(?,?,?)";
    private static final String INSERT_ADDRESS_WITH_RETURNING_ID = "INSERT INTO addresses( city, street, flatNumber) values(?,?,?) RETURNING id";
    private static final String UPDATE_ADDRESS = "UPDATE addresses set city = ?, street = ?, flatNumber =? WHERE id = ?";
    private static final String DELETE_ADDRESS = "DELETE FROM addresses WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM addresses ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final RowMapper<Address> addressMapper = (resultSet, rowNum) -> Address.builder()
            .withId(resultSet.getLong("id"))
            .withStreet(resultSet.getString("street"))
            .withCity(resultSet.getString("city"))
            .withFlatNumber(resultSet.getInt("flatNumber"))
            .build();

    @Autowired
    public AddressDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Address address) {
        jdbcTemplate.update(INSERT_ADDRESS, address.getCity(), address.getStreet(), address.getFlatNumber());
        log.info("created a new Address:{}", address);
    }

    @Override
    public Long createAndReturnId(Address address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_ADDRESS_WITH_RETURNING_ID, new String[]{"city", "street", "flatNumber"});
                    ps.setString(1, address.getCity());
                    ps.setString(2, address.getStreet());
                    ps.setInt(3, address.getFlatNumber());
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Address> findById(Long id) {
        try {
            log.info("Look for an address in the db with ID={}", id);
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, addressMapper, id));
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public void update(Address address) {
        log.info("Try to update an address in the db {}", address);
        jdbcTemplate.update(UPDATE_ADDRESS, address.getCity(), address.getStreet(), address.getFlatNumber(), address.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("Try to delete an address by ID={}", id);
        jdbcTemplate.update(DELETE_ADDRESS, id);
    }

    @Override
    public List<Address> findAll() {
        log.info("Try to find all the addresses");
        return jdbcTemplate.query(FIND_ALL, addressMapper);
    }

    @Override
    public Pageable<Address> findAll(Page page) {
        log.info("Try to find all the addresses");
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Address> addresses = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, addressMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(addresses, page.getPageNumber(), page.getItemsPerPage());
    }
}
