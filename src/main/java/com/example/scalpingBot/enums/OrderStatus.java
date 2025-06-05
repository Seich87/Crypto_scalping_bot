package com.example.scalpingBot.enums;

import lombok.Getter;
import java.util.EnumSet;
import java.util.Set;

/**
 * Перечисление статусов торговых ордеров для скальпинг-бота
 *
 * Жизненный цикл ордера:
 * PENDING → SUBMITTED → PARTIALLY_FILLED → FILLED
 *              ↓           ↓                ↓
 *          REJECTED    CANCELLED        CANCELLED
 *              ↓           ↓                ↓
 *            FAILED      FAILED          FAILED
 *
 * @author ScalpingBot Team
 * @version 1.0
 */
@Getter
public enum OrderStatus {

    /**
     * Ордер создан в системе, но еще не отправлен на биржу
     *
     * Характеристики:
     * - Начальный статус всех ордеров
     * - Ордер проходит валидацию
     * - Возможна отмена без обращения к бирже
     * - Не влияет на баланс
     */
    PENDING(
            "PENDING",
            "Ожидает отправки",
            false,  // не активен на бирже
            true,   // может быть отменен
            false,  // не завершен
            false,  // не влияет на баланс
            "⏳",   // песочные часы
            0       // нет приоритета
    ),

    /**
     * Ордер отправлен на биржу и принят к исполнению
     *
     * Характеристики:
     * - Ордер находится в стакане заявок
     * - Ожидает исполнения по цене
     * - Может быть отменен через API биржи
     * - Резервирует средства на балансе
     */
    SUBMITTED(
            "SUBMITTED",
            "Отправлен на биржу",
            true,   // активен на бирже
            true,   // может быть отменен
            false,  // не завершен
            true,   // резервирует средства
            "📤",   // стрелка исходящая
            1       // низкий приоритет обработки
    ),

    /**
     * Ордер частично исполнен
     *
     * Характеристики:
     * - Часть объема уже исполнена
     * - Остальная часть остается в стакане
     * - Важно для крупных ордеров в скальпинге
     * - Частично влияет на баланс
     */
    PARTIALLY_FILLED(
            "PARTIALLY_FILLED",
            "Частично исполнен",
            true,   // остальная часть активна
            true,   // можно отменить остаток
            false,  // еще не завершен полностью
            true,   // частично влияет на баланс
            "🔄",   // стрелки обновления
            2       // средний приоритет
    ),

    /**
     * Ордер полностью исполнен
     *
     * Характеристики:
     * - Весь объем исполнен
     * - Финальный успешный статус
     * - Полностью влияет на баланс
     * - Генерирует торговую позицию
     */
    FILLED(
            "FILLED",
            "Исполнен",
            false,  // больше не активен
            false,  // нельзя отменить
            true,   // завершен успешно
            true,   // полностью влияет на баланс
            "✅",   // галочка
            3       // высокий приоритет обработки
    ),

    /**
     * Ордер отменен пользователем или системой
     *
     * Характеристики:
     * - Ордер снят с биржи
     * - Средства разблокированы
     * - Может быть отменен как вручную, так и автоматически
     * - При частичном исполнении - отменена оставшаяся часть
     */
    CANCELLED(
            "CANCELLED",
            "Отменен",
            false,  // больше не активен
            false,  // уже отменен
            true,   // завершен
            false,  // не влияет на баланс (или частично при partial fill)
            "❌",   // крестик
            2       // средний приоритет
    ),

    /**
     * Ордер отклонен биржей
     *
     * Характеристики:
     * - Биржа не приняла ордер
     * - Возможные причины: недостаток средств, неверные параметры
     * - Требует анализа причины отклонения
     * - Критично для скальпинг стратегии
     */
    REJECTED(
            "REJECTED",
            "Отклонен биржей",
            false,  // не был активен
            false,  // нельзя отменить
            true,   // завершен неуспешно
            false,  // не влияет на баланс
            "🚫",   // запрет
            4       // очень высокий приоритет для анализа
    ),

    /**
     * Ошибка при обработке ордера
     *
     * Характеристики:
     * - Системная ошибка
     * - Требует вмешательства
     * - Может потребовать ручной проверки баланса
     * - Критический статус для мониторинга
     */
    FAILED(
            "FAILED",
            "Ошибка обработки",
            false,  // не активен
            false,  // нельзя отменить
            true,   // завершен неуспешно
            false,  // статус баланса неопределен
            "💥",   // взрыв
            5       // критический приоритет
    ),

    /**
     * Ордер истек по времени
     *
     * Характеристики:
     * - Превышен таймаут ордера
     * - Автоматически отменен системой
     * - Важно для быстрых скальпинг операций
     * - Требует пересмотра тайминга стратегии
     */
    EXPIRED(
            "EXPIRED",
            "Истек по времени",
            false,  // больше не активен
            false,  // автоматически отменен
            true,   // завершен
            false,  // не влияет на баланс
            "⏰",   // часы
            3       // высокий приоритет для анализа
    );

    /**
     * Код статуса для API биржи
     */
    private final String code;

    /**
     * Человекочитаемое описание
     */
    private final String description;

    /**
     * Активен ли ордер на бирже
     */
    private final boolean activeOnExchange;

    /**
     * Может ли быть отменен
     */
    private final boolean cancellable;

    /**
     * Завершен ли ордер (финальный статус)
     */
    private final boolean terminal;

    /**
     * Влияет ли на баланс
     */
    private final boolean affectsBalance;

