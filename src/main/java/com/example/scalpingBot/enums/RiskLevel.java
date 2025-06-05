package com.example.scalpingBot.enums;

import lombok.Getter;
import java.math.BigDecimal;

/**
 * Перечисление уровней риска для скальпинг-бота
 *
 * Определяет степень риска для:
 * - Отдельных торговых операций
 * - Портфеля в целом
 * - Рыночных условий
 * - Автоматической корректировки параметров
 *
 * @author ScalpingBot Team
 * @version 1.0
 */
@Getter
public enum RiskLevel {

    /**
     * Очень низкий риск - консервативная торговля
     *
     * Условия применения:
     * - Стабильные рыночные условия
     * - Низкая волатильность (ATR < 2%)
     * - Высокая ликвидность
     * - Отсутствие важных новостей
     * - Стабильные корреляции между активами
     */
    VERY_LOW(
            "VERY_LOW",
            "Очень низкий риск",
            new BigDecimal("1.0"),   // максимум 1% капитала на позицию
            new BigDecimal("0.2"),   // стоп-лосс 0.2% (консервативнее обычного)
            new BigDecimal("0.4"),   // тейк-профит 0.4% (соотношение 1:2)
            3,                       // максимум 3 позиции одновременно
            new BigDecimal("0.5"),   // дневной лимит потерь 0.5%
            30,                      // анализ каждые 30 секунд
            new BigDecimal("2.0"),   // максимальная волатильность 2%
            "🟢",                    // зеленый
            "#00FF00",              // зеленый hex
            1                       // низший приоритет
    ),

    /**
     * Низкий риск - стандартная осторожная торговля
     *
     * Условия применения:
     * - Нормальные рыночные условия
     * - Умеренная волатильность (ATR 2-4%)
     * - Хорошая ликвидность
     * - Минимальное влияние новостей
     * - Стабильный тренд
     */
    LOW(
            "LOW",
            "Низкий риск",
            new BigDecimal("2.5"),   // максимум 2.5% капитала на позицию
            new BigDecimal("0.3"),   // стоп-лосс 0.3%
            new BigDecimal("0.6"),   // тейк-профит 0.6% (соотношение 1:2)
            5,                       // максимум 5 позиций одновременно
            new BigDecimal("1.0"),   // дневной лимит потерь 1%
            20,                      // анализ каждые 20 секунд
            new BigDecimal("4.0"),   // максимальная волатильность 4%
            "🟡",                    // желтый
            "#FFFF00",              // желтый hex
            2                       // низкий приоритет
    ),

    /**
     * Средний риск - стандартные параметры скальпинга
     *
     * Условия применения:
     * - Обычные рыночные условия
     * - Стандартная волатильность (ATR 4-6%)
     * - Достаточная ликвидность
     * - Обычная активность новостей
     * - Базовые настройки бота
     */
    MEDIUM(
            "MEDIUM",
            "Средний риск",
            new BigDecimal("5.0"),   // максимум 5% капитала на позицию (стандарт)
            new BigDecimal("0.4"),   // стоп-лосс 0.4% (стандартный параметр)
            new BigDecimal("0.8"),   // тейк-профит 0.8% (стандартный параметр)
            10,                      // максимум 10 позиций одновременно (стандарт)
            new BigDecimal("2.0"),   // дневной лимит потерь 2% (стандарт)
            15,                      // анализ каждые 15 секунд (стандарт)
            new BigDecimal("6.0"),   // максимальная волатильность 6%
            "🟠",                    // оранжевый
            "#FFA500",              // оранжевый hex
            3                       // средний приоритет
    ),

    /**
     * Высокий риск - агрессивная торговля
     *
     * Условия применения:
     * - Волатильные рыночные условия
     * - Высокая волатильность (ATR 6-10%)
     * - Возможны проскальзывания
     * - Активные новости и события
     * - Сильные трендовые движения
     */
    HIGH(
            "HIGH",
            "Высокий риск",
            new BigDecimal("7.5"),   // максимум 7.5% капитала на позицию
            new BigDecimal("0.6"),   // стоп-лосс 0.6% (шире для волатильности)
            new BigDecimal("1.2"),   // тейк-профит 1.2% (соотношение 1:2)
            15,                      // максимум 15 позиций одновременно
            new BigDecimal("3.0"),   // дневной лимит потерь 3%
            10,                      // анализ каждые 10 секунд (чаще)
            new BigDecimal("10.0"),  // максимальная волатильность 10%
            "🔶",                    // оранжевый ромб
            "#FF8000",              // темно-оранжевый hex
            4                       // высокий приоритет
    ),

