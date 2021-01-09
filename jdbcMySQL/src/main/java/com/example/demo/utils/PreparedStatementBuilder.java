package com.example.demo.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * В нашей моделе мы лишь используем поля типа Long, String, int, Double
 * то есть,мы можем сделать обёртку для PreparedStatement и реализовать паттерн Builder
 * (для удобства написания дальнейшего кода)
 * Также, по нашему бизнес сценарию name является обязательным полем, а остальные поля -- опциональные
 * в этой обёртке будет производится обработка Null, чтобы программа не падала с NPE
 */
public class PreparedStatementBuilder {
    private final PreparedStatement statement;

    public PreparedStatementBuilder setInt(int index, Integer value) throws SQLException {
        if(value == null)
            statement.setNull(index, Types.INTEGER);
        else
            statement.setInt(index,value);
        return this;
    }

    public PreparedStatementBuilder setDouble(int index, Double value) throws SQLException {
        if(value == null)
            statement.setNull(index, Types.DOUBLE);
        else
            statement.setDouble(index, value);
        return this;
    }

    public PreparedStatementBuilder setString(int index, String value) throws SQLException {
        if(value == null)
            statement.setNull(index, Types.VARCHAR);
        statement.setString(index, value);
        return this;
    }

    public PreparedStatement build(){
        return statement;
    }
    private PreparedStatementBuilder(PreparedStatement statement){
        this.statement = statement;
    }
    // Не синглтон!!! Просто замена конструктору для красоты
    public static PreparedStatementBuilder getInstance(PreparedStatement statement){
        return new PreparedStatementBuilder(statement);
    }
}
