package Novoe.LoyaltySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

/**
 * Класс транзакций
 */
@Getter
@Setter
@Entity
@Table(name="t_transactions")
public class Transaction extends BaseEntity{

    /**
     * Время транзакции
     */
    @Column(name ="date")
    private Date date;

    /**
     * Компания от которой пришла транзакция
     */
    @OneToOne
    private Company company;

    /**
     * Карта клиента
     */
    @OneToOne
    private CardItem cardItem;

    /**
     * Исходный чек в формате json
     */
    @Column(name = "order_in")
    @JdbcTypeCode(SqlTypes.JSON)
    private String orderIn;

    /**
     * Результат обработки
     */
    @Column(name = "order_out")
    @JdbcTypeCode(SqlTypes.JSON)
    private String orderOut;

    /**
     * Если бонус начислялся,то int>0.
     * Еслс бонус списывался, то int<0.
     */
    @Column(name = "amount_bonus")
    private int amountBonus;
}
