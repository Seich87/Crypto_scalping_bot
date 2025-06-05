package com.example.scalpingBot.enums;

import lombok.Getter;

/**
 * Перечисление сторон торговых ордеров для скальпинг-бота
 *
 * Определяет направление торговой операции:
 * - BUY - покупка (открытие длинной позиции)
 * - SELL - продажа (открытие короткой позиции или закрытие длинной)
 *
 * @author ScalpingBot Team
 * @version 1.0
 */
@Getter
public enum OrderSide {

    /**
     * Покупка - открытие длинной позиции
     *
     * Используется когда:
     * - Ожидается рост цены актива
     * - Технические индикаторы показывают восходящий тренд
     * - RSI в зоне перепроданности (<30)
     * - Цена пробивает верхнюю границу Bollinger Bands
     * - MACD показывает бычий сигнал
     * - Объемы растут при росте цены
     */
    BUY(
            "BUY",
            "Покупка",
            "Длинная позиция",
            true,   // увеличивает баланс базового актива
            false,  // уменьшает баланс котируемого актива
            "🟢"    // зеленый индикатор
    ),

    /**
     * Продажа - открытие короткой позиции или закрытие длинной
     *
     * Используется когда:
     * - Ожидается падение цены актива
     * - Технические индикаторы показывают нисходящий тренд
     * - RSI в зоне перекупленности (>70)
     * - Цена пробивает нижнюю границу Bollinger Bands
     * - MACD показывает медвежий сигнал
     * - Объемы растут при падении цены
     * - Необходимо закрыть прибыльную длинную позицию
     */
    SELL(
            "SELL",
            "Продажа",
            "Короткая позиция или закрытие длинной",
            false,  // уменьшает баланс базового актива
            true,   // увеличивает баланс котируемого актива
            "🔴"    // красный индикатор
    );

    /**
     * Код стороны ордера для API биржи
     */
    private final String code;

    /**
     * Человекочитаемое название
     */
    private final String displayName;

    /**
     * Подробное описание действия
     */
    private final String description;

    /**
     * Увеличивает ли баланс базового актива (например, BTC в паре BTCUSDT)
     */
    private final boolean increasesBaseAsset;

    /**
     * Увеличивает ли баланс котируемого актива (например, USDT в паре BTCUSDT)
     */
    private final boolean increasesQuoteAsset;

    /**
     * Визуальный индикатор для логов и уведомлений
     */
    private final String indicator;

    /**
     * Конструктор перечисления
     */
    OrderSide(String code, String displayName, String description,
              boolean increasesBaseAsset, boolean increasesQuoteAsset, String indicator) {
        this.code = code;
        this.displayName = displayName;
        this.description = description;
        this.increasesBaseAsset = increasesBaseAsset;
        this.increasesQuoteAsset = increasesQuoteAsset;
        this.indicator = indicator;
    }

    /**
     * Найти сторону ордера по коду
     *
     * @param code код стороны ордера
     * @return найденная сторона ордера
     * @throws IllegalArgumentException если код не найден
     */
    public static OrderSide fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Order side code cannot be null or empty");
        }

        String normalizedCode = code.trim().toUpperCase();

        for (OrderSide side : values()) {
            if (side.code.equals(normalizedCode)) {
                return side;
            }
        }

        throw new IllegalArgumentException("Unknown order side code: " + code);
    }

    /**
     * Получить противоположную сторону
     *
     * @return противоположная сторона ордера
     */
    public OrderSide getOpposite() {
        return this == BUY ? SELL : BUY;
    }

    /**
     * Проверить, является ли сторона покупкой
     *
     * @return true если это покупка
     */
    public boolean isBuy() {
        return this == BUY;
    }

    /**
     * Проверить, является ли сторона продажей
     *
     * @return true если это продажа
     */
    public boolean isSell() {
        return this == SELL;
    }

    /**
     * Получить направление для стоп-лосса
     * Для длинной позиции (BUY) стоп-лосс должен быть SELL
     * Для короткой позиции (SELL) стоп-лосс должен быть BUY
     *
     * @return сторона для стоп-лосса
     */
    public OrderSide getStopLossSide() {
        return getOpposite();
    }

    /**
     * Получить направление для тейк-профита
     * Аналогично стоп-лоссу - противоположная сторона
     *
     * @return сторона для тейк-профита
     */
    public OrderSide getTakeProfitSide() {
        return getOpposite();
    }

    /**
     * Получить множитель для расчета P&L
     * BUY: +1 (прибыль при росте цены)
     * SELL: -1 (прибыль при падении цены)
     *
     * @return множитель для расчета прибыли/убытка
     */
    public int getPnlMultiplier() {
        return this == BUY ? 1 : -1;
    }

    /**
     * Получить рекомендуемую позицию в стакане заявок
     * BUY: покупаем по ask (верхний уровень стакана)
     * SELL: продаем по bid (нижний уровень стакана)
     *
     * @return рекомендуемая позиция в стакане
     */
    public String getRecommendedBookSide() {
        return this == BUY ? "ASK" : "BID";
    }

    /**
     * Получить цветовой код для UI и уведомлений
     *
     * @return hex код цвета
     */
    public String getColorCode() {
        return this == BUY ? "#00FF00" : "#FF0000"; // зеленый / красный
    }

    /**
     * Проверить совместимость с типом ордера для скальпинга
     *
     * @param orderType тип ордера
     * @return true если комбинация подходит для скальпинга
     */
    public boolean isCompatibleForScalping(OrderType orderType) {
        switch (orderType) {
            case MARKET:
            case LIMIT:
                return true; // Любая сторона подходит

            case STOP_LOSS:
                return true; // Стоп-лосс может быть в любую сторону

            case TAKE_PROFIT:
                return true; // Тейк-профит может быть в любую сторону

            case OCO:
                return true; // OCO содержит оба направления

            default:
                return false;
        }
    }

    /**
     * Получить рекомендуемый размер позиции как процент от доступного баланса
     *
     * @param maxPositionSizePercent максимальный размер позиции в процентах
     * @return рекомендуемый размер позиции
     */
    public double getRecommendedPositionSize(double maxPositionSizePercent) {
        // Для скальпинга используем максимальный разрешенный размер
        // так как держим позиции очень короткое время (до 1 часа)
        return Math.min(maxPositionSizePercent, 5.0); // максимум 5% как в риск-менеджменте
    }

    /**
     * Получить описание действия с учетом актива
     *
     * @param tradingPair торговая пара (например, BTCUSDT)
     * @return описание действия
     */
    public String getActionDescription(String tradingPair) {
        if (tradingPair == null || tradingPair.isEmpty()) {
            return displayName;
        }

        // Извлекаем базовый актив (например, BTC из BTCUSDT)
        String baseAsset = tradingPair.replaceAll("USDT$|BUSD$|USDC$", "");

        return this == BUY
                ? String.format("Покупка %s", baseAsset)
                : String.format("Продажа %s", baseAsset);
    }

    /**
     * Получить эмодзи индикатор для логов
     *
     * @return эмодзи индикатор
     */
    public String getEmoji() {
        return indicator;
    }

    /**
     * Форматированное представление для логов
     *
     * @return строковое представление с индикатором
     */
    public String toLogString() {
        return String.format("%s %s", indicator, displayName.toUpperCase());
    }

    @Override
    public String toString() {
        return displayName;
    }
}