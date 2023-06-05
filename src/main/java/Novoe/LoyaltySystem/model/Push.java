package Novoe.LoyaltySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * Класс пуш уведомлений
 */
@Getter
@Setter
@Entity
@Table(name="t_push")
public class Push extends BaseEntity{

    /**
     * Заголовок уведомления
     */
    @Column(name = "title")
    private String title;

    /**
     * Текст уведомления
     */
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    /**
     * Компания, в которой создано уведомление
     */
    @OneToOne
    private Company company;
}
