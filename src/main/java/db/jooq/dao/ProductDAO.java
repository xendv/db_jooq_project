package db.jooq.dao;

import db.jooq.entities.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

import static db.jooq.generated.tables.Product.PRODUCT;

public class ProductDAO extends DAO<Product>{

    public ProductDAO(@NotNull Connection connection) {
        super(connection, PRODUCT, Product.class);
    }
}