    /**
     * Очень высокий риск - экстремальные условия
     *
     * Условия применения:
     * - Экстремальная волатильность (ATR > 10%)
     * - Кризисные рыночные условия
     * - Серьезные новости и события
     * - Низкая ликвидность
     * - Аварийный режим торговли
     */
    VERY_HIGH(
            "VERY_HIGH",
            "Очень высокий риск",
            new BigDecimal("10.0"),  // максимум 10% капитала на позицию
            new BigDecimal("1.0"),   // стоп-лосс 1.0% (широкий для экстремальной волатильности)
            new BigDecimal("2.0"),   // тейк-профит 2.0% (соотношение 1:2)
            20,                      // максимум 20 позиций одновременно
            new BigDecimal("5.0"),   // дневной лимит потерь 5%
            5,                       // анализ каждые 5 секунд (очень часто)
            new BigDecimal("20.0"),  // максимальная волатильность 20%
            "🔴",                    // красный
            "#FF0000",              // красный hex
            5                       // критический приоритет
    ),

    /**
     * Критический риск - автоматическая остановка торговли
     *
     * Условия применения:
     * - Системные сбои
     * - Аномальные рыночные условия
     * - Превышение всех лимитов риска
     * - Потеря связи с биржей
     * - Критические ошибки
     */
    CRITICAL(
            "CRITICAL",
            "Критический риск - торговля остановлена",
            new BigDecimal("0.0"),   // торговля запрещена
            new BigDecimal("0.0"),   // стоп-лоссы не размещаются
            new BigDecimal("0.0"),   // тейк-профиты не размещаются
            0,                       // позиции запрещены
            new BigDecimal("0.0"),   // дневной лимит превышен
            0,                       // анализ остановлен
            new BigDecimal("0.0"),   // любая волатильность критична
            "💀",                    // череп
            "#800000",              // темно-красный hex
            6                       // максимальный приоритет
    );

    /**
     * Код уровня риска
     */
    private final String code;

    /**
     * Человекочитаемое описание
     */
    private final String description;

    /**
     * Максимальный размер позиции в процентах от капитала
     */
    private final BigDecimal maxPositionSizePercent;

    /**
     * Стоп-лосс в процентах
     */
    private final BigDecimal stopLossPercent;

    /**
     * Тейк-профит в процентах
     */
    private final BigDecimal takeProfitPercent;

    /**
     * Максимальное количество одновременных позиций
     */
    private final int maxSimultaneousPositions;

    /**
     * Дневной лимит потерь в процентах
     */
    private final BigDecimal dailyLossLimitPercent;

    /**
     * Интервал анализа в секундах
     */
    private final int analysisIntervalSeconds;

    /**
     * Максимальная допустимая волатильность (ATR) в процентах
     */
    private final BigDecimal maxVolatilityPercent;

    /**
     * Эмодзи индикатор
     */
    private final String emoji;

    /**
     * Цветовой код
     */
    private final String colorCode;

    /**
     * Приоритет обработки (чем выше число, тем выше приоритет)
     */
    private final int priority;

    /**
     * Конструктор перечисления
     */
    RiskLevel(String code, String description, BigDecimal maxPositionSizePercent,
              BigDecimal stopLossPercent, BigDecimal takeProfitPercent,
              int maxSimultaneousPositions, BigDecimal dailyLossLimitPercent,
              int analysisIntervalSeconds, BigDecimal maxVolatilityPercent,
              String emoji, String colorCode, int priority) {
        this.code = code;
        this.description = description;
        this.maxPositionSizePercent = maxPositionSizePercent;
        this.stopLossPercent = stopLossPercent;
        this.takeProfitPercent = takeProfitPercent;
        this.maxSimultaneousPositions = maxSimultaneousPositions;
        this.dailyLossLimitPercent = dailyLossLimitPercent;
        this.analysisIntervalSeconds = analysisIntervalSeconds;
        this.maxVolatilityPercent = maxVolatilityPercent;
        this.emoji = emoji;
        this.colorCode = colorCode;
        this.priority = priority;
    }

