package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AddressDao extends AbstractDao<Long, Address> {

    private static final String FIND_ALL = "SELECT * FROM addresses";
    private static final String FIND_BY_ID = "SELECT * FROM addresses where id = ?";
    private static final String INSERT_ADDRESS = "INSERT INTO addresses( city, street, flatNumber) values(?,?,?)";
    private static final String UPDATE_ADDRESS = "UPDATE addresses set city = ?, street = ?, flatNumber =? WHERE id = ?";
    private static final String DELETE_ADDRESS = "DELETE FROM addresses WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM addresses ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final RowMapper<Address> addressMapper = (resultSet, rowNum) ->  Address.builder()
            .withId(resultSet.getLong("id"))
            .withStreet(resultSet.getString("street"))
            .withCity(resultSet.getString("city"))
            .withFlatNumber(resultSet.getInt("flatNumber"))
            .build();

    @Autowired
    public AddressDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Address address) {
        jdbcTemplate.update(INSERT_ADDRESS, address.getCity(), address.getStreet(), address.getFlatNumber());
    }

    @Override
    public Optional<Address> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, addressMapper, id));
    }

    @Override
    public void update(Address address) {
        jdbcTemplate.update(UPDATE_ADDRESS, address.getCity(), address.getStreet(), address.getFlatNumber(), address.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_ADDRESS, id);
    }

    @Override
    public List<Address> findAll() {
        return jdbcTemplate.query(FIND_ALL, addressMapper);
    }

    @Override
    public Pageable<Address> findAll(Page page) {
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Address> addresses = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, addressMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(addresses, page.getPageNumber(), page.getItemsPerPage());
    }
}
