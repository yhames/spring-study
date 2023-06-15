package hello.itemservice.repository.jdbctemplate;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * JdbcTemplate - NamedParameterJdbcTemplate
 * @`NamedParameterJdbcTemplate` : 매개변수로 `SqlParameterSource` 인터페이스 혹은 단순히 `Map` 객체를 갖는다.
 * @`SqlParameterSource`인터페이스 : 구현체로 `BeanPropertySqlParameterSource`, `MapSqlParameterSource`가 있다.
 * @`MapSqlParameterSource` : Map과 유사함, SQL 특화 기능 지원(타입지정 등), 메서드 체인 기능제공
 * @`BeanPropertySqlParameterSource` : **자바빈 프로퍼티 규악**을 통해 자동으로 파라미터 객체 생성, 내부적으로 Getter 사용
 * @`BeanPropertyRowMapper` : 이름 지정 파리미터를 사용하여 `ResultSet`을 자바빈프로퍼티 규약에 맞춰서 객체로 매핑(snake_case to camelCase 자동변환)
 */
@Slf4j
public class JdbcTemplateItemRepositoryV2 implements ItemRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateItemRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String sql = "insert into item(item_name, price, quantity)" +
                " values (:itemName, :price, :quantity)";

        /**
         * BeanPropertySqlParameterSource
         */
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        item.setId(key);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item" +
                " set item_name=:itemName, price=:price, quantity=:quantity" +
                " where id=:id";

        /**
         * MapSqlParameterSource
         * `ItemUpdateDto`는 `id` 필드가 없기 때문에 "where id=:id"에서 `id` 값은
         * `BeanPropertyParameterSource`를 통해 매핑할 수 없다.
         * 따라서 `MapSqlParameterSource`을 사용하거나 Map을 사용해야 한다.
         */
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);    // HERE
        template.update(sql, param);
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        String sql = "select id, item_name, price, quantity from item where id=:id";

        /**
         * Map.of()
         */
        try {
            Map<String, Object> param = Map.of("id", itemId);
            Item item = template.queryForObject(sql, param, itemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        String sql = "select id, item_name, price, quantity from item";

        // 동적 쿼리
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }

        boolean andFlag = false;
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',:itemName,'%')";
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <= :maxPrice";
        }

        log.info("sql={}", sql);

        return template.query(sql, param, itemRowMapper());
    }

    private RowMapper<Item> itemRowMapper() {   // ResultSet -> Object
        /**
         * BeanPropertyRowMapper
         */
        return BeanPropertyRowMapper.newInstance(Item.class);   // snake_case to camelCase 자동변환
    }
}