    /**
     * Эмодзи индикатор для логов и уведомлений
     */
    private final String emoji;

    /**
     * Приоритет обработки (чем выше число, тем выше приоритет)
     */
    private final int processingPriority;

    /**
     * Конструктор перечисления
     */
    OrderStatus(String code, String description, boolean activeOnExchange,
                boolean cancellable, boolean terminal, boolean affectsBalance,
                String emoji, int processingPriority) {
        this.code = code;
        this.description = description;
        this.activeOnExchange = activeOnExchange;
        this.cancellable = cancellable;
        this.terminal = terminal;
        this.affectsBalance = affectsBalance;
        this.emoji = emoji;
        this.processingPriority = processingPriority;
    }

    /**
     * Найти статус по коду
     *
     * @param code код статуса
     * @return найденный статус
     * @throws IllegalArgumentException если код не найден
     */
    public static OrderStatus fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Order status code cannot be null or empty");
        }

        String normalizedCode = code.trim().toUpperCase();

        for (OrderStatus status : values()) {
            if (status.code.equals(normalizedCode)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown order status code: " + code);
    }

    /**
     * Получить все активные статусы
     *
     * @return множество активных статусов
     */
    public static Set<OrderStatus> getActiveStatuses() {
        return EnumSet.of(SUBMITTED, PARTIALLY_FILLED);
    }

    /**
     * Получить все финальные статусы
     *
     * @return множество финальных статусов
     */
    public static Set<OrderStatus> getTerminalStatuses() {
        return EnumSet.of(FILLED, CANCELLED, REJECTED, FAILED, EXPIRED);
    }

    /**
     * Получить все успешные статусы
     *
     * @return множество успешных статусов
     */
    public static Set<OrderStatus> getSuccessfulStatuses() {
        return EnumSet.of(FILLED, PARTIALLY_FILLED);
    }

    /**
     * Получить все статусы ошибок
     *
     * @return множество статусов ошибок
     */
    public static Set<OrderStatus> getErrorStatuses() {
        return EnumSet.of(REJECTED, FAILED);
    }

    /**
     * Проверить, является ли статус успешным
     *
     * @return true если статус успешный
     */
    public boolean isSuccessful() {
        return this == FILLED || this == PARTIALLY_FILLED;
    }

    /**
     * Проверить, является ли статус ошибкой
     *
     * @return true если статус указывает на ошибку
     */
    public boolean isError() {
        return this == REJECTED || this == FAILED;
    }

    /**
     * Проверить, требует ли статус мониторинга
     *
     * @return true если требует отслеживания
     */
    public boolean requiresMonitoring() {
        return activeOnExchange || this == PENDING;
    }

    /**
     * Проверить, требует ли статус уведомления
     *
     * @return true если нужно отправить уведомление
     */
    public boolean requiresNotification() {
        return isError() || this == FILLED || this == EXPIRED;
    }

    /**
     * Получить следующие возможные статусы
     *
     * @return множество возможных следующих статусов
     */
    public Set<OrderStatus> getPossibleNextStatuses() {
        switch (this) {
            case PENDING:
                return EnumSet.of(SUBMITTED, CANCELLED, FAILED);
            case SUBMITTED:
                return EnumSet.of(PARTIALLY_FILLED, FILLED, CANCELLED, REJECTED, EXPIRED, FAILED);
            case PARTIALLY_FILLED:
                return EnumSet.of(FILLED, CANCELLED, FAILED);
            default:
                return EnumSet.noneOf(OrderStatus.class); // Финальные статусы
        }
    }

    /**
     * Проверить, можно ли изменить статус на указанный
     *
     * @param newStatus новый статус
     * @return true если переход возможен
     */
    public boolean canTransitionTo(OrderStatus newStatus) {
        return getPossibleNextStatuses().contains(newStatus);
    }

    /**
     * Получить цветовой код для UI
     *
     * @return hex код цвета
     */
    public String getColorCode() {
        switch (this) {
            case FILLED:
                return "#00FF00"; // зеленый
            case PARTIALLY_FILLED:
                return "#FFA500"; // оранжевый
            case CANCELLED:
                return "#808080"; // серый
            case REJECTED:
            case FAILED:
                return "#FF0000"; // красный
            case EXPIRED:
                return "#FF6600"; // темно-оранжевый
            case SUBMITTED:
                return "#0080FF"; // синий
            case PENDING:
            default:
                return "#FFFF00"; // желтый
        }
    }

    /**
     * Получить рекомендуемое действие для статуса
     *
     * @return рекомендуемое действие
     */
    public String getRecommendedAction() {
        switch (this) {
            case PENDING:
                return "Ожидание отправки на биржу";
            case SUBMITTED:
                return "Мониторинг исполнения";
            case PARTIALLY_FILLED:
                return "Отслеживание оставшейся части";
            case FILLED:
                return "Обновление позиции и баланса";
            case CANCELLED:
                return "Анализ причины отмены";
            case REJECTED:
                return "Проверка параметров ордера";
            case FAILED:
                return "Анализ системной ошибки";
            case EXPIRED:
                return "Пересмотр тайминга стратегии";
            default:
                return "Мониторинг";
        }
    }

    /**
     * Форматированное представление для логов
     *
     * @return строковое представление с эмодзи
     */
    public String toLogString() {
        return String.format("%s %s", emoji, description.toUpperCase());
    }

    @Override
    public String toString() {
        return description;
    }
}