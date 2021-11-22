package db.jooq.dao;

import db.jooq.entities.Organization;
import db.jooq.entities.Product;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.*;
import java.util.Comparator;

import static db.jooq.generated.Tables.*;
import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.sum;

// класс с отчетами
public class QueryManager {

    private final @NotNull Connection connection;
    final DSLContext context;

    final String FULL_QUANTITY = "FULL_QUANTITY";
    final String TOTAL_SUM = "TOTAL_SUM";
    final String AVG_PRICE = "AVG_PRICE";

    public QueryManager(@NotNull Connection connection) {
        this.connection = connection;
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    // Выбрать первые 10 поставщиков по количеству поставленного товара
    @NotNull
    public List<Organization> get10ByProductsQuantity() {
        var records = context.select(ORGANIZATION.ITN, ORGANIZATION.NAME, ORGANIZATION.PAYMENT_ACCOUNT)
                .from(ORGANIZATION).join(INVOICE).on(ORGANIZATION.ITN.eq(INVOICE.SENDER_ORG_ITN))
                .join(INVOICE_ITEM).on(INVOICE.ID.eq(INVOICE_ITEM.INVOICE_ID))
                .groupBy(ORGANIZATION.ITN).orderBy(sum(INVOICE_ITEM.QUANTITY).desc())
                .limit(10).fetch();
        return records.into(Organization.class);
    }

    // Выбрать поставщиков с суммой поставленного товара выше указанного количества
    // (товар и его количество должны допускать множественное указание).
    // Реализован вариант 'OR'
    @NotNull
    public List<Organization> getOrganisationsWithProductsOR(Map<Product, Integer> productMap) {
        ArrayList<Organization> result = new ArrayList<>();

        // making query
        SelectQuery<?> query= context.selectQuery();
        query.addSelect(ORGANIZATION.ITN, ORGANIZATION.NAME, ORGANIZATION.PAYMENT_ACCOUNT,
                INVOICE_ITEM.PRODUCT_CODE,
                sum(INVOICE_ITEM.QUANTITY).as(FULL_QUANTITY));
        query.addFrom(ORGANIZATION);
        query.addJoin(INVOICE,ORGANIZATION.ITN.eq(INVOICE.SENDER_ORG_ITN));
        query.addJoin(INVOICE_ITEM, INVOICE.ID.eq(INVOICE_ITEM.INVOICE_ID));

        // adding WHERE condition to query
        Condition condition = null;
        boolean first = true;
        for (var entry: productMap.entrySet()){
            if (first) {
                condition=INVOICE_ITEM.PRODUCT_CODE.eq(entry.getKey().getInternalCode());
                first = false;
            }
            else condition= condition.or(INVOICE_ITEM.PRODUCT_CODE.eq(entry.getKey().getInternalCode()));
        }
        query.addConditions(condition);
        query.addGroupBy(ORGANIZATION.ITN,INVOICE_ITEM.PRODUCT_CODE);
        var res = query.fetch();

        // finding suitable Organizations
        for (var record: res){
            Organization tempOrganization = record.into(ORGANIZATION).into(Organization.class);
            int productId = record.getValue(INVOICE_ITEM.PRODUCT_CODE);
            Product tempProduct =  productMap.keySet().stream().filter(product ->
                    productId == product.getInternalCode()).findFirst().orElse(null);
            int productFullQuantity = ((BigDecimal)record.getValue(FULL_QUANTITY)).intValue();
            if (productFullQuantity >= productMap.get(tempProduct) && !result.contains(tempOrganization))
                result.add(tempOrganization);
        }

        return result;
    }

    // За каждый день для каждого товара рассчитать количество и сумму полученного товара
    // в указанном периоде, посчитать итоги за период
    @NotNull
    public Map<Product,Map<Integer, Integer>> getQuantityAndSumByPeriod(Date start, Date end){
        Result<Record4<Integer,String, BigDecimal, BigDecimal>> records = context.select(PRODUCT.INTERNAL_CODE, PRODUCT.NAME,
                        sum(INVOICE_ITEM.QUANTITY).as(FULL_QUANTITY),
                        sum((INVOICE_ITEM.QUANTITY).multiply(INVOICE_ITEM.PRICE)).as(TOTAL_SUM))
                .from(PRODUCT)
                .join(INVOICE_ITEM).on(INVOICE_ITEM.PRODUCT_CODE.eq(PRODUCT.INTERNAL_CODE))
                .join(INVOICE).on(INVOICE.DATE.greaterOrEqual(start.toLocalDate())).and(INVOICE.DATE.lessOrEqual(end.toLocalDate()))
                .and(INVOICE.ID.eq(INVOICE_ITEM.INVOICE_ID))
                .groupBy(PRODUCT.INTERNAL_CODE).orderBy(PRODUCT.INTERNAL_CODE)
                .fetch();

        Map<Product,Map<Integer, Integer>> resultMap = new HashMap<>();
        for (var record: records){
            Integer quant = ((BigDecimal)record.getValue(FULL_QUANTITY)).intValue();
            Integer sum = ((BigDecimal)record.getValue(TOTAL_SUM)).intValue();
            Map<Integer, Integer> indicatorsMap = Map.of(quant, sum);
            resultMap.put(record.into(PRODUCT).into(Product.class), indicatorsMap);
        }

        return resultMap;
    }
    // Human-readable version
    public String getQuantityAndSumByPeriodHReadable(Date start, Date end){
        StringBuilder stringBuilder = new StringBuilder();
        getQuantityAndSumByPeriod(start, end).forEach(
                (product, indicators) -> {
                    String productResult = product + " total quantity = " + indicators.keySet().toArray()[0] +
                            " total cost = " + indicators.values().toArray()[0] + "\n";
                    stringBuilder.append(productResult);
                });
        return stringBuilder.toString();
    }

    // Рассчитать среднюю цену полученного товара за период
    public double getAvgPriceByPeriod(int innerCode, Date start, Date end){
        BigDecimal c;
        double result = 0;

        var resultRecord = context.select(avg(INVOICE_ITEM.PRICE).as(AVG_PRICE))
                .from(INVOICE)
                .join(INVOICE_ITEM).on(INVOICE_ITEM.INVOICE_ID.eq(INVOICE.ID))
                .where(INVOICE.DATE.greaterOrEqual(start.toLocalDate())).and(INVOICE.DATE.lessOrEqual(end.toLocalDate()))
                .and(INVOICE_ITEM.PRODUCT_CODE.eq(innerCode))
                .fetchOne();

        if (resultRecord != null) {
            c = ((BigDecimal)resultRecord.get(AVG_PRICE));
            if (c!= null)
                result = c.doubleValue();
        }
        return result;
    }

    // Вывести список товаров, поставленных организациями за период.
    // Если организация товары не поставляла, то она все равно должна быть отражена в списке.
    @NotNull
    public Map<Organization,List<Product>> getProductsAndOrgInPeriod(Date start, Date end) {
        Map<Organization,List<Product>> result = new TreeMap<>(Comparator.comparing(Organization::getItn));
        var records = context.select(
                        ORGANIZATION.ITN, ORGANIZATION.NAME, ORGANIZATION.PAYMENT_ACCOUNT,
                        PRODUCT.INTERNAL_CODE, PRODUCT.NAME)
                .from(ORGANIZATION)
                .leftOuterJoin(INVOICE).on(ORGANIZATION.ITN.eq(INVOICE.SENDER_ORG_ITN))
                .and(INVOICE.DATE.greaterOrEqual(start.toLocalDate())).and(INVOICE.DATE.lessOrEqual(end.toLocalDate()))
                .leftJoin(INVOICE_ITEM).on(INVOICE_ITEM.INVOICE_ID.eq(INVOICE.ID))
                .leftJoin(PRODUCT).on(INVOICE_ITEM.PRODUCT_CODE.eq(PRODUCT.INTERNAL_CODE))
                .orderBy(ORGANIZATION.ITN,  INVOICE_ITEM.PRODUCT_CODE)
                .fetchGroups(ORGANIZATION, PRODUCT);

        for (var entry: records.entrySet()){
            var productList = entry.getValue().into(Product.class);
            if (productList.size() == 1 && productList.get(0).getInternalCode() == 0) result.put(entry.getKey().into(Organization.class), null);
            else {
                productList.sort(Comparator.comparing(Product::getInternalCode));
                result.put(entry.getKey().into(Organization.class), productList);
            }
        }
        return result;
    }

    // Human-readable version
    @NotNull
    public String getProductsAndOrgInPeriodHReadable(Date start, Date end) {
        StringBuilder stringBuilder = new StringBuilder();
        getProductsAndOrgInPeriod(start, end).forEach(
                (organization, productList) -> {
                    String resultPart;
                    if (productList != null)
                        for (var product: productList){
                                resultPart = organization + " " + product + "\n";
                                stringBuilder.append(resultPart);
                        }
                    else {
                        resultPart = organization + " shipped no products during this period\n";
                        stringBuilder.append(resultPart);
                    }
                });
        return stringBuilder.toString();
    }
}