    /**
     * Найти уровень риска по коду
     *
     * @param code код уровня риска
     * @return найденный уровень риска
     * @throws IllegalArgumentException если код не найден
     */
    public static RiskLevel fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Risk level code cannot be null or empty");
        }

        String normalizedCode = code.trim().toUpperCase();

        for (RiskLevel level : values()) {
            if (level.code.equals(normalizedCode)) {
                return level;
            }
        }

        throw new IllegalArgumentException("Unknown risk level code: " + code);
    }

    /**
     * Определить уровень риска на основе волатильности (ATR)
     *
     * @param atrPercent волатильность в процентах
     * @return соответствующий уровень риска
     */
    public static RiskLevel fromVolatility(BigDecimal atrPercent) {
        if (atrPercent == null || atrPercent.compareTo(BigDecimal.ZERO) < 0) {
            return MEDIUM; // по умолчанию
        }

        if (atrPercent.compareTo(new BigDecimal("2.0")) <= 0) {
            return VERY_LOW;
        } else if (atrPercent.compareTo(new BigDecimal("4.0")) <= 0) {
            return LOW;
        } else if (atrPercent.compareTo(new BigDecimal("6.0")) <= 0) {
            return MEDIUM;
        } else if (atrPercent.compareTo(new BigDecimal("10.0")) <= 0) {
            return HIGH;
        } else if (atrPercent.compareTo(new BigDecimal("20.0")) <= 0) {
            return VERY_HIGH;
        } else {
            return CRITICAL;
        }
    }

    /**
     * Определить уровень риска на основе текущих потерь
     *
     * @param currentLossPercent текущие потери в процентах
     * @param maxDailyLoss максимальный дневной лимит потерь
     * @return соответствующий уровень риска
     */
    public static RiskLevel fromCurrentLoss(BigDecimal currentLossPercent, BigDecimal maxDailyLoss) {
        if (currentLossPercent == null || maxDailyLoss == null) {
            return MEDIUM;
        }

        BigDecimal lossRatio = currentLossPercent.abs().divide(maxDailyLoss, 2, BigDecimal.ROUND_HALF_UP);

        if (lossRatio.compareTo(new BigDecimal("0.25")) <= 0) {
            return VERY_LOW;  // потери < 25% от лимита
        } else if (lossRatio.compareTo(new BigDecimal("0.5")) <= 0) {
            return LOW;       // потери < 50% от лимита
        } else if (lossRatio.compareTo(new BigDecimal("0.75")) <= 0) {
            return MEDIUM;    // потери < 75% от лимита
        } else if (lossRatio.compareTo(new BigDecimal("0.9")) <= 0) {
            return HIGH;      // потери < 90% от лимита
        } else if (lossRatio.compareTo(new BigDecimal("1.0")) < 0) {
            return VERY_HIGH; // потери близки к лимиту
        } else {
            return CRITICAL;  // лимит превышен
        }
    }

    /**
     * Проверить, разрешена ли торговля на данном уровне риска
     *
     * @return true если торговля разрешена
     */
    public boolean isTradingAllowed() {
        return this != CRITICAL;
    }

    /**
     * Проверить, требует ли уровень риска немедленного вмешательства
     *
     * @return true если требует вмешательства
     */
    public boolean requiresImmediateAction() {
        return this == VERY_HIGH || this == CRITICAL;
    }

    /**
     * Получить рекомендуемое действие для уровня риска
     *
     * @return рекомендуемое действие
     */
    public String getRecommendedAction() {
        switch (this) {
            case VERY_LOW:
                return "Можно увеличить агрессивность";
            case LOW:
                return "Стандартная торговля";
            case MEDIUM:
                return "Мониторинг рыночных условий";
            case HIGH:
                return "Снизить размеры позиций";
            case VERY_HIGH:
                return "Закрыть часть позиций";
            case CRITICAL:
                return "ОСТАНОВИТЬ ВСЮ ТОРГОВЛЮ";
            default:
                return "Мониторинг";
        }
    }

    /**
     * Получить соотношение риск/прибыль
     *
     * @return соотношение риск/прибыль
     */
    public BigDecimal getRiskRewardRatio() {
        if (stopLossPercent.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return takeProfitPercent.divide(stopLossPercent, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Получить максимальную экспозицию портфеля
     *
     * @return максимальная экспозиция в процентах
     */
    public BigDecimal getMaxPortfolioExposure() {
        return maxPositionSizePercent.multiply(new BigDecimal(maxSimultaneousPositions));
    }

    /**
     * Проверить совместимость с текущими рыночными условиями
     *
     * @param currentVolatility текущая волатильность
     * @param currentPositions текущее количество позиций
     * @return true если уровень риска подходит
     */
    public boolean isCompatibleWith(BigDecimal currentVolatility, int currentPositions) {
        if (this == CRITICAL) {
            return false;
        }

        return currentVolatility.compareTo(maxVolatilityPercent) <= 0 &&
                currentPositions <= maxSimultaneousPositions;
